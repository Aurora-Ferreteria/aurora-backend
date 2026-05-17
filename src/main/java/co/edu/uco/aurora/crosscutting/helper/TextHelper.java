package co.edu.uco.aurora.crosscutting.helper;

import java.text.MessageFormat;
import java.util.regex.Pattern;

public final class TextHelper {

    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String PHONE_REGEX = "^\\+?\\d{7,20}$";
    public static final String NAME_REGEX = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{3,100}$";
    public static final String ID_NUMBER_REGEX = "^[0-9]+$";

    private static final String EMPTY = "";

    private TextHelper() {
    }

    public static String getDefault() {
        return EMPTY;
    }

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
        var length = (mustApplyTrim
                ? getDefaultWithTrim(value)
                : getDefault(value)).length();
        return length >= min && length <= max;
    }

    public static boolean matchesRegex(final String text, final String regex) {
        if (isEmpty(text) || isEmpty(regex)) {
            return false;
        }
        return Pattern.matches(regex, text);
    }

    public static String format(final String message, final String... params) {
        if (isEmpty(message)) {
            return getDefault();
        }
        return MessageFormat.format(message, (Object[]) params);
    }
}
