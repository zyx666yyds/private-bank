package com.chinasoft.controller;

import com.chinasoft.bankcommon.common.BaseResponse;
import com.chinasoft.bankcommon.common.ResultUtils;
import com.chinasoft.model.dto.RiskAssessmentDTO;
import com.chinasoft.service.RiskService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class RiskController {

    @Autowired
    public RiskService riskService;



    // 提交风险评估
    @PostMapping("/submitRiskAssessment")
    public BaseResponse<RiskAssessmentDTO> submitRiskAssessment(@RequestBody RiskAssessmentDTO riskAssessmentdto) {
        log.info("收到请求: {}", riskAssessmentdto);  // 添加日志
        RiskAssessmentDTO result= riskService.submitRiskAssessment(riskAssessmentdto);
        return ResultUtils.success(result);
    }

    // 获取指定客户最新评估结果
    @GetMapping("/getLatestAssessment/{clientId}")
    public BaseResponse<RiskAssessmentDTO>getLatestAssessment(@PathVariable String clientId){
        RiskAssessmentDTO result=riskService.getLatestAssessment(clientId);
        return ResultUtils.success(result);
    }

    //获取指定客户的指定时间的历史评估结果
    @GetMapping("/getAssessmentHistory/{clientId}")
    public BaseResponse<List<RiskAssessmentDTO>> getAssessmentHistory(@PathVariable String clientId,@RequestParam(defaultValue = "12") int months){
        List<RiskAssessmentDTO> result=riskService.getAssessmentHistory(clientId,months);
        return ResultUtils.success(result);
    }

}
