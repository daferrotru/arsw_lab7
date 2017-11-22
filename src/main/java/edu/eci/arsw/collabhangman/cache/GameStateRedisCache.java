/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.collabhangman.cache;

import edu.eci.arsw.collabhangman.model.game.HangmanGame;
import edu.eci.arsw.collabhangman.model.game.HangmanRedisGame;
import edu.eci.arsw.collabhangman.services.GameCreationException;
import edu.eci.arsw.collabhangman.services.GameServicesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2104835
 */
@Service
public class GameStateRedisCache implements GameStateCache {

    private StringRedisTemplate template;

    @Override
    public void createGame(int id, String word) throws GameCreationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HangmanGame getGame(int gameid) throws GameServicesException {
        String gameId= "game:"+gameid;
        if (!template.hasKey(gameId)) throw new GameServicesException("This current room does not exist");
        return new HangmanRedisGame(gameid, template);
    }

    @Autowired
    public void setTemplate(StringRedisTemplate template) {
        this.template = template;
    }
    
}
