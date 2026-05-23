package co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.dto;

public class MessageAttributesDTO {
    private String key;
    private String value;
    private String locale;


    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getLocale() { return locale; }
    public void setLocale(String locale) { this.locale = locale; }
}