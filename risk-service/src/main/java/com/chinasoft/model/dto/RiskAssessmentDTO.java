package com.chinasoft.model.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class RiskAssessmentDTO {
    @Size(max=18, message="客户编号长度不能超过18")
    private String clientId;
    private String evaluatorId;
    private Integer score;
    private String resultLevel;
    private String remarks;

    // 用于响应
    private String riskId;
    private LocalDateTime createdAt;
}
