package com.wealth.portfolioservice.service;
import com.wealth.portfolioservice.dto.*;
import java.util.List;
/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description:投资组合服务接口,定义投资组合管理的核心业务逻辑方法
 */
public interface PortfolioService {
    PortfolioDTO createPortfolio(String clientId, String name);  // 创建投资组合
    PortfolioDTO getPortfolio(String portfolioId);  // 获取投资组合详情
    List<PortfolioDTO> getPortfoliosByClient(String clientId);  // 获取客户所有投资组合
    PortfolioDTO addItem(String portfolioId, PortfolioItemDTO itemDTO);  // 添加投资组合项
    PortfolioDTO updateItem(String portfolioId, String itemId, PortfolioItemDTO itemDTO);  // 更新投资组合项
    void deleteItem(String portfolioId, String itemId);  // 删除投资组合项
    PortfolioDTO calculatePortfolioValue(String portfolioId);  // 计算投资组合价值
    PortfolioRatioDTO calculatePortfolioRatio(String portfolioId);  // 计算投资组合比例
    ProfitLossDTO calculateProfitLoss(String portfolioId);  // 计算投资组合盈亏
    // 投资组合优化相关方法
    PortfolioDTO createDraftPortfolio(PortfolioDraftDTO draftDTO);  // 创建投资组合草案
    RebalancePlanDTO generateRebalancePlan(RebalanceRequestDTO requestDTO);  // 生成再平衡计划
    PortfolioDTO executeRebalance(String portfolioId, RebalancePlanDTO planDTO);  // 执行再平衡
}