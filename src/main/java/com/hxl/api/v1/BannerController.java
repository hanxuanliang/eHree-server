package com.hxl.api.v1;

import com.hxl.dto.PersonDTO;
import com.hxl.exception.http.ForbiddenException;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: Banner controller
 * @Author: hanxuanliang
 * @Date: 2020/2/5 9:50
 */
@RestController
@RequestMapping("/banner")
@Validated
public class BannerController {

    @PostMapping("/test/{id}")
    public PersonDTO test(@PathVariable @Range(min = 1, max = 100, message = "id必须在1到100之间") Integer id,
                       @RequestParam @Length(min = 6, message = "姓名长度至少6个字符以上") String name,
                       @RequestBody @Validated PersonDTO personDTO) {
//        throw new ForbiddenException(10001);
        return PersonDTO.builder().name("chxlD").age(1024).build();
    }
}
