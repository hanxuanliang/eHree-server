package com.hxl.api.v1;

import com.hxl.exception.NotFoundException;
import com.hxl.model.Banner;
import com.hxl.service.BannerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * Banner API接口
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

    @GetMapping("/name/{name}")
    public Banner getByName(@PathVariable @NotBlank String name) {
        Banner banner = bannerService.getByName(name);
        if (banner == null) {
            throw new NotFoundException(30005);
        }
        return banner;
    }
}
