/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arttu
 */
public class Kysymys {

    private int id;
    private String kurssi;
    private String aihe;
    private String kysymysteksti;

    public Kysymys(int id, String kurssi, String aihe, String kysymysteksti) {
        this.aihe = aihe;
        this.kurssi = kurssi;
        this.kysymysteksti = kysymysteksti;
        this.id = id;

    }

    public void setKurssi(String kurssi) {
        this.kurssi = kurssi;
    }

    public void setAihe(String aihe) {
        this.aihe = aihe;
    }

    public void setKysymysteksti(String kysymysteksti) {
        this.kysymysteksti = kysymysteksti;
    }

    public String getKurssi() {
        return this.kurssi;
    }

    public String getAihe() {
        return this.aihe;
    }

    public String getKysymysteksti() {
        return this.kysymysteksti;
    }

    public int getId() {
        return this.id;
    }
}
