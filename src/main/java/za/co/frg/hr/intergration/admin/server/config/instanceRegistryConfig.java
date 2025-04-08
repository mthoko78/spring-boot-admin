//package za.co.frg.hr.intergration.admin.server.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
//import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
//import de.codecentric.boot.admin.server.services.InstanceRegistry;
//
//@Configuration
//public class instanceRegistryConfig {
//
//
//    @Bean
//    @Primary
//    @Autowired
//    public InstanceRegistry instanceRegistry(InstanceRepository repository, InstanceIdGenerator generator) {
//        return new CustomInstanceRegistry(repository, generator);
//    }
//}
