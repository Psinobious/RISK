/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

/**
 *
 * @author psino
 */
public class Command {
    
    private Action action;
    private int armySize;
    
    public Command(Action action, int armySize){
        this.action = action;
        this.armySize = armySize;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
    
    

    public int getArmySize() {
        return armySize;
    }

    public void setArmySize(int armySize) {
        this.armySize = armySize;
    }

    @Override
    public String toString() {
        switch(action){
            case ATTACK:
                break;
            case DEPLOY:
                break;
            case TRANSFER:
                break;
        }
            
        return "Command{" + "action=" + action + ", armySize=" + armySize + '}';
    }
    
}
