package com.jike.moba.controller;

import com.jike.moba.common.BaseResponse;
import com.jike.moba.common.ErrorCode;
import com.jike.moba.common.ResultUtil;
import com.jike.moba.exception.BusinessException;
import com.qimu.jujiao.model.entity.User;
import com.jike.moba.model.request.FriendAddRequest;
import com.jike.moba.model.vo.FriendsRecordVO;
import com.jike.moba.service.FriendsService;
import com.jike.moba.service.UserService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * @Author: jike
 * @Date: 2023年03月08日 23:21
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping("/friends")
public class FriendsController {
    @Resource
    private FriendsService friendsService;

    @Resource
    private UserService userService;

    @PostMapping("add")
    public BaseResponse<Boolean> addFriendRecords(@RequestBody FriendAddRequest friendAddRequest, HttpServletRequest request) {
        if (friendAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求有误");
        }
        User loginUser = userService.getLoginUser(request);
        boolean addStatus = friendsService.addFriendRecords(loginUser, friendAddRequest);
        return ResultUtil.success(addStatus, "申请成功");
    }

    @GetMapping("getRecords")
    public BaseResponse<List<FriendsRecordVO>> getRecords(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        List<FriendsRecordVO> friendsList = friendsService.obtainFriendApplicationRecords(loginUser);
        return ResultUtil.success(friendsList);
    }

    @GetMapping("getRecordCount")
    public BaseResponse<Integer> getRecordCount(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        int recordCount = friendsService.getRecordCount(loginUser);
        return ResultUtil.success(recordCount);
    }

    @GetMapping("getMyRecords")
    public BaseResponse<List<FriendsRecordVO>> getMyRecords(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        List<FriendsRecordVO> myFriendsList = friendsService.getMyRecords(loginUser);
        return ResultUtil.success(myFriendsList);
    }

    @PostMapping("agree/{fromId}")
    public BaseResponse<Boolean> agreeToApply(@PathVariable("fromId") Long fromId, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        boolean agreeToApplyStatus = friendsService.agreeToApply(loginUser, fromId);
        return ResultUtil.success(agreeToApplyStatus);
    }

    @PostMapping("canceledApply/{id}")
    public BaseResponse<Boolean> canceledApply(@PathVariable("id") Long id, HttpServletRequest request) {
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求有误");
        }
        User loginUser = userService.getLoginUser(request);
        boolean canceledApplyStatus = friendsService.canceledApply(id, loginUser);
        return ResultUtil.success(canceledApplyStatus);
    }

    @GetMapping("/read")
    public BaseResponse<Boolean> toRead(@RequestParam(required = false) Set<Long> ids, HttpServletRequest request) {
        if (CollectionUtils.isEmpty(ids)) {
            return ResultUtil.success(false);
        }
        User loginUser = userService.getLoginUser(request);
        boolean isRead = friendsService.toRead(loginUser, ids);
        return ResultUtil.success(isRead);
    }
}
