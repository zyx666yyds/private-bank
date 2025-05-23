package com.chinasoft.clientservice.controller;


import com.chinasoft.bankcommon.common.BaseResponse;
import com.chinasoft.bankcommon.common.ResultUtils;
import com.chinasoft.clientservice.enums.ErrorCode;
import com.chinasoft.clientservice.pojo.Client;
import com.chinasoft.clientservice.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    //查询所有客户信息
    @RequestMapping("/c1/selectall")
    public BaseResponse register() {
        return ResultUtils.success(clientService.selectAll());
    }
    //按客户姓名查询客户信息
    @RequestMapping("/c1/selectbyname")
    public BaseResponse selectByname(@RequestBody String name) {
        if (name != null) {
            return ResultUtils.success(clientService.selectByname(name));
        } else if (name == null) {
            return ResultUtils.error(ErrorCode.BAD_REQUEST.getCode(), "查询失败");
        }
        return null;
    }
    //保存客户信息
    @RequestMapping("/c1/save")
    public BaseResponse save(@RequestBody Client client) {
        return ResultUtils.success(clientService.save(client));
    }
    //按客户风险等级查询客户信息
    @RequestMapping("/c1/selectbyrisklevel")
    public BaseResponse selectByRiskLevel(@RequestBody String riskLevel) {
        return ResultUtils.success(clientService.selectByRiskLevel(riskLevel));
    }
    //按客户姓名删除客户信息
    @RequestMapping("/c1/deletebyname")
    public BaseResponse deleteByname(@RequestBody String name) {
        if (name != null){
            return ResultUtils.success(clientService.deleteByClientname(name));
        }else if (name == null) {
            return ResultUtils.error(ErrorCode.BAD_REQUEST.getCode(), "删除失败");
        }
        return null;

    }

    @RequestMapping("/c1/findByNameLike")
    public BaseResponse findByNameLike(@RequestBody String name) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(name, Map.class);
        if (name != null){
            return  ResultUtils.success(clientService.findByNameLike((String) map.get("name")));
        }else if (name == null){
            return ResultUtils.error(ErrorCode.BAD_REQUEST.getCode(),"查询失败");
        }
        return null;
    }
    //手机号查询客户信息
    @RequestMapping("/c1/findByPhoneNumber")
    public BaseResponse findByPhoneNumber(@RequestBody String phoneNumber) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(phoneNumber, Map.class);
        if (phoneNumber != null){
            return  ResultUtils.success(clientService.findByPhoneNumber((String) map.get("phoneNumber")));
        }else if (phoneNumber == null){
            return ResultUtils.error(ErrorCode.BAD_REQUEST.getCode(),"查询失败");
        }
        return null;
    }
}
