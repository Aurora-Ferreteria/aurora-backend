package co.edu.uco.aurora.application.usecase.impl.mapper;

public interface EntityMapper <E, D>{

    E toEntity(D domain);

    D toDomain(E entity);
}
