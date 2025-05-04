package com.desastres.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService; // Injete o UserDetailsService

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Se o perfil ativo for "test", desabilita a segurança
        if (isTestProfileActive()) {
            http
                .authorizeRequests()
                .anyRequest().permitAll()  // Permite todas as requisições no ambiente de teste
                .and()
                .csrf().disable();  // Desabilita o CSRF para os testes
        } else {
            // Configuração padrão de segurança
            http
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/desastres/**").authenticated()
                .antMatchers("/localizacoes/**").authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilterBefore(new JwtAuthorizationFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
        }
    }

    // Método para verificar se o perfil ativo é "test"
    private boolean isTestProfileActive() {
        return "test".equals(System.getProperty("spring.profiles.active"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
