package com.atoudeft.banque;

public class OperationTransfer extends Operation{
    double montant;
    String destinataire;
    public OperationTransfer(TypeOperation type, double montant, String destinataire) {
        super(type);
        this.montant = montant;
        this.destinataire = destinataire;
    }

    public String toString() {
        return (getDate() + "\f" + "TRANSFER\f" + this.montant + "\f" + this.destinataire);
    }
}
