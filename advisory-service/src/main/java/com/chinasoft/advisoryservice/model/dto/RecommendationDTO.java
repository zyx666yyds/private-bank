package com.chinasoft.advisoryservice.model.dto;

import com.chinasoft.advisoryservice.model.entity.RecommendationItem;
import lombok.Data;

import java.util.List;

@Data
public class RecommendationDTO {
    private String clientId;
    private String advisorId;
    private List<RecommendationItem> items;
}
