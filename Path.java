/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import javafx.scene.shape.Line;

/**
 *
 * @author psino
 */
public class Path {
    Territory territory;
    Line line;
    public Path(Territory territory, Line line){
        this.line = line;
        this.territory = territory;
    }
    public Path(){
        
    }
    public Territory getTerritory() {
        return territory;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }
    
}
