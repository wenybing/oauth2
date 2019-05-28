package com.example.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @Author wenyabing
 * @Date 2019/5/20 1:29
 * 资源服务器配置
 */
@Configuration
/**
 * 开启资源服务器
 */
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        /**
         * 配置资源 id ，这里的资源 id 和授权服务器中的资源 id 一致，然后设置这些资源仅基
         * 于令牌认证。
         */
        resources.resourceId("rid").stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/hello").hasAnyRole("admin")
                .antMatchers("/user/hello").hasRole("user")
                .anyRequest()
                .authenticated();
    }
}
