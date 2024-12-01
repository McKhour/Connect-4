package org.example;

import java.util.Objects;

public final class Jatekos {
    /**
     * A játékos nevét menit el.
     */
    private final String nev;
    /**
     * A játékos pontját menit el.
     */
    private final int pont;

    /**
     * A konstruktorokért felel.
     * @param nevek Továbbra is a játékos nevéért felel.
     * @param pontok Továbbra is a játékso pontjáért felel.
     */
    public Jatekos(final String nevek, final int pontok) {
        this.nev = nevek;
        this.pont = pontok;
    }

    /**
     * Egy getter.
     * @return visszaadja a nevet.
     */
    public String getNev() {
        return nev;
    }

    /**
     * Egy getter.
     * @return vissza adja a pontot.
     */
    public int getPont() {
        return pont;
    }

    /**
     * A pontokért felel az adatbázisban.
     * @return feltölti a pontokat
     */
    public Jatekos incrementScore() {
        return new Jatekos(this.nev, this.pont + 1);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Jatekos player = (Jatekos) o;
        return pont == player.pont && Objects.equals(nev, player.nev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nev, pont);
    }

    @Override
    public String toString() {
        return "Játékos{"
                + "név='" + nev + '\''
                + ", pontszáma=" + pont
                + '}';
    }
}
