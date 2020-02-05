package com.hxl.api.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: Banner controller
 * @Author: hanxuanliang
 * @Date: 2020/2/5 9:50
 */
@RestController
public class Banner {

    @GetMapping("/v1/banner")
    public String banner() {

        return "/v1/banner";
    }
}
