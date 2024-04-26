package br.com.fiap.docschedule.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import br.com.fiap.docschedule.auth.CustomUserService;

@Configuration
public class SecurityConfig {

    @Autowired
    CustomUserService customUserService;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/login.css", "style.css")
                    // .requestMatchers("*.css")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
                .oauth2Login(login -> login
                    .loginPage("/login")
                    .defaultSuccessUrl("/consultas", true)
                    .userInfoEndpoint(userInfo -> userInfo.userService(customUserService))
                    .permitAll()
                )
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login").permitAll())
                .build();
    }

}
