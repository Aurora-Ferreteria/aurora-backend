package co.edu.uco.aurora.features.customer.addcustomer.application.usecase.mapper;

public interface EntityMapper <E, D>{

    E toEntity(D domain);

    D toDomain(E entity);
}
