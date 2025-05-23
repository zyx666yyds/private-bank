package com.chinasoft.clientservice.service;



import com.chinasoft.clientservice.pojo.Client;

import java.util.List;

public interface ClientService {
    //查询所有客户
    List<Client> selectAll();
    //按客户名查询客户
    Client selectByname(String name);
    //按客户ID查询客户
    Client save(Client client);
    //按客户风险等级查询客户
    Client selectByRiskLevel(String riskLevel);
    //按客户ID删除客户
    Client deleteByClientname (String name);
    //按客户名模糊查询客户
    List<Client> findByNameLike(String name);
    //按客户手机号查询客户
    List<Client> findByPhoneNumber(String phoneNumber);

}

