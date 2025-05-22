package com.chinasoft.clientservice.service;


import com.chinasoft.authservice.pojo.vo.ClientVO;
import com.chinasoft.bankcommon.common.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("auth-service")
public interface AuthService {

    @RequestMapping("/v1/authorized")
    public BaseResponse authorized(@RequestBody ClientVO clientVO);

}
