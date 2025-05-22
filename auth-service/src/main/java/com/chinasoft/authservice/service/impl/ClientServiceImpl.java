package com.chinasoft.authservice.service.impl;

import com.chinasoft.authservice.enums.ClientStatus;
import com.chinasoft.authservice.pojo.Client;
import com.chinasoft.authservice.pojo.vo.ClientVO;
import com.chinasoft.authservice.repository.ClientRepository;
import com.chinasoft.authservice.service.ClientService;
import com.chinasoft.authservice.utils.MD5Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;



@Log4j2
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client register(ClientVO clientVO) {
        // 检查是否已存在相同的手机号
        if (clientRepository.findByPhoneNumber(clientVO.getPhoneNumber()) != null) {
            return null; // 手机号已存在，注册失败
        }
        Client client = new Client();
        BeanUtils.copyProperties(clientVO, client);

        client.setClientId(UUID.randomUUID().toString().substring(0,18));
        client.setPassword(MD5Utils.encode(client.getPassword()));
//        client.setStatus(ClientStatus.NORMAL);
        client.setStatus("正常");
        client.setRegisterDate(LocalDateTime.now());

        // 设置KYC到期时间为半年后
        LocalDateTime kycExpiryDate = LocalDateTime.now().plusMonths(6).withHour(23).withMinute(59).withSecond(59);
        client.setKycDueDate(LocalDate.from(kycExpiryDate));

        return clientRepository.save(client);
    }

    @Override
    public Client authorized(ClientVO clientVO) {
        // 检查手机号和密码是否匹配
        Client client = clientRepository.findByPhoneNumber(clientVO.getPhoneNumber());
        if (client == null) {
            return null;
        }
        if (!MD5Utils.encode(clientVO.getPassword()).equals(client.getPassword())) {
            return null;
        }
//        if (!client.getStatus().equals(ClientStatus.NORMAL)) {
        if (!client.getStatus().equals("正常")){
            return null; // 账号异常，登录失败
        }
        log.info(client.getName() + "登录成功");
        return client;
    }
}
