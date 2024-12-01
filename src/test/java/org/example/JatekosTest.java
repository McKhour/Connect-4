package org.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
public class JatekosTest {

    private Jatekos mockPlayer;

    @BeforeEach
    public void setUp() {
        // Create a mock Player object using Mockito
        mockPlayer = mock(Jatekos.class);
    }

    @Test
    public void testGetNev() {
        // Given that the player's name is "John"
        when(mockPlayer.getNev()).thenReturn("John");

        // Test if the getName method returns the expected name
        assertEquals("John", mockPlayer.getNev());

        // Verify if getName() was called once
        verify(mockPlayer, times(1)).getNev();
    }

    @Test
    public void testGetScore() {
        // Given that the player's score is 10
        when(mockPlayer.getPont()).thenReturn(10);

        // Test if the getScore method returns the expected score
        assertEquals(10, mockPlayer.getPont());

        // Verify if getScore() was called once
        verify(mockPlayer, times(1)).getPont();
    }

    @Test
    public void testIncrementScore() {
        // Create a real Player object
        Jatekos realPlayer = new Jatekos("John", 10);

        // Test incrementScore
        Jatekos updatedPlayer = realPlayer.incrementScore();

        // Assert that the score is incremented
        assertEquals(11, updatedPlayer.getPont());
    }
}