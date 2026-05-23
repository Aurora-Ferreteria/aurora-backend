package co.edu.uco.aurora.infrastructure.externalservices.messagecatalog.dto;

public class StrapiMessageDataDTO {
    private int id;
    private MessageAttributesDTO attributes;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public MessageAttributesDTO getAttributes() { return attributes; }
    public void setAttributes(MessageAttributesDTO attributes) { this.attributes = attributes; }
}