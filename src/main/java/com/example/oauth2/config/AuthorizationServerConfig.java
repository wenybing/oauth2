package com.example.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Author wenyabing
 * @Date 2019/5/20 0:59
 * 授权服务器配置
 */
@Configuration
/**
 * 开启授权服务器
 */
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    /**
     * 用于支持password模式
     */
    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * 用于完成redis缓存，将令牌信息存储到redis缓存中
     */
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    /**
     * 用于为刷新token提供支持
     */
    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override

    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        /**
         *允许表单认证
         */
        security.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /**
         *  配置password授权模式，authorizedGrantTypes表示OAuth2中的授权模式为
         *  "password" 和"refresh_token"两种，在标准的OAuth2协议中，权模式并不包括
         *  "refresh _token"，但是在Spring Security的实现中其归为一种，因此如果要实现access_token
         *  的刷新，就需要添加这样一种技权模式； accessTokenValiditySeconds方法配置了access_token
         *  的过期时间， resourceIds配置了资源 id; secret方法配置了加密后的密码，明文是 123
         */
        //密码授权模式
        clients.inMemory()
                .withClient("client_1")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(1800)
                .resourceIds("rid")
                .scopes("all")
                .secret("$2a$10$K0uU50HPrlkFdij/vVdckuV2Waja3Wza6Jb3YulDk/f51.7tEjumG");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /**
         * 配置令牌的存储，AuthenticationManager和UserDetailsService主要用于支持
         * password模式以及令牌的刷新
         */
        endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }
}
