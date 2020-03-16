package com.hxl.api.v1;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.hxl.bo.PageCounter;
import com.hxl.exception.NotFoundException;
import com.hxl.model.Spu;
import com.hxl.service.SpuService;
import com.hxl.utils.CommonUtil;
import com.hxl.vo.PagingVO;
import com.hxl.vo.SpuVO;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
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

    /**
     * 【分页机制】下的获取最新spuList
     *
     * @param start 开始数目
     * @param count 条数
     * @return SpuVOList
     * @date: 2020/3/16 10:24
     */
    @GetMapping("/latest")
    public PagingVO<Spu, SpuVO> getLatestSpuList(@RequestParam(defaultValue = "0") Integer start,
                                        @RequestParam(defaultValue = "10") Integer count) {

        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);

        Page<Spu> pageSpu = spuService.getLatestPagingSpu(pageCounter.getPage(), pageCounter.getCount());

        return new PagingVO<>(pageSpu, SpuVO.class);
    }
}
