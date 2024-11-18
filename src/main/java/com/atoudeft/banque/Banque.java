package com.atoudeft.banque;

import com.atoudeft.banque.serveur.CompteEpargne;
import jdk.nashorn.internal.ir.WhileNode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Banque implements Serializable {
    private String nom;
    private List<CompteClient> comptes;

    public Banque(String nom) {
        this.nom = nom;
        this.comptes = new ArrayList<>();
    }

    /**
     * Recherche un compte-client à partir de son numéro.
     *
     * @param numeroCompteClient le numéro du compte-client
     * @return le compte-client s'il a été trouvé. Sinon, retourne null
     */
    public CompteClient getCompteClient(String numeroCompteClient) {
        CompteClient cpt = new CompteClient(numeroCompteClient,"");
        int index = this.comptes.indexOf(cpt);
        if (index != -1)
            return this.comptes.get(index);
        else
            return null;
    }

    /**
     * Vérifier qu'un compte-bancaire appartient bien au compte-client.
     *
     * @param numeroCompteBancaire numéro du compte-bancaire
     * @param numeroCompteClient    numéro du compte-client
     * @return  true si le compte-bancaire appartient au compte-client
     */
    public boolean appartientA(String numeroCompteBancaire, String numeroCompteClient) {
        throw new NotImplementedException();
    }

    /**
     * Effectue un dépot d'argent dans un compte-bancaire
     *
     * @param montant montant à déposer
     * @param numeroCompte numéro du compte
     * @return true si le dépot s'est effectué correctement
     */
    public boolean deposer(double montant, String numeroCompte) {
        throw new NotImplementedException();
    }

    /**
     * Effectue un retrait d'argent d'un compte-bancaire
     *
     * @param montant montant retiré
     * @param numeroCompte numéro du compte
     * @return true si le retrait s'est effectué correctement
     */
    public boolean retirer(double montant, String numeroCompte) {
        throw new NotImplementedException();
    }

    /**
     * Effectue un transfert d'argent d'un compte à un autre de la même banque
     * @param montant montant à transférer
     * @param numeroCompteInitial   numéro du compte d'où sera prélevé l'argent
     * @param numeroCompteFinal numéro du compte où sera déposé l'argent
     * @return true si l'opération s'est déroulée correctement
     */
    public boolean transferer(double montant, String numeroCompteInitial, String numeroCompteFinal) {
        throw new NotImplementedException();
    }

    /**
     * Effectue un paiement de facture.
     * @param montant montant de la facture
     * @param numeroCompte numéro du compte bancaire d'où va se faire le paiement
     * @param numeroFacture numéro de la facture
     * @param description texte descriptif de la facture
     * @return true si le paiement s'est bien effectuée
     */
    public boolean payerFacture(double montant, String numeroCompte, String numeroFacture, String description) {
        throw new NotImplementedException();
    }

    /**
     * Crée un nouveau compte-client avec un numéro et un nip et l'ajoute à la liste des comptes.
     *
     * @param numCompteClient numéro du compte-client à créer
     * @param nip nip du compte-client à créer
     * @return true si le compte a été créé correctement
     */
    public boolean ajouter(String numCompteClient, String nip) {
        if (numCompteClient.length() < 6 || numCompteClient.length() > 8) { //entre 6 et 8
            return false;
        }
        for (char c : numCompteClient.toCharArray()) { // ne contient que des lettre majuscule et chiffre
            if (!Character.isUpperCase(c) && !Character.isDigit(c)) {
                return false;
            }
        }
        // pin entre 4 et 5
        if (nip.length() < 4 || nip.length() > 5) {
            return false;
        }
        //contient que des chiffre
        for (char c : nip.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false; // Retourne false si un caractère non numérique est trouvé
            }
        }
        for(CompteClient client : comptes) {
            if(client.getNumero().equals(numCompteClient)){
                return false;
            }
        }
        //si toute les vérification sont réussis
        CompteClient nouveauClient = new CompteClient(numCompteClient, nip);
        //générer un nouveau numéro
        String nouveauNumeroCompte;
        do{
            nouveauNumeroCompte = CompteBancaire.genereNouveauNumero();
        }
        while(compteDejaExistant(nouveauNumeroCompte));
        // Créer un comptechèque le numéro
        CompteCheque compteCheque = new CompteCheque(nouveauNumeroCompte, TypeCompte.CHEQUE);
        nouveauClient.ajouter(compteCheque); //méthode "ajouter" dans CompteClient
        return comptes.add(nouveauClient); // Ajouter à la collection des comptes
        //return this.comptes.add(new CompteClient(numCompteClient,nip)); //À modifier
    }
    // Méthode pour vérifier si un compte exist déjà
    private boolean compteDejaExistant(String numeroCompte) {
        for (CompteClient client : comptes) {
            for (CompteBancaire compte : client.getComptes()) {
                if (compte.getNumero().equals(numeroCompte)) {
                    return true; // Le numexiste déjà
                }
            }
        }
        return false; // Aucun compte existe avec ce num

    }

    /**
     * Retourne le numéro du compte-chèque d'un client à partir de son numéro de compte-client.
     *
     * @param numCompteClient numéro de compte-client
     * @return numéro du compte-chèque du client ayant le numéro de compte-client
     */
    public String getNumeroCompteParDefaut(String numCompteClient) {
        for(CompteClient client : comptes) { // pour parcourir la list des compte
            if(client.getNumero().equals(numCompteClient)){
                if(!client.getComptes().isEmpty()) {// verifaction s'il a des comptes
                    return client.getComptes().get(0).getNumero();// 1er compte
                } else{
                    return null; //si le client n'a pas de compte
                }
            }
        }
        return null; //À modifier. client nn trouvé
    }

    /**
     * Recherche et retourne le numéro du compte épargne associé à un client.
     * Cette méthode parcourt les comptes du client spécifié par son numéro de compte-client.
     * Si un compte épargne est trouvé parmi les comptes du client, son numéro est retourné.
     * Si aucun compte épargne n'est trouvé, la méthode retourne null.
     *
     *
     *
     * @param numCompteClient Le numéro du compte-client pour lequel on cherche le compte épargne.
     * @return Le numéro du compte épargne s'il existe, sinon null.
     */
    public String getNumeroCompteEpargne(String numCompteClient) {
        CompteClient client = getCompteClient(numCompteClient);
        if (client != null) {
            for (CompteBancaire compte : client.getComptes()) {
                if (compte instanceof CompteEpargne) {
                    return compte.getNumero();
                }
            }
        }
        return null; // Si aucun compte épargne n'est trouvé
    }
}