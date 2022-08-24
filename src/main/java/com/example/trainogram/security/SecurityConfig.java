package com.example.trainogram.security;

import com.example.trainogram.model.Role;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(@Qualifier("customUserDetailsService") CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);

        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                /*.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()*/
                .authorizeRequests()
                /*.antMatchers("/auth/**").permitAll()
                .antMatchers("/users/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                .antMatchers("/admin/**").hasRole(Role.ADMIN.name())*/
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/users/**").authenticated()
                .antMatchers("/admin/**").authenticated()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth/login")
                .defaultSuccessUrl("/")
                .and()
                .logout().logoutUrl("/auth/logout")
                .logoutSuccessUrl("/")
                .and()
                .httpBasic();
    }
}
