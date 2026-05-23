package co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class StrapiMessageResponseDTO {
    private Object data;

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}
