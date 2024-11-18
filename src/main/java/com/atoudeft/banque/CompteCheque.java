package com.atoudeft.banque;

/**
 * Représente un compte bancaire de type CompteChèque.
 * Cette classe permet de gérer les opérations bancaires associées à un compte chèque, telles que le crédit, le débit,
 * le paiement de factures et les transferts.
 *
 * @author Amir
 */
public class CompteCheque extends CompteBancaire{
    /**
     * Crée un compte bancaire de type CompteCheque.
     *
     * @param numero numéro du compte
     * @param type   type du compte
     */
    public CompteCheque(String numero, TypeCompte type) {
        super(numero, type);
    }

    /**
     * Crédite le compte-chèque avec un montant donné.
     *
     * Si le montant est positif, il est ajouté au solde du comptechèque.
     * Le solde du comte est mis à jour en consequence.
     *
     * @param montant Le montant à ajouter au solde du compte.
     * @return true si le crédit a réussi (montant > 0), false sinon (montant invalide).
     */
    @Override
    public boolean crediter(double montant){
        if(montant > 0){ //montant positif?
            double nouveauSolde = getSolde() + montant;
            setSolde(nouveauSolde);  //je ne saia pas s'il faut créer cette méthode dans CompteBancaire pcq c'est pas demandé
            return true; //crédit réussi
        }
        return false; //montant invalide
    }

    /**
     * Débite le compte-chèque du montant donné
     *
     * Si le montant est positif et inférieur ou égal au solde du compte,
     * le montant est retiré du solde du compt

     * @param montant Le montant à retirer du solde du compte
     * @return true si le débit a réussi (montant > 0 et solde suffisant), false sinon.
     */
    @Override
    public boolean debiter(double montant){
        if(montant > 0 && getSolde() >= montant){
            double nouveauSolde = getSolde() - montant;
            setSolde(nouveauSolde);
            return true;
        }
        return false;
    }

    /**
     * Méthode pour payer une facture.
     *
     * Actuellement, cette méthode ne fait rien et retourne toujours false.
     * Cette fonctionnalité pourrait être implémentée à l'avenir.
     *
     * @param numeroFacture Le numéro de la facture à payer.
     * @param montant Le montant de la facture à payer.
     * @param description Une description de la facture.
     * @return false, car cette fonctionnalité n'est pas encore implémentée.
     */
    @Override
    public boolean payerFacture(String numeroFacture, double montant, String description){
       return false;
    }


    /**
     * Effectue un transfert d'argent vers un autre compte bancaire.
     *
     * Actuellement, cette méthode ne fait rien et retourne toujours false.
     * Cette fonctionnalité pourrait etre ajoutée à lavenir.
     *
     * @param montant Le montant à transférer.
     * @param numeroCompteDestinataire Le numéro du compte destinataire du transfert.
     * @return false, car cette fonctionnalité n'est pas encore implémentée.
     */
    @Override
    public boolean transferer(double montant, String numeroCompteDestinataire){
        return false;
    }
}


























