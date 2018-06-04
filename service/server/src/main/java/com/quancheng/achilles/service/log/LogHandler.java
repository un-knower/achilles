package com.quancheng.achilles.service.log;

import java.net.InetAddress;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <strong>描述：</strong> 日志<br>
 * <strong>功能：维护logId</strong><br>
 * <strong>使用场景：</strong><br>
 * <strong>注意事项：</strong>
 * <ul>
 * <li></li>
 * </ul>
 *
 * @author jianyang 2017/5/8
 */
@Aspect
@Component
public class LogHandler {

    public static volatile Logger log = LoggerFactory.getLogger("TRACE");

    public static final ThreadLocal<Long> CURRENT_THREAD = new ThreadLocal<>();

    @Value("${spring.application.name}")
    public String appName;

    @Value("${log.around:true}")
    public boolean logAround;

    String jsonTemplate="{\"type\":\"HTTP\",\"tag\":\"{}\","
            + "\"host_ip\":\"{}\""
            + "\"span_id\":null,\"parent_id\":null,\"trace_id\":null,\"log_id\":null,\"method_name\":\"{}\",\"user_id\":null,\"start_time\":{},\"end_time\":{},\"duration\":{}}";
    
    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();
        Object obj = null;
        long endtime=0;
        try {
            obj = joinPoint.proceed(args);
            if (!methodName.startsWith("com.quancheng.achilles.service.web.DownLoadRecordController")) {
                endtime= System.currentTimeMillis();
                log.trace(jsonTemplate, "SUCCESS",InetAddress.getLocalHost().getHostAddress(), methodName, startTime,endtime,endtime- startTime);
            }
        } catch (Exception e) {
            log.error("system error", e);
            log.trace(jsonTemplate, "ERROR",InetAddress.getLocalHost().getHostAddress(), methodName, startTime,endtime,endtime- startTime);
            throw e;
        } finally {
            CURRENT_THREAD.remove();
        }
        return obj;
    }
    

}
