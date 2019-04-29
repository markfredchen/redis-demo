package com.cloudhelios.redisdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@SpringBootApplication
@EnableCaching
public class RedisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner redisDemoRunner(FooService fooService) {
        return args -> {
            fooService.getById(1000L);
            fooService.getById(2000L);
        };
    }

}

@Data
@AllArgsConstructor
class Foo implements Serializable {
    private Long id;
    private String foo;
}

@Service
@CacheConfig(cacheNames = "FOO")
class FooService {
    @Cacheable()
    public Foo getById(Long id) {
        return new Foo(id, RandomStringUtils.randomAlphabetic(6));
    }
}