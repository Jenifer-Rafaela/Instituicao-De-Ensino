package com.A3.Curso.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.function.RequestPredicates;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers ->headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(httpRequests -> httpRequests
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers("/login","/logout", "/js/**", "/images/**")
                        .permitAll()
                        .requestMatchers("/admin/**")
                        .hasAuthority("ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/admin/home", true)
                        .failureUrl("/login?error")
                )
                .exceptionHandling(exception -> exception.accessDeniedPage("/forbidden"))
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                );
        return http.build();
    }

    @Bean
    AuthenticationProvider authenticationProvider(){
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(userDetailsService);
            provider.setPasswordEncoder(new BCryptPasswordEncoder());
            return provider;
    }

}
