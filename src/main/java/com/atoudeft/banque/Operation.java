package com.atoudeft.banque;

import java.io.Serializable;
import java.sql.Date;

/**
 * Classe representant une operation bancaire et sa date
 * @author aymanelaghrieb
 */
public abstract class Operation implements Serializable {
    /*Type d'operation entre Depot, Retrait, Facture et Transfert*/
    private TypeOperation type;
    /*Date de l'operation*/
    private Date date;

    /**
     * Constructeur d'une operation
     * @param type Type de l'operation (Depot, Retrait, Facture et Transfert)
     */
    public Operation(TypeOperation type) {
        this.type = type;
        this.date = new Date(System.currentTimeMillis()); //Dès que l'operation est crée, la date est crée comme la date d'aujourd'hui
    }

    /**
     * Retourne la date de l'operation
     * @return La date de l'operation
     */
    public Date getDate() {
        return this.date;
    }
}
