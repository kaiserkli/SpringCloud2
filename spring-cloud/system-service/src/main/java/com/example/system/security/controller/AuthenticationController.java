package com.example.system.security.controller;

import com.example.common.constants.HttpMethodConstant;
import com.example.common.controller.BaseController;
import com.example.common.exceptions.BusinessException;
import com.example.common.util.AccountInfo;
import com.example.common.web.response.ResponseEntity;
import com.example.system.security.dto.request.LoginInfoDTO;
import com.example.system.security.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@Api(value = "登录、权限验证接口",description = "登录、权限验证接口")
public class AuthenticationController extends BaseController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", httpMethod = HttpMethodConstant.POST)
    public ResponseEntity<AccountInfo> login(@RequestBody @Validated LoginInfoDTO dto,
                                             HttpServletRequest request) throws BusinessException {
        return success();
    }
}
