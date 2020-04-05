package com.hxl.api.v1;

import com.hxl.exception.NotFoundException;
import com.hxl.model.Activity;
import com.hxl.service.ActivityService;
import com.hxl.vo.ActivityPureVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 活动 api接口
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/5 11:03
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @GetMapping("/name/{name}")
    public ActivityPureVO getHomeActivity(@PathVariable String name) {
        Activity activity = activityService.getByName(name);
        if (activity == null) {
            throw new NotFoundException(40001);
        }
        return new ActivityPureVO(activity);
    }

}
