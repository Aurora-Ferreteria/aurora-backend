package co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StrapiNotificationDataDTO {
    private Long id;
    private String documentId;
    private String key;
    private String value;
    private String locale;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDocumentId() { return documentId; }
    public void setDocumentId(String documentId) { this.documentId = documentId; }
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getLocale() { return locale; }
    public void setLocale(String locale) { this.locale = locale; }
}