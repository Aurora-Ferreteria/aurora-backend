package co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StrapiNotificationResponseDTO {
    private List<StrapiData> data;

    public List<StrapiData> getData() { return data; }
    public void setData(List<StrapiData> data) { this.data = data; }

    // Clase interna para Data
    public static class StrapiData {
        private StrapiAttributes attributes;

        public StrapiAttributes getAttributes() { return attributes; }
        public void setAttributes(StrapiAttributes attributes) { this.attributes = attributes; }
    }

    // Clase interna para Attributes
    public static class StrapiAttributes {
        private String key;
        private String value;

        public String getKey() { return key; }
        public void setKey(String key) { this.key = key; }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
    }
}





