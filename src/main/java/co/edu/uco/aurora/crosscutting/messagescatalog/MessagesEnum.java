package co.edu.uco.aurora.crosscutting.messagescatalog;

import co.edu.uco.aurora.crosscutting.helper.TextHelper;

public enum MessagesEnum {

    STRING_FORMAT_VALUES_IS_VALID_RULE_DATA_IS_NULL(
            "Se ha presentado un problema inesperado tratando de llevar a cabo la operación deseada.",
            "No se recibieron los parámetros requeridos para ejecutar la regla StringFormatValuesIsValidRule."
    ),
    STRING_FORMAT_VALUES_IS_VALID_RULE_DATA_LENGTH_INVALID(
            "Se ha presentado un problema inesperado tratando de llevar a cabo la operación deseada.",
            "Se requerían cuatro parámetros y llegó una cantidad menor para ejecutar la regla StringFormatValuesIsValidRule."
    ),
    STRING_FORMAT_VALUES_IS_VALID_RULE_FORMAT_IS_INVALID(
            "El dato [{0}] no cumple con el formato esperado.",
            "La regla StringFormatValuesIsValidRule falló porque el dato [{0}] no cumple con el formato (Regex): {1}"
    );


    private final String title;
    private final String content;

    private MessagesEnum(final String title, final String content) {
        this.title = TextHelper.getDefaultWithTrim(title);
        this.content = TextHelper.getDefaultWithTrim(content);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}