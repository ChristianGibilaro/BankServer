package com.atoudeft.banque;

/**
 * Classe heritant de la classe Operation. Represente une operation de type retrait d'argent
 * @author aymanelaghrieb
 */
public class OperationRetrait extends Operation{
    /*Montant debite par le retrait  */
    private double montant;

    /**
     * Constructeur d'OperationRetrait
     * @param type Type de l'operation
     * @param montant Montant debite par le retrait
     */
    public OperationRetrait(TypeOperation type, double montant) {
        super(type);
        this.montant = montant;
    }

    @Override
    public String toString() {
        return (getDate() + "\f" + "RETRAIT\f" + this.montant);
    }
}
