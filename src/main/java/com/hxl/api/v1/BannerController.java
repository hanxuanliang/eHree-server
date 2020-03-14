package com.hxl.api.v1;

import com.hxl.model.Banner;
import com.hxl.service.BannerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * Banner controller
 *
 * @Author: hanxuanliang
 * @Date: 2020/2/5 9:50
 */
@RestController
@RequestMapping("/banner")
@Validated
public class BannerController {

    @Resource
    private BannerService bannerService;

    @GetMapping("/{name}")
    public Banner getByName(@PathVariable @NotBlank String name) {
        return bannerService.getByName(name);
    }
}
