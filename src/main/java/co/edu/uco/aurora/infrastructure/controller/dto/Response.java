package co.edu.uco.aurora.infrastructure.controller.dto;



import co.edu.uco.aurora.crosscutting.helper.ObjectHelper;
import co.edu.uco.aurora.crosscutting.helper.TextHelper;

import java.util.ArrayList;
import java.util.List;

public final class Response <T>{

    private List<String> messages;
    private List<T> data;
    boolean responseSucceded;

    public Response (List<String> messages, List<T> data ,final boolean responseSucceded) {
        setMessages(messages);
        setData(data);
        setResponseSucceded(responseSucceded);
    }

    public static <T> Response<T> createSuccededResponse() {
        return new Response<>(new ArrayList<>(), new ArrayList<>(), true);
    }

    public static <T> Response<T> createFailedResponse() {
        return new Response<>(new ArrayList<>(), new ArrayList<>(), false);
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(final List<String> messages) {
        this.messages = ObjectHelper.getDefault(messages, new ArrayList<>());
    }

    public void addMessage(final String message) {

        if (!TextHelper.isEmptyWithTrim(message)){
            getMessages().add(message);
        }
    }

    public List<T> getData() {
        return data;
    }

    public void setData(final List<T> data) {
        this.data = ObjectHelper.getDefault(data, new ArrayList<>());
    }



    public void setResponseSucceded(final boolean responseSucceded) {
        this.responseSucceded = responseSucceded;
    }
}

