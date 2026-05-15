package co.edu.uco.aurora.infrastructure.persistence.repository.adapter.sql.jpa.mapper;

import java.util.List;

public interface JPAMapper <E, J> {

    J toJPAEntity(E entity);
    E toEntity(J jpaEntity);

    List<J> toJPAEntities(List<E> entities);
    List<E> toEntities(List<J> jpaEntities);

}