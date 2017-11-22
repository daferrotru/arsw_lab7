/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.collabhangman.model.game;

import java.util.Collections;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 *
 * @author 2104835
 */
public class HangmanRedisGame extends HangmanGame {

    private String gameId;
    private StringRedisTemplate template;

    public HangmanRedisGame(int gameId, StringRedisTemplate template) {
        super("");
        this.gameId = "game:" + gameId;
        this.template = template;
    }

    /**
     * @pre gameFinished==false
     * @param l new letter
     * @return the secret word with all the characters 'l' revealed
     */
    @Override
    public String addLetter(char l) {

        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/addLetter.lua")));
        redisScript.setResultType(String.class);

        return template.execute(new SessionCallback< String>() {

            @SuppressWarnings("unchecked")
            @Override
            public < K, V> String execute(final RedisOperations< K, V> operations) throws DataAccessException {
                operations.watch((K) gameId);
                operations.multi();
                String res= operations.execute(redisScript, Collections.singletonList((K) gameId), l+"");
                operations.exec();
                return res;
            }
        });
    }

    @Override
    public synchronized boolean tryWord(String playerName, String s) {
        String word = (String) template.opsForHash().get(gameId, "completeWord");

        if (s.toLowerCase().equals(word)) {
            template.opsForHash().put(gameId, "winner", playerName);
            template.opsForHash().put(gameId, "status", "true");
            template.opsForHash().put(gameId, "guessedWord", s.toLowerCase());
            return true;
        }
        return false;

    }

    @Override
    public boolean gameFinished() {
        String gameFinished = (String) template.opsForHash().get(gameId, "status");
        return gameFinished.equals("true");
    }

    /**
     * @pre gameFinished=true;
     * @return winner's name
     */
    @Override
    public String getWinnerName() {
        return (String) template.opsForHash().get(gameId, "winner");

    }

    @Override
    public String getCurrentGuessedWord() {
        return (String) template.opsForHash().get(gameId, "guessedWord");

    }

}
