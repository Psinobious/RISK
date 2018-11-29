/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This is the class that is used to create the territories and provide the necessary framework
 * for it to be inter-actable on the RISK board
 * @author Daniel Johnson
 */
public class Territory extends Pane{

    private Text text, monitor;
    private int xCoord = 0;
    private int yCoord = 0;
    private int width, height;
    private int strength = 0;
    private int adjacentTerritoryNumber;
    private Color color;
    private String territoryID, territoryName;
    private Rectangle rectangle;
    private String ownerID;
    private ArrayList<String> adjacentTerritories = new ArrayList<String>();
    
    private ArrayList<Arrow> arrows;
    
    /**
     * This constructor is used to create the territories of the board 
     * @param width
     * @param height
     * @param ownerID
     * @param territoryName
     * @param strength
     * @param xCoord
     * @param yCoord
     */
    public Territory(int width, int height, String ownerID, 
            String territoryName, int strength, int xCoord, int yCoord){
        this.width = width;
        this.height = height;
        this.strength = strength;
        this.territoryName = territoryName;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.ownerID = ownerID;
               
        rectangle = new Rectangle(this.width, this.height);
        rectangle.setX(this.xCoord);
        rectangle.setY(this.yCoord);
        rectangle.setFill(this.color);
        
        text = new Text(territoryName);
        text.setFont(Font.font("Times New Roman", 40));
        text.setFill(Color.BLACK);
        text.setX(xCoord);
        text.setY(yCoord + 50);
        
        monitor = new Text(""+strength);
        monitor.setFont(Font.font("Times New Roman", 60));
        monitor.setFill(Color.BLACK);
        monitor.setX(xCoord + 20);
        monitor.setY(yCoord + 100);
        
        arrows = new ArrayList<Arrow>();
        getChildren().addAll(rectangle, text, monitor);
        setColor(this.ownerID);
    }
    /**
     * This method is used to add arrows onto the territory that will be used to illustrate the routes that can be taken
     * @param startX start x coordinate of the line
     * @param startY start y coordinate of the line 
     * @param endX coordinate of the line
     * @param endY coordinate of the line
     */
    public void setArrow(double startX, double startY, double endX, double endY){
        double originX, originY, destinationX, destinationY;
        
        originX = startX + (width/2);
        originY = startY + (height/2);
        
        destinationX = endX + (width/2);
        destinationY = endY + (height/2);
        
        arrows.add(new Arrow(originX, originY, destinationX, destinationY, 30));
        
        int index = arrows.size() - 1;
        
        arrows.get(index).toFront();
        this.getChildren().add(arrows.get(index));
        arrows.get(index).setVisible(false);
        
    }
    
    /**
     * Used to show all the routes this territory contains
     */
    public void showRoutes(){
        for(int x = 0; x < arrows.size(); x++){
            arrows.get(x).setVisible(true);        
        }
    }
    /**
     * Used to hide all the routes this territory contains
     */
    public void hideRoutes(){
        for(int x = 0; x < arrows.size(); x++){
            arrows.get(x).setVisible(false);
        }
    }
    /**
     * Used to retrieve individual adjacent territories
     * @param index
     * @return individual adjacent territories
     */
    public String getAdjacentTerritory(int index) {
        return adjacentTerritories.get(index);
    }
    /**
     * Used to add adjacent territories to the territory indirectly
     * @param input 
     */
    public void addAdjacentTerritories(Territory territory) {
        String input = territory.getTerritoryName();
        adjacentTerritories.add(input);
    }
    /**
     * Used to retrieve the arraylist of adjacent territories
     * @return 
     */
    public ArrayList<String> getAdjacentTerritories() {
        return adjacentTerritories;
    }
    /**
     * Used to modify the list of adjacent territories
     * @param adjacentTerritories the new list of adjacent territories
     */
    public void setAdjacentTerritories(ArrayList<String> adjacentTerritories) {
        this.adjacentTerritories = adjacentTerritories;
    }
    /**
     * Retrieves the ownerID of the territory
     * @return ownerID
     */
    public String getOwnerID() {
        return ownerID;
    }
    /**
     * Sets the owner id of the territory
     * @param ownerID 
     */
    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }
    
    /**
     * Retrieves the name of the territory
     * @return territoryName
     */
    public String getTerritoryName() {
        return territoryName;
    }
    /**
     * Sets the name of the territory
     * @param territoryName 
     */
    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }

    public int getCoordX(){
        return xCoord;
    }
    public int getCoordY(){
        return yCoord;
    }
    /**
     * Retrieves the number of armies the territories has
     * @return strength
     */
    public int getStrength() {
        return strength;
    }
    /**
     * Used to set the color based on the ownerid 
     * @param input used to determine territory color
     */
    public void setColor(String input){
        switch(input){
            case "Player_1":
                color = Color.RED;
                this.rectangle.setFill(color);
                break;
            case "Player_2":
                color = Color.BLUE;
                this.rectangle.setFill(color);
                break;
            case "Player_3":
                color = Color.GREEN;
                this.rectangle.setFill(color);
                break;
            case "Player_4":
                color = Color.ORANGE;
                this.rectangle.setFill(color);
                break;
            default:
                color = Color.GREY;
                this.rectangle.setFill(color);
                break;
        }
        
    }
    /**
     * Adjust number of armies on territory
     * @param strength 
     */
    public void setStrength(int strength) {
        this.strength = strength;
        monitor.setText(""+this.strength);
    }

    public int getTerritoryWidth() {
        return width;
    }

    public int getTerritoryHeight() {
        return height;
    }
    
    /**
     * Displays information of the territory
     * @return Territory information
     */
    @Override
    public String toString() {
        return "Territory{" + "strength=" + strength + ", adjacentTerritoryNumber=" + adjacentTerritoryNumber + ", territoryName=" + territoryName + ", ownerID=" + ownerID + '}';
    }
    

}
