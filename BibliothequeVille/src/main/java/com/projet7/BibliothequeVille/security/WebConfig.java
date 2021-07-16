package com.projet7.BibliothequeVille.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private MessageSource messageSource;

    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        //registry.addViewController("/").setViewName("entree");
        //registry.addViewController("/login").setViewName("login-view");
        //registry.addViewController("/home").setViewName("home");
        //registry.addViewController("/admin/users").setViewName("list-users");
        //registry.addViewController("/403").setViewName("403");
        //registry.addViewController("/registration").setViewName("registration-view");
        //registry.addViewController("/registration/create").setViewName("success");
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource);
        return factory;
    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

}
