package com.hxl.model;

import com.hxl.utils.SuperGenericAndJson;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Map;

/**
 * 用户 模型表
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/30 13:01
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Where(clause = "delete_time is null")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String openId;

    private String nickname;

    private Long unifyUid;

    private String email;

    private String password;

    private String mobile;

    // 分组
    private String group;

    @Convert(converter = SuperGenericAndJson.class)
    private Map<String, Object> wxProfile;
}
