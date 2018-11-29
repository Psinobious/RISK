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
public class Army {
int infantry = 1;
int calvary = 3;
int cannon = 5;
int strength = 0;
int infantryNumber, calvaryNumber, cannonNumber;

    public Army(int infantry, int calvary, int cannon){
        this.infantryNumber = infantry;
        this.calvaryNumber = calvary;
        this.cannonNumber = cannon;
        this.strength = (this.infantry*this.infantryNumber) + 
                (this.calvary*this.calvaryNumber) + (this.cannon*this.cannonNumber);
    }

    public int getInfantry() {
        return infantry;
    }

    public void setInfantry(int infantry) {
        this.infantry = infantry;
    }

    public int getCalvary() {
        return calvary;
    }

    public void setCalvary(int calvary) {
        this.calvary = calvary;
    }

    public int getCannon() {
        return cannon;
    }

    public void setCannon(int cannon) {
        this.cannon = cannon;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getInfantryNumber() {
        return infantryNumber;
    }

    public void setInfantryNumber(int infantryNumber) {
        this.infantryNumber = infantryNumber;
    }

    public int getCalvaryNumber() {
        return calvaryNumber;
    }

    public void setCalvaryNumber(int calvaryNumber) {
        this.calvaryNumber = calvaryNumber;
    }

    public int getCannonNumber() {
        return cannonNumber;
    }

    public void setCannonNumber(int cannonNumber) {
        this.cannonNumber = cannonNumber;
    }
    
}
