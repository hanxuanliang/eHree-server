package com.hxl.dto;

import com.hxl.validators.annotations.PasswordRepeatEqual;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @Description: person 前端发来的封装数据结构 “数据传输对象”
 * @Author: hanxuanliang
 * @Date: 2020/2/5 19:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@PasswordRepeatEqual
public class PersonDTO {

    private String name;

    private Integer age;

    @Length(min = 6, max = 16, message = "密码长度需要在6和16之间")
    private String password;

    private String repeatPassword;

}
