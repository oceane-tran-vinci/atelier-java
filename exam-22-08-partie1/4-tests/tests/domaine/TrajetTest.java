package domaine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TrajetTest {

    //créer un trajet pour pvr tester
    private Trajet trajet;

    @BeforeEach
    void setUp() {
        trajet = new Trajet("1", LocalDate.MAX, "Bruxelles", "Paris");
    }

    //a. Vérifiez que la méthode peutAjouter lance une IllegalArgumentException lorsqu’on lui passe null en paramètre.
    @Test
    @DisplayName("Test a: Si caisse est nulle")
    void peutAjouterNull() {
        assertThrows(IllegalArgumentException.class, () -> trajet.peutAjouter(null));
    }

    //b. Vérifiez que la méthode peutAjouter renvoie false
    // quand on lui passe une caisse dont la ville de départ ne correspond pas à celle du trajet.
    @Test
    @DisplayName("Test b: La ville de départ de la caisse est différente du trajet")
    void peutAjouterFalse() {
        Caisse caisse = new Caisse("reference", LocalDate.MAX, "Amsterdam", "Paris", 100);
        assertFalse(trajet.peutAjouter(caisse));
    }

    //c. Vérifiez que la méthode peutAjouter renvoie true
    // lorsqu’on lui ajoute une caisse qui peut être ajouter au trajet.
    @Test
    @DisplayName("Test c: La ville de départ de la caisse est la même que celle du trajet")
    void peutAjouterTrue() {
        Caisse caisse = new Caisse("reference", LocalDate.MAX, "Bruxelles", "Paris", 100);
        assertTrue(trajet.peutAjouter(caisse));
    }


}