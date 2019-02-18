package com.collmall.config;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * Mybatis 的配置
 * @Author: xulihui
 * @Date: 2019/1/24 14:48
 */
@MapperScan("com.collmall.mapper")
@org.springframework.context.annotation.Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer(){

            @Override
            public void customize(Configuration configuration) {
                //使用jdbc的getGeneratedKeys获取数据库自增主键值
                configuration.setUseGeneratedKeys(true);
                //使用列别名替换列名 select user as User
                configuration.setUseColumnLabel(true);
                //-自动使用驼峰命名属性映射字段   userId    user_id
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
