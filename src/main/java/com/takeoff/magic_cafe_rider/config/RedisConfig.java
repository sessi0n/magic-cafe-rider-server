package com.takeoff.magic_cafe_rider.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

@Configuration
public class RedisConfig {
    private @Value("${spring.data.redis.host}") String redisHost;
    private @Value("${spring.data.redis.port}") int redisPort;
    private @Value("${spring.data.redis.password}") String redisPwd;

    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
        redisStandaloneConfiguration.setPassword(redisPwd); //redis에 비밀번호가 설정 되어 있는 경우 설정해주면 됩니다.
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
//        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

//    @Bean
//    JedisConnectionFactory connectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
//        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPwd));
////        return redisStandaloneConfiguration;
//        return new JedisConnectionFactory(redisStandaloneConfiguration);
//
////        returnJedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
////        jedisConnectionFactory.setHostName(redisHost);
////        jedisConnectionFactory.setPort(redisPort);
////        jedisConnectionFactory.setUsePool(true);
////        return redisStandaloneConfiguration;
//    }
//
//    @Bean
//    RedisTemplate< String, Object > redisTemplate() {
////        final RedisTemplate< String, Long > template =  new RedisTemplate< String, Long >();
////        template.setConnectionFactory( jedisConnectionFactory() );
////        template.setKeySerializer( new StringRedisSerializer() );
////        template.setHashValueSerializer( new GenericToStringSerializer< Long >( Long.class ) );
////        template.setValueSerializer( new GenericToStringSerializer< Long >( Long.class ) );
////        return template;
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        redisTemplate.setConnectionFactory(connectionFactory());
//        return redisTemplate;
//    }
}
