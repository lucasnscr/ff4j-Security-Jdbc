package com.lucasnscr.ff4jsecurity.config;

import org.ff4j.FF4j;
import org.ff4j.spring.boot.web.api.config.EnableFF4jSwagger;
import org.ff4j.web.FF4jDispatcherServlet;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableFF4jSwagger
@ConditionalOnClass({FF4jDispatcherServlet.class})
@AutoConfigureAfter(FF4jConfig.class)
public class FF4jWebConsoleConfiguration extends SpringBootServletInitializer implements WebMvcConfigurer {

    /**
     * Definition of the servlet for web console
     */
    @Bean
    @ConditionalOnMissingBean
    public FF4jDispatcherServlet getFF4jDispatcherServlet(FF4j ff4j) {
        FF4jDispatcherServlet ff4jConsoleServlet = new FF4jDispatcherServlet();
        ff4jConsoleServlet.setFf4j(ff4j);
        return ff4jConsoleServlet;
    }

    @Bean
    @SuppressWarnings({"rawtypes","unchecked"})
    public ServletRegistrationBean ff4jDispatcherServletRegistrationBean(FF4jDispatcherServlet ff4jDispatcherServlet) {
        return new ServletRegistrationBean(ff4jDispatcherServlet, "/ff4j-web-console/*");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

}
