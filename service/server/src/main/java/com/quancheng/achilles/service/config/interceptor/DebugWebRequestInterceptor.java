package com.quancheng.achilles.service.config.interceptor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

@Component
public class DebugWebRequestInterceptor implements WebRequestInterceptor {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteTimeInterceptor.class);

	@Override
	public void preHandle(WebRequest request) throws Exception {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("--headers--");
			Iterator<String> itr = request.getHeaderNames();
			while (itr.hasNext()) {
				String headerName = itr.next();
				String[] values = request.getHeaderValues(headerName);
				LOGGER.debug("    {} = {}",headerName, Arrays.toString(values));
			}  
			LOGGER.debug("--request parameters--");
			Map<String, String[]> parameterMap = request.getParameterMap();
			for (String param : parameterMap.keySet()) {
				String[] values =parameterMap.get(param);
				LOGGER.debug("    {} = {}",param, Arrays.toString(values));
			}
			LOGGER.debug("--request attributes--");
			String[] attrNames = request.getAttributeNames(WebRequest.SCOPE_REQUEST);
			for (String name : attrNames) {
				Object value = request.getAttribute(name, WebRequest.SCOPE_REQUEST);
				LOGGER.debug("   {} = {}",name, value);
			}
			LOGGER.debug("--session attributes--");
			String[] sessionAttrNames = request.getAttributeNames(WebRequest.SCOPE_SESSION);
			for (String name : sessionAttrNames) {
				Object value = request.getAttribute(name, WebRequest.SCOPE_REQUEST);
				LOGGER.debug("   {} = {}",name, value);
			}
		}

	}

	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("--response--");
			if (model!=null) {
				for(String key : model.keySet()) {
					Object value = model.get(key);
					LOGGER.debug("    {} = {}",key, value);
				}
			}
		}
	}

	@Override
	public void afterCompletion(WebRequest request, Exception ex) throws Exception {

	}

}
