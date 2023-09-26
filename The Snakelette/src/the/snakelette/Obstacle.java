/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package the.snakelette;;

/**
 *
 * @author User
 */
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Obstacle {
    
     private final Position position;
   private final Rectangle rectangle;
    private final Color color = Color.BLUE;
    private final BorderPane borderPane;
    private final Random random = new Random();
    private final int size;
    
    public Obstacle(double xPos, double yPos, BorderPane borderPane, double size) {
        this.borderPane = borderPane;
        this.size = (int) size;
        position = new Position(xPos,yPos);
        rectangle = new Rectangle(position.getXPos(),position.getYPos(),size,size);
        rectangle.setFill(color);
        borderPane.getChildren().add(rectangle);
    }
    
      public Rectangle getRectangle() {
        return rectangle;
    }
      public Position getPosition() {
        return position;
    }
      
}
      