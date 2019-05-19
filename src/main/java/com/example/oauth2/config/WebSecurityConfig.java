package com.example.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author wenyabing
 * @Date 2019/5/20 1:36
 * web安全配置
 */
@Configuration
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("$2a$10$K0uU50HPrlkFdij/vVdckuV2Waja3Wza6Jb3YulDk/f51.7tEjumG")
                .roles("admin")
                .and()
                .withUser("zhangsan")
                .password("$2a$10$K0uU50HPrlkFdij/vVdckuV2Waja3Wza6Jb3YulDk/f51.7tEjumG")
                .roles("user");
    }

    // @formatter:off
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 这里配置了两个Bean将注入授权服务器配置类中使用。
         * 另外，这里的 HttpSecurity 配置主要是配置“／oauth/** ”模式的URL ，
         * 这一类的请求直接放行。在Spring Security 配置和资源服务器配置中， 共涉及两个
         * HttpSecurity ，其中 Spring Security 中的配置优先级高于资源服务器中的配置，即请求地址先经过
         * Spring Security HttpSecurity ，再经过资源服务器的 Http Security
         */
        http.antMatcher("/oauth/**")
                .authorizeRequests()
                .antMatchers("/oauth/**")
                .permitAll()
                .and()
                .csrf()
                .disable();
    }
    // @formatter:on
}
