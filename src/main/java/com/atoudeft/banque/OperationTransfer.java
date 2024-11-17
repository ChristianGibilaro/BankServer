package com.atoudeft.banque;

/**
 * Classe heritant de la classe Operation. Represente une operation de type transfert d'argent entre comptes
 * @author aymanelaghrieb
 */
public class OperationTransfer extends Operation{
    /* Montant transfere */
    double montant;
    /* Numero de compte du destinataire du transfert */
    String destinataire;

    /**
     * Constructeur de l'OperationTransfer
     * @param type Type de l'operation
     * @param montant Montant transfere
     * @param destinataire Numero de compte du destinataire du transfert
     */
    public OperationTransfer(TypeOperation type, double montant, String destinataire) {
        super(type);
        this.montant = montant;
        this.destinataire = destinataire;
    }
    @Override
    public String toString() {
        return (getDate() + "\f" + "TRANSFER\f" + this.montant + "\f" + this.destinataire);
    }
}
