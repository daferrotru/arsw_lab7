/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.collabhangman.restcontrollers;

import edu.eci.arsw.collabhangman.services.GameServices;
import edu.eci.arsw.collabhangman.services.GameServicesException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hcadavid
 */
@RestController
@Service
@RequestMapping("/users")
public class UsersResourceController {
    
    private GameServices gameServices;
  
    
    @RequestMapping(method = RequestMethod.GET, path = "/{userId}")
    public ResponseEntity<?> getUser(@PathVariable int userId) {
        try {
            return new ResponseEntity<>(gameServices.loadUserData(userId), HttpStatus.ACCEPTED);
        } catch (GameServicesException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/scores/{score}")
    public ResponseEntity<?> findByScore(@PathVariable int score) {
        System.out.println(score);
        return new ResponseEntity<>(gameServices.findByScore(score), HttpStatus.ACCEPTED);
    }
    
    @Autowired
    public void setGameServices(GameServices gameServices) {
        this.gameServices = gameServices;
    }
    
    
    
    
}
