//package com.example.pojo.ServiceImpl;
//
//import com.example.pojo.Entity.User;
//import com.example.pojo.Service.UserService;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//@Service
//public class CustomUserService implements UserDetailsService {
//
//    @Resource
//    private UserService userService;
//    static private final Logger logger = LogManager.getLogger(CustomUserService.class);
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        if(null==userService) System.out.println("userService是null");
//        // 获取user
//        User user = userService.searchByUsername(s);
//        if(null==user) {
//            throw new UsernameNotFoundException("找不到用户");
//        }
//        List<String> roles = user.getRoles();
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        // 添加角色
//        roles.forEach((roleName)->{
//            authorities.add(new SimpleGrantedAuthority(roleName));
//        });
//        System.out.println("登录信息 username:"+user.getUsername()+",password:"+user.getPassword()+",authorities:"+ authorities.toString());
//        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
//    }
//}
