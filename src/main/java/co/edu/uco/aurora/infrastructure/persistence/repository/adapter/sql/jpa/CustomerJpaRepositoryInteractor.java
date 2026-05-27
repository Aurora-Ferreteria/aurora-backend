package co.edu.uco.aurora.infrastructure.persistence.repository.adapter.sql.jpa;

import co.edu.uco.aurora.infrastructure.persistence.repository.CustomerRepository;
import co.edu.uco.aurora.infrastructure.persistence.repository.adapter.sql.jpa.mapper.customer.CustomerJpaMapper;
import co.edu.uco.aurora.infrastructure.persistence.repository.entity.CustomerEntity;
import co.edu.uco.aurora.infrastructure.persistence.repository.sql.jpa.CustomerJpaRepository;
import co.edu.uco.aurora.infrastructure.persistence.repository.sql.jpa.entity.CustomerJpaEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class CustomerJpaRepositoryInteractor implements CustomerRepository {

    private final CustomerJpaRepository repository;
    private final CustomerJpaMapper mapper;

    public CustomerJpaRepositoryInteractor(CustomerJpaRepository repository, CustomerJpaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @CacheEvict(value = "customers", allEntries = true)
    public void create(CustomerEntity entity) {
        // CustomerEntity -> CustomerJPAEntity (MAPPER)
        CustomerJpaEntity jpaEntity = mapper.toJPAEntity(entity);
        repository.save(jpaEntity);

    }

    @Override
    @CacheEvict(value = {"customers", "customer"}, allEntries = true)
    public void update(UUID id, CustomerEntity entity) {
        // CustomerEntity -> CustomerJPAEntity (MAPPER)
        CustomerJpaEntity jpaEntity = mapper.toJPAEntity(entity);
        jpaEntity.setId(id);
        repository.save(jpaEntity);
    }

    @Override
    @CacheEvict(value = {"customers", "customer"}, allEntries = true)
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    @Cacheable(value = "customers")
    public List<CustomerEntity> findAll() {
        return repository.findAll().stream()
                .map(mapper::toEntity)
                .toList();
    }

    @Override
    public CustomerEntity findById(CustomerEntity filter) {
        return null;
    }

    @Override
    @Cacheable(value = "customer", key = "#id")
    public CustomerEntity findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toEntity)
                .orElse(null);
    }

    @Override
    public boolean existsByIdentificationNumber(String identificationNumber) {
        return repository.existsByIdentificationNumber(identificationNumber);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return repository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }
}
