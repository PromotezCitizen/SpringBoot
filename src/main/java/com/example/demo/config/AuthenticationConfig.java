package com.example.demo.config;

import com.example.demo.token.JwtFilter;
import org.hibernate.StatelessSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthenticationConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                ); // swagger
        http
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers(HttpMethod.POST,"/posts").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/posts/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/posts/{id}").authenticated()
                ) // post
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/comments/**").authenticated()
                ) // comment
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers(HttpMethod.POST,"/boards").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/boards/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/boards/{id}").authenticated()
                        .anyRequest().denyAll()
                ); // board

        http
                .formLogin(AbstractHttpConfigurer::disable);

        http
                .csrf(AbstractHttpConfigurer::disable);

        http
                .sessionManagement( session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

//        http.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
