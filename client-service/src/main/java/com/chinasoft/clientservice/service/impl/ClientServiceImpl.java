package com.chinasoft.clientservice.service.impl;



import com.chinasoft.clientservice.pojo.Client;
import com.chinasoft.clientservice.repository.ClientRepository;
import com.chinasoft.clientservice.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;


@Log4j2
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;


    //查询所有
    @Override
    public List<Client> selectAll() {
        return clientRepository.findAll();
    }

    //根据name查询
    @Override
    public Client selectByname(String name) {
        return clientRepository.findByName(name);
    }

    //添加
    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }
    //根据风险等级查询
    @Override
    public Client selectByRiskLevel(String riskLevel) {
        return clientRepository.findByRiskLevel(riskLevel);
    }
    //删除
    @Override
    public Client deleteByClientname(String name) {
        return clientRepository.deleteByName(name);
    }
    @Override
    public List<Client> findByNameLike(String name){
        return clientRepository.findByNameLike(name);
    }

}
