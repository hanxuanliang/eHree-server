package com.hxl.service;

import com.hxl.model.Activity;
import com.hxl.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 活动 service
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/5 11:01
 */
@Service
public class ActivityService {

    @Resource
    private ActivityRepository activityRepository;

    public Activity getByName(String name) {
        return activityRepository.findByName(name);
    }

}
