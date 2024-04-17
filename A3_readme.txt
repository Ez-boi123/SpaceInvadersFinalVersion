# A3_readme.txt

## How to Run the Code
To run the application, open your terminal and enter the following command:
gradle clean build run
This command will clean the previous build, build the application, and run it.

## Implemented Features
The following features have been implemented in the extension:

- Difficulty Level: The user can select the difficulty level of the game.
- Time and Score: The game keeps track of the time elapsed and the player’s score.
- Undo and Cheat: The user has the ability to undo actions and cheat in the game.

## Design Patterns Used
### Memento Pattern
- Class: Memo
- Participant Roles: This class is responsible for storing the state of an object to be restored at a later time.

### Adapter Pattern
- Interface: ConfigAdapter
- Classes: EasyConfigAdapter, HardConfigAdapter, MediumConfigAdapter
- Participant Roles: These classes are responsible for adapting the configuration settings based on the selected difficulty level.

### Mediator Pattern
- Class: ScoreMediator
- Interface: ScoreParticipant
- Class: ScoreUpdater
- Participant Roles: The ScoreMediator class is responsible for controlling the communication between ScoreParticipant objects, and the ScoreUpdater class is responsible for updating the score based on the game’s progress.

## How to Use Features
### Selecting Difficulty Level
At the beginning of the game, a pop-up window will appear prompting the user to select the game's difficulty level. Follow the instructions on the pop-up window to make your selection.

### Undo
To undo an action, press the “S” key on your keyboard to save the current state. Then, press the “L” key to revert back to the saved state.

### Cheat
To cheat in the game:
- Press the “F” key to eliminate fast projectiles.
- Press the “P” key to eliminate slow projectiles.

## Additional Information
Feel free to reach out if you have any questions or need further clarification on how to use the application or about its implementation.
