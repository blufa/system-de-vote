package com.senatic.servervotingsystem.model.entity.enums;


public enum EstadoAprendiz {

    EN_FORMACION("EN FORMACION"),
    CANCELADO("CANCELADO"),
    RETIRADO("RETIRADO"),
    RETIRO_VOLUNTARIO("RETIRO VOLUNTARIO"),
    TRASLADADO("TRASLADADO"),
    SUSPENDIDO("SUSPENDIDO"),
    NOT_PROVIDED("NOT PROVIDED");

    private String plainText;

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    EstadoAprendiz(String plainText){
        this.plainText = plainText;
    } 

    public static EstadoAprendiz checkAndAssign(String status){

        EstadoAprendiz estadoAssigned = EstadoAprendiz.NOT_PROVIDED;

        if (EstadoAprendiz.EN_FORMACION.plainText.equals(status)) {
            estadoAssigned = EstadoAprendiz.EN_FORMACION;
        } else if (EstadoAprendiz.CANCELADO.plainText.equals(status)) {
            estadoAssigned = EstadoAprendiz.EN_FORMACION;
        } else if (EstadoAprendiz.CANCELADO.plainText.equals(status)) {
            estadoAssigned = EstadoAprendiz.EN_FORMACION;
        } else if (EstadoAprendiz.RETIRADO.plainText.equals(status)) {
            estadoAssigned = EstadoAprendiz.RETIRADO;
        } else if (EstadoAprendiz.RETIRO_VOLUNTARIO.plainText.equals(status)) {
            estadoAssigned = EstadoAprendiz.RETIRO_VOLUNTARIO;
        } else if (EstadoAprendiz.TRASLADADO.plainText.equals(status)) {
            estadoAssigned = EstadoAprendiz.TRASLADADO;
        } else if (EstadoAprendiz.SUSPENDIDO.plainText.equals(status)) {
            estadoAssigned = EstadoAprendiz.SUSPENDIDO;
        }

        return estadoAssigned;
    }

}
