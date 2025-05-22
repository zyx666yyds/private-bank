package com.chinasoft.approvalservice.repository;

import com.chinasoft.approvalservice.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TradeRepository extends JpaRepository<Trade, String> {
    List<Trade> findByClientIdAndIsDeletedFalse(String clientId);
    List<Trade> findByStatusAndIsDeletedFalse(String status);
}

