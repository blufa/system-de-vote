package com.senatic.servervotingsystem.model.entity.enums;


public enum EstadoAprendiz {

    EN_FORMACION("EN FORMACION"),
    CANCELADO("CANCELADO"),
    RETIRADO("RETIRADO"),
    TRASLADADO("TRASLADADO"),
    SUSPENDIDO("SUSPENDIDO");

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

}
