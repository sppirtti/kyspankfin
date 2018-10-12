/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kysymyspankki;

/**
 *
 * @author Samuli
 */
public class Kysymys {

    private Integer id;
    private String kurssi;
    private String aihe;
    private String kysymysteksti;

    public Kysymys(Integer id, String kurssi, String aihe, String kysymysteksti) {
        this.id = id;
        this.kurssi = kurssi;
        this.aihe = aihe;
        this.kysymysteksti = kysymysteksti;
    }

    public Integer getId() {
        return this.id;
    }

    public String getKysymysteksti() {
        return this.kysymysteksti;
    }
    public String getKurssi() {
        return this.kurssi;
    }
    public String getAihe() {
        return this.aihe;
    }

}
