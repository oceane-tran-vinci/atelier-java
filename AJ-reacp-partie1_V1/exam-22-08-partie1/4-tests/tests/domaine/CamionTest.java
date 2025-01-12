package domaine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CamionTest {
    private Camion camion;
    private Trajet trajet;

    @BeforeEach
    void setUp() {
        camion = new Camion("immatriculation1", 50, 200);
        trajet = Mockito.mock(Trajet.class);

        Mockito.when(trajet.getDate()).thenReturn(LocalDate.MAX);
        // Je dois pas les mettre car pas en lien avec l'énoncé
        //Mockito.when(trajet.getVilleArrivee()).thenReturn("Bruxelles");
        //Mockito.when(trajet.getVilleDepart()).thenReturn("Paris");
        Mockito.when(trajet.nbCaisses()).thenReturn(55);
        Mockito.when(trajet.calculerPoidsTotal()).thenReturn(100.0);
        //s’ils comportent trop de caisses par rapport à la capacité du camion.
        //ça veut dire que le camion crée doit avoir une capacité +gd mm si le nbr de caisse est bon pour un camion
    }

    //a. Vérifiez que la méthode ajouterTrajet ne permet pas d’ajouter un trajet
    //s’ils comportent trop de caisses par rapport à la capacité du camion.
    @Test
    void ajouterTrajet() {
        assertFalse(camion.ajouterTrajet(trajet));
    }
}