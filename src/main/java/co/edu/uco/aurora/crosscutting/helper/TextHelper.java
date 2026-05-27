package co.edu.uco.aurora.crosscutting.helper;

import java.text.MessageFormat;
import java.util.regex.Pattern;

public final class TextHelper {

    public static String EMAIL_REGEX;
    public static String PHONE_REGEX;
    public static String NAME_REGEX;
    public static String ID_NUMBER_REGEX;

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
