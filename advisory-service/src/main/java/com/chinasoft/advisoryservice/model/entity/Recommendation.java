package com.chinasoft.advisoryservice.model.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recommendation")
@Data
public class Recommendation {
    @Id
    @Column(name = "recommendation_id")
    private String recommendationId;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "advisor_id")
    private String advisorId;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "accepted")
    private boolean accepted;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private boolean deleted;
}