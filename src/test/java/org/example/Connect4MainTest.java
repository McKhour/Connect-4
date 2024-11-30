package org.example;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Connect4MainTest {

    @Test
    void testEllenorzesValidMove() {
        char[][] mezo = new char[Connect4Main.SOR][Connect4Main.OSZLOP];
        for (int i = 0; i < Connect4Main.SOR; i++) {
            for (int j = 0; j < Connect4Main.OSZLOP; j++) {
                mezo[i][j] = ' ';
            }
        }

        assertTrue(Connect4Main.ellenorzes(3, mezo), "Valós");
    }

    @Test
    void testEllenorzesInvalidMove() {
        char[][] mezo = new char[Connect4Main.SOR][Connect4Main.OSZLOP];
        for (int i = 0; i < Connect4Main.SOR; i++) {
            for (int j = 0; j < Connect4Main.OSZLOP; j++) {
                mezo[i][j] = ' ';
            }
        }
        mezo[0][3] = 'S';

        assertFalse(Connect4Main.ellenorzes(3, mezo), "Nem valós");
    }

    @Test
    void testGyoztesHorizontalWin() {
        char[][] mezo = new char[Connect4Main.SOR][Connect4Main.OSZLOP];
        for (int i = 0; i < Connect4Main.SOR; i++) {
            for (int j = 0; j < Connect4Main.OSZLOP; j++) {
                mezo[i][j] = ' ';
            }
        }

        mezo[2][0] = 'S';
        mezo[2][1] = 'S';
        mezo[2][2] = 'S';
        mezo[2][3] = 'S';
        assertTrue(Connect4Main.gyoztes('S', mezo), "A játékosnak kell nyernie");

    }

    @Test
    void testGyoztesVerticalWin() {
        char[][] mezo = new char[Connect4Main.SOR][Connect4Main.OSZLOP];
        for (int i = 0; i < Connect4Main.SOR; i++) {
            for (int j = 0; j < Connect4Main.OSZLOP; j++) {
                mezo[i][j] = ' ';
            }
        }

        mezo[0][2] = 'S';
        mezo[1][2] = 'S';
        mezo[2][2] = 'S';
        mezo[3][2] = 'S';
        assertTrue(Connect4Main.gyoztes('S', mezo), "Az S a nyertess");
    }

    @Test
    void testAilepes() {
        char[][] mezo = new char[Connect4Main.SOR][Connect4Main.OSZLOP];
        for (int i = 0; i < Connect4Main.SOR; i++) {
            for (int j = 0; j < Connect4Main.OSZLOP; j++) {
                mezo[i][j] = ' ';
            }
        }

        int aiMove = Connect4Main.ailepes(mezo);
        assertTrue(aiMove >= 0 && aiMove < Connect4Main.OSZLOP, "AI should select a valid column.");
    }

    /*@Test
    void testMezoFileba() throws IOException {
        char[][] mezo = new char[Connect4Main.SOR][Connect4Main.OSZLOP];
        for (int i = 0; i < Connect4Main.SOR; i++) {
            for (int j = 0; j < Connect4Main.OSZLOP; j++) {
                mezo[i][j] = ' ';
            }
        }

        BufferedWriter writerMock = Mockito.mock(BufferedWriter.class);
        Connect4Main.mezoFileba(mezo);

        Mockito.verify(writerMock).write(Mockito.anyString());
    }
    */
    @Test
    void testGameFlow() {
        Scanner scannerMock = Mockito.mock(Scanner.class);
        Mockito.when(scannerMock.nextInt()).thenReturn(0, 1, 2, 3);
        Mockito.when(scannerMock.nextLine()).thenReturn("Player");
    }
}