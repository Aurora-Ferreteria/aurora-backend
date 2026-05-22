package co.edu.uco.aurora.crosscutting.messagescatalog;

import co.edu.uco.aurora.crosscutting.helper.TextHelper;

public enum MessagesEnum {

    // =================================================================================
    // REGLAS DE VALIDACIÓN: Formato de Cadenas
    // =================================================================================
    STRING_FORMAT_VALUES_IS_VALID_RULE_DATA_IS_NULL(
            "No se recibieron los parámetros requeridos para validar el formato del texto."),
    STRING_FORMAT_VALUES_IS_VALID_RULE_DATA_LENGTH_INVALID(
            "No se recibió la cantidad de datos necesarios para validar el formato del texto."),
    STRING_FORMAT_VALUES_IS_VALID_RULE_FORMAT_IS_INVALID(
            "El dato [{0}] no cumple con el formato esperado."),

    // =================================================================================
    // REGLAS DE VALIDACIÓN: Longitud de Cadenas
    // =================================================================================
    STRING_LENGTH_VALUES_IS_VALID_RULE_DATA_IS_NULL(
            "No se recibieron los parámetros requeridos para validar la longitud del texto."),
    STRING_LENGTH_VALUES_IS_VALID_RULE_DATA_LENGTH_INVALID(
            "No se recibió la cantidad de datos necesarios para validar la longitud del texto."),
    STRING_LENGTH_VALUES_IS_VALID_RULE_LENGTH_IS_INVALID(
            "La longitud del dato [{0}] no es válida. Debe estar entre {1} y {2} caracteres."),

    // =================================================================================
    // REGLAS DE VALIDACIÓN: Presencia de Valores
    // =================================================================================
    STRING_VALUELS_PRESENT_RULE_DATA_IS_NULL(
            "No se recibieron los parámetros requeridos para validar la presencia del dato."),
    STRING_VALUELS_PRESENT_RULE_DATA_LENGTH_INVALID(
            "No se recibió la cantidad de datos necesarios para validar la presencia del dato."),
    STRING_VALUELS_PRESENT_RULE_DATA_IS_EMPTY(
            "El dato [{0}] es requerido y no puede estar vacío."),

    // =================================================================================
    // REGLAS DE VALIDACIÓN: Identificadores (UUID)
    // =================================================================================
    ID_VALUE_IS_NOT_DEFAULT_RULE_DATA_IS_NULL(
            "No se recibieron los parámetros requeridos para validar el identificador."),
    ID_VALUE_IS_NOT_DEFAULT_RULE_DATA_LENGTH_INVALID(
            "No se recibió la cantidad de datos necesarios para validar el identificador."),
    ID_VALUE_IS_NOT_DEFAULT_RULE_UUID_IS_NULL(
            "El identificador de [{0}] es requerido y no puede ser nulo."),
    ID_VALUE_IS_NOT_DEFAULT_RULE_UUID_IS_DEFAULT(
            "El identificador de [{0}] no es un valor válido."),

    // =================================================================================
    // REGLAS DE NEGOCIO: Tipos de Identificación
    // =================================================================================
    ID_TYPE_EXISTS_BY_ID_RULE_DATA_IS_NULL(
            "No se recibieron los parámetros requeridos para consultar el tipo de identificación."),
    ID_TYPE_EXISTS_BY_ID_RULE_DATA_LENGTH_INVALID(
            "No se recibió la cantidad de datos necesarios para consultar el tipo de identificación."),
    ID_TYPE_EXISTS_BY_ID_RULE_ID_TYPE_NOT_FOUND(
            "El tipo de identificación proporcionado no es válido o no existe en el sistema."),

    // =================================================================================
    // REGLAS DE NEGOCIO: Clientes (Identificación)
    // =================================================================================
    CUSTOMER_ID_NUMBER_DOES_NOT_EXIST_RULE_DATA_IS_NULL(
            "No se recibieron los parámetros requeridos para validar la existencia del número de identificación."),
    CUSTOMER_ID_NUMBER_DOES_NOT_EXIST_RULE_DATA_LENGTH_INVALID(
            "No se recibió la cantidad de datos necesarios para validar la existencia del número de identificación."),
    CUSTOMER_ID_NUMBER_DOES_NOT_EXIST_RULE_CUSTOMER_ALREADY_EXISTS(
            "El número de identificación ingresado ya se encuentra registrado en el sistema."),

    // =================================================================================
    // REGLAS DE NEGOCIO: Clientes (Teléfono)
    // =================================================================================
    CUSTOMER_PHONE_NUMBER_DOES_NOT_EXIST_RULE_DATA_IS_NULL(
            "No se recibieron los parámetros requeridos para validar la existencia del número de teléfono."),
    CUSTOMER_PHONE_NUMBER_DOES_NOT_EXIST_RULE_DATA_LENGTH_INVALID(
            "No se recibió la cantidad de datos necesarios para validar la existencia del número de teléfono."),
    CUSTOMER_PHONE_NUMBER_DOES_NOT_EXIST_RULE_CUSTOMER_ALREADY_EXISTS(
            "El número de teléfono ingresado ya se encuentra registrado en el sistema."),

    // =================================================================================
    // REGLAS DE NEGOCIO: Clientes (Correo Electrónico)
    // =================================================================================
    CUSTOMER_EMAIL_DOES_NOT_EXIST_RULE_DATA_IS_NULL(
            "No se recibieron los parámetros requeridos para validar la existencia del correo electrónico."),
    CUSTOMER_EMAIL_DOES_NOT_EXIST_RULE_DATA_LENGTH_INVALID(
            "No se recibió la cantidad de datos necesarios para validar la existencia del correo electrónico."),
    CUSTOMER_EMAIL_DOES_NOT_EXIST_RULE_CUSTOMER_ALREADY_EXISTS(
            "El correo electrónico ingresado ya se encuentra registrado en el sistema."),

    // =================================================================================
    // MENSAJES DE ÉXITO Y ERROR EN CONTROLADORES
    // =================================================================================
    SUCCESS_OPERATION(
            "Operación realizada con éxito."),
    CUSTOMERS_UNEXPECTED_ERROR(
            "Se ha presentado un problema inesperado al intentar registrar el nuevo cliente."),
    FIND_CUSTOMER_ERROR(
            "Se ha presentado un problema inesperado al intentar consultar los clientes."),
    FIND_IDENTIFICATION_TYPE_ERROR(
            "Se ha presentado un problema inesperado al intentar consultar los tipos de identificación."),

    // =================================================================================
    // SERVICIOS EXTERNOS (NOTIFICACIÓN)
    // =================================================================================
    WELCOME_EMAIL_SENDING_ERROR(
            "El cliente fue registrado con éxito, pero no se pudo enviar el correo de bienvenida. Detalle: {0}"),
    RESEND_SERVICE_SENDING_ERROR(
            "Ocurrió un problema con el proveedor de correos al intentar enviar la notificación. Detalle: {0}"),

    // =================================================================================
    // EXCEPCIONES GLOBALES
    // =================================================================================
    ERROR_HTTP_MESSAGE_NOT_READABLE_GENERIC(
            "El formato de los datos enviados en la petición no es válido. Por favor, revise los campos.");

    private final String message;

    private MessagesEnum(final String message) {
        this.message = TextHelper.getDefaultWithTrim(message);
    }

    public String getMessage() {
        return message;
    }
}