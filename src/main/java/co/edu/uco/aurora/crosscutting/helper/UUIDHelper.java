package co.edu.uco.aurora.crosscutting.helper;

import java.util.UUID;

public final class UUIDHelper {

    private static final String UUID_DEFAULT_AS_STRING = "00000000-0000-0000-0000-000000000000";

    private UUIDHelper() {
    }

    public static UUID getDefault() {
        return UUID.fromString(UUID_DEFAULT_AS_STRING);
    }

    public static UUID getDefault(final UUID value) {
        return ObjectHelper.getDefault(value, getDefault());
    }

    public static UUID getFromString(final String uuidAsString) {
        return TextHelper.isEmpty(uuidAsString) ? getDefault() : UUID.fromString(uuidAsString);
    }

    public static boolean isDefaultUUID(final UUID value) {
        return getDefault().equals(getDefault(value));
    }

    public static UUID generateNewUUID() {
        return UUID.randomUUID();
    }
}