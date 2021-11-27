package com.tmb.recordmanager;

import com.tmb.recordmanager.rest.RecordManagerController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecordManagerTestConfiguration {

    @Bean
    public RecordManagerController recordManagerController() {
        return new RecordManagerController();
    }
}
