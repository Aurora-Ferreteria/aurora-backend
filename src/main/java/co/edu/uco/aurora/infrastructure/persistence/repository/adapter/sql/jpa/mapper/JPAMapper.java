package co.edu.uco.aurora.infrastructure.persistence.repository.adapter.sql.jpa.mapper;

public interface JPAMapper <E, J> {

    J toJPAEntity(E entity);

    E toEntity(J jpaEntity);

}
