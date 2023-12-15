package com.dearsanta.app.config;

import com.dearsanta.app.security.JwtInterceptor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Log4j
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    @Qualifier("jwtInterceptor")
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://dearsanta.shop", "https://dearsanta.shop", "http://www.dearsanta.shop", "https://www.dearsanta.shop", properties.getFRONT_LOCAL_URL(), )
                .allowedMethods("OPTIONS","GET","POST","PUT","DELETE");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns();
    }
}
