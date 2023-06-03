package com.jike.moba.controller;

import com.jike.moba.common.BaseResponse;
import com.jike.moba.common.ErrorCode;
import com.jike.moba.common.ResultUtil;
import com.jike.moba.exception.BusinessException;
import com.qimu.jujiao.model.entity.User;
import com.jike.moba.model.request.ChatRequest;
import com.jike.moba.model.vo.MessageVo;
import com.jike.moba.service.ChatService;
import com.jike.moba.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.jike.moba.contant.ChatConstant.*;

/**
 * @Author: jike
 * @Date: 2023年04月11日 11:37
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Resource
    private ChatService chatService;
    @Resource
    private UserService userService;

    @PostMapping("/privateChat")
    public BaseResponse<List<MessageVo>> getPrivateChat(@RequestBody ChatRequest chatRequest, HttpServletRequest request) {
        if (chatRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求有误");
        }
        User loginUser = userService.getLoginUser(request);
        List<MessageVo> privateChat = chatService.getPrivateChat(chatRequest, PRIVATE_CHAT, loginUser);
        return ResultUtil.success(privateChat);
    }

    @GetMapping("/hallChat")
    public BaseResponse<List<MessageVo>> getHallChat(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        List<MessageVo> hallChat = chatService.getHallChat(HALL_CHAT, loginUser);
        return ResultUtil.success(hallChat);
    }

    @PostMapping("/teamChat")
    public BaseResponse<List<MessageVo>> getTeamChat(@RequestBody ChatRequest chatRequest, HttpServletRequest request) {
        if (chatRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求有误");
        }
        User loginUser = userService.getLoginUser(request);
        List<MessageVo> teamChat = chatService.getTeamChat(chatRequest, TEAM_CHAT, loginUser);
        return ResultUtil.success(teamChat);
    }
}
