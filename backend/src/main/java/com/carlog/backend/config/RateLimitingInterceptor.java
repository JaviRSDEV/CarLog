package com.carlog.backend.config;

import com.carlog.backend.error.RateLimitExceededException;
import com.carlog.backend.service.RateLimitingService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class RateLimitingInterceptor implements HandlerInterceptor {

    private final RateLimitingService rateLimitingService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String ip = request.getRemoteAddr();
        if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }

        Bucket bucket = rateLimitingService.resolveBucket(ip);

        if(bucket.tryConsume(1)){
            return  true;
        }else{
            throw new RateLimitExceededException("Demasiados intentonces. Por favor espera 1 minuto");
        }
    }
}
