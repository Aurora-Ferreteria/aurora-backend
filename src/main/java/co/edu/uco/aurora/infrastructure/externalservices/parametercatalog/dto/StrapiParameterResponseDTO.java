package co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.dto;

import java.util.List;

public class StrapiParameterResponseDTO {
    private List<StrapiParameterDataDTO> data;

    public List<StrapiParameterDataDTO> getData() { return data; }
    public void setData(List<StrapiParameterDataDTO> data) { this.data = data; }
}