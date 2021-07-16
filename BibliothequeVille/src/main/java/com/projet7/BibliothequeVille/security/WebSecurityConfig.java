package com.projet7.BibliothequeVille.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Pour l'identification
    /*
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    // Pour annuler les autorisations sur les ressources
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/pictures/**");

    }

    // le gros de la configuration est ici
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
                .antMatchers("/bibliotheques/**","/webjars/**","/livres/**","/reservations/**","/","/user/**").permitAll()
                .antMatchers("/bibliottheques/addBibliotheque","/bibliottheques/updateBibliotheque","/bibliottheques/deleteBibliotheque/**","/livres/delete/**","/livres/addLivre","/livres/updateLivre","/reservations/reservation/**","/reservations/deleteReservation/**","/reservations/updateReservation/**").hasRole("ADMIN")
                .antMatchers().permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("my-remember-me-cookie")
                .permitAll()
                .and()
                .rememberMe()
                //.key("my-secure-key")
                .rememberMeCookieName("my-remember-me-cookie")
                //.tokenValiditySeconds(24 * 60 * 60)
                .and()
                .exceptionHandling()
        ;
    }
*/

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http
                .formLogin()
                .and()
                .authorizeRequests().anyRequest()
                .permitAll().and().csrf().disable()

                    /*.antMatchers("/bibliotheques/**",
                            "/webjars/**","/livres/**","/reservations/**",
                            "/","/user/**").permitAll()
                    .antMatchers("/bibliottheques/addBibliotheque","/bibliottheques/updateBibliotheque",
                            "/bibliottheques/deleteBibliotheque/**","/livres/delete/**",
                            "/livres/addLivre","/livres/updateLivre","/reservations/reservation/**","/reservations/deleteReservation/**",
                            "/reservations/updateReservation/**").hasRole("ADMIN")
                    .anyRequest().authenticated()*/

            http
                    .authorizeRequests().anyRequest().permitAll()
                    .and()
                    .httpBasic()
                    .and()
                    .csrf().disable()
                    .formLogin().disable();

/*
            http
                    .formLogin().loginPage("/login").failureUrl("/login?error=true")
                    .defaultSuccessUrl("/loggedhome")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .and()
                    .csrf().disable()
                    .authorizeRequests().anyRequest().permitAll()
                    .and()
                    .httpBasic();
*/
        //HTTP Basic authentication
        /*
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/books/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/books/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable();

           */



    }
}
