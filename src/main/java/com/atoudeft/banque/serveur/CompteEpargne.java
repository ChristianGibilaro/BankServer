package com.atoudeft.banque.serveur;

import com.atoudeft.banque.CompteBancaire;
import com.atoudeft.banque.TypeCompte;

/**
 * Classe représentant un compte epargnes heritant d'un compte bancaire
 * @author aymanelaghrieb
 */
public class CompteEpargne extends CompteBancaire {
    /*Taux d'interets du compte epargne*/
    private double tauxInteret;

    /*Limite de 1000$ pour les calculs de prélevement*/
    private final double LIMITE_MILLE = 1000;
    /*Montant de prélevement du compte epargne*/
    private final double PRELEVEMENT = 2;
    /*Nombre de mois par année*/
    private final double NB_MOIS = 12;
    /**
     * Crée un compte bancaire.
     *
     * @param numero numéro du compte
     * @param type   type du compte
     */
    public CompteEpargne(String numero, TypeCompte type, double tauxInteret) {
        super(numero, type);
        this.tauxInteret = tauxInteret;
    }

    /**
     * Crédite le compte épargne avec un montant donné.
     *
     * Si le montant est positif, il est ajouté au solde du compte épargne.
     * @param montant Le montant à ajouter au solde du compte épargne.
     * @return true si le crédit a réussi (montant > 0), false sinon.
     */
    @Override
    public boolean crediter(double montant) {
        double solde = getSolde();
        if (montant > 0) {
            solde+= montant;
            setSolde(solde);
            return true;
        }
        return false;
    }

    /**
     * Débite le compte épargne du montant donné, avec une vérification des conditions.
     *
     * Si le montant est positif et inférieur au solde du compte, le montant est retiré.
     * Si le solde initial est inférieur à la limite de 1000$, des frais de prélèvement sont appliqués.
     * @param montant Le montant à retirer du solde du compte épargne.
     * @return true si le débit a réussi, sinon false. Si le montant est inférieur au solde et qu'il y a des frais à prélever, ces derniers sont également appliqués.
     */
    @Override
    public boolean debiter(double montant) {
        double soldeInitial = getSolde();
        if(montant > 0) {
            if(montant < soldeInitial) {
                setSolde(soldeInitial-montant);
                if(soldeInitial < LIMITE_MILLE) {
                    setSolde(getSolde() - PRELEVEMENT);
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode pour payer une facture.
     * Actuellement, cette méthode ne fait rien et retourne toujours false.
     *
     * @param numeroFacture Le numéro de la facture à payer.
     * @param montant Le montant de la facture.
     * @param description La description de la facture.
     * @return false, car cette fonctionnalité n'est pas encore implémentée.
     */
    @Override
    public boolean payerFacture(String numeroFacture, double montant, String description) {
        return false;
    }

    /**
     * Effectue un transfert d'argent vers un autre compte bancaire.
     * Actuellement, cette méthode ne fait rien et retourne toujours false.
     *
     * @param montant Le montant à transférer.
     * @param numeroCompteDestinataire Le numéro du compte destinataire du transfert.
     * @return false, car cette fonctionnalité n'est pas encore implémentée.
     */
    @Override
    public boolean transferer(double montant, String numeroCompteDestinataire) {
        return false;
    }

    /**
     * Calcule les interets et les ajoute au solde
     */
    public void ajouterInterets() {
        double interets = getSolde() * tauxInteret / NB_MOIS;
        setSolde(getSolde() - interets);
    }
}
