package com.sky.interceptor;

import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.properties.JwtProperties;
import com.sky.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt token verification interceptor
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * Verify jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Determine if the current intercepted method is a Controller method or other resources
        if (!(handler instanceof HandlerMethod)) {
            // The current intercepted method is not a dynamic method, directly pass
            return true;
        }

        //1 Get token from request header
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        //2 Verify token
        try {
            log.info("jwt verification: {}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
            log.info("Current employee id: {}", empId);
            BaseContext.setCurrentId(empId);
            //3 Pass, pass
            return true;
        } catch (Exception ex) {
            //4 Not passed, respond with 401 status code
            response.setStatus(401);
            return false;
        }
    }
}
