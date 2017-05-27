package com.quancheng.achilles.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.quancheng.achilles.service.config.interceptor.DebugWebRequestInterceptor;
import com.quancheng.achilles.service.config.interceptor.ExecuteTimeInterceptor;

@Import(SwaggerConfiguration.class)
@Configuration 
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	DebugWebRequestInterceptor debugWebRequestInterceptor;
	@Autowired
	ExecuteTimeInterceptor executeTimeInterceptor;
	@Value("${inn.debug-interceptor}")
	boolean debugInterceptor = false;
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/home").setViewName("index");
        registry.addViewController("/404").setViewName("404");
        registry.addViewController("/500").setViewName("500");
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		super.configureContentNegotiation(configurer);
		configurer.defaultContentType(MediaType.APPLICATION_JSON).
        mediaType("xml", MediaType.APPLICATION_XML).
        mediaType("json", MediaType.APPLICATION_JSON);
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
        
		registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
		
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		if(debugInterceptor) {
			registry.addInterceptor(executeTimeInterceptor);
			registry.addWebRequestInterceptor(debugWebRequestInterceptor);
		}
	}
	
	
}
