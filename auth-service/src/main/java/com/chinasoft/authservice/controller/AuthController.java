package com.chinasoft.authservice.controller;

import com.chinasoft.authservice.enums.ErrorCode;
import com.chinasoft.authservice.pojo.Client;
import com.chinasoft.authservice.pojo.vo.ClientVO;
import com.chinasoft.authservice.service.ClientService;
import com.chinasoft.authservice.utils.JWTUtils;
import com.chinasoft.bankcommon.common.BaseResponse;
import com.chinasoft.bankcommon.common.ResultUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

     private final ClientService clientService;

    @RequestMapping("/v1/register")
    public BaseResponse register(@RequestBody ClientVO clientVO) {
        Client register = clientService.register(clientVO);
        if (register != null) {
            return ResultUtils.success(register.getClientId());
        }
        else
            return ResultUtils.error(ErrorCode.BAD_REQUEST.getCode(),  "注册失败");
    }

    @RequestMapping("/v1/authorized")
    public BaseResponse authorized(@RequestBody ClientVO clientVO) {
            Client client = clientService.authorized(clientVO);
        if (client != null) {
            Map<String, String> map = new HashMap<>();
            map.put("clientId", client.getClientId());
            map.put("password", client.getPassword());

            return ResultUtils.success(JWTUtils.getToken(map));
        }else
            return ResultUtils.error(ErrorCode.BAD_REQUEST.getCode(), "登录失败");
    }
}
