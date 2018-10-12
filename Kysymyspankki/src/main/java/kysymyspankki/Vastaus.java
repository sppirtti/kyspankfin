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
public class Vastaus {

    private Integer id;
    private Integer kysymys_id;
    private String vastaus;
    private Boolean oikein;

    public Vastaus(Integer id, Integer kysymys_id, String vastaus, Boolean oikein) {
        this.id = id;
        this.kysymys_id = kysymys_id;
        this.vastaus = vastaus;
        this.oikein = oikein;
    }

    public Integer getId() {
        return this.id;
    }
    
    public Integer getKysymys_id() {
        return this.kysymys_id;
    }

    public String getVastaus() {
        return this.vastaus;
    }
    
    public Boolean getOikein() {
        return this.oikein;
    }

}
