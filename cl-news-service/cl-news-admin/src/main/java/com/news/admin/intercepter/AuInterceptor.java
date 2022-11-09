package com.news.admin.intercepter;


import com.cl.common.dto.User;
import com.cl.common.util.WmThreadLocal;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("userid1111111111111111111111111111");
        String userId = request.getHeader("userId");
        System.out.println(userId);
        System.out.println("userid");
        if(!StringUtils.isEmpty(userId)){
            User user = new User(Integer.parseInt(userId));
            WmThreadLocal.set(user);
            
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        WmThreadLocal.remove();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
