//package za.co.frg.hr.intergration.admin.server.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Field;
//import java.util.List;
//import java.util.concurrent.ConcurrentMap;
//
//import de.codecentric.boot.admin.server.domain.entities.Instance;
//import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
//import de.codecentric.boot.admin.server.domain.values.InstanceId;
//import de.codecentric.boot.admin.server.domain.values.Registration;
//import de.codecentric.boot.admin.server.services.InstanceIdGenerator;
//import de.codecentric.boot.admin.server.services.InstanceRegistry;
//import reactor.core.publisher.Mono;
//
//@Component
//public class CustomInstanceRegistry extends InstanceRegistry {
//
//    private static final Logger log = LoggerFactory.getLogger(CustomInstanceRegistry.class);
//
//    private final InstanceRepository repository;
//
//    @Autowired
//    public CustomInstanceRegistry(InstanceRepository repository, InstanceIdGenerator generator) {
//        super(repository, generator);
//        this.repository = repository;
//    }
//
//    @Override
//    public Mono<InstanceId> register(Registration registration) {
//        // fetch existing instances before registration
//        final List<Instance> instances = repository
//            .findByName(registration.getName())
//            .collectList().block();
//        final Mono<InstanceId> register = super.register(registration);
//        if (instances.stream().noneMatch(instance -> instance.getId().equals(register.block()))) {
//            // this is a new instance, therefore remove all other stale instances with the same name as incoming registration
//            instances
//                .stream()
//                .filter(instance -> instance.getStatusInfo().isOffline())
//                .forEach(instance -> removeStaleInstance(instance));
//        }
//        return register;
//    }
//
//    private void removeStaleInstance(Instance instance) {
//        try {
//            deregister(instance.getId()).then();
//            final Field snapshotsField = repository.getClass().getDeclaredField("snapshots");
//            snapshotsField.setAccessible(true);
//            ConcurrentMap<InstanceId, Instance> snapshots = (ConcurrentMap<InstanceId, Instance>) snapshotsField.get(repository);
//            snapshots.remove(instance.getId());
//            log.info("Stale instance {} has been removed", instance.getId());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
