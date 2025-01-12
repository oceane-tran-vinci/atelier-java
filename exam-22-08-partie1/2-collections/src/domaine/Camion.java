package domaine;

import java.time.LocalDate;
import java.util.*;

public class Camion {
    private String immatriculation;
    private int nbMaxCaisses;
    private int chargeMaximale;

    //private Set<Trajet> trajets = new HashSet<>();
    private SortedMap<LocalDate, Trajet> mapTrajets = new TreeMap<>();

    public Camion(String immatriculation, int nbMaxCaisses, int chargeMaximale) {
        this.immatriculation = immatriculation;
        this.nbMaxCaisses = nbMaxCaisses;
        this.chargeMaximale = chargeMaximale;
    }

    /**
     * ajoute un trajet pour le camion
     *
     * @param trajet le trajet à ajouter
     * @return false
     * la date du jour n'est pas antérieure à la date du trajet
     * - s'il y a déjà un trajet prévu ce jour-là pour le camion
     * - s'il y a déjà un trajet prévu la veille et que la ville d'arrivée de ce trajet ne correspond
     * pas à la ville de départ du trajet à ajouter
     * - s'il y a déjà un trajet prévu le lendemain et que la ville de départ de ce trajet ne correspond
     * pas à la ville d'arrivée du trajet à ajouter
     * - s'il y a trop de palettes à transporter par rapport à la capacité du camion
     * - si le poids total à transporter est supérieur à la charge maximale du camion
     */
    public boolean ajouterTrajet(Trajet trajet) {
        LocalDate dateActuelle = LocalDate.now();
        if (!dateActuelle.isBefore(trajet.getDate())) return false;
        if (this.chargeMaximale < trajet.calculerPoidsTotal()) return false;
        if (this.nbMaxCaisses < trajet.nbCaisses()) return false;
        /*for (Trajet trajetPrevu : trajets){

            if (trajetPrevu.getDate().equals(trajet.getDate())) return false;
            if (trajetPrevu.getDate().plusDays(1).equals(trajet.getDate())
                    && !trajetPrevu.getVilleArrivee().equals(trajet.getVilleDepart()))
                return false;
            if (trajetPrevu.getDate().minusDays(1).equals(trajet.getDate())
                    && !trajetPrevu.getVilleDepart().equals(trajet.getVilleArrivee()))
                return false;
        }
        return trajets.add(trajet);
        */

        //pas besoin de faire foreach car Les dates ciblées (clé) sont connues (date, date + 1, date - 1), et la map permet un accès direct.
        if (mapTrajets.containsKey(trajet.getDate())) {
            return false;
        }
        if (mapTrajets.containsKey(trajet.getDate().plusDays(1))) { // Vérifie si y a déja un trajet prévu le jour suivant
            Trajet trajetSuivant = mapTrajets.get(trajet.getDate().plusDays(1)); // Je stock le trajet du jour suivant
            //Si trajetSuivant != null => Il y a déjà un trajet pour le jour suivant
            //et que !trajet.getVilleArrivee().equals(trajetSuivant.getVilleDepart())
            //return false
            if (trajetSuivant != null && !trajet.getVilleArrivee().equals(trajetSuivant.getVilleDepart())) {
                return false;
            }
        }
        if (mapTrajets.containsKey(trajet.getDate().minusDays(1))) {
            Trajet trajetPrecedent = mapTrajets.get(trajet.getDate().minusDays(1));
            if (trajetPrecedent != null && !trajet.getVilleDepart().equals(trajetPrecedent.getVilleArrivee())) {
                return false;
            }
        }
        mapTrajets.put(trajet.getDate(), trajet);
        return true;
    }

    /**
     * renvoie, s'il existe, le trajet prévu à la date passée en paramètre
     *
     * @param dateTrajet la date du trajet recherché
     * @return le trajet prévu à cette date s'il existe et null sinon
     */
    public Trajet trouverTrajet(LocalDate dateTrajet) {
        /*for (Trajet trajetPrevu : trajets) {
            if (trajetPrevu.getDate().equals(dateTrajet)) return trajetPrevu;
        }
        return null;
        */

        return mapTrajets.get(dateTrajet);
    }


    /**
     * recherche le premier trajet, dont la date est ultérieure à la date du jour, auquel la caisse peut être
     * ajoutée et, s'il en a un, lui ajoute la caisse.
     *
     * @param caisse caisse à ajouter
     * @return false s'il n'y a pas de trajet auquel la caisse peut être ajoutée
     * @date date du trajet recherché pour ajouter la caisse
     */
    public boolean ajouterCaisse(Caisse caisse) {
        Trajet trajet = rechercherTrajet(caisse);
        if (trajet == null) return false;
        return trajet.ajouter(caisse);
    }

    /**
     * renvoie, s'il existe, le premier trajet, dont la date est ultérieure à la date du jour, auquella caisse
     * peut lui être ajoutée
     *
     * @param caisse
     * @return null s'il n'y a pas de trajet auquel la caisse peut être ajoutée
     */
    private Trajet rechercherTrajet(Caisse caisse) {
        /*Trajet trajet = null;
        for (Trajet trajetPrevu : trajets) {
            if (trajetPrevu.peutAjouter(caisse)
                    && trajetPrevu.nbCaisses() < nbMaxCaisses
                    && trajetPrevu.calculerPoidsTotal() + caisse.getPoids() <= chargeMaximale) {
                if (trajet == null || trajetPrevu.getDate().isBefore(trajet.getDate())) {
                    trajet = trajetPrevu;
                }
            }
        }
        return trajet;
         */
        //Vu que c'est une SortedMap, la map est ordonné selon sa clé
        //Ici vu qu'on cherche à avoir la première bonne date, il faut itérer sur la clé
        //Il faut parcourir toutes les dates pour trouver le premier trajet valide selon des critères.
        //(on peut pas juste se baser sur la clé dc doit faire iter)
        Trajet trajet = null; //trajet sert à stocker le meilleur trajet valide trouvé au cours de la boucle
        for(LocalDate date : mapTrajets.keySet()){//iter s/toutes les dates (clés) de mapTrajets grâce à mapTrajets.keySet().
            Trajet trajetPrevu = mapTrajets.get(date);//Pr chaque date, on stock ds trajetPrevu le trajet correspondant avec mapTrajets.get(date)
            if (trajetPrevu.peutAjouter(caisse)
                    && trajetPrevu.nbCaisses() < nbMaxCaisses
                    && trajetPrevu.calculerPoidsTotal() + caisse.getPoids() <= chargeMaximale) {
                if (trajet == null ||trajetPrevu.getDate().isBefore(trajet.getDate())) {
                    trajet = trajetPrevu;
                }
            }
        }
        return trajet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Camion camion = (Camion) o;
        return Objects.equals(immatriculation, camion.immatriculation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(immatriculation);
    }

}
