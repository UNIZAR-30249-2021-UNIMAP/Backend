package com.labis.appserver.common;

public class Constantes {
    //Strings de tipo de petición a la API
    public static final String STRING_LOGIN = "/login";
    public static final String STRING_INCIDENCIA = "/incidencia"; // Get: Todas las incidencias
    public static final String STRING_INCIDENCIA_REPORTE = "/incidencia/reporte"; // Post: Reporta la incidencia
    public static final String STRING_REGISTRO = "/registro";
    public static final String STRING_INCIDENCIA_MANTENIMIENTO = "/incidencia/mantenimiento"; // Get: Incidencias de un empleado Post: finalizar incidencia
    public static final String STRING_INCIDENCIA_ADMIN = "/incidencia/administrador"; // Asignar/rechazar incidencias
    public static final String STRING_MANTENIMIENTO = "/mantenimiento"; // Get: lista de empleados
    public static final String STRING_ESPACIO = "/espacio"; // Get: info de un espacio Post: reserva espacio
    public static final String STRING_AFORO = "/aforo"; // Get: lista de espacios y su aforo Post: aforo o regla de aforo
    public static final String STRING_ESPACIOS = "/espacios"; // Get: lista de espacios parametrizada

    //Strings de prioridad de una incidencia
    public static final String STRING_PRIORIDAD_NORMAL =  "NORMAL";
    public static final String STRING_PRIORIDAD_URGENTE =  "URGENTE";

    //Strings de tipo de código que puede devolver una petición al cliente
    public static final String STRING_STATUS_OK = "OK";
    public static final String STRING_STATUS_ERROR = "error";
}