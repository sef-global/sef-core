package org.sefglobal.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("https://sef-academix.herokuapp.com", "https://sef-academix-dashboard.herokuapp.com")
                .allowedMethods("PUT", "DELETE", "GET", "POST")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
