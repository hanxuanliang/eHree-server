package com.hxl.api.v1;

import com.hxl.dto.TokenWithGetDTO;
import com.hxl.dto.TokenWithVerifyDTO;
import com.hxl.exception.NotFoundException;
import com.hxl.service.WxAuthenticationService;
import com.hxl.utils.JwtToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Token API接口
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/30 15:49
 */
@RestController
@RequestMapping("/token")
public class TokenController {

    @Resource
    private WxAuthenticationService wxAuthenticationService;

    @PutMapping("")
    public Map<String, String> getToken(@RequestBody @Validated TokenWithGetDTO userData) {
        Map<String, String> map = new HashMap<>(4);
        String token = null;
        switch (userData.getLoginType()) {
            case USER_WX:
                token = wxAuthenticationService.code2Session(userData.getAccount());
                break;
            case USER_EMAIL:
                // TODO
                break;
            default:
                // TODO
                throw new NotFoundException(10003);
        }
        map.put("token", token);
        return map;
    }

    @PostMapping("/verify")
    public Map<String, Boolean> verify(@RequestBody TokenWithVerifyDTO token) {
        Map<String, Boolean> map = new HashMap<>(4);
        Boolean valid = JwtToken.verifyToken(token.getToken());
        map.put("is_valid", valid);
        return map;
    }

}
