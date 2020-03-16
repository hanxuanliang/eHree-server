package com.hxl.api.v1;

import com.hxl.exception.NotFoundException;
import com.hxl.model.Banner;
import com.hxl.model.Spu;
import com.hxl.service.SpuService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Spu API接口
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/15 21:54
 */
@RestController
@RequestMapping("/spu")
@Validated
public class SpuController {

    @Resource
    private SpuService spuService;

    @GetMapping("/id/{id}/detail")
    public Spu getDetail(@PathVariable @NotBlank Long id) {
        Spu spu = spuService.getSpu(id);
        if (spu == null) {
            throw new NotFoundException(30003);
        }
        return spu;
    }

    @GetMapping("/latest")
    public List<Spu> getLatestSpuList() {
        return spuService.getLatestPagingSpu();
    }
}
