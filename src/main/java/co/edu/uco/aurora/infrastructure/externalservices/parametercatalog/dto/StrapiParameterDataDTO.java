package co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.dto;

public class StrapiParameterDataDTO {
    private Long id;
    private String documentId;
    private String key;
    private String value;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDocumentId() { return documentId; }
    public void setDocumentId(String documentId) { this.documentId = documentId; }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}