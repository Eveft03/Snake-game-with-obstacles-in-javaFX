/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package the.snakelette;;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Controller implements Initializable {


    private final double snakeSize = 20.;
    private final double obSize=18.;
    private int score;
    
    //The head of the snake is created
    private Rectangle snakeHead;
    //First snake tail created behind the head of the snake
    private Rectangle snakeTail_1;
    //x and y position of the snake head different from starting position
    double xPos;
    double yPos;

    //Food
    Food food;
     Obstacle obstacle;

    //Direction snake is moving at start
    private Direction direction;

    //List of all position of head
    private final List<Position> positions = new ArrayList<>();
    private final ArrayList<Rectangle> snakeBody = new ArrayList<>();
    private final List<Obstacle> obstacles = new ArrayList<>();

    //Game ticks is how many times the snake have moved
    private int gameTicks;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button startButton;
    
    @FXML
    private BorderPane borderPane;
    
    @FXML
    private Label scorelabel;

    Timeline timeline;
    Timeline obstacleTimer;
    private boolean canChangeDirection;

    @FXML
    void start() {

        //Clear game
        for (Rectangle snake : snakeBody) {
            borderPane.getChildren().remove(snake);
        }
        
        
        for (Obstacle obstacle : obstacles) {
            borderPane.getChildren().remove(obstacle.getRectangle());
        }
        
        
        gameTicks = 0;
         score = 0;
        positions.clear();
        snakeBody.clear();
        obstacles.clear();
        
        // Reset score
         score = 0; 
         updateScoreLabel();
         
        //Snake
        snakeHead = new Rectangle(250, 250, snakeSize, snakeSize);
        snakeTail_1 = new Rectangle(snakeHead.getX() - snakeSize, snakeHead.getY(), snakeSize, snakeSize);
        xPos = snakeHead.getLayoutX();
        yPos = snakeHead.getLayoutY();
        snakeBody.add(snakeHead);
        snakeHead.setFill(Color.GREEN);
        snakeBody.add(snakeTail_1);
        snakeTail_1.setFill(Color.GREEN);

        borderPane.getChildren().addAll(snakeHead, snakeTail_1);
        
        //Dirrection and Food
        direction = Direction.RIGHT;
        canChangeDirection = true;
        food.moveFood();

       
        
        //Timelines
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        obstacleTimer.setCycleCount(Animation.INDEFINITE);
        obstacleTimer.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), e -> {
            positions.add(new Position(snakeHead.getX() + xPos, snakeHead.getY() + yPos));
            moveSnakeHead(snakeHead);
            for (int i = 1; i < snakeBody.size(); i++) {
                moveSnakeTail(snakeBody.get(i), i);
            }
            
            canChangeDirection = true;
            eatFood();
            gameTicks++;
            if(checkIfGameIsOver(snakeHead)){
                timeline.stop();
            }
        }));
        
        
        obstacleTimer = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            
            if(!checkIfGameIsOver(snakeHead)){
            spawnObstacle(); }
            
            else if(checkIfGameIsOver(snakeHead)){
                obstacleTimer.stop();
            }
              
        }));
        
        
        food = new Food(-50,-50,borderPane,snakeSize,obstacles);
        obstacle = new Obstacle(-50, -50,  borderPane, snakeSize);
        start();
    }

    
    @FXML
    void moveSquareKeyPressed(KeyEvent event) {
        if(canChangeDirection){
            if (event.getCode().equals(KeyCode.W) && direction != Direction.DOWN) {
                direction = Direction.UP;
            } else if (event.getCode().equals(KeyCode.S) && direction != Direction.UP) {
                direction = Direction.DOWN;
            } else if (event.getCode().equals(KeyCode.A) && direction != Direction.RIGHT) {
                direction = Direction.LEFT;
            } else if (event.getCode().equals(KeyCode.D) && direction != Direction.LEFT) {
                direction = Direction.RIGHT;
            }
            canChangeDirection = false;
        }
    }


    @FXML
    void addBodyPart(ActionEvent event) {
        addSnakeTail();
    }

   
    private void moveSnakeHead(Rectangle snakeHead) {
        if (direction.equals(Direction.RIGHT)) {
            xPos = xPos + snakeSize;
            snakeHead.setTranslateX(xPos);
        } else if (direction.equals(Direction.LEFT)) {
            xPos = xPos - snakeSize;
            snakeHead.setTranslateX(xPos);
        } else if (direction.equals(Direction.UP)) {
            yPos = yPos - snakeSize;
            snakeHead.setTranslateY(yPos);
        } else if (direction.equals(Direction.DOWN)) {
            yPos = yPos + snakeSize;
            snakeHead.setTranslateY(yPos);
        }
    }

    //A specific tail is moved to the position of the head x game ticks after the head was there
    private void moveSnakeTail(Rectangle snakeTail, int tailNumber) {
        double yPos = positions.get(gameTicks - tailNumber + 1).getYPos() - snakeTail.getY();
        double xPos = positions.get(gameTicks - tailNumber + 1).getXPos() - snakeTail.getX();
        snakeTail.setTranslateX(xPos);
        snakeTail.setTranslateY(yPos);
    }

    //New snake tail is created and added to the snake and the anchor pane
    private void addSnakeTail() {
        Rectangle rectangle = snakeBody.get(snakeBody.size() - 1);
        Rectangle snakeTail = new Rectangle(
                snakeBody.get(1).getX() + xPos + snakeSize,
                snakeBody.get(1).getY() + yPos,
                snakeSize, snakeSize);
        snakeBody.add(snakeTail);
        snakeTail.setFill(Color.GREEN);
        borderPane.getChildren().add(snakeTail);
    }

    public boolean checkIfGameIsOver(Rectangle snakeHead) {
        if (xPos > 320 || xPos < -250 || yPos < -250 || yPos > 250) {
            System.out.println("Game_over");
            return true;
        } else if(snakeHitItSelf()){
            return true;
        }
          else if(snakeHitObstacle()){
            return true;
          }
        return false;
    }

    public boolean snakeHitItSelf(){
        int size = positions.size() - 1;
        if(size > 2){
            for (int i = size - snakeBody.size(); i < size; i++) {
                if(positions.get(size).getXPos() == (positions.get(i).getXPos())
                        && positions.get(size).getYPos() == (positions.get(i).getYPos())){
                    System.out.println("Hit");
                    return true;
                }
            }
        }
        return false;
    }

    private void eatFood() {
        double foodX = food.getPosition().getXPos();
        double foodY = food.getPosition().getYPos();

       
          if (snakeHead.getBoundsInParent().intersects(foodX, foodY, snakeSize, snakeSize)) {
        System.out.println("Eat food");
        foodCantSpawnInsideSnake();
        addSnakeTail();
        increaseScore(); // Increment the score
        updateScoreLabel(); // Update the score label on the screen
    }
    }

    private void foodCantSpawnInsideSnake(){
        food.moveFood();
        while (isFoodInsideSnake()){
            food.moveFood();
        }


    }

    private boolean isFoodInsideSnake(){
        int size = positions.size();
        if(size > 2){
            for (int i = size - snakeBody.size(); i < size; i++) {
                if(food.getPosition().getXPos() == (positions.get(i).getXPos())
                        && food.getPosition().getYPos() == (positions.get(i).getYPos())){
                    System.out.println("Inside");
                    return true;
                }
            }
        }
        return false;
    }



     
     private void spawnObstacle() {
          if (obstacles.size() < 25) {
        double obstacleX = Math.random() * (borderPane.getWidth() - obSize);
        double obstacleY = Math.random() * (borderPane.getHeight() - obSize);

        // Check for overlapping obstacles
        boolean isOverlapping = false;
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getRectangle().getBoundsInParent().intersects(obstacleX, obstacleY, obSize, obSize)) {
                isOverlapping = true;
                break;
            }
        }

        if (!isOverlapping) {
            Obstacle obstacle = new Obstacle(obstacleX, obstacleY, (BorderPane) borderPane, obSize);
            obstacles.add(obstacle);
        }
    }
}
     
     
     private void foodCantSpawnInsideObstacle() {
          double obstacleX = Math.random() * (borderPane.getWidth() - obSize);
        double obstacleY = Math.random() * (borderPane.getHeight() - obSize);
        
    boolean isOverlapping = false;
        for (Obstacle obstacle : obstacles) {
            if (food.getRectangle().getBoundsInParent().intersects(obstacleX, obstacleY, obSize, obSize)) {
                isOverlapping = true;
                 food.moveFood();
                break;
            }
        }

     }
     
      private boolean snakeHitObstacle() {
    for (Obstacle obstacle : obstacles) {
            if (snakeHead.getBoundsInParent().intersects(obstacle.getRectangle().getBoundsInParent())) {
                System.out.println("Hit obstacle");
                return true;
            }
        }
        return false;
    
}
      

private void increaseScore() {
    score += 10;
}


private void updateScoreLabel() {
   
    
    scorelabel.setText("Score: " + score);
}


@FXML
private void exitGame(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.close(); // Close the current stage
}

}