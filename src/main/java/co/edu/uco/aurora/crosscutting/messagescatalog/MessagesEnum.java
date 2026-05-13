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
    ),

    STRING_LENGTH_VALUES_IS_VALID_RULE_DATA_IS_NULL(
            "Se ha presentado un problema inesperado tratando de llevar a cabo la operación deseada.",
                    "No se recibieron los parámetros requeridos para ejecutar la regla StringLengthValuesIsValidRule."
    ),
    STRING_LENGTH_VALUES_IS_VALID_RULE_DATA_LENGTH_INVALID(
            "Se ha presentado un problema inesperado tratando de llevar a cabo la operación deseada.",
                    "Se requerían cinco parámetros y llegó una cantidad menor para ejecutar la regla StringLengthValuesIsValidRule."
    ),
    STRING_LENGTH_VALUES_IS_VALID_RULE_LENGTH_IS_INVALID(
            "La longitud del dato [{0}] no es válida. Debe estar entre {1} y {2} caracteres.",
                    "La regla StringLengthValuesIsValidRule falló porque la longitud del dato [{0}] no se encuentra en el rango permitido (Min: {1}, Max: {2})."
    ),


    STRING_VALUELS_PRESENT_RULE_DATA_IS_NULL(
            "Se ha presentado un problema inesperado tratando de llevar a cabo la operación deseada.",
                    "No se recibieron los parámetros requeridos para ejecutar la regla StringValueIsPresentRule."
    ),
    STRING_VALUELS_PRESENT_RULE_DATA_LENGTH_INVALID(
            "Se ha presentado un problema inesperado tratando de llevar a cabo la operación deseada.",
                    "Se requerían tres parámetros y llegó una cantidad menor para ejecutar la regla StringValueIsPresentRule."
    ),
    STRING_VALUELS_PRESENT_RULE_DATA_IS_EMPTY(
            "El dato [{0}] es requerido y no puede estar vacío.",
                    "La regla StringValueIsPresentRule falló porque el dato [{0}] se encuentra vacío, es nulo o contiene solo espacios en blanco."
    ),

    ID_VALUE_IS_NOT_DEFAULT_RULE_DATA_IS_NULL(
            "Se ha presentado un problema inesperado tratando de llevar a cabo la operación deseada.",
                    "No se recibieron los parámetros requeridos para ejecutar la regla IdValueIsNotDefaultValueRule."
    ),
    ID_VALUE_IS_NOT_DEFAULT_RULE_DATA_LENGTH_INVALID(
            "Se ha presentado un problema inesperado tratando de llevar a cabo la operación deseada.",
                    "Se requerían dos parámetros y llegó una cantidad menor para ejecutar la regla IdValueIsNotDefaultValueRule."
    ),
    ID_VALUE_IS_NOT_DEFAULT_RULE_UUID_IS_NULL(
            "El identificador de [{0}] es requerido y no puede ser nulo.",
                    "La regla IdValueIsNotDefaultValueRule falló porque el UUID para el dato [{0}] llegó nulo."
    ),
    ID_VALUE_IS_NOT_DEFAULT_RULE_UUID_IS_DEFAULT(
            "El identificador de [{0}] no es un valor válido.",
                    "La regla IdValueIsNotDefaultValueRule falló porque el UUID para el dato [{0}] es el valor por defecto (ceros)."
    ),


    ID_TYPE_EXISTS_BY_ID_RULE_DATA_IS_NULL(
            "Se ha presentado un problema inesperado tratando de llevar a cabo la operación deseada.",
                    "No se recibieron los parámetros requeridos para ejecutar la regla IdentificationTypeExistsByIdRule."
    ),
    ID_TYPE_EXISTS_BY_ID_RULE_DATA_LENGTH_INVALID(
            "Se ha presentado un problema inesperado tratando de llevar a cabo la operación deseada.",
                    "Se requerían dos parámetros y llegó una cantidad menor para ejecutar la regla IdentificationTypeExistsByIdRule."
    ),
    ID_TYPE_EXISTS_BY_ID_RULE_ID_TYPE_NOT_FOUND(
            "El tipo de identificación proporcionado no es válido o no existe en el sistema.",
                    "La regla IdentificationTypeExistsByIdRule falló porque no se encontró un registro en la base de datos con el ID: {0}"
    ),


    CUSTOMER_ID_NUMBER_DOES_NOT_EXIST_RULE_DATA_IS_NULL(
            "Se ha presentado un problema inesperado tratando de llevar a cabo la operación deseada.",
            "No se recibieron los parámetros requeridos para ejecutar la regla CustomerIdentificationNumberDoesNotExistRule."
    ),
    CUSTOMER_ID_NUMBER_DOES_NOT_EXIST_RULE_DATA_LENGTH_INVALID(
            "Se ha presentado un problema inesperado tratando de llevar a cabo la operación deseada.",
            "Se requerían dos parámetros y llegó una cantidad menor para ejecutar la regla CustomerIdentificationNumberDoesNotExistRule."
    ),
    CUSTOMER_ID_NUMBER_DOES_NOT_EXIST_RULE_CUSTOMER_ALREADY_EXISTS(
            "El número de identificación ingresado ya se encuentra registrado en el sistema.",
            "La regla CustomerIdentificationNumberDoesNotExistRule falló porque ya existe un cliente con el número de identificación: {0}"
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