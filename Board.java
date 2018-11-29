/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * This is the client class used to create the board for players to play RISK
 * @author Daniel Johnson
 */
public class Board extends Application{
    private int numberOfTerritories;
    private Territory[] territories;
    private String player = "Player_1";
    private Group group = new Group();
    //private ActionMenu menu;
    private Menu test;
    private MenuBar menuBar;
    private ComboBox comboBox;
      // Input and output streams from/to server
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    private String host = "localhost";
    
    private Statement statement = null;
    private Connection connection = null;
    
    private ArrayList<Line> lines;
    private LinkedHashMap<String, List<Path>> lineMap;
    private LinkedHashMap<String, Command> commandMap;
    /**
     *
     * @param args
     */
    public static void main(String[]args){
        Application.launch(args);
        
    }
    /**
     * This method is used to run the GUI for the player and provide the player 
     * with a means to interact with the board
     * @param primaryStage
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception { 
        numberOfTerritories = 5;
        territories = new Territory[numberOfTerritories];
        commandMap = new LinkedHashMap<String, Command>();
        lineMap = new LinkedHashMap<String, List<Path>>();
        
        
        createTerritories(territories);
        assignAdjacentTerritories(territories);
        //createArrows(territories);  
        createPaths(territories);
        addTerritoriesToBoard();
        
        System.out.println(lineMap.toString());
        BorderPane border = new BorderPane();
        GridPane gPane = new GridPane();
        FlowPane fPane = new FlowPane();
        
        
        Pane canvas = new Pane();
        canvas.setPrefSize(700, 400);
        canvas.getChildren().add(group);
        
        
   
        
        gPane.add(canvas,0,0);
        gPane.setBorder(new Border(new BorderStroke(Color.BLACK, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


        Label TerritoryLabel = new Label("Territory");
        TextArea TerritoryName = new TextArea();
        TerritoryName.setPrefRowCount(1);
        TerritoryName.setEditable(false);
        
        Label TerritoryStrength = new Label("Army Strength");
        TextArea ArmyStrength = new TextArea();
        ArmyStrength.setPrefRowCount(1);
        ArmyStrength.setEditable(false);
        
        Label AdjacentTerritoryLabel = new Label("Adjacent Territory");
        
        comboBox = new ComboBox();
        comboBox.setPrefWidth(200);
        
        Label ArmyAllocatedLabel = new Label("Army Allocated");
        TextArea ArmyAllocated = new TextArea();
        ArmyAllocated.setPrefRowCount(1);
        ArmyAllocated.setEditable(false);
        
        
        fPane.getChildren().add(TerritoryLabel);
        fPane.getChildren().add(TerritoryName);
        fPane.getChildren().add(TerritoryStrength);
        fPane.getChildren().add(ArmyStrength);
        fPane.getChildren().add(AdjacentTerritoryLabel);
        fPane.getChildren().add(comboBox);
        fPane.getChildren().add(ArmyAllocatedLabel);
        fPane.getChildren().add(ArmyAllocated);
        
        
        
        fPane.setOrientation(Orientation.VERTICAL);
        
        test = new Menu("test");
        
        menuBar = new MenuBar();
        menuBar.getMenus().add(test);
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        menuBar.toFront();
        
//        menu = new ActionMenu(territories);
       // menu.show();
        //menu.toFront();
        
        
        border.setCenter(gPane);
        border.setRight(fPane);
        border.setTop(menuBar);
        
        
        Group bigGroup = new Group();
        bigGroup.getChildren().add(border);

        
        
        gPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            boolean sentinel = false;
            @Override
            public void handle(MouseEvent event) {
                System.out.println();
                
                for(int x = 0; x < territories.length; x++){
                    //System.out.println("Territory: " + territories[x].getTerritoryName() + "   status: " +
                    //        isClicked(event.getSceneX(), event.getSceneY(), territories[x]));
                    
                    if(isClicked(event.getSceneX(), event.getSceneY(), territories[x]) == true){
                        if(territories[x].getOwnerID().compareTo(player) == 0){          
                            for(int y = 0; y < territories.length; y++){
                                territories[y].hideRoutes();
                            }
                            territories[x].showRoutes();
                            TerritoryName.setText(territories[x].getTerritoryName());
                            ArmyStrength.setText(String.valueOf(territories[x].getStrength()));
                            comboBox.getItems().clear();
                            for(int monitor = 0; monitor < territories[x].getAdjacentTerritories().size(); monitor++){
                                comboBox.getSelectionModel().clearSelection();
                                comboBox.getItems().add(territories[x].getAdjacentTerritory(monitor));    
                            }
                            
                        }
                    }
                }

            }
        });
        
        
        Scene scene = new Scene(bigGroup, 900, 550);
        
        
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show(); 
        
    }
    public boolean isClicked(double xCoord, double yCoord, Territory territory){
        double originX, originY, width, height;
        
        originX = territory.getCoordX();
        originY = territory.getCoordY();
        width = territory.getTerritoryWidth();
        height = territory.getTerritoryHeight();
        
        if(xCoord > originX && xCoord < originX + width){
            if(yCoord > originY && yCoord < originY + height){
                return true;
            }
        }
        return false;
    }
   
    public void addTerritoriesToBoard(){
        for(int x = 0; x < territories.length; x++){
            group.getChildren().add(territories[x]);
        }

    }
    public void createTerritories(Territory[] territories){
        territories[0] = new Territory(120, 120, "Player_1", "camp", 15, 50, 100);
        territories[1] = new Territory(120, 120, "neutral", "field", 1, 250, 100);
        territories[2] = new Territory(120, 120, "neutral", "tower", 1, 450, 70);
        territories[3] = new Territory(120, 120, "Player_2", "fort", 15, 500, 250);
        territories[4] = new Territory(120, 120, "Player_1", "fort1", 151, 300, 250);

    }
    public void assignAdjacentTerritories(Territory[] territories){
        
        
        territories[0].addAdjacentTerritories(territories[1]);    
    
        territories[1].addAdjacentTerritories(territories[0]);
        territories[1].addAdjacentTerritories(territories[2]);
        territories[1].addAdjacentTerritories(territories[3]);
        
        territories[2].addAdjacentTerritories(territories[3]);
        territories[2].addAdjacentTerritories(territories[2]);
        
        territories[3].addAdjacentTerritories(territories[2]);
        territories[3].addAdjacentTerritories(territories[1]);
        
        territories[4].addAdjacentTerritories(territories[1]);
    }
    public Territory findTerritory(String territoryName){
        for(int x = 0; x < territories.length; x++){
            if(territoryName.compareTo(territories[x].getTerritoryName()) == 0){
                return territories[x];
            }
        }
        return null;
    }
    public void createPaths(Territory[] territories){
        int originX, originY, destinationX, destinationY;
        Line line;
        List<Path> paths;
        
        for(int x =0; x < territories.length; x++){
            for(int y = 0; y < territories[x].getAdjacentTerritories().size(); y++){
                originX = territories[x].getCoordX();
                originY = territories[x].getCoordY();
                
                destinationX = findTerritory(territories[x].getAdjacentTerritory(y)).getCoordX();
                destinationY = findTerritory(territories[x].getAdjacentTerritory(y)).getCoordY();
                line = new Line(originX, originY, destinationX, destinationY);
                
                
               // lineMap.put(territories[x].getTerritoryName(), paths);
                
            }
        }System.out.println(lineMap.values());
    }
    public void createArrows(Territory[] territories){
        int originX, originY, destinationX, destinationY;
        for(int x =0; x < territories.length; x++){
            for(int y = 0; y < territories[x].getAdjacentTerritories().size(); y++){
                originX = territories[x].getCoordX();
                originY = territories[x].getCoordY();
                
                destinationX = findTerritory(territories[x].getAdjacentTerritory(y)).getCoordX();
                destinationY = findTerritory(territories[x].getAdjacentTerritory(y)).getCoordY();
                
                territories[x].setArrow(originX, originY, destinationX, destinationY);
            }
        }
        

    }

    /**
     * This inner class is used to provide a menu for the players so they can 
     * choose what territory to attack and where to send their army
     */
    class ActionMenu extends Stage{
        private ArrayList<String> routes = new ArrayList<String>();
        private ArrayList<String> territoryList = new ArrayList<String>();
        private ChoiceBox territoryBox = new ChoiceBox(FXCollections.observableArrayList(territoryList));
        private ChoiceBox adjacentRoutes = new ChoiceBox(FXCollections.observableArrayList(routes));
        private Label ownedTerritory, territoryRoute;
        private TextField transferNumber;
        private Button attack, transfer;
        int sentinel = 0;
        int monitor = 0;
        
        public ActionMenu(Territory[] territory){
            GridPane group = new GridPane();
            ownedTerritory = new Label("Owned Territory:");
            territoryRoute = new Label("Routes");
            attack = new Button("Attack");
            transferNumber = new TextField();
            transfer = new Button("Transfer");
            FlowPane buttons = new FlowPane();
            buttons.getChildren().addAll(attack, transfer);
            group.add(ownedTerritory, 0, 0);
            group.add(territoryBox, 0, 1);
            group.add(territoryRoute, 0, 2);
            group.add(adjacentRoutes, 0, 3);
            group.add(buttons, 0, 4);
            group.add(transferNumber, 0, 5);
            
            Group bigGroup = new Group();
            bigGroup.getChildren().addAll(group);
            
            Scene scene = new Scene(bigGroup, 300, 300);
            setScene(scene);
            setWidth(200);
            setHeight(200);
            setMenu(50, 50, territory);
            /**
             * This listener is used to send your army in one territory to attack the territory
             * of another player
             */
            attack.setOnAction(new EventHandler(){
                @Override
                public void handle(Event event) {
                    String message = territory[sentinel].getTerritoryName() 
                            + " attacks " + territory[monitor].getTerritoryName() 
                            + " with " + territory[sentinel].getStrength() + " armies";
                    new Thread(new sendInformation(message)).start();
                    determineBattle(territory[sentinel], territory[monitor]);
                }
            });
            /**
             * This listener is used to transfer your armies across territories you own
             */
            transfer.setOnAction(new EventHandler(){
                public void handle(Event event){
                    transferTroops(1, territory[sentinel], territory[monitor]);
                }
            });
            /**
             * ActionListener used to select the territory the owner has. As different territories
             * are selected, arrows will appear and disappear. Indirect referencing of territories 
             * is also used to determine which territories to manipulate
             */
            territoryBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    adjacentRoutes.getSelectionModel().clearSelection();
                    try{
                        for(int x = 0; x < territory.length; x++){
                                territory[x].hideRoutes();

                        }
                        for(int x = 0; x < territory.length; x++){
                            if(territory[x].getTerritoryName().compareTo(newValue) == 0){
                                sentinel = x;
                                adjacentRoutes.setItems(FXCollections.observableArrayList(territory[sentinel].getAdjacentTerritories()));
                                territory[sentinel].showRoutes();
                                System.out.println(territory[sentinel].toString());

                                } 
                        
                        }
                    }catch(NullPointerException exe){                     
                    }
                }
            });
            /**
             * ActionListener used to select routes that the territories have
             */
            adjacentRoutes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    String destinationOwner = "";
                    try{
                        
                        for(int x = 0; x < territory.length; x++){
                            if(territory[x].getTerritoryName().compareTo(newValue) == 0){
                                monitor = x;
                                destinationOwner = territory[x].getOwnerID();
                                break;
                            }
                        }
                        System.out.println("Source territory: " + territory[sentinel].getTerritoryName());
                        System.out.println("Owner: " + territory[sentinel].getOwnerID());
                        System.out.println("destination: " + newValue);
                        System.out.println("Owner: " + destinationOwner);

                    }catch(NullPointerException exe){              
                    }
                }
            });
        }
        /**
         * Used to modify menu
         * @param xCoord
         * @param yCoord
         * @param territory 
         */
        public void setMenu(double xCoord, double yCoord, Territory[] territory){
            this.setX(xCoord);
            this.setY(yCoord);
            setTerritory(territory);
        }
        /**
         * Sets the menu options by adding territories owned by the user
         * @param territory 
         */
        public void setTerritory(Territory[] territory){
            for(int x = 0; x < territory.length; x++){
                if(territory[x].getOwnerID().compareTo(player) == 0){
                    territoryList.add(territory[x].getTerritoryName());
                }
            }
            territoryBox.setItems(FXCollections.observableArrayList(territoryList));
        }
        /**
         * Used to transfer armies to other territories
         * @param number of armies to move
         * @param territory1 the source territory
         * @param territory2 the destination territory
         */
        public void transferTroops(int number, Territory territory1, Territory territory2){
            if(territory2.getOwnerID().compareTo(player) == 0){
                territory1.setStrength(territory1.getStrength() - number);
                territory2.setStrength(territory2.getStrength() + number);
            }
        }
        /**
         * Used to determine the battles of each territories army
         * @param territory1 the attacker territory
         * @param territory2  the defender territory
         */
        public void determineBattle(Territory territory1, Territory territory2){          
            int army1 = territory1.getStrength();
            int army2 = territory2.getStrength();
            int attackSize = 0;
            int defenderSize = 0;

            if(army1 == 1){
                attackSize = 1;
            }else if(army1 == 2){
                attackSize = 2;
            }else if(army1 >= 3){
                attackSize = 3;
            }
            if(army2 == 1){
                defenderSize = 1;
            }else if(army2 >= 2){
                defenderSize = 2;
            }
            
            Integer[] attacking = new Integer[attackSize];
            Integer[] defending = new Integer[defenderSize];
            for(int x = 0; x < attacking.length; x++){
                attacking[x] = (int)Math.round(Math.random()*6);
                
            }
            for(int x = 0; x <defending.length; x++){
                defending[x] = (int)Math.round(Math.random()*6);
            }
            Arrays.sort(attacking, Collections.reverseOrder());
            Arrays.sort(defending, Collections.reverseOrder());
            System.out.println(Arrays.toString(attacking));
            System.out.println(Arrays.toString(defending));
            int battle1 = -1;
            int battle2 = -1;
            if(defending.length > 1 && attacking.length > 1){
                if(attacking[0] > defending[0]){
                    battle1 = 1;
                }
                if(attacking[1] > defending[1]){
                    battle2 = 1;

                }
                if(battle1 == 1 && battle2 == 1){
                    territory2.setStrength(territory2.getStrength() - 2);
                }else if((battle1 == 1 && battle2 == 0)||(battle1 == 0) && (battle2 == 1)){
                    territory1.setStrength(territory1.getStrength() - 1);
                    territory2.setStrength(territory2.getStrength() - 1);
                }
                else{
                    territory1.setStrength(territory1.getStrength() - 2);
                }
            }
            else{
                if(attacking[0] > defending[0]){
                    battle1 = 1;
                }
                if(battle1 == 1){
                    territory2.setStrength(territory2.getStrength() - 1);
                }
                else{
                    territory1.setStrength(territory1.getStrength() - 1);
                }
            }
            if(territory2.getStrength() <= 0){
            territory2.setOwnerID(territory1.getOwnerID());
            territory2.setColor(territory1.getOwnerID());
            territory2.setStrength(territory1.getStrength() - 1);
            territory1.setStrength(1);
            territoryList.add(territory2.getTerritoryName());
           
            territoryBox.setItems(FXCollections.observableArrayList(territoryList));
            }
        }
        
   }
    /**
     * Used to send information to the server
     */
    class sendInformation implements Runnable{
        private String message;
        public sendInformation(String message){
            this.message = message;
        }
        @Override
        public void run() {
            try {
                toServer.writeUTF(message);
                toServer.flush();
                
            } catch (IOException ex) {
                
            }
        }
        
    }
    private void connectToServer() {
    try {
      // Create a socket to connect to the server
      Socket socket = new Socket(host, 8000);

      // Create an input stream to receive data from the server
      fromServer = new DataInputStream(socket.getInputStream());

      // Create an output stream to send data to the server
      toServer = new DataOutputStream(socket.getOutputStream());
      new Thread(()->{
          try {
              int player = fromServer.readInt();
              if(player == 1){
                  this.player = "Player_1";
                  System.out.println(this.player);
              }else if(player == 2){
                  this.player = "Player_2";
                  System.out.println(this.player);
              }else if(player == 3){
                  this.player = "Player_3";
                  System.out.println(this.player);
              }else if(player == 4){
                  this.player = "Player_4";
                  System.out.println(this.player);
              }
          } catch (IOException ex) {
              Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
          }
        }).start();
    }
    catch (Exception ex) {
      ex.printStackTrace();
        }
    }
}