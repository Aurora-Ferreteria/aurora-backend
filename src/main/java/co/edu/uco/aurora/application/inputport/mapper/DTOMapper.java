package co.edu.uco.aurora.application.inputport.mapper;

public interface DTOMapper <T, D>{

    T toDTO(D domain);

    D  toDomain(T dto);

}
