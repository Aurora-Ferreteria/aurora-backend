package co.edu.uco.aurora.crosscutting.exception;

import co.edu.uco.aurora.crosscutting.helper.ObjectHelper;
import co.edu.uco.aurora.crosscutting.helper.TextHelper;

public class AuroraException extends RuntimeException {

    private static final long serialVersionUID = -7034897190745766939L;

    private final Throwable rootException;
    private final String userMessage;
    private final String technicalMessage;

    private AuroraException(final Throwable rootException, final String userMessage, final String technicalMessage) {
        super(TextHelper.getDefaultWithTrim(technicalMessage), ObjectHelper.getDefault(rootException, new Exception()));
        this.rootException = ObjectHelper.getDefault(rootException, new Exception());
        this.userMessage = TextHelper.getDefaultWithTrim(userMessage);
        this.technicalMessage = TextHelper.getDefaultWithTrim(technicalMessage);
    }

    public static AuroraException create(final String userMessage) {
        return new AuroraException(new Exception(), userMessage, userMessage);
    }

    public static AuroraException create(final String userMessage, final String technicalMessage) {
        return new AuroraException(new Exception(), userMessage, technicalMessage);
    }

    public static AuroraException create(final Throwable rootException, final String userMessage, final String technicalMessage) {
        return new AuroraException(rootException, userMessage, technicalMessage);
    }

    public Throwable getRootException() {
        return rootException;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getTechnicalMessage() {
        return technicalMessage;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}