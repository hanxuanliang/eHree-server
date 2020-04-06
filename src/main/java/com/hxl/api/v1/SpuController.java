package com.hxl.api.v1;

import com.hxl.bo.PageCounter;
import com.hxl.exception.NotFoundException;
import com.hxl.model.Spu;
import com.hxl.service.SpuService;
import com.hxl.utils.CommonUtil;
import com.hxl.vo.PagingVO;
import com.hxl.vo.SpuPureVO;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

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
    public Spu getDetail(@PathVariable @Positive Long id) {
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
    public PagingVO<Spu, SpuPureVO> getLatestSpuList(@RequestParam(defaultValue = "0") Integer start,
                                                     @RequestParam(defaultValue = "10") Integer count) {
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);

        Page<Spu> pageSpu = spuService.getLatestPagingSpu(pageCounter.getPage(), pageCounter.getCount());

        return new PagingVO<>(pageSpu, SpuPureVO.class);
    }

    @GetMapping("/by/category/{id}")
    public PagingVO<Spu, SpuPureVO> getByCategoryId(@PathVariable @NotBlank @Positive(message = "{id.positive}") Long id,
                                                    @RequestParam(name = "is_root", defaultValue = "false") Boolean isRoot,
                                                    @RequestParam(defaultValue = "0") Integer start,
                                                    @RequestParam(defaultValue = "10") Integer count) {
        PageCounter pageCounter = CommonUtil.convertToPageParameter(start, count);

        Page<Spu> page = spuService.getByCategoryId(id, isRoot, pageCounter.getPage(), pageCounter.getCount());
        return new PagingVO<>(page, SpuPureVO.class);
    }
}
