package com.io.kira.common.config;

import com.io.kira.Application;
import com.io.kira.adapter.announcement.out.persistence.mapper.AnnouncementJacksonMixIn;
import com.io.kira.adapter.classroom.out.persistence.mapper.ClassroomStudentJacksonMixIn;
import com.io.kira.adapter.user.out.persistence.mapper.UserJacksonMixIn;
import com.io.kira.domain.announcement.entity.Announcement;
import com.io.kira.domain.classroom.entity.ClassroomStudent;
import com.io.kira.domain.user.entity.User;
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
                                        .allowIfSubType("java.util")
                                        .allowIfSubType("java.time")
                                        .allowIfSubType("java.lang")
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
                .transactionAware()
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
        mixIns.put(Announcement.class, AnnouncementJacksonMixIn.class);

        return mixIns;

    }
}
