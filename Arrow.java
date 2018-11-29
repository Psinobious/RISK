/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * This class is used to display the routes territories have to the user
 * https://gist.github.com/kn0412/2086581e98a32c8dfa1f69772f14bca4
 * @author kn
 */
public class Arrow extends Path{
    private static final double defaultArrowHeadSize = 5.0;
    private static String sourceID, destinationID;
    private static boolean interactable = false;
    private static Territory source;
    public Arrow(double startX, double startY, double endX, double endY,
            double arrowHeadSize){
        super();
        strokeProperty().bind(fillProperty());
        setFill(Color.BLACK);
        
        //Line
        getElements().add(new MoveTo(startX, startY));
        getElements().add(new LineTo(endX, endY));
        
        //ArrowHead
        double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        //point1
        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;
        //point2
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;
        
        getElements().add(new LineTo(x1, y1));
        getElements().add(new LineTo(x2, y2));
        getElements().add(new LineTo(endX, endY));
        this.source = source;
    }

    public Arrow(double startX, double startY, double endX, double endY){
        this(startX, startY, endX, endY, defaultArrowHeadSize);
    }

    public static Territory getSource() {
        return source;
    }

    public static void setSource(Territory source) {
        Arrow.source = source;
    }
    
    public static boolean isInteractable() {
        return interactable;
    }

    public static void setInteractable(boolean interactable) {
        Arrow.interactable = interactable;
    }
    public static String getDestinationID() {
        return destinationID;
    }

    public static void setDestinationID(String destinationID) {
        Arrow.destinationID = destinationID;
    }
    
}