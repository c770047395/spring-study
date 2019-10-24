package com.cp.config;

import com.cp.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.cp.pojo")//显式扫描包
@Import(MyConfig2.class)
public class MyConfig {

    //注册一个bean,相当于我们之前写的一个bean标签
    //这个方法的名字相当于bean的id
    //返回值相当于bean中的class属性
    @Bean
    public User getUser(){
        return new User();
    }
}
