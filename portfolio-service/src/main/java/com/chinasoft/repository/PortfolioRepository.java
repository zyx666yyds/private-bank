package com.wealth.portfolioservice.repository;

import com.wealth.portfolioservice.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * @author:张宇阳Alan
 * @date:2025/5/22
 * @description:投资组合的访问接口，提供对Portfolio实体的数据库操作
 */
@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, String> {
    List<Portfolio> findByClientId(String clientId);
}    