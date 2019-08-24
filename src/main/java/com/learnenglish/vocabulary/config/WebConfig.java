package com.learnenglish.vocabulary.config;

import com.learnenglish.vocabulary.util.ViewNames;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName(ViewNames.HOME);
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//    }
}
