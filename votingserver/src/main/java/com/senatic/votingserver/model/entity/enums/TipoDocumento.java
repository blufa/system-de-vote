package com.senatic.votingserver.model.entity.enums;

public enum TipoDocumento {
    
    CC("CC"), 
    TI("TI"), 
    PEP("PEP"), 
    PPT("PPT"), 
    CE("CE"), 
    NA("NA");
    
    private String plainText;

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    TipoDocumento(String plainText){
        this.plainText = plainText;
    } 

    public static TipoDocumento checkAndAssign(String tipoDocumento){
        TipoDocumento tipoAssigned = TipoDocumento.NA;
        for ( TipoDocumento tipo : TipoDocumento.values()) {
            if (tipo.plainText.equalsIgnoreCase(tipoDocumento)) {
                tipoAssigned = tipo;
            }
        }
        return tipoAssigned;
    }

}
