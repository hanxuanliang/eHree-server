package com.hxl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxl.exception.ParameterException;
import com.hxl.model.User;
import com.hxl.repository.UserRepository;
import com.hxl.utils.JwtToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;

/**
 * 微信授权 service
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/30 13:51
 */
@Service
public class WxAuthenticationService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private ObjectMapper mapper;

    @Resource
    private RestTemplate restTemplate;

    @Value("${wx.code2session}")
    private String code2SessionUrl;

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.appsecret}")
    private String appsecret;

    public String code2Session(String code) {
        String url = MessageFormat.format(code2SessionUrl, appid, appsecret, code);
        Map<String, Object> session = null;
        String sessionMsg = restTemplate.getForObject(url, String.class);
        try {
            session = mapper.readValue(sessionMsg, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return register(session);
    }

    public String register(Map<String, Object> session) {
        String openid = (String) session.get("openid");
        if (openid == null) {
            throw new ParameterException(20004);
        }

        Optional<User> userOptional = userRepository.findByOpenId(openid);
        if (userOptional.isPresent()) {
            return JwtToken.makeToken(userOptional.get().getId());
        }

        User user = User.builder()
                .openId(openid)
                .build();
        userRepository.save(user);
        Long uid = user.getId();
        return JwtToken.makeToken(uid);
    }

}
