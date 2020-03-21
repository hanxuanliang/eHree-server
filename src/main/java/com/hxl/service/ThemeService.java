package com.hxl.service;

import com.hxl.model.Theme;
import com.hxl.repository.ThemeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * Theme service
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/21 10:22
 */
@Service
@Slf4j
public class ThemeService {

    @Resource
    private ThemeRepository themeRepository;

    public List<Theme> findByNames(List<String> names) {
        return themeRepository.findByNames(names);
    }

    public Optional<Theme> findByName(String name) {
        return themeRepository.findByName(name);
    }

}
