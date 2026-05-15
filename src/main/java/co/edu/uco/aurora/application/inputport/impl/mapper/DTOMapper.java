package co.edu.uco.aurora.application.inputport.impl.mapper;

public interface DTOMapper <T, D>{

    T toDTO(D domain);

    D  toDomain(T dto);

}
