package com.chinasoft.authservice.pojo.vo;

import com.chinasoft.authservice.enums.Gender;
import com.chinasoft.authservice.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientVO {

    private String name;

    private Gender gender;

    private LocalDate birthday;

    private String phoneNumber;

    private String password;

    private String email;

    private String nationality;

    private IdType idType;

    private String idNumber;
}
