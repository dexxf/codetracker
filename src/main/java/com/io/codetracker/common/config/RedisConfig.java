package com.io.codetracker.common.config;

import com.io.codetracker.Application;
import com.io.codetracker.adapter.classroom.out.persistence.mapper.ClassroomStudentJacksonMixIn;
import com.io.codetracker.adapter.user.out.persistence.mapper.UserJacksonMixIn;
import com.io.codetracker.domain.classroom.entity.ClassroomStudent;
import com.io.codetracker.domain.user.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {

        GenericJacksonJsonRedisSerializer serializer =
                GenericJacksonJsonRedisSerializer.builder()
                        .customize(
                                builder -> builder
                                        .addMixIns(getMixIns())
                        )
                        .enableDefaultTyping(
                                BasicPolymorphicTypeValidator.builder()
                                        .allowIfSubType(Application.class.getPackageName())
                                        .build()
                        )
                        .build();

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(serializer)
                );

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }


    /**
     *  it's for classes that does not have public constructor
     * @return map of mixIns.
     */
    private Map<Class<?>, Class<?>> getMixIns() {
        Map<Class<?>, Class<?>> mixIns = new HashMap<>();

        mixIns.put(User.class, UserJacksonMixIn.class);
        mixIns.put(ClassroomStudent.class, ClassroomStudentJacksonMixIn.class);

        return mixIns;

    }
}