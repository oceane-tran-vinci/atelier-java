package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PoulaillerTest {
    private Lot lot;
    private Poulailler poulailler;

    @BeforeEach
    void setUp() {
        poulailler = new PoulaillerImpl("Id", 200);
        lot = Mockito.mock(Lot.class);
        Mockito.when(lot.signalerAffectation()).thenReturn(false);
    }

    @Test
    void ajouterLot() {
        assertAll(
                () -> assertFalse(poulailler.ajouterLot(lot)),
                () -> assertEquals(0, poulailler.getTousLesLots().size())
                // OU
                // () -> assertTrue(poulailler.getTousLesLots().isEmpty())
        );
    }
}