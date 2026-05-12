package com.carlog.backend.dto;

import com.carlog.backend.service.CarCatalogService;

import java.util.List;

public record NhtsaMakeResponse(List<NhtsaMakeResult> Results) {
}
