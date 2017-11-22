package edu.eci.arsw.collabhangman.model.game.entities;

/**
 * @author KevinMendieta
 */
public class Score {

    private int value;
    private String date;

    public Score(int value, String date) {
        this.value = value;
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    

}
