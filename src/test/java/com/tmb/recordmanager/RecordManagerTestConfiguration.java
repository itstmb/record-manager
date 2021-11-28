package com.tmb.recordmanager;

import com.tmb.recordmanager.business_logic.RecordManagerService;
import com.tmb.recordmanager.business_logic.RecordManagerServiceImpl;
import com.tmb.recordmanager.mocks.EntityManagerMock;
import com.tmb.recordmanager.repository.RecordManagerRepository;
import com.tmb.recordmanager.repository.RecordManagerRepositoryImpl;
import com.tmb.recordmanager.rest.RecordManagerController;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecordManagerTestConfiguration {

    @Bean
    public RecordManagerController recordManagerController(RecordManagerService recordManagerService) {
        return new RecordManagerController(recordManagerService);
    }

    @Bean
    public RecordManagerService recordManagerService(RecordManagerRepository recordManagerRepository) {
        return new RecordManagerServiceImpl(recordManagerRepository);
    }

    @Bean
    public RecordManagerRepository recordManagerRepository(EntityManagerMock entityManagerMock) {
        return new RecordManagerRepositoryImpl(entityManagerMock);
    }

    @Bean
    public EntityManagerMock entityManagerMock() {
        return new EntityManagerMock();
    }

    @MockBean
    public SessionFactoryImpl sessionFactory;
}
