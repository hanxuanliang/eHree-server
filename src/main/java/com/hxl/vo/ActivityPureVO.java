package com.hxl.vo;

import com.hxl.model.Activity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 前端传来的 活动 VO对象
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/5 11:04
 */
@ToString
@Setter
@Getter
@NoArgsConstructor
public class ActivityPureVO {

    private Long id;

    private String title;

    private String description;

    private Date startTime;

    private Date endTime;

    private String remark;

    private Boolean online;

    private String entranceImg;

    private String internalTopImg;

    private String name;

    public ActivityPureVO(Activity activity) {
        BeanUtils.copyProperties(activity, this);
    }

}
