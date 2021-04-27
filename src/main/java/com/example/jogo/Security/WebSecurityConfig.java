//package com.example.jogo.Security;
//
//import com.example.jogo.ServiceImpl.CustomUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import javax.annotation.Resource;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Resource
//    private CustomUserService customUserService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        // 配置跨域设置
//        http.csrf().disable();
//        http.cors().configurationSource(CorsConfigurationSource());
////        http.authorizeRequests().antMatchers("/index/*").permitAll();
////        http.authorizeRequests().antMatchers("/login/test").hasRole("QU");
////
////        http.formLogin().loginPage("/login").loginProcessingUrl("/loginPage").usernameParameter("username").passwordParameter("password").failureForwardUrl("/error").defaultSuccessUrl("/login/success").permitAll()
////        .failureHandler(new AuthenticationFailureHandler() {
////            @Override
////            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
////                PrintWriter printWriter = httpServletResponse.getWriter();
////                StringBuffer sb = new StringBuffer();
////                sb.append("{\"status\":-1}");
////                printWriter.write(sb.toString());
////                printWriter.flush();
////                printWriter.close();
////            }
////        }).successHandler(new AuthenticationSuccessHandler() {
////            @Override
////            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
////
////            }
////        });
////        super.configure(http);
//    }
//
//    @Autowired
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.userDetailsService(customUserService).passwordEncoder(new MyPasswordEncoder());
//    }
//
//    private CorsConfigurationSource CorsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source =   new UrlBasedCorsConfigurationSource();
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("http://localhost:8080");	//同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
//        corsConfiguration.addAllowedHeader("*");//header，允许哪些header，本案中使用的是token，此处可将*替换为token；
//        corsConfiguration.addAllowedMethod("*");	//允许的请求方法，PSOT、GET等
//        source.registerCorsConfiguration("/**",corsConfiguration); //配置允许跨域访问的url
//        return source;
//    }
//
//
//}
