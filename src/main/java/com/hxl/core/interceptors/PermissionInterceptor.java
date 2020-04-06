package com.hxl.core.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.hxl.core.LocalUser;
import com.hxl.core.annotations.ScopeLevel;
import com.hxl.exception.ForbiddenException;
import com.hxl.exception.UnAuthenticatedException;
import com.hxl.model.User;
import com.hxl.service.UserService;
import com.hxl.utils.JwtToken;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/**
 * 登陆许可拦截
 *
 * @Author: hanxuanliang
 * @Date: 2020/3/30 16:41
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<ScopeLevel> scopeLevel = getScopeLevel(handler);
        if (!scopeLevel.isPresent()) {
            return true;
        }
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isEmpty(bearerToken)) {
            throw new UnAuthenticatedException(10004);
        }
        if (!bearerToken.startsWith("Bearer")) {
            throw new UnAuthenticatedException(10004);
        }
        String[] tokens = bearerToken.split(" ");
        if (tokens.length != 2) {
            throw new UnAuthenticatedException(10004);
        }
        String token = tokens[1];
        Optional<Map<String, Claim>> optionalMap = JwtToken.getClaims(token);
        Map<String, Claim> map = optionalMap.orElseThrow(() -> new UnAuthenticatedException(10004));
        boolean isValid = hasPermission(scopeLevel.get(), map);
        if (isValid) {
            setLocalUser(map);
        }
        return isValid;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 一定要在拦截器完成的时候把这个 localUser 手动删除【阿里规范】
        LocalUser.removeLocalUser();
        super.afterCompletion(request, response, handler, ex);
    }

    private Optional<ScopeLevel> getScopeLevel(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ScopeLevel scopeLevel = handlerMethod.getMethod().getAnnotation(ScopeLevel.class);
            if (scopeLevel == null) {
                return Optional.empty();
            }
            return Optional.of(scopeLevel);
        }
        return Optional.empty();
    }

    private void setLocalUser(Map<String, Claim> claimMap) {
        Long uid = claimMap.get("uid").asLong();
        Integer scope = claimMap.get("scope").asInt();
        User user = userService.getUserById(uid);
        LocalUser.setLocalUser(user, scope);
    }

    private Boolean hasPermission(ScopeLevel scopeLevel, Map<String, Claim> map) {
        int level = scopeLevel.level();
        Integer scope = map.get("scope").asInt();
        if (level > scope) {
            throw new ForbiddenException(10005);
        }
        return true;
    }
}
