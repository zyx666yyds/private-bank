package com.wealth.portfolioservice.controller;

import com.wealth.portfolioservice.dto.*;
import com.wealth.portfolioservice.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description:投资组合控制器，提供REST API接口
 */
@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {
    
    @Autowired
    private PortfolioService portfolioService;
    
    @PostMapping    // 创建投资组合
    public ResponseEntity<PortfolioDTO> createPortfolio(@RequestBody PortfolioDraftDTO draftDTO) {
        PortfolioDTO portfolioDTO = portfolioService.createDraftPortfolio(draftDTO);
        return new ResponseEntity<>(portfolioDTO, HttpStatus.CREATED);
    }
    
    @GetMapping("/{portfolioId}")      //获取投资组合详情
    public ResponseEntity<PortfolioDTO> getPortfolio(@PathVariable String portfolioId) {
        PortfolioDTO portfolioDTO = portfolioService.getPortfolio(portfolioId);
        return new ResponseEntity<>(portfolioDTO, HttpStatus.OK);
    }
    
    @GetMapping("/client/{clientId}")      // 获取客户所有投资组合
    public ResponseEntity<List<PortfolioDTO>> getPortfoliosByClient(@PathVariable String clientId) {
        List<PortfolioDTO> portfolios = portfolioService.getPortfoliosByClient(clientId);
        return new ResponseEntity<>(portfolios, HttpStatus.OK);
    }
    
    @PostMapping("/{portfolioId}/items")        // 添加投资组合项
    public ResponseEntity<PortfolioDTO> addItem(@PathVariable String portfolioId, 
                                              @RequestBody PortfolioItemDTO itemDTO) {
        PortfolioDTO portfolioDTO = portfolioService.addItem(portfolioId, itemDTO);
        return new ResponseEntity<>(portfolioDTO, HttpStatus.OK);
    }
    
    @PutMapping("/{portfolioId}/items/{itemId}")       // 更新投资组合项
    public ResponseEntity<PortfolioDTO> updateItem(@PathVariable String portfolioId, 
                                                 @PathVariable String itemId, 
                                                 @RequestBody PortfolioItemDTO itemDTO) {
        PortfolioDTO portfolioDTO = portfolioService.updateItem(portfolioId, itemId, itemDTO);
        return new ResponseEntity<>(portfolioDTO, HttpStatus.OK);
    }
    
    @DeleteMapping("/{portfolioId}/items/{itemId}")        // 删除投资组合项
    public ResponseEntity<Void> deleteItem(@PathVariable String portfolioId, 
                                         @PathVariable String itemId) {
        portfolioService.deleteItem(portfolioId, itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/{portfolioId}/rebalance")    // 生成再平衡计划
    public ResponseEntity<RebalancePlanDTO> generateRebalancePlan(@PathVariable String portfolioId,
                                                                  @RequestBody @Valid RebalanceRequestDTO requestDTO) {
        requestDTO.setPortfolioId(portfolioId);
        RebalancePlanDTO plan = portfolioService.generateRebalancePlan(requestDTO);
        return new ResponseEntity<>(plan, HttpStatus.OK);
    }
    // 生成再平衡计划
    @PostMapping("/{portfolioId}/execute-rebalance")      // 执行再平衡
    public ResponseEntity<PortfolioDTO> executeRebalance(@PathVariable String portfolioId,
                                                       @RequestBody RebalancePlanDTO planDTO) {
        PortfolioDTO portfolioDTO = portfolioService.executeRebalance(portfolioId, planDTO);
        return new ResponseEntity<>(portfolioDTO, HttpStatus.OK);
    }
    
    @GetMapping("/{portfolioId}/ratio")      // 获取投资组合比例
    public ResponseEntity<PortfolioRatioDTO> getPortfolioRatio(@PathVariable String portfolioId) {
        PortfolioRatioDTO ratio = portfolioService.calculatePortfolioRatio(portfolioId);
        return new ResponseEntity<>(ratio, HttpStatus.OK);
    }
    
    @GetMapping("/{portfolioId}/profit-loss")       // 获取投资组合盈亏
    public ResponseEntity<ProfitLossDTO> getProfitLoss(@PathVariable String portfolioId) {
        ProfitLossDTO profitLoss = portfolioService.calculateProfitLoss(portfolioId);
        return new ResponseEntity<>(profitLoss, HttpStatus.OK);
    }
}    