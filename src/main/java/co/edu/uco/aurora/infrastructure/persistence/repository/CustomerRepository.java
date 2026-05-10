package co.edu.uco.aurora.infrastructure.persistence.repository;

import co.edu.uco.aurora.infrastructure.persistence.repository.entity.CustomerEntity;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository {

    void create(CustomerEntity entity);

    void update(UUID id, CustomerEntity entity);

    void delete(UUID id);

    List<CustomerEntity> findAll();

    CustomerEntity findById(CustomerEntity filter);

    CustomerEntity findById(UUID id);

}
