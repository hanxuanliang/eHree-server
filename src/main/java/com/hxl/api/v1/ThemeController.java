package com.hxl.api.v1;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.hxl.exception.NotFoundException;
import com.hxl.model.Theme;
import com.hxl.service.ThemeService;
import com.hxl.vo.ThemePureVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Theme API接口
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/21 11:00
 */
@RestController
@RequestMapping("/theme")
@Validated
public class ThemeController {

    @Resource
    private ThemeService themeService;

    @GetMapping("/by/names")
    public List<ThemePureVO> findByNames(@RequestParam(name = "names") String names) {
        List<String> nameList = Arrays.asList(names.split(","));
        List<Theme> themes = themeService.findByNames(nameList);

        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List<ThemePureVO> themePureVOList = new ArrayList<>(themes.size());
        themes.forEach(theme -> {
            ThemePureVO vo = mapper.map(theme, ThemePureVO.class);
            themePureVOList.add(vo);
        });
        return themePureVOList;
    }

    @GetMapping("/name/{name}/with_spu")
    public Theme getThemeByNameWithSpu(@PathVariable(name = "name") String themeName) {
        Optional<Theme> theme = themeService.findByName(themeName);
        return theme.orElseThrow(() -> new NotFoundException(30003));
    }
}
