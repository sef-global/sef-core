package org.sefglobal.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sefglobal.core.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private AuthenticationService authenticationService;

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(authenticationService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/link/*").permitAll()
                    .antMatchers("/academix/admin/**").authenticated()
                    .antMatchers("/academix/**").permitAll()
                    .antMatchers("/fellowship/admin/**").authenticated()
                    .antMatchers("/fellowship/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .successHandler(new AuthSuccessHandler())
                    .failureHandler(new AuthFailureHandler())
                    .permitAll()
                .and()
                    .logout().permitAll()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://sef-academix.herokuapp.com", "https://sef-academix-dashboard.herokuapp.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With","Origin","Content-Type",
                "Accept","Authorization","Set-Cookie"));

        // This allow us to expose the headers
        configuration.setExposedHeaders(Arrays.asList("Set-Cookie","Access-Control-Allow-Headers"
                , "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private static class AuthSuccessHandler implements AuthenticationSuccessHandler {
        private ObjectMapper objectMapper = new ObjectMapper();
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                Authentication authentication) throws IOException {
            Map<String, Object> data = new HashMap<>();
            data.put("username", authentication.getName());
            response.setContentType("application/json");
            response.setStatus(HttpStatus.OK.value());
            response.getOutputStream().println(objectMapper.writeValueAsString(data));
        }
    }

    private static class AuthFailureHandler implements AuthenticationFailureHandler {
        private ObjectMapper objectMapper = new ObjectMapper();
        @Override
        public void onAuthenticationFailure(HttpServletRequest request,
                HttpServletResponse response,
                AuthenticationException exception) throws IOException, ServletException {
            Map<String, Object> data = new HashMap<>();
            response.setContentType("application/json");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            data.put("exception", exception.getMessage());
            response.getOutputStream().println(objectMapper.writeValueAsString(data));
        }
    }
}
