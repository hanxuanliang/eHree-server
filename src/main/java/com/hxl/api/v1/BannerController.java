package com.hxl.api.v1;

import com.hxl.exception.http.ForbiddenException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: Banner controller
 * @Author: hanxuanliang
 * @Date: 2020/2/5 9:50
 */
@RestController
@RequestMapping("/banner")
public class BannerController {

    @GetMapping("/test")
    public String test() {
//        throw new RuntimeException("test API");
        throw new ForbiddenException(10001);
//        return "/v1/"banner;
    }
}
