# 🎮 Game Project Structure

This document outlines the organization and components of this Java game project. The project is organized into core logic components and user interface/object components.

---

## 🚀 Getting Started

To run this game project, you need to have the **Java Development Kit (JDK)** installed on your system.

### ⚙️ How to Compile and Run

1.  **Save the Files:** Ensure all `.java` files (e.g., `Main.java`, `MainMenu.java`, `Paddle.java`, etc.) are placed in the correct directory structure (`game/`).
2.  **Open Terminal/Command Prompt:** Navigate to the root directory containing the `game` folder.
3.  **Compile:** Use the Java compiler (`javac`) to compile all the source files:
    ```bash
    javac game/*.java game/logic/*.java game/objects/*.java
    ```
    *(Note: The exact command may vary depending on how many sub-packages you have. The above assumes all files are in `game`, `game.logic`, and `game.objects`.)*
4.  **Run:** Execute the compiled application by running the `Main` class:
    ```bash
    java game.Main
    ```

---

## 📁 File Structure

The project uses a standard package structure (`game`) to organize its classes:

### `game.logic` (Core Game Management)
Contains classes responsible for managing the state, physics, and rules of the game.

* `AIController`
* `CollisionManager` (Manages collision and collision detection)
* `ScoreManager`

### `game.objects` (Game Entities and UI Elements)
Contains classes representing the game entities, level structure, and user interface components.

* `Brick`
* `BrickGrid`
* `Paddle`
* `GameOverDialog` (Defines the Game Over screen/popup)
* `GamePanel`
* `InstructionsMenu`
* `Main`

---

## 📝 Description

### 🧱 Game Mechanics & Objects
* **`game.logic`**: Contains classes that manage collision, collision detection, and game state.
* **`Brick bof`**: Refers to the various brick objects used in the game.
* **`Game.objects`**: Contains the primary game entities (like `Paddle` and `Brick`).
* **`GameOver`**: Defines the `GameOverDialog`.

### 🖥️ User Interface & Menus
* **`GameWindow`**: The main window or frame for the game.
* **Panels**: Panels for rendering the game and displaying dialogs, including `DifficultyMenu` and `InstructionsMenu`.
