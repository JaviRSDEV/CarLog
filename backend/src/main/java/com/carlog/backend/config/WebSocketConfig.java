package com.carlog.backend.config;

import com.carlog.backend.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${URL_CORS}")
    private String urlCors;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws-carlog")
                .setAllowedOriginPatterns(urlCors);
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration){
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel){
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if(accessor != null && StompCommand.SUBSCRIBE.equals(accessor.getCommand())){
                    String destination = accessor.getDestination();
                    Principal principal = accessor.getUser();

                    if(principal instanceof UsernamePasswordAuthenticationToken auth && auth.getPrincipal() instanceof User user){
                        String userDni = user.getDni();

                        if(destination != null && destination.startsWith("/topic/notificaciones/")){
                            String requestedDni = destination.substring("/topic/notificaciones/".length());
                            if(!userDni.equals(requestedDni)){
                                throw new IllegalArgumentException("Acceso denegado: Suscripción a canal no autorizado");
                            }
                        }
                    }else {
                        throw new IllegalArgumentException("Usuario no autenticado en la conexión STOMP.");
                    }
                }
                return message;
            }
        });
    }
}
