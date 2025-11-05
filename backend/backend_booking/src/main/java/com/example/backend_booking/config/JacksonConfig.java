package com.example.backend_booking.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Support Java 8 date/time types
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // Avoid failure on types like Hibernate proxies when module not present
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // Try to register Hibernate6Module if available on classpath
        try {
            Class<?> cls = Class.forName("com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module");
            Object module = cls.getDeclaredConstructor().newInstance();
            if (module instanceof com.fasterxml.jackson.databind.Module) {
                mapper.registerModule((com.fasterxml.jackson.databind.Module) module);
            }
        } catch (ClassNotFoundException ignored) {
            // Hibernate6Module not present - that's fine
        } catch (Exception ignored) {
            // Any other failure registering module - ignore to avoid startup failure
        }

        return mapper;
    }
}
