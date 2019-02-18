package com.example.system;

import com.example.common.constants.HttpMethodConstant;
import com.example.common.controller.BaseController;
import com.example.common.web.response.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@Api("Test")
public class TestController extends BaseController {

    @RequestMapping("hello")
    @ApiOperation(value = "Say Hello", httpMethod = HttpMethodConstant.GET)
    public ResponseEntity<Test> sayHello() {
        List<Test> t = new ArrayList<>();
        t.add(new Test("1", "2"));
        t.add(new Test("3", "4"));
        ResponseEntity<Test> er = success(new Test("1", "2"));
        return er;
    }
}
