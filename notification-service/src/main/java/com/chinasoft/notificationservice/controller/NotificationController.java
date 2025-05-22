package com.chinasoft.notificationservice.controller;

import com.chinasoft.bankcommon.common.BaseResponse;
import com.chinasoft.bankcommon.common.ResultUtils;
import com.chinasoft.notificationservice.model.domain.Notification;
import com.chinasoft.notificationservice.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class NotificationController {

    private NotificationService notificationService;

    // 获取某个用户的所有通知列表
    @GetMapping("/notifications/{clientId}")
    public BaseResponse<List<Notification>> getNotifications(
            @PathVariable String clientId,
            @RequestParam(required = false) Boolean unread // 是否只获取未读消息
    ){
        List<Notification> result=notificationService.getNotifications(clientId, unread);
        return ResultUtils.success(result);
    }


}
