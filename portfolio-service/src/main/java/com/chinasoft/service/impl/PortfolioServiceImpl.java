package com.wealth.portfolioservice.service.impl;
import com.wealth.portfolioservice.dto.*;
import com.wealth.portfolioservice.entity.Portfolio;
import com.wealth.portfolioservice.entity.PortfolioItem;
import com.wealth.portfolioservice.repository.PortfolioItemRepository;
import com.wealth.portfolioservice.repository.PortfolioRepository;
import com.wealth.portfolioservice.service.PortfolioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description: 投资组合服务实现类，实现投资组合管理的具体业务逻辑
 */
@Service
@Transactional
public class PortfolioServiceImpl implements PortfolioService {
    
    @Autowired
    private PortfolioRepository portfolioRepository;
    
    @Autowired
    private PortfolioItemRepository portfolioItemRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public PortfolioDTO createPortfolio(String clientId, String name) {
        Portfolio portfolio = new Portfolio();
        portfolio.setPortfolioId(generatePortfolioId());
        portfolio.setClientId(clientId);
        portfolio.setName(name);
        portfolio.setTotalValue(BigDecimal.ZERO);
        
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        return modelMapper.map(savedPortfolio, PortfolioDTO.class);
    }
    
    @Override
    public PortfolioDTO getPortfolio(String portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("投资组合不存在"));
        
        PortfolioDTO portfolioDTO = modelMapper.map(portfolio, PortfolioDTO.class);
        List<PortfolioItem> items = portfolioItemRepository.findByPortfolioId(portfolioId);
        
        List<PortfolioItemDTO> itemDTOs = items.stream()
                .map(item -> {
                    PortfolioItemDTO itemDTO = modelMapper.map(item, PortfolioItemDTO.class);
                    itemDTO.setMarketValue(item.getAmount().multiply(item.getUnitValue()));
                    return itemDTO;
                })
                .collect(Collectors.toList());
        
        portfolioDTO.setItems(itemDTOs);
        portfolioDTO.setRatio(calculatePortfolioRatio(portfolioId));
        portfolioDTO.setProfitLoss(calculateProfitLoss(portfolioId));
        
        return portfolioDTO;
    }
    
    @Override
    public List<PortfolioDTO> getPortfoliosByClient(String clientId) {
        List<Portfolio> portfolios = portfolioRepository.findByClientId(clientId);
        
        return portfolios.stream()
                .map(portfolio -> {
                    PortfolioDTO dto = modelMapper.map(portfolio, PortfolioDTO.class);
                    dto.setItems(portfolioItemRepository.findByPortfolioId(portfolio.getPortfolioId()).stream()
                            .map(item -> {
                                PortfolioItemDTO itemDTO = modelMapper.map(item, PortfolioItemDTO.class);
                                itemDTO.setMarketValue(item.getAmount().multiply(item.getUnitValue()));
                                return itemDTO;
                            })
                            .collect(Collectors.toList()));
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public PortfolioDTO addItem(String portfolioId, PortfolioItemDTO itemDTO) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("投资组合不存在"));
        
        PortfolioItem item = modelMapper.map(itemDTO, PortfolioItem.class);
        item.setItemId(UUID.randomUUID().toString().replace("-", ""));
        item.setPortfolioId(portfolioId);
        
        portfolioItemRepository.save(item);
        return calculatePortfolioValue(portfolioId);
    }
    
    @Override
    public PortfolioDTO updateItem(String portfolioId, String itemId, PortfolioItemDTO itemDTO) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("投资组合不存在"));
        
        PortfolioItem item = portfolioItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("投资组合项不存在"));
        
        if (!item.getPortfolioId().equals(portfolioId)) {
            throw new RuntimeException("投资组合项与投资组合不匹配");
        }
        
        item.setAmount(itemDTO.getAmount());
        item.setUnitValue(itemDTO.getUnitValue());
        item.setProductCode(itemDTO.getProductCode());
        item.setProductName(itemDTO.getProductName());
        item.setType(itemDTO.getType());
        
        portfolioItemRepository.save(item);
        return calculatePortfolioValue(portfolioId);
    }
    
    @Override
    public void deleteItem(String portfolioId, String itemId) {
        PortfolioItem item = portfolioItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("投资组合项不存在"));
        
        if (!item.getPortfolioId().equals(portfolioId)) {
            throw new RuntimeException("投资组合项与投资组合不匹配");
        }
        
        portfolioItemRepository.delete(item);
        calculatePortfolioValue(portfolioId);
    }
    
    @Override
    public PortfolioDTO calculatePortfolioValue(String portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("投资组合不存在"));
        
        List<PortfolioItem> items = portfolioItemRepository.findByPortfolioId(portfolioId);
        BigDecimal totalValue = items.stream()
                .map(item -> item.getAmount().multiply(item.getUnitValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        portfolio.setTotalValue(totalValue);
        portfolio.setUpdatedAt(new Date());
        portfolioRepository.save(portfolio);
        
        PortfolioDTO portfolioDTO = modelMapper.map(portfolio, PortfolioDTO.class);
        List<PortfolioItemDTO> itemDTOs = items.stream()
                .map(item -> {
                    PortfolioItemDTO itemDTO = modelMapper.map(item, PortfolioItemDTO.class);
                    BigDecimal marketValue = item.getAmount().multiply(item.getUnitValue());
                    itemDTO.setMarketValue(marketValue);
                    itemDTO.setProportion(marketValue.divide(totalValue, 4, RoundingMode.HALF_UP));
                    return itemDTO;
                })
                .collect(Collectors.toList());
        
        portfolioDTO.setItems(itemDTOs);
        portfolioDTO.setRatio(calculatePortfolioRatio(portfolioId));
        portfolioDTO.setProfitLoss(calculateProfitLoss(portfolioId));
        
        return portfolioDTO;
    }
    
    @Override
    public PortfolioRatioDTO calculatePortfolioRatio(String portfolioId) {
        List<PortfolioItem> items = portfolioItemRepository.findByPortfolioId(portfolioId);
        if (items.isEmpty()) {
            return new PortfolioRatioDTO();
        }
        
        Map<String, BigDecimal> typeRatio = new HashMap<>();
        Map<String, BigDecimal> topProducts = new LinkedHashMap<>();
        
        BigDecimal totalValue = items.stream()
                .map(item -> item.getAmount().multiply(item.getUnitValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // 计算各类资产占比
        for (PortfolioItem item : items) {
            BigDecimal marketValue = item.getAmount().multiply(item.getUnitValue());
            String type = item.getType();
            
            typeRatio.put(type, typeRatio.getOrDefault(type, BigDecimal.ZERO)
                    .add(marketValue.divide(totalValue, 4, RoundingMode.HALF_UP)));
        }
        
        // 找出占比最高的几项产品
        List<PortfolioItem> sortedItems = new ArrayList<>(items);
        sortedItems.sort((i1, i2) -> i2.getAmount().multiply(i2.getUnitValue())
                .compareTo(i1.getAmount().multiply(i1.getUnitValue())));
        
        for (int i = 0; i < Math.min(5, sortedItems.size()); i++) {
            PortfolioItem item = sortedItems.get(i);
            BigDecimal marketValue = item.getAmount().multiply(item.getUnitValue());
            topProducts.put(item.getProductName(), marketValue.divide(totalValue, 4, RoundingMode.HALF_UP));
        }
        
        PortfolioRatioDTO ratioDTO = new PortfolioRatioDTO();
        ratioDTO.setTypeRatio(typeRatio);
        ratioDTO.setTopProducts(topProducts);
        
        return ratioDTO;
    }
    
    @Override
    public ProfitLossDTO calculateProfitLoss(String portfolioId) {
        // 实际项目中需要根据历史数据计算盈亏
        // 这里简化处理，返回模拟数据
        ProfitLossDTO profitLoss = new ProfitLossDTO();
        
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("投资组合不存在"));
        
        List<PortfolioItem> items = portfolioItemRepository.findByPortfolioId(portfolioId);
        
        // 模拟计算总盈亏
        BigDecimal totalProfit = new BigDecimal(Math.random() * 10000).setScale(2, RoundingMode.HALF_UP);
        // 模拟计算收益率
        BigDecimal profitRate = totalProfit.divide(portfolio.getTotalValue(), 4, RoundingMode.HALF_UP);
        // 模拟计算日盈亏
        BigDecimal dailyProfit = new BigDecimal(Math.random() * 100).setScale(2, RoundingMode.HALF_UP);
        // 模拟计算日收益率
        BigDecimal dailyProfitRate = dailyProfit.divide(portfolio.getTotalValue(), 6, RoundingMode.HALF_UP);
        
        profitLoss.setTotalProfit(totalProfit);
        profitLoss.setProfitRate(profitRate);
        profitLoss.setDailyProfit(dailyProfit);
        profitLoss.setDailyProfitRate(dailyProfitRate);
        
        return profitLoss;
    }
    
    @Override
    public PortfolioDTO createDraftPortfolio(PortfolioDraftDTO draftDTO) {
        // 实际项目中可能需要调用风险评估服务等
        Portfolio portfolio = new Portfolio();
        portfolio.setPortfolioId(generatePortfolioId());
        portfolio.setClientId(draftDTO.getClientId());
        portfolio.setName(draftDTO.getName());
        portfolio.setTotalValue(BigDecimal.ZERO);
        
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        
        // 保存推荐的投资组合项
        if (draftDTO.getRecommendedItems() != null && !draftDTO.getRecommendedItems().isEmpty()) {
            for (PortfolioItemDTO itemDTO : draftDTO.getRecommendedItems()) {
                PortfolioItem item = modelMapper.map(itemDTO, PortfolioItem.class);
                item.setItemId(UUID.randomUUID().toString().replace("-", ""));
                item.setPortfolioId(savedPortfolio.getPortfolioId());
                portfolioItemRepository.save(item);
            }
        }
        
        return calculatePortfolioValue(savedPortfolio.getPortfolioId());
    }
    
    @Override
    public RebalancePlanDTO generateRebalancePlan(RebalanceRequestDTO requestDTO) {
        Portfolio portfolio = portfolioRepository.findById(requestDTO.getPortfolioId())
                .orElseThrow(() -> new RuntimeException("投资组合不存在"));
        
        List<PortfolioItem> currentItems = portfolioItemRepository.findByPortfolioId(requestDTO.getPortfolioId());
        RebalancePlanDTO plan = new RebalancePlanDTO();
        
        plan.setPortfolioId(portfolio.getPortfolioId());
        plan.setClientId(portfolio.getClientId());
        plan.setCurrentTotalValue(portfolio.getTotalValue());
        plan.setTargetRiskLevel(requestDTO.getTargetRiskLevel());
        
        // 模拟再平衡计算逻辑
        List<RebalanceActionDTO> actions = new ArrayList<>();
        AtomicReference<BigDecimal> estimatedFees = new AtomicReference<>(BigDecimal.ZERO);
        
        // 计算当前各类资产的比例
        Map<String, BigDecimal> currentTypeAmount = new HashMap<>();
        for (PortfolioItem item : currentItems) {
            BigDecimal marketValue = item.getAmount().multiply(item.getUnitValue());
            currentTypeAmount.put(item.getType(), currentTypeAmount.getOrDefault(item.getType(), BigDecimal.ZERO).add(marketValue));
        }
        
        // 根据目标比例计算调整方案
        for (RebalanceConditionDTO condition : requestDTO.getConditions()) {
            String type = condition.getType();
            BigDecimal targetPercentage = new BigDecimal(condition.getTargetPercentage() / 100);
            BigDecimal tolerance = new BigDecimal(condition.getTolerance() / 100);
            
            // 当前该类资产的总价值
            BigDecimal currentAmount = currentTypeAmount.getOrDefault(type, BigDecimal.ZERO);
            // 目标价值
            BigDecimal targetAmount = portfolio.getTotalValue().multiply(targetPercentage);
            
            // 判断是否需要调整
            if (currentAmount.subtract(targetAmount).abs()
                    .divide(portfolio.getTotalValue(), 4, RoundingMode.HALF_UP)
                    .compareTo(tolerance) > 0) {
                
                // 获取该类资产的所有项
                List<PortfolioItem> typeItems = currentItems.stream()
                        .filter(item -> item.getType().equals(type))
                        .collect(Collectors.toList());
                
                if (!typeItems.isEmpty()) {
                    // 选择市值最大的一项进行调整
                    PortfolioItem itemToAdjust = typeItems.stream()
                            .max(Comparator.comparing(i -> i.getAmount().multiply(i.getUnitValue())))
                            .orElse(typeItems.get(0));
                    
                    BigDecimal currentItemValue = itemToAdjust.getAmount().multiply(itemToAdjust.getUnitValue());
                    BigDecimal adjustAmount = targetAmount.subtract(currentAmount);
                    
                    RebalanceActionDTO action = new RebalanceActionDTO();
                    action.setProductCode(itemToAdjust.getProductCode());
                    action.setProductName(itemToAdjust.getProductName());
                    action.setType(itemToAdjust.getType());
                    action.setCurrentAmount(itemToAdjust.getAmount());
                    
                    if (adjustAmount.compareTo(BigDecimal.ZERO) > 0) {
                        // 需要买入
                        action.setActionType("BUY");
                        action.setAdjustAmount(adjustAmount.divide(itemToAdjust.getUnitValue(), 2, RoundingMode.HALF_UP));
                        action.setTargetAmount(itemToAdjust.getAmount().add(action.getAdjustAmount()));
                        // 估算费用
                        estimatedFees.updateAndGet(v -> v.add(adjustAmount.multiply(new BigDecimal("0.001"))));
                    } else {
                        // 需要卖出
                        action.setActionType("SELL");
                        action.setAdjustAmount(adjustAmount.abs().divide(itemToAdjust.getUnitValue(), 2, RoundingMode.HALF_UP));
                        action.setTargetAmount(itemToAdjust.getAmount().subtract(action.getAdjustAmount()));
                        // 估算费用
                        estimatedFees.updateAndGet(v -> v.add(adjustAmount.abs().multiply(new BigDecimal("0.001"))));
                    }
                    
                    actions.add(action);
                }
            }
        }
        
        plan.setActions(actions);
        plan.setEstimatedFees(estimatedFees.get());
        plan.setRecommendation("基于您的风险偏好和市场情况，建议进行上述调整以优化您的投资组合。");
        
        return plan;
    }
    
    @Override
    public PortfolioDTO executeRebalance(String portfolioId, RebalancePlanDTO planDTO) {
        // 实际项目中需要调用交易服务执行调仓
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("投资组合不存在"));
        
        // 执行调仓操作
        for (RebalanceActionDTO action : planDTO.getActions()) {
            List<PortfolioItem> items = portfolioItemRepository.findByPortfolioId(portfolioId).stream()
                    .filter(item -> item.getProductCode().equals(action.getProductCode()))
                    .collect(Collectors.toList());
            
            if (!items.isEmpty()) {
                PortfolioItem item = items.get(0);
                
                if ("BUY".equals(action.getActionType())) {
                    // 买入：增加持有量
                    item.setAmount(action.getTargetAmount());
                } else if ("SELL".equals(action.getActionType())) {
                    // 卖出：减少持有量
                    item.setAmount(action.getTargetAmount());
                    
                    // 如果卖出后持有量为0，删除该项
                    if (action.getTargetAmount().compareTo(BigDecimal.ZERO) <= 0) {
                        portfolioItemRepository.delete(item);
                        continue;
                    }
                }
                
                portfolioItemRepository.save(item);
            } else if ("BUY".equals(action.getActionType())) {
                // 新增买入的产品
                PortfolioItem newItem = new PortfolioItem();
                newItem.setItemId(UUID.randomUUID().toString().replace("-", ""));
                newItem.setPortfolioId(portfolioId);
                newItem.setProductCode(action.getProductCode());
                newItem.setProductName(action.getProductName());
                newItem.setType(action.getType());
                newItem.setAmount(action.getTargetAmount());
                // 这里需要获取最新净值，简化处理使用1.0
                newItem.setUnitValue(BigDecimal.ONE);
                
                portfolioItemRepository.save(newItem);
            }
        }
        
        // 更新投资组合价值
        return calculatePortfolioValue(portfolioId);
    }
    
    private String generatePortfolioId() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = now.format(formatter);
        return "PF" + timestamp + String.format("%04d", new Random().nextInt(10000));
    }
}    