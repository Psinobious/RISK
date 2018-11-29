/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import javafx.scene.paint.Color;

/**
 *
 * @author psino
 */
public class Player {
    private String playerID, name;
    private Color color;
    
    
    private int armyPool = 3;
    
    
    public Player(String playerID, String name){
        this.playerID = playerID;
        this.name = name;
    }
}
