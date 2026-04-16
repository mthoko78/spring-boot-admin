package com.mthoko.admin.server.config;

import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotifierConfiguration {
    private final InstanceRepository repository;

    public NotifierConfiguration(InstanceRepository repository) {
        this.repository = repository;
    }

    @Bean
    public CustomNotifier customNotifier() {
        return new CustomNotifier(repository);
    }
}