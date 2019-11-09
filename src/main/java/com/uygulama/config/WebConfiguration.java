package com.uygulama.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import org.springframework.core.env.Environment;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration //Konfigürasyon sınıfı olduğunu belirtir
@EnableWebMvc // Web isteklerinin yürütülmesi MVC mimarisi ile olur
@PropertySource(value = "classpath:view.properties", encoding = "UTF-8")
@ComponentScan(basePackages={"com.uygulama.config"}) //Konfigürasyon sınıflarının bulunduğu paket bilgisi
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private Environment environment;

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        //Gelen request hangı action'a ait
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(environment.getProperty("spring.mvc.view.prefix"));
        viewResolver.setSuffix(environment.getProperty("spring.mvc.view.suffix"));
        viewResolver.setContentType("text/html;charset=UTF-8");
        viewResolver.setExposeContextBeansAsAttributes(true);

        return viewResolver;
    }
    }