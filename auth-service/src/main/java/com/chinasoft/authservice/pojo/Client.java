package com.chinasoft.authservice.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client", uniqueConstraints = @UniqueConstraint(columnNames = "phone_number"))
public class Client {

    @Id
    @Column(name = "client_id", length = 18, nullable = false)
    private String clientId;

    @Column(name = "name", length = 50)
    private String name;

//    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 1)
    private String gender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "phone_number", length = 20, unique = true)
    private String phoneNumber;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "nationality", length = 50)
    private String nationality;

//    @Enumerated(EnumType.STRING)
    @Column(name = "id_type", length = 20)
    private String idType;

    @Column(name = "id_number", length = 30)
    private String idNumber;

    @Column(name = "income_level", length = 30)
    private String incomeLevel;

//    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", length = 20)
    private String riskLevel;

    @Column(name = "total_assets", precision = 20, scale = 2)
    private BigDecimal totalAssets;

    @Column(name = "relationship_manager_id", length = 12)
    private String relationshipManagerId;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @Column(name = "kyc_due_date")
    private LocalDate kycDueDate;

//    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10)
    private String status;

    @Lob
    @Column(name = "remarks")
    private String remarks;

}