package com.quancheng.achilles.service.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ExecuteTimeInterceptor implements HandlerInterceptor {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteTimeInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	        throws Exception {
		long startTime = System.currentTimeMillis();
		request.setAttribute("DEBUG_startTime", startTime);

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
	        ModelAndView modelAndView) throws Exception {
		long startTime = (Long) request.getAttribute("DEBUG_startTime");
		long endTime = System.currentTimeMillis();
		long executeTime = endTime - startTime;
		// log it
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[" + handler + "] executeTime : " + executeTime + "ms");
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
	        throws Exception {
	}

}
