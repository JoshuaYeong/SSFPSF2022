package com.example.demo.configuration;

// import java.time.Duration;

// import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.cache.RedisCacheConfiguration;
// import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

// import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

// @Configuration
// public class CacheConfig {

//     @Bean
//     public RedisCacheConfiguration cacheConfiguration() {
//     return RedisCacheConfiguration.defaultCacheConfig()
//       .entryTtl(Duration.ofMinutes(60))
//       .disableCachingNullValues()
//       .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
//     }

//     @Bean
//     public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
//         return (builder) -> builder
//         .withCacheConfiguration("itemCache",
//             RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
//         .withCacheConfiguration("customerCache",
//             RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));
//     }
    
// }