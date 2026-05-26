package co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto;

public class NotificationCatalogDTO {
    private String key;
    private String value;

    public NotificationCatalogDTO(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() { return key; }
    public String getValue() { return value; }
}