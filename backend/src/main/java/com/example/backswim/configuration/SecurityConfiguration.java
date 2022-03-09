package com.example.backswim.configuration;

import com.example.backswim.component.JwtComponent;
import com.example.backswim.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    //로그인 실패 핸들러
    @Bean
    UserAuthenticationFailureHandler getFailureHandler(){return new UserAuthenticationFailureHandler();}
    
    //Password Encoder
    @Bean
    PasswordEncoder getPasswordEncoder(){return new BCryptPasswordEncoder();
    }

    private final JwtComponent jwtComponent;
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //보안사항 제외할 페이지 설정
        http.authorizeRequests().antMatchers("/admin/makedatabaseset",
                "/api/pool/getpoolmapforlocate",
                "/api/pooldetail/getpooldetail",
                "/api/search/searchquery",
                "/admin/makechosung",
                "/api/search/searchaddress",
                "/api/joinmember/*",
                "/api/login/denied",
                "/api/login/login").permitAll()
                .anyRequest().hasRole("USER")
                .and().addFilterBefore(new JwtAuthenticationFilter(jwtComponent), UsernamePasswordAuthenticationFilter.class);
                ;

        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/api/logout/logout"))
                .logoutSuccessUrl("/api/login/logout").deleteCookies().invalidateHttpSession(false);

        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                response.sendRedirect("/api/login/denied");
            }
        });

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());

        super.configure(auth);
    }
}
