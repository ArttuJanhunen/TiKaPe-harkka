/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arttu
 */
public class Vastaus {
    private String vastausteksti;
    private boolean oikein;
    private int id;
    private int kysymysId;
    
    public Vastaus(int id, int kysymysId, String vastausteksti, boolean oikein){
        this.vastausteksti=vastausteksti;
        this.oikein=oikein;
        this.id=id;
        this.kysymysId=kysymysId;
    }
    
    
    public void setTeksti(String teksti){
        this.vastausteksti=teksti;
    }
    
    public void setOikein(boolean arvo){
        this.oikein=arvo;
    }
    
    public String getTeksti(){
        return this.vastausteksti;
    }
    
    public boolean getArvo(){
        return this.oikein;
    }
    
    public int getId(){
        return this.id;
    }
    
    public int getKysymysId(){
        return this.kysymysId;
    }
    
    public String getTekstina(){
        if (getArvo()){
            return "Oikein";
        }else{
            return "Väärin";
        }
    }
}
