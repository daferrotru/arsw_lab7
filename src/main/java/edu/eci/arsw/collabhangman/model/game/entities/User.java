/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.collabhangman.model.game.entities;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author hcadavid
 */
@Document(collection = "users")
public class User {
    
    @Id
    private int id;
    
    private List<Score> scores;
    
    private String name;
    
    private String photoUrl;

    public User(int id, String name, String photoUrl) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public User(int id, String name, String photoUrl, List<Score> scores) {
        this.id = id;
        this.scores = scores;
        this.name = name;
        this.photoUrl = photoUrl;
    }
    
    

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public List getScores() {
        return scores;
    }
    
    public void setScores(List scores) {
        this.scores = scores;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    
    
    
}
