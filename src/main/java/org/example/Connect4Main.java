package org.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.util.concurrent.TimeUnit;

final class Connect4Main {

    /**
     * Tartalmazza a jdbc-hez tartozó URL-t.
     */
    public static final String URL = "jdbc:mysql://localhost:3306/connect4";
    /**
     * Tartalmazza a jdbc-hez tartozó belépést.
     */
    public static final String USER = "root";
    /**
     * Ez üresen marad.
     */
    public static final String PASSWORD = "";

    private Connect4Main() {
        throw new UnsupportedOperationException(
                "Ez egy utility class és nem lehet példányosítani");
    }
    /**
     * Leírja a sorok számát.
     */
    public static final int SOR = 6;
    /**
     * Leírja az oszlopok számát.
     */
    public static final int OSZLOP = 7;
    /**
     * Leírja a játékban megtehető maximum lépéseket.
     */
    public static final int MAX_LEPES = 42;
    /**
     * Megkeresi hány újabb korong kell a győzelemhez.
     */
    public static final int MAX_TAVOLSAG = 3;
    /**
     * Őszinte leszek, a checkstyle miatt csinálom már csak ezt.
     */
    public static final int HARMAS = 3;

    public static void main(final String[] args) throws InterruptedException {

        Scanner donto = new Scanner(System.in);
        System.out.println("""
                Válassza ki mit szeretne:
                Játék indítása: \t1
                 Pont táblázat: \t2""");
        //Ez egy kisebb menű szerűség lenne
        if (donto.nextInt() == 2) {
            System.out.println("------------------------------");
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection =
                        DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet =
                        statement.executeQuery("select * from pontok");
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1) + " "
                            + resultSet.getString(2) + " "
                            + resultSet.getInt(HARMAS));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("------------------------------");
            System.out.println("A visszalépéshez nyomja meg a 0-s gombot!");
            if (donto.nextInt() != 0) {
                System.out.println("Igazából mindegy mit nyom meg");
                //viccess akartam lenni
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("Kezdődjék a játék!");
        } else {
            System.out.println("Kérem helyes értéket adjon legközelebb");
        }
        System.out.println("Kezdődjék a játék");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Kérem adja meg a játékos nevét!");
        Scanner be = new Scanner(System.in);
        String nev = be.nextLine();
        Jatekos player = new Jatekos(nev, 0);
        //itt van a teljes játék önmaga
        //minden egy while ciklusban
        boolean ujabbKor = true;
        while (ujabbKor) {
            //itt határozuk meg a méreteit (sor és oszlop)
            char[][] mezo = new char[SOR][OSZLOP];

            for (int sor = 0; sor < mezo.length; sor++) {
                for (int oszlop = 0; oszlop < mezo[0].length; oszlop++) {
                    mezo[sor][oszlop] = ' ';
                }
            }
            //ez a szerkentyű később még jól jöhet
            int kor = 1;
            char jatekos = 'S';
            boolean nyertes = false;

            //egy kör lejátszása
            while (!nyertes && kor <= MAX_LEPES) {
                boolean helyesLepes;
                int lepes;
                if (jatekos == 'S') {
                    do {
                        megjelenit(mezo);

                        System.out.print("Válasszon egy mezőt: ");
                        lepes = be.nextInt() - 1;
                        while (lepes >= OSZLOP) {
                            System.out.println("Helytelen, lépés, lépjen újra");
                            lepes = be.nextInt() - 1;
                        }

                        //megnézi hogy helyes-e a lépés
                        helyesLepes = ellenorzes(lepes, mezo);

                    } while (!helyesLepes);
                } else {

                    //átvált a gépre
                    System.out.println("A gép köre következik");
                    TimeUnit.SECONDS.sleep(2);
                    lepes = ailepes(mezo);
                    System.out.println("A gép a " + (lepes + 1)
                            + " oszlopba lépett.");
                }
                //"beledobja" a korongot
                for (int sor = mezo.length - 1; sor >= 0; sor--) {
                    if (mezo[sor][lepes] == ' ') {
                        mezo[sor][lepes] = jatekos;
                        break;
                    }
                }

                //megnézi van-e nyertes
                nyertes = gyoztes(jatekos, mezo);

                //játékos váltás
                jatekos = (jatekos == 'S') ? 'P' : 'S';

                kor++;
            }
            megjelenit(mezo);

            if (nyertes) {
                if (jatekos == 'S') {
                    System.out.println("A gép nyert");
                } else {
                    System.out.println(nev + " nyert");
                    player = player.incrementScore();
                }
            } else { //igen, lehet döntetlen is
                System.out.println("Döntetlen");
            }
            try {
                mezoFileba(mezo);
            } catch (IOException e) {
                System.out.println("Hiba történt a fájl írásakor: "
                        + e.getMessage());
            }
            // ha végig ment a kör és van eredmény, ide dobódunk le
            TimeUnit.SECONDS.sleep(2);
            System.out.println("Szeretne új játékot játszani? (igen/nem)");
            String valasz = be.next().toLowerCase();
            ujabbKor = valasz.equals("igen");
        }
        pontMentese(player);
        System.out.println("Köszönjük a játékot!");
        be.close();
    }

    //ez a rész felel a mező megjelenítéséért
    private static void megjelenit(final char[][] mezo) {
        System.out.println(" 1 2 3 4 5 6 7");
        System.out.println("---------------");
        for (char[] chars : mezo) {
            System.out.print("|");
            for (int oszlop = 0; oszlop < mezo[0].length; oszlop++) {
                System.out.print(chars[oszlop]);
                System.out.print("|");
            }
            System.out.println();
            System.out.println("---------------");
        }
        System.out.println(" 1 2 3 4 5 6 7");
        System.out.println();
    }

    static boolean ellenorzes(final int oszlop, final char[][] mezo) {
        //leteszteli hogy megfelel-e az oszlop
        if (oszlop < 0 || oszlop > mezo[0].length) {
            System.out.println("Helytelen, lépés, lépjen újra");
            return false;
        }


        //leteszteli hogy tele van-e az oszlop
        if (mezo[0][oszlop] != ' ') {
            System.out.println("Helytelen, lépés, lépjen újra");
            return false;
        }
        //ha mind a kettő megbukott, akkor az érték igaz lesz, és tovább lép
        return true;
    }

    static boolean gyoztes(final char jatekos, final char[][] mezo) {
        //megnézi hogy van-e 4 korong vízszintesen
        for (char[] chars : mezo) {
            for (int oszlop = 0; oszlop < mezo[0].length - MAX_TAVOLSAG;
                 oszlop++) {
                if (chars[oszlop] == jatekos
                        && chars[oszlop + 1] == jatekos
                        && chars[oszlop + 2] == jatekos
                        && chars[oszlop + MAX_TAVOLSAG] == jatekos) {
                    return true;
                }
            }
        }
        //ugyan ez függőre
        for (int sor = 0; sor < mezo.length - MAX_TAVOLSAG; sor++) {
            for (int oszlop = 0; oszlop < mezo[0].length; oszlop++) {
                if (mezo[sor][oszlop] == jatekos
                        && mezo[sor + 1][oszlop] == jatekos
                        && mezo[sor + 2][oszlop] == jatekos
                        && mezo[sor + MAX_TAVOLSAG][oszlop] == jatekos) {
                    return true;
                }
            }
        }
        //bal fentről jobb lentre
        for (int sor = MAX_TAVOLSAG; sor < mezo.length; sor++) {
            for (int oszlop = 0; oszlop < mezo[0].length - MAX_TAVOLSAG;
                 oszlop++) {
                if (mezo[sor][oszlop] == jatekos
                        && mezo[sor - 1][oszlop + 1] == jatekos
                        && mezo[sor - 2][oszlop + 2] == jatekos
                        && mezo[sor - MAX_TAVOLSAG][oszlop + MAX_TAVOLSAG]
                        == jatekos) {
                    return true;
                }
            }
        }
        //bal lentről jobb fentre
        for (int sor = 0; sor < mezo.length - MAX_TAVOLSAG; sor++) {
            for (int oszlop = 0; oszlop < mezo[0].length - MAX_TAVOLSAG;
                 oszlop++) {
                if (mezo[sor][oszlop] == jatekos
                        && mezo[sor + 1][oszlop + 1] == jatekos
                        && mezo[sor + 2][oszlop + 2] == jatekos
                        && mezo[sor + MAX_TAVOLSAG][oszlop + MAX_TAVOLSAG]
                        == jatekos) {
                    return true;
                }
            }
        }
        return false;
    }

    /*az "AI" lépései
    egyedüli hátul ütő, hogy nem tudtam megoldani azt az esetet
    amikor illegális (nem helyes lépést) hajt végre
    */
    static int ailepes(final char[][] mezo) {
        //a Random-mal később fog foglalkozni, lényegében arra van,
        // hogy valahova ledobja a korongot
        Random rand = new Random();
        /*ezzel dobja le a korongot a gépnek
        ezek mellet egy picit csal, mert a fő feladata,
        hogy blokkolja a játékost, és persze keres egy helyes (nyerő) lépést
         */
        for (int oszlop = 0; oszlop < OSZLOP; oszlop++) {
            if (ellenorzes(oszlop, mezo)) {
                for (int sor = mezo.length - 1; sor >= 0; sor--) {
                    if (mezo[sor][oszlop] == ' ') {
                        mezo[sor][oszlop] = 'P';  // Szimulál egy lépést
                        if (gyoztes('P', mezo)) {
                            mezo[sor][oszlop] = ' '; // Vissza vonja a lépést
                            return oszlop; // Megvan a helyes lépés
                        }
                        mezo[sor][oszlop] = ' '; // Visszavonja a dolgokat
                        break;
                    }
                }
            }
        }
        //ez a rész felel a játékos blokkolásáért
        for (int oszlop = 0; oszlop < OSZLOP; oszlop++) {
            if (ellenorzes(oszlop, mezo)) {
                //a játékos lépésének szimulálása
                for (int sor = mezo.length - 1; sor >= 0; sor--) {
                    if (mezo[sor][oszlop] == ' ') {
                        mezo[sor][oszlop] = 'S';
                        //ideiglenesen lerakja (magának) a játékos korongját
                        if (gyoztes('S', mezo)) {
                            mezo[sor][oszlop] = ' '; //visszavonja a lépést
                            return oszlop; //blokkoló lépés megtalálva
                        }
                        mezo[sor][oszlop] = ' '; //visszavonja a dolgokat
                        break;
                    }
                }
            }
        }
        /*ha nem érzékeli hogy tudna nyerni, vagy a játékost meg
        tudja akadályozni egy győzelemtől, akkor
        kiválaszt egy oszlpot random, ahova bedobhassa a korongot
         */
        int lepes;
        do {
            lepes = rand.nextInt(OSZLOP);
        } while (!ellenorzes(lepes, mezo));

        return lepes;
    }

    /*file-ba írás
    kiírja a pálya végső állapotát
    */
    static void mezoFileba(final char[][] mezo) throws IOException {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter("vegso_allas.txt"))) {
            writer.write(" 1 2 3 4 5 6 7\n");
            writer.write("---------------\n");
            for (char[] chars : mezo) {
                writer.write("|");
                for (int oszlop = 0; oszlop < mezo[0].length; oszlop++) {
                    writer.write(chars[oszlop]);
                    writer.write("|");
                }
                writer.write("\n---------------\n");
            }
            writer.write(" 1 2 3 4 5 6 7\n");
        }
    }
    //Ez az SQL/JDBC miatt szükséges
    private static void pontMentese(final Jatekos jatekos) {
        try (Connection conn =
                     DriverManager.getConnection(URL, USER, PASSWORD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "INSERT INTO pontok (player_name, score) "
                    + "VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, jatekos.getNev());
                stmt.setInt(2, jatekos.getPont());
                stmt.executeUpdate();
                System.out.println("Az eredmény sikeresen elmentve "
                        + "az adatbázisba!");
            }
        } catch (Exception e) {
            System.out.println("Hiba történt az adatbázis művelet során: "
                    + e.getMessage());
        }
    }
}
//MM FOOD
