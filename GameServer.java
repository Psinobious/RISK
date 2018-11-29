///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package risk;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.application.Application;
//import javafx.scene.control.TextArea;
//import javafx.stage.Stage;
//
///**
// * This is the game server that is used to take inputs from the players 
// * @author Daniel Johnson
// */
//public class GameServer extends Application{
//   private TextArea ta = new TextArea();
//    private static final int numberOfPlayers = 2;
//    private Player[] players = new Player[numberOfPlayers];
//    private static ServerSocket serverSocket = null;
//    private static Socket socket = null;
//    private Stage stage = null;
//    
//    public static void main(String[]args){
//        Application.launch(args);
//    }
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//         new Thread( () -> {
//      try {
//        // Create a server socket
//        ServerSocket serverSocket = new ServerSocket(8000);
//        
//        // Ready to create a session for every two players
//        while (true) {
//
//          // Connect to player 1
//          Socket player1 = serverSocket.accept();
//
//  
//          // Notify that the player is Player 1
//          new DataOutputStream(
//            player1.getOutputStream()).writeInt(1);
//          System.out.println("Player 1 connected");
//          // Connect to player 2
//          Socket player2 = serverSocket.accept();
//
//          // Notify that the player is Player 2
//          new DataOutputStream(
//            player2.getOutputStream()).writeInt(2);
//          System.out.println("Player 2 connected");
//
//
//        }
//      }
//      catch(IOException ex) {
//        ex.printStackTrace();
//      }
//    }).start();
//    }
//    /**
//     * This inner class is used to handle a total of two players and perform necessary calculations
//     * from the input recieved from the players. This inner class is unfinished and needs more work 
//     * before fully operational
//     */
//    class HandleASession extends Thread implements Runnable{
//        private Socket player1;
//        private Socket player2;
//        
//        private DataInputStream fromPlayer1;
//        private DataOutputStream toPlayer1;
//        private DataInputStream fromPlayer2;
//        private DataOutputStream toPlayer2;
//        
//        public HandleASession(Socket player1, Socket player2){
//            
//        }
//        public void run(){
//        DataInputStream fromPlayer1 = null;
//            try {
//                // Create data input and output streams
//                fromPlayer1 = new DataInputStream(
//                        player1.getInputStream());
//                DataOutputStream toPlayer1 = new DataOutputStream(
//                        player1.getOutputStream());
//                DataInputStream fromPlayer2 = new DataInputStream(
//                        player2.getInputStream());
//                DataOutputStream toPlayer2 = new DataOutputStream(
//                        player2.getOutputStream());
//                toPlayer1.writeInt(1);
//                while(true){
//                    fromPlayer1.readUTF();
//                    toPlayer1.writeUTF("Good");
//                    toPlayer2.writeUTF("meh");
//                }
//            } catch (IOException ex) {
//                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
//            } finally {
//                try {
//                    fromPlayer1.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//    }
//    
//    public void determineBattle(Territory territory1, Territory territory2){          
//            int army1 = territory1.getStrength();
//            int army2 = territory2.getStrength();
//            int attackSize = 0;
//            int defenderSize = 0;
//
//            if(army1 == 1){
//                attackSize = 1;
//            }else if(army1 == 2){
//                attackSize = 2;
//            }else if(army1 >= 3){
//                attackSize = 3;
//            }
//            if(army2 == 1){
//                defenderSize = 1;
//            }else if(army2 >= 2){
//                defenderSize = 2;
//            }
//            
//            Integer[] attacking = new Integer[attackSize];
//            Integer[] defending = new Integer[defenderSize];
//            for(int x = 0; x < attacking.length; x++){
//                attacking[x] = (int)Math.round(Math.random()*6);
//                
//            }
//            for(int x = 0; x <defending.length; x++){
//                defending[x] = (int)Math.round(Math.random()*6);
//            }
//            Arrays.sort(attacking, Collections.reverseOrder());
//            Arrays.sort(defending, Collections.reverseOrder());
//            System.out.println(Arrays.toString(attacking));
//            System.out.println(Arrays.toString(defending));
//            int battle1 = -1;
//            int battle2 = -1;
//            if(defending.length > 1 && attacking.length > 1){
//                if(attacking[0] > defending[0]){
//                    battle1 = 1;
//                }
//                if(attacking[1] > defending[1]){
//                    battle2 = 1;
//
//                }
//                if(battle1 == 1 && battle2 == 1){
//                    territory2.setStrength(territory2.getStrength() - 2);
//                }else if((battle1 == 1 && battle2 == 0)||(battle1 == 0) && (battle2 == 1)){
//                    territory1.setStrength(territory1.getStrength() - 1);
//                    territory2.setStrength(territory2.getStrength() - 1);
//                }
//                else{
//                    territory1.setStrength(territory1.getStrength() - 2);
//                }
//            }
//            else{
//                if(attacking[0] > defending[0]){
//                    battle1 = 1;
//                }
//                if(battle1 == 1){
//                    territory2.setStrength(territory2.getStrength() - 1);
//                }
//                else{
//                    territory1.setStrength(territory1.getStrength() - 1);
//                }
//            }
//            if(territory2.getStrength() <= 0){
//            territory2.setOwnerID(territory1.getOwnerID());
//            territory2.setColor(territory1.getOwnerID());
//            territory2.setStrength(territory1.getStrength() - 1);
//            territory1.setStrength(1);
//            }
//        }
//}
