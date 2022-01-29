package com.example.backswim.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    //로그인 실패 핸들러
    @Bean
    UserAuthenticationFailureHandler getFailureHandler(){return new UserAuthenticationFailureHandler();}
    
    //Password Encoder
    @Bean
    PasswordEncoder getPasswordEncoder(){return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();

        //보안사항 제외할 페이지 설정
        http.authorizeRequests().antMatchers("/admin/makedatabaseset","/api/pool/getpoolmapforlocate","/api/pooldetail/getpooldetail").permitAll();

        //http.formLogin()
        //        .loginPage("")
        //        .permitAll();

        //로그아웃 관련 부분 설정
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);

        super.configure(http);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        //auth.userDetailsService(null)
        //        .passwordEncoder(getPasswordEncoder());

        super.configure(auth);
    }
}
