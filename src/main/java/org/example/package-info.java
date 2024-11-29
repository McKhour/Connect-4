/**
 * Egy Connect 4 játékot implementál, ahol a játékos
 * megmérheti erejét egy (kissé fapados) számítógéppel
 * (modenrség kedvéért AI) szemben
 * A játék logikája, megjelenítése és használata mind a
 * {@code Connect4Main} osztályban
 * vannak implementálva
 *
 * <p>
 * Az osztály a következő fő konstansokat tartalmazza (14-17 sor)
 * </p>
 * <ul>
 *     <li>{@code SOR} - A Connect4 sorainak számát tartalmazza.</li>
 *     <li>{@code OSZLOP} - A Connect4 oszlopainak számát tartalmazza.</li>
 *     <li>{@code MAX_LEPES} - A játékban megtehető maximum
 *     lépések számát tartalmazza.mEzt a következő képlettel
 *     kaptuk: {@code SOR * OSZLOP}.</li>
 *     <li>{@code MAX_TAVOLSAG} - A nyeréshez szükséges max
 *     korongok számát tartalmazza,
 *     az akkor ledobott korong kivételével.</li>
 * </ul>
 *
 * <p>
 * A package egy Connect4 játék fő funkcionalitását tartalmazza.
 * Magába foglalja a különböző metódusokat hogy megalkossa a játék mezőt,
 * a játékos bevitelét
 * implementálja az AI lépéseit, és megvizsgálja hogy ki a nyertes.
 * A játék kiírja a jelenlegi
 * játékállást a konzolra, és a mező legutolsó állapotát
 * kiírja egy mellékes .txt file-ba.
 * </p>
 */
package org.example;
