package co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto;

public class StrapiNotificationDataDTO {
    private Long id;
    private String documentId; // Strapi 5 usa esto como identificador principal
    private String key;        // Tu campo personalizado
    private String value;      // Tu campo personalizado

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDocumentId() { return documentId; }
    public void setDocumentId(String documentId) { this.documentId = documentId; }
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}