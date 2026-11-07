# The Snakelette

A JavaFX-based Snake game with obstacles, score tracking, and turn-based movement controls. The player guides a growing snake around the board, eats food to score points, and avoids crashing into walls, obstacles, or its own body.

## Features

- Classic Snake gameplay in JavaFX
- Random food spawning
- Growing snake body
- Obstacle generation during play
- Score display and restart option
- Menu screen with start and exit flow

## How to run

1. Open the project in NetBeans or another Java IDE.
2. Make sure JavaFX is configured for the project.
3. Compile and run the main class:

```bash
java --module-path <path-to-javafx> --add-modules javafx.controls,javafx.fxml the.snakelette.TheSnakelette
```

If you are using an IDE, run `TheSnakelette` directly from the project source.

## Controls

- `W` - move up
- `A` - move left
- `S` - move down
- `D` - move right

## Gameplay

- The snake grows when it eats food.
- Each food pickup adds to the score.
- The game ends if the snake hits:
  - the game boundaries
  - an obstacle
  - itself

## Project structure

- `TheSnakelette.java` – JavaFX application entry point
- `Menu.fxml` – start screen UI
- `MenuC.java` – menu controller logic
- `Snake.fxml` – game screen UI
- `Controller.java` – game loop, movement, collision detection, and scoring
- `Food.java` – food placement and movement
- `Obstacle.java` – obstacle objects
- `Direction.java` – movement direction enum
- `Position.java` – position data for snake parts and food

## Requirements

- Java JDK 17+
- JavaFX configured in the IDE or build tool

## Notes

This project was created as a learning exercise and is intended for educational and personal use.
