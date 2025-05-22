package com.chinasoft.service.impl;

import com.chinasoft.model.domain.RiskAssessment;
import com.chinasoft.model.dto.RiskAssessmentDTO;
import com.chinasoft.repository.RiskRepository;
import com.chinasoft.service.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RiskServiceImpl implements RiskService {



    @Autowired
    public RiskRepository riskRepository;


    @Override
    public RiskAssessmentDTO submitRiskAssessment(RiskAssessmentDTO riskAssessmentdto) {

        // 生成risk_id
        String riskId = "RISK-"+ LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)+"-"+riskAssessmentdto.getClientId();

        // 创建风险评估表实体
        RiskAssessment riskAssessment = new RiskAssessment();
        riskAssessment.setRiskId(riskId);
        riskAssessment.setClientId(riskAssessmentdto.getClientId());
        riskAssessment.setEvaluatorId(riskAssessmentdto.getEvaluatorId());
        riskAssessment.setScore(riskAssessmentdto.getScore());
        riskAssessment.setResultLevel(riskAssessmentdto.getResultLevel());
        riskAssessment.setUpdatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        riskAssessment.setRemarks(riskAssessmentdto.getRemarks());

        riskRepository.save(riskAssessment);

        //todo 更新客户风险等级


        //todo 更新DTO对象中的risk_id和创建时间
        riskAssessmentdto.setRiskId(riskId);
        riskAssessmentdto.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));


        return riskAssessmentdto;

    }

    @Override
    public RiskAssessmentDTO getLatestAssessment(String clientId) {
        RiskAssessment riskAssessment= riskRepository.findTopByClientIdOrderByCreatedAtDesc(clientId);
        //todo 返回最新的评估结果
        return this.convertToDTO(riskAssessment);
    }

    @Override
    public List<RiskAssessmentDTO> getAssessmentHistory(String clientId, int months) {
        //todo 返回指定客户在最近几个月的评估历史记录
        LocalDateTime dateTime = LocalDateTime.now().minusMonths(months); // 计算截止日期
        List<RiskAssessment> riskAssessments = riskRepository.findByClientIdAndCreatedAtAfter(clientId, dateTime);
        List<RiskAssessmentDTO> dtoList = riskAssessments
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return dtoList;
    }

    public RiskAssessmentDTO convertToDTO(RiskAssessment risk){
        RiskAssessmentDTO riskAssessmentdto = new RiskAssessmentDTO();
        riskAssessmentdto.setRiskId(risk.getRiskId());
        riskAssessmentdto.setClientId(risk.getClientId());
        riskAssessmentdto.setEvaluatorId(risk.getEvaluatorId());
        riskAssessmentdto.setScore(risk.getScore());
        riskAssessmentdto.setResultLevel(risk.getResultLevel());
        riskAssessmentdto.setCreatedAt(risk.getCreatedAt());
        riskAssessmentdto.setRemarks(risk.getRemarks());
        return riskAssessmentdto;
    }

}
