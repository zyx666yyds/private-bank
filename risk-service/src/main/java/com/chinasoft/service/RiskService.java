package com.chinasoft.service;

import com.chinasoft.model.dto.RiskAssessmentDTO;

import java.util.List;

public interface RiskService {

    RiskAssessmentDTO submitRiskAssessment(RiskAssessmentDTO riskAssessmentdto);

    RiskAssessmentDTO getLatestAssessment(String clientId);

    List<RiskAssessmentDTO> getAssessmentHistory(String clientId, int months);
}
