package co.edu.uco.aurora.infrastructure.externalservices.notificationcatalog.dto;

import java.util.List;

public class StrapiNotificationResponseDTO {
    private List<StrapiNotificationDataDTO> data;


    public List<StrapiNotificationDataDTO> getData() { return data; }
    public void setData(List<StrapiNotificationDataDTO> data) { this.data = data; }
}