package main;

import domain.Ferme;
import domain.Lot;
import domain.Poulailler;
import domain.VenteOeufs;
import java.time.LocalDate;

public class GestionPoulaillers {

  public static void main(String[] args) {
    Ferme fermeAuxPoulets = new Ferme();

    // Poules rousses

    int NBR_POULES_ROUSSES = 10000;
    Lot lotPoulesRousses = new Lot(NBR_POULES_ROUSSES, 90000, "Poules rousses");


    VenteOeufs v1 = new VenteOeufs(LocalDate.of(2022, 9, 30), 83500,0.15, "Supermarché",
        "Delhaize");
    VenteOeufs v2 = new VenteOeufs(LocalDate.of(2022,9,5), 27000, 0.2, "Supermarché",
        "Delhaize");
    VenteOeufs v3 = new VenteOeufs(LocalDate.of(2022, 9, 9), 27500,0.2, "Supermarché",
        "Carrefour");
    VenteOeufs v4 = new VenteOeufs(LocalDate.of(2022, 9, 10), 144,0.35, "Epicerie",
        "JetJ");
    VenteOeufs v5= new VenteOeufs(LocalDate.of(2022, 9, 10), 288,0.35, "Epicerie",
        "Rosemarie");
    VenteOeufs v6 = new VenteOeufs(LocalDate.of(2022, 9, 23), 49000,0.2, "Supermarché",
        "Carrefour");
    VenteOeufs v7 = new VenteOeufs(LocalDate.of(2022, 9, 10), 12,0.4, "Particulier");


    Poulailler poulaillerEnsoleille = new Poulailler("Poulailler ensoleillé", 10000);
    fermeAuxPoulets.ajouterPoulailler(poulaillerEnsoleille);

    poulaillerEnsoleille.ajouterLot(lotPoulesRousses);

    lotPoulesRousses.initialiserVentes(v1, v2, v3, v4, v5, v6, v7);

    double coutSeptembre2022LotPoulesRousses = NBR_POULES_ROUSSES * 2;
    lotPoulesRousses.augmenterCout(coutSeptembre2022LotPoulesRousses);

    // Poules blanches

    int NBR_POULES_BLANCHES = 1000;
    Lot lotPoulesBlanches = new Lot(NBR_POULES_BLANCHES, 30000, "Poules blanches");

    VenteOeufs v8 = new VenteOeufs(LocalDate.of(2022,9,30), 3000, 0.4, "Supermarché",
        "Carrefour");
    VenteOeufs v9 = new VenteOeufs(LocalDate.of(2022,9,23), 1500, 0.4, "Supermarché",
        "Delhaize");

    lotPoulesBlanches.enregistrerVente(v8);
    lotPoulesBlanches.enregistrerVente(v9);

    Poulailler poulaillerOmbrage = new Poulailler("Poulailler ombragé", 1000);
    fermeAuxPoulets.ajouterPoulailler(poulaillerOmbrage);

    poulaillerOmbrage.ajouterLot(lotPoulesBlanches);

    double coutSeptembre2022LotPoulesBlanches = NBR_POULES_BLANCHES * 2.5;
    lotPoulesBlanches.augmenterCout(coutSeptembre2022LotPoulesBlanches);

    // Poules roses
    int NBR_POULES_ROSES = 100;
    Lot lotPoulesRoses = new Lot(NBR_POULES_ROSES, 5000, "Poules roses");

    VenteOeufs v10 = new VenteOeufs(LocalDate.of(2022, 9, 10), 48,1, "Epicerie",
        "JetJ");
    VenteOeufs v11 = new VenteOeufs(LocalDate.of(2022, 9, 10), 96,1, "Epicerie",
        "Rosemarie");

    lotPoulesRoses.enregistrerVente(v10);
    lotPoulesRoses.enregistrerVente(v11);

    Poulailler poulaillerLuxueux = new Poulailler("Poulailler luxueux", 1000);
    fermeAuxPoulets.ajouterPoulailler(poulaillerLuxueux);

    poulaillerLuxueux.ajouterLot(lotPoulesRoses);

    double coutSeptembre2022LotPoulesRoses= NBR_POULES_ROSES * 3;
    lotPoulesRoses.augmenterCout(coutSeptembre2022LotPoulesRoses);

    System.out.println("Vente la plus élevée pour les poules rousses (q3.1) : " + fermeAuxPoulets.trouverVenteLaPlusGrande(lotPoulesRousses));
    System.out.println("Résultat attendu pour la vente la plus élevée (q3.1) : date=30/09/2022, nombreOeufsVendus=83500, prixPiece=0.15, nomAcheteur=Delhaize");

    System.out.println();
    System.out.println("Vente pour l'acheteur Carrefour (q3.2) : " + fermeAuxPoulets.trouverVentesPourNomAcheteur("Carrefour"));
    System.out.println("Résultat attendu pour l'acheteur Carrefour (q3.2) : [date=09/09/2022, nombreOeufsVendus=27500, prixPiece=0.2, nomAcheteur=Carrefour, date=23/09/2022, nombreOeufsVendus=49000, prixPiece=0.2, nomAcheteur=Carrefour, date=30/09/2022, nombreOeufsVendus=3000, prixPiece=0.4, nomAcheteur=Carrefour]");

    System.out.println();
    System.out.println("Oeufs vendus par acheteur (q3.3) : " + fermeAuxPoulets.calculerOeufsVendusParNomAcheteur());
    System.out.println("Résultat attendu pour les oeufs vendus (q3.3) : {JetJ=192, Carrefour=79500, Delhaize=112000, Rosemarie=384, Particulier=12}");
    System.out.println("NB : Il n'est pas demandé que l'ordre d'affichage des éléments soit le même que ci-dessus. "
        + "C'est une map, cet affichage peut donc être fait dans un ordre différent ; )");
  }

}