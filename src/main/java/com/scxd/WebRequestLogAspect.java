package com.scxd;

import com.alibaba.druid.util.StringUtils;
import com.scxd.beans.Response;
import com.scxd.beans.database.SysOpLogBean;
import com.scxd.beans.extendbeans.Authority;
import com.scxd.service.OptLogService;
import com.scxd.toolkit.JSONUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;

@Aspect
@Component
public class WebRequestLogAspect {
    private ThreadLocal<SysOpLogBean> tlocal = new ThreadLocal<SysOpLogBean>();

    @Autowired
    private OptLogService optLogService;

    @Pointcut("execution(public * com.*.controller.*.*(..))")
    public void webRequestLog() {
    }

    // @Order(5)
    @Before("webRequestLog()")
    public void doBefore(JoinPoint joinPoint) {
        try {

            long beginTime = System.currentTimeMillis();

            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String uri = request.getRequestURI();
            String remoteAddr = getIpAddr(request);
            Authority authority = (Authority) request.getSession().getAttribute("authority");
            String UNAME = "";
            String RelUNAME = "";
            if (authority != null) {
                UNAME = authority.getUser().getAccount();
                RelUNAME = authority.getUser().getRelname();
            }
            String method = request.getMethod();
            String params = "";
            if ("POST".equals(method)) {
                Object[] paramsArray = joinPoint.getArgs();
                if ("/ncweb/user/login".equals(uri)) {
                    UNAME = (String) ((Map) paramsArray[0]).get("account");
                    RelUNAME = UNAME;
                }
                params = argsArrayToString(paramsArray);
            } else {
                Map<String, String[]> paramsmap = request.getParameterMap();
                String queryString = "";
                for (String key : paramsmap.keySet()) {
                    String[] values = paramsmap.get(key);
                    if ("_".equals(key)) {

                    } else {
                        for (int i = 0; i < values.length; i++) {
                            String value = values[i];
                            queryString += key + "=" + value + "&";
                        }
                    }
                }
                // 去掉最后一个空格
                params = queryString.substring(0, queryString.length() - 1);
            }
//
            SysOpLogBean optLog = new SysOpLogBean();
            optLog.setId(UUID.randomUUID().toString());
            optLog.setOpContent(StringUtils.isEmpty(params) ? UNAME : params);
            optLog.setOpUrl(uri);
            optLog.setOpIp(remoteAddr);
            optLog.setUname(UNAME);
            optLog.setRealname(RelUNAME);
            optLog.setCheckdigit(RelUNAME);
            optLog.setLogType(2l);
            optLog.setFuncType(1l);
            optLog.setRequestTime(beginTime);
            tlocal.set(optLog);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // @Order(5)
    @AfterReturning(returning = "result", pointcut = "webRequestLog()")
    public void doAfterReturning(Object result) {
        try {
            // 处理完请求，返回内容
            SysOpLogBean optLog = tlocal.get();
            long ops = 0l;
            if (result instanceof Response) {
                Response resultr = (Response) result;
                if (resultr.isSuccess()) {
                    ops = 1l;
                }

            } else if (result instanceof Map) {
                Map map = (Map) result;
                if (map.size() > 0) {
                    ops = 1l;
                }
            }
            optLog.setOpResult(ops);
            optLog.setOpResultInfo(JSONUtil.toJSONString(result));
            long beginTime = optLog.getRequestTime();
            long requestTime = (System.currentTimeMillis() - beginTime) / 1000;
            optLog.setRequestTime(requestTime);
            optLogService.saveLog(optLog);
        } catch (Exception e) {
        }
    }


    /**
     * 获取登录用户远程主机ip地址
     *
     * @param request
     * @return
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 请求参数拼装
     *
     * @param paramsArray
     * @return
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                Object jsonObj = JSONUtil.toJSONString(paramsArray[i]);
                if (jsonObj != null) {
                    params += jsonObj.toString() + " ";
                }
            }
        }
        return params.trim();
    }

}
