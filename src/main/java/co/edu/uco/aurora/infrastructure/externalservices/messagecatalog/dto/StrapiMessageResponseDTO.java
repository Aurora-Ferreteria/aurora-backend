package co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.dto;

import java.util.List;

public class StrapiMessageResponseDTO {
    private List<StrapiMessageDataDTO> data;

    public List<StrapiMessageDataDTO> getData() { return data; }
    public void setData(List<StrapiMessageDataDTO> data) { this.data = data; }
}