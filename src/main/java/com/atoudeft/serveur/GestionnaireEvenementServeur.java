package com.atoudeft.serveur;

import com.atoudeft.banque.*;
import com.atoudeft.banque.serveur.CompteEpargne;
import com.atoudeft.banque.serveur.ConnexionBanque;
import com.atoudeft.banque.serveur.ServeurBanque;
import com.atoudeft.commun.evenement.Evenement;
import com.atoudeft.commun.evenement.GestionnaireEvenement;
import com.atoudeft.commun.net.Connexion;

/**
 * Cette classe représente un gestionnaire d'événement d'un serveur. Lorsqu'un serveur reçoit un texte d'un client,
 * il crée un événement à partir du texte reçu et alerte ce gestionnaire qui réagit en gérant l'événement.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementServeur implements GestionnaireEvenement {
    private Serveur serveur;

    /**
     * Construit un gestionnaire d'événements pour un serveur.
     *
     * @param serveur Serveur Le serveur pour lequel ce gestionnaire gère des événements
     */
    public GestionnaireEvenementServeur(Serveur serveur) {
        this.serveur = serveur;
    }
//s
    /**
     * Méthode de gestion d'événements. Cette méthode contiendra le code qui gère les réponses obtenues d'un client.
     *
     * @param evenement L'événement à gérer.
     */

    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        ServeurBanque serveurBanque = (ServeurBanque)serveur;
        Banque banque;
        ConnexionBanque cnx;
        String msg, typeEvenement, argument, numCompteClient, nip;
        String[] t;


        if (source instanceof Connexion) {
            cnx = (ConnexionBanque) source;
            System.out.println("SERVEUR: Recu : " + evenement.getType() + " " + evenement.getArgument());
            typeEvenement = evenement.getType();
            cnx.setTempsDerniereOperation(System.currentTimeMillis());
            switch (typeEvenement) {
                /******************* COMMANDES GÉNÉRALES *******************/
                case "EXIT": //Ferme la connexion avec le client qui a envoyé "EXIT":
                    cnx.envoyer("END");
                    serveurBanque.enlever(cnx);
                    cnx.close();
                    break;
                case "LIST": //Envoie la liste des numéros de comptes-clients connectés :
                    cnx.envoyer("LIST " + serveurBanque.list());
                    break;
                /******************* COMMANDES DE GESTION DE COMPTES *******************/
                case "NOUVEAU": //Crée un nouveau compte-client :
                    if (cnx.getNumeroCompteClient()!=null) {
                        cnx.envoyer("NOUVEAU NO deja connecte");
                        break;
                    }
                    argument = evenement.getArgument();
                    t = argument.split(":");
                    if (t.length<2) {
                        cnx.envoyer("NOUVEAU NO");
                    }
                    else {
                        numCompteClient = t[0];
                        nip = t[1];
                        banque = serveurBanque.getBanque();
                        if (banque.ajouter(numCompteClient,nip)) {
                            cnx.setNumeroCompteClient(numCompteClient);
                            cnx.setNumeroCompteActuel(banque.getNumeroCompteParDefaut(numCompteClient));
                            cnx.envoyer("NOUVEAU OK " + t[0] + " cree");
                        }
                        else
                            cnx.envoyer("NOUVEAU NO "+t[0]+" existe");
                    }
                    break;

                case "CONNECT":
                    //Verifie la connection au compte client
                    if (cnx.getNumeroCompteClient()!=null) {
                        cnx.envoyer("CONNECT NO deja connecte");
                        break;
                    }
                    //Vérifie si les parametres (Numero de compte et NIP)
                    argument = evenement.getArgument();
                    t = argument.split(":");
                    if (t.length<2) {
                        cnx.envoyer("CONNECT NO");
                    }
                    else {
                        //Verifie si le nip est pareil à celui enregistré
                        numCompteClient = t[0];
                        nip = t[1];
                        banque = serveurBanque.getBanque();
                        CompteClient compte = banque.getCompteClient(numCompteClient);
                        //si le nip est valide et que le compte est existant
                        if (compte != null && compte.getNip().equals(nip) ) {
                            cnx.setNumeroCompteClient(numCompteClient);
                            cnx.setNumeroCompteActuel(banque.getNumeroCompteParDefaut(numCompteClient));
                            cnx.envoyer("CONNECT OK");
                        } else {
                            cnx.envoyer("CONNECT NO");
                        }

                    }
                break;

                case "EPARGNE":
                    // Vérifier si le client est déjà connecté
                    if (cnx.getNumeroCompteClient() == null) {
                        cnx.envoyer("EPARGNE NO");

                    }else{
                    banque = serveurBanque.getBanque();// Vérification si le client possède déjà un compte épargne
                    CompteClient compteClient = banque.getCompteClient(cnx.getNumeroCompteClient());
                    boolean compteEpargneExist = false;
                    for (CompteBancaire compte : compteClient.getComptes()) {// Parcourir les comptes bancaires du client pour vérifier s'il a déjà un compte épargne
                        if (compte instanceof CompteEpargne) {
                            compteEpargneExist = true;
                            break;
                        }
                    }
                    if (compteEpargneExist) {
                        cnx.envoyer("EPARGNE NO");// Si un compte épargne existe déjà
                    } else { // Créer un nouveau compte épargne avec un taux d'intérêt de 5%
                        String numeroCompte = CompteBancaire.genereNouveauNumero();
                        CompteEpargne compteEpargne = new CompteEpargne(numeroCompte, TypeCompte.EPARGNE, 0.05);
                        //
                        compteClient.ajouter(compteEpargne); // Ajouter le compte épargne à la list
                        cnx.envoyer("EPARGNE OK"); //compte épargne créé
                    }
                    }
                    break;

                case "SELECT":
                    String accountType = evenement.getArgument().toLowerCase();
                    // vérification de la connexion du client
                    if (cnx.getNumeroCompteClient() == null) {
                        cnx.envoyer("SELECT NO");
                    } else {
                        String accountNumber = null;
                        // sélection du compte en fonction de l'argument (chèque ou épargne)
                        banque = serveurBanque.getBanque(); // initialisation de banque
                        if (accountType.equalsIgnoreCase("cheque")) {
                            accountNumber = banque.getNumeroCompteParDefaut(cnx.getNumeroCompteClient());
                        } else if (accountType.equalsIgnoreCase("epargne")) {
                            accountNumber = banque.getNumeroCompteEpargne(cnx.getNumeroCompteClient());
                        } else {
                            cnx.envoyer("SELECT NO Invalid");
                            return;
                        }
                        if (accountNumber != null) {// Vérification si compte existe
                            cnx.setNumeroCompteActuel(accountNumber);
                            cnx.envoyer("SELECT OK");
                        } else {
                            cnx.envoyer("SELECT NO");
                        }
                    }
                    break;

                case "DEPOT":
                    // vérification de la connexion du compte client et la selection du compte ou faire la transaction
                    if (cnx.getNumeroCompteClient() == null || cnx.getNumeroCompteActuel() == null) {
                        cnx.envoyer("DEPOT NO 1");
                    }else {

                        // cherche dans la liste des comptes du compte client pour trouvé le compte selectionné
                        banque = serveurBanque.getBanque();
                        CompteClient compteClient = banque.getCompteClient(cnx.getNumeroCompteClient());
                        boolean compteTrouver= false;
                        CompteBancaire compte = null;
                        for(CompteBancaire c : compteClient.getComptes())
                        {
                            if (c.getNumero().equals(cnx.getNumeroCompteActuel())) {
                                compteTrouver = true;
                                compte = c;
                                break;
                            }
                        }

                        //si compte est trouvé et que la somme est valide, fait le depot
                        if(!compteTrouver){
                            cnx.envoyer("DEPOT NO");
                        }else {
                            argument = evenement.getArgument();
                            t = argument.split(" ");
                            Double montant;
                            try {
                                montant = Double.parseDouble(t[0]);
                            }
                            catch (NumberFormatException a) {
                                cnx.envoyer("DEPOT NO");
                                break;
                            }
                            if (!compte.crediter(montant)) {
                                cnx.envoyer("DEPOT NO");
                            } else {
                                OperationDepot opDepot = new OperationDepot(TypeOperation.DEPOT, montant);
                                compte.getHistorique().empiler(opDepot);
                                cnx.envoyer("DEPOT OK " + compte.getSolde());
                            }
                        }
                    }
                    break;

                case "RETRAIT" :
                    if (cnx.getNumeroCompteClient() == null || cnx.getNumeroCompteActuel() == null) {
                        cnx.envoyer("RETRAIT NO pas de compte");
                    }else {

                        // cherche dans la liste des comptes du compte client pour trouvé le compte selectionné
                        banque = serveurBanque.getBanque();
                        CompteClient compteClient = banque.getCompteClient(cnx.getNumeroCompteClient());
                        boolean compteTrouver= false;
                        CompteBancaire compte = null;
                        for(CompteBancaire c : compteClient.getComptes())
                        {
                            if (c.getNumero().equals(cnx.getNumeroCompteActuel())) {
                                compteTrouver = true;
                                compte = c;
                                break;
                            }
                        }

                        //si compte est trouvé et que la somme est valide, fait le retrait
                        if(!compteTrouver){
                            cnx.envoyer("RETRAIT NO");
                        }else {
                            argument = evenement.getArgument();
                            t = argument.split(" ");
                            Double montant;
                            try {
                                montant = Double.parseDouble(t[0]);
                            }
                            catch (NumberFormatException a) {
                                cnx.envoyer("RETRAIT NO");
                                break;
                            }
                            if (!compte.debiter(montant)) {
                                cnx.envoyer("RETRAIT NO");
                            } else {
                                OperationRetrait opRetrait = new OperationRetrait(TypeOperation.RETRAIT, montant);
                                compte.getHistorique().empiler(opRetrait);
                                cnx.envoyer("RETRAIT OK");
                            }
                        }
                    }
                    break;

                /*case "DEBUG":
                    if (cnx.getNumeroCompteClient() != "22222222") {
                        banque = serveurBanque.getBanque();
                        CompteClient compteClient = banque.getCompteClient(cnx.getNumeroCompteClient());
                        cnx.envoyer("\n");
                        cnx.envoyer("\nNum compte client: " + cnx.getNumeroCompteClient());
                        cnx.envoyer("\nnombre de comptes: " + compteClient.getComptes().toArray().length);
                        cnx.envoyer("\nListe:");

                        boolean compteTrouver = false;
                        CompteBancaire compte = null;
                        for (CompteBancaire c : compteClient.getComptes()) {
                            if(c.getNumero().equals(cnx.getNumeroCompteActuel()))
                            {
                                compteTrouver = true;
                                compte = c;
                            }
                            cnx.envoyer("\n" + c.getNumero() + " : " + c.getType().toString() + "-SOLDE: " + c.getSolde());
                        }
                        cnx.envoyer("\nNum compte SELECTED: " + cnx.getNumeroCompteActuel());


                        if (compteTrouver) {
                            cnx.envoyer("\ntype: " + compte.getType().toString());
                            cnx.envoyer("\nsolde: " + compte.getSolde());
                        }


                    }
                    break;*/

                case "FACTURE": if (cnx.getNumeroCompteClient() == null || cnx.getNumeroCompteActuel() == null) {
                    cnx.envoyer("PAIEMENT FACTURE NO pas de compte");
                } else {
                    banque = serveurBanque.getBanque();
                    CompteClient compteClient = banque.getCompteClient(cnx.getNumeroCompteClient());

                    boolean compteTrouve = false;
                    CompteBancaire compte = null;

                    for (CompteBancaire c : compteClient.getComptes()) {
                        if (c.getNumero().equals(cnx.getNumeroCompteActuel())) {
                            compteTrouve = true;
                            compte = c;
                            break;
                        }
                    }
                    if (!compteTrouve) {
                        cnx.envoyer("PAIEMENT FACTURE NO");
                    } else {
                        argument = evenement.getArgument();
                        t = argument.split(" ");

                        Double montant;

                        try {
                            montant = Double.parseDouble(t[0]);
                        }
                        catch (NumberFormatException a){
                            cnx.envoyer("PAIEMENT FACTURE NO");
                            break;
                        }

                        if (!compte.debiter(montant)) {
                            cnx.envoyer("PAIEMENT FACTURE NO");
                        } else {
                            OperationFacture opFacture = new OperationFacture(TypeOperation.FACTURE, montant, t[1], t[2]);
                            compte.getHistorique().empiler(opFacture);
                            cnx.envoyer("PAIEMENT FACTURE OK ");
                        }
                    }
                }
                break;

                case "TRANSFER":
                if (cnx.getNumeroCompteClient() == null || cnx.getNumeroCompteActuel() == null) {
                        cnx.envoyer("TRANSFER NO pas de compte");
                    } else {

                    banque = serveurBanque.getBanque();
                    CompteClient compteClient = banque.getCompteClient(cnx.getNumeroCompteClient());

                    argument = evenement.getArgument();
                    t = argument.split(" ");
                    if (t.length < 2) {
                        cnx.envoyer("TRANSFER NO Invalid arguments");
                        return;
                    }

                    Double montant;
                    String numeroCompteCible = t[1];
                    try {
                        montant = Double.parseDouble(t[0]);
                    } catch (NumberFormatException e) {
                        cnx.envoyer("TRANSFER NO Invalid amount");
                        return;
                    }

                    // Recherche du compte source
                    CompteBancaire compteSource = null;
                    for (CompteBancaire c : compteClient.getComptes()) {
                        if (c.getNumero().equals(cnx.getNumeroCompteActuel())) {
                            compteSource = c;
                            break;
                        }
                    }

                    if (compteSource == null) {
                        cnx.envoyer("TRANSFER NO Source account not found");
                        return;
                    }

                    // Recherche du compte cible
                    CompteClient compteCible = banque.getCompteClient(numeroCompteCible);

                    if (compteCible == null) {
                        cnx.envoyer("TRANSFER NO Target account not found");
                        return;
                    }

                    CompteCheque compteChequeDestinataire = (CompteCheque) compteCible.getComptes().get(0);
                    // Effectuer le transfert
                    if (!compteSource.debiter(montant)) {
                        cnx.envoyer("TRANSFER NO Insufficient funds");
                    } else if (!compteChequeDestinataire.crediter(montant)) {
                        cnx.envoyer("TRANSFER NO Error crediting target account");
                    } else {
                        OperationTransfer opTransfer = new OperationTransfer(TypeOperation.TRANSFER, montant, t[1]);
                        compteSource.getHistorique().empiler(opTransfer);
                        cnx.envoyer("TRANSFER OK");
                    }
                }
                break;
                case "HIST":
                    if (cnx.getNumeroCompteClient() == null || cnx.getNumeroCompteActuel() == null) {
                        cnx.envoyer("HIST NO pas de compte");
                    } else {
                        banque = serveurBanque.getBanque();
                        CompteClient compteClient = banque.getCompteClient(cnx.getNumeroCompteClient());
                        CompteBancaire compte = null;

                        // Trouver le compte actif
                        for (CompteBancaire c : compteClient.getComptes()) {
                            if (c.getNumero().equals(cnx.getNumeroCompteActuel())) {
                                compte = c;
                                break;
                            }
                        }

                        if (compte == null) {
                            cnx.envoyer("HIST NO pas de compte");
                        } else {
                            // Utiliser la méthode getHistoriqueAsString pour récupérer l'historique
                            String historiqueString = compte.getHistoriqueAsString();
                            cnx.envoyer("HIST\n" + historiqueString);
                        }
                    }
                    break;

                //Ajouter les details du paiement de facture dans la pile
                    /******************* TRAITEMENT PAR DÉFAUT *******************/
                default: //Renvoyer le texte recu convertit en majuscules :
                    msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                    cnx.envoyer(msg);
            }
        }
    }
}