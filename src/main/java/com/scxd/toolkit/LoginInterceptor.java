package com.scxd.toolkit;

import com.scxd.beans.extendbeans.Authority;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户登录拦截器
 */
//@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o){
       try{

           HttpSession session = httpServletRequest.getSession();
           Authority authority = (Authority)session.getAttribute("authority");
           String type = httpServletRequest.getHeader("X-Requested-With");// XMLHttpRequest
           String basePath = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" +
                   httpServletRequest.getServerPort() + httpServletRequest.getContextPath() + "/static/pages/backside/login.html";

           if(authority!=null){
               return true;
           }
           else{
              if (type!=null){
                   if (type.equals("XMLHttpRequest")){
                       httpServletResponse.setHeader("SESSIONSTATUS","TIMEOUT");
                       httpServletResponse.setHeader("CONTEXTPATH",basePath);
                       httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                       return false;
                   }else{
                       httpServletResponse.sendRedirect(basePath);
                       return false;
                   }
               }else{
                   return true;
               }

           }
        }catch(IllegalStateException e){
           e.printStackTrace();
           return false;
       }catch (IOException e){
           e.printStackTrace();
           return false;
       }
    }

    //访问controller时的操作
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,ModelAndView modelAndView)throws Exception{
//        System.out.println("$$$$$$$$$$");
//String ip=httpServletRequest.getRemoteAddr();
//String requrl=httpServletRequest.getRequestURI();
//String queryString=httpServletRequest.getQueryString();
//String ip=httpServletRequest.getRemoteAddr();

    }

    //访问结束时做的操作
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("$$$$$$$$$$");
//        String ip=request.getRemoteAddr();
//        String requrl=request.getRequestURI();
//        String queryString=request.getQueryString();
    }
}
