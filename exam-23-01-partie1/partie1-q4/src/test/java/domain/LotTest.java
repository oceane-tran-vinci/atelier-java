package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LotTest {
    private Lot lot;

    @BeforeEach
    void setUp() {
        lot = new LotImpl(3, 3, "Poule");
    }

    @Test
    @DisplayName("Test: Constructeur")
    void testConstructeur() {
        assertThrows(IllegalArgumentException.class, () -> new LotImpl(0, 3, "Coq"));
    }

    @Test
    @DisplayName("Test: SignalerAffectation => True")
    void testSignalerAffectationTrue() {
        assertTrue(lot.signalerAffectation());
    }

    @Test
    @DisplayName("Test: SignalerAffectation => False")
    void testSignalerAffectationFalse() {
        lot.signalerAffectation();
        assertFalse(lot.signalerAffectation());
    }
}