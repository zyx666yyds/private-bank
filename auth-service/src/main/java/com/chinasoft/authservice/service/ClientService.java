package com.chinasoft.authservice.service;


import com.chinasoft.authservice.pojo.Client;
import com.chinasoft.authservice.pojo.vo.ClientVO;

public interface ClientService {

    Client register(ClientVO clientVO);

    Client authorized(ClientVO clientVO);
}
