package co.edu.uco.aurora.crosscutting.helper;

import co.edu.uco.aurora.infrastructure.externalservices.parametercatalog.ParameterCatalog;
import java.text.MessageFormat;
import java.util.regex.Pattern;

public final class TextHelper {

    private static ParameterCatalog parameterCatalog;

    private static final String EMPTY = "";

    private TextHelper() {}

    public static void setParameterCatalog(ParameterCatalog catalog) {
        TextHelper.parameterCatalog = catalog;
    }

    public static String getEmailRegex() {
        return parameterCatalog != null ? parameterCatalog.getParameterValue("EMAIL_REGEX") : null;
    }

    public static String getPhoneRegex() {
        return parameterCatalog != null ? parameterCatalog.getParameterValue("PHONE_REGEX") : null;
    }

    public static String getNameRegex() {
        return parameterCatalog != null ? parameterCatalog.getParameterValue("NAME_REGEX") : null;
    }

    public static String getIdNumberRegex() {
        return parameterCatalog != null ? parameterCatalog.getParameterValue("ID_NUMBER_REGEX") : null;
    }


    public static String getDefault() { return EMPTY; }

    public static String getDefault(final String value) {
        return ObjectHelper.getDefault(value, getDefault());
    }

    public static String getDefaultWithTrim(final String value) {
        return getDefault(value).trim();
    }

    public static boolean isEmpty(final String value) {
        return EMPTY.equals(getDefault(value));
    }

    public static boolean isEmptyWithTrim(final String value) {
        return EMPTY.equals(getDefaultWithTrim(value));
    }

    public static boolean lengthIsValid(final String value, final int min, final int max, final boolean mustApplyTrim) {
        var length = (mustApplyTrim ? getDefaultWithTrim(value) : getDefault(value)).length();
        return length >= min && length <= max;
    }

    public static boolean matchesRegex(final String text, final String regex) {
        if (isEmpty(text) || isEmpty(regex)) return false;
        return Pattern.matches(regex, text);
    }

    public static String format(final String message, final String... params) {
        if (isEmpty(message)) return getDefault();
        return MessageFormat.format(message, (Object[]) params);
    }
}