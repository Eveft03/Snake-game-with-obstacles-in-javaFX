/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package the.snakelette;;

/**
 *
 * @author User
 */
import java.util.List;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;
import javafx.scene.layout.BorderPane;

public class Food {
    private Position position;
    private Rectangle rectangle;
    private Color color = Color.RED;
    private BorderPane borderPane;
    private Random random = new Random();
    private int size;

    
    private List<Obstacle> obstacles; // Add a reference to the list of obstacles

    public Food(double xPos, double yPos, BorderPane borderPane, double size, List<Obstacle> obstacles) {
        this.borderPane = borderPane;
        this.size = (int) size;
        this.obstacles = obstacles;
        position = new Position(xPos,yPos);
        rectangle = new Rectangle(position.getXPos(),position.getYPos(),size,size);
        rectangle.setFill(color);
        borderPane.getChildren().add(rectangle);
    }

    public Position getPosition() {
        return position;
    }

    public void moveFood(){
        getRandomSpotForFood();
    }
    
      public Rectangle getRectangle() {
        return rectangle;
    }

    public void getRandomSpotForFood(){
         int positionX, positionY;
        boolean foodOverlapsObstacle;

        do {
            positionX = random.nextInt(30);
            positionY = random.nextInt(20);

            // Check if the food overlaps with any obstacle
            foodOverlapsObstacle = false;
            for (Obstacle obstacle : obstacles) {
                Rectangle obstacleRect = obstacle.getRectangle();
                double foodX = positionX * size;
                double foodY = positionY * size;

                if (obstacleRect.getBoundsInParent().intersects(foodX, foodY, size, size)) {
                    foodOverlapsObstacle = true;
                    break;
                }
            }
        } while (foodOverlapsObstacle);

        rectangle.setX(positionX * size);
        rectangle.setY(positionY * size);

        position.setXPos(positionX * size);
        position.setYPos(positionY * size);
    }
}