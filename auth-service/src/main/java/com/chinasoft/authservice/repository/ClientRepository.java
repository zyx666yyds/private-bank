package com.chinasoft.authservice.repository;


import com.chinasoft.authservice.pojo.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Client findByPhoneNumber(String phoneNumber);
}
