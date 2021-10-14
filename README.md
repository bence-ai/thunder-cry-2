
# Dungeon Crawl (sprint 1)

## Story

[Roguelikes](https://en.wikipedia.org/wiki/Roguelike) are one of the oldest
types of video games, the earliest ones were made in the 70s, they were inspired
a lot by tabletop RPGs. Roguelikes have the following in common usually:

- They are tile-based.
- The game is divided into turns, e.g. you make one action, then the other
  entities (monsters, allies, etc. controlled by the CPU) make one.
- Usually the task is to explore a labyrinth and retrieve some treasure from its
  bottom.
- They feature permadeath: if you die its game over, you need to start from the
  beginning again.
- Are heavily using procedural generation: Levels, monster placement, items,..
  are randomized, so the game does not get boring.


## Tasks

1. Create a game logic which restricts the movement of the player so s/he cannot run into walls and monsters.
    - The hero is not able to move into walls.
    - The hero is not able to move into monsters.

2. There are items lying around the dungeon. They are visible in the GUI.
    - There are many item types, for instance a key, and a sword.
    - There can be one item in a map square.
    - A player can stand on the same square as an item.
    - The item has to be displayed on screen (unless somebody stands on the same square)
    - Picking up armour increases defense permamently
    - Picking up Weapon increases attaack accordingly
    - The Hero is able to change weapon

3. Create a feature that allows the hero to pick up an item.
    - There is an interract keybutton "F" what is used to pick up items or battle a foe.
    - After the player hits the button, the item the hero is standing on should be gone from map, or enter a battle mode
	where there are options to fight.

4. Show picked up items in the inventory list.
    - There is an `Inventory` list on the screen.
    - After the hero picks up an item, it should appear in inventory.

5. Make the hero to able to battle against creatures.
    - In battle mode, the Hero is able to chose by pressing button from 0-9 to make an action and ends his turn.
    - Actions are attacking, using spells or healing.
    - After Hero's turn the enemy randomly choses and action on it's turn.
    - Enemies action behaviours are different
    - Having a weapon should increase your attack strength.
    - Different monsters have different health and attack strength and different magic spells and moving behaviours.
    - Battle ends whenever someoen dies.
    - When Hero wins its stats are improving

6. Create doors in the dungeon that open by using keys.
    - The hero cannot pass through a closed door, unless it has a key in his/her inventory. Then it becomes an open door.

7. Create three different monster types with different behaviors.
    - There are at least three different monster types with different behaviors.
    - One type of monster may teleport or chase you.
    - One type of monster moves randomly. It cannot go trough walls or open doors.
    - One type of monster patrols around.

8. Create a more sophisticated movement AI.
    - One type of monster moves around randomly and teleports to a random free square every few turns.
    - A custom movement logic is implemented (like Ghosts that can move trough walls, monster that chases the player, etc.)

9. Create maps that have more varied scenery. Take a look at the tile sheet (tiles.png). Get inspired!
    - At least three more tiles are used. These can be more monsters, items, or background. At least one of them has to be not purely decorative, but have some effect on gameplay.
    - On every level you gain a new magic spell.

10.  Allow the player to set a name for my character.
    - There is a `Name` label and text field on the screen.
    - There is an option to fully customise your character look as well.

11. Implement bigger levels than the game window.
    - Levels are larger than the game window (for example 3 screens wide and 3 screens tall).
    - When the player moves the player character stays in the center of the view.


12. Allow the user to save the current state of the game in a database.
    - The application uses PostgreSQL database.
    - The application respects the `PSQL_USER_NAME`, `PSQL_PASSWORD`, `PSQL_DB_NAME` environment variables
    - When the user hits `Ctrl+s`, a modal window pops up with one text input field (labelled `Name`) and two buttons, `Save` and `Cancel`.
    - When the user clicks on `Save`, the game saves the current state (current map, player's position and content of inventory) in the database
    - If the given name is new then it saves the state
    - If the given username already exist in the db the system shows a dialogbox with a question: `Would you like to overwrite the already existing state?`
    - Choosing `Yes`: the already existing state is updated and all modal window closes
    - Choosing `No`: the dialog closes and the name input field content on the saving dialog is selected again
    - In case of clicking on `Cancel` the saving process terminates without any further action
    - The modal window is automatically closed after the operation
    - Already discovered maps are also saved in DB.
    - There is a `Load` menu which brings up a modal window, showing the previously saved states with their names as a selectable list. Choosing an element loads the selected game state with the proper map, position and inventory.

13. Allow the user to export (serialize) his game state into a text file, and load (deserialize) the game from the exported file.
    - There is a menu item with a label `Export game` which triggers the export mechanism
    - The exporting process asks the user for the location and the name of the exported file. The file is created in the defined directory using the given name as a JSON file. eg. `<my-fantastic-game>.json`
    - The file stores every necessary game state information in JSON format.
    - There is a menu button labeled `Import` for importing a previously saved game.
    - The system shows a file browser to select an exported file
    - If the chosen file isn't in proper format, the application shows a dialog window (OK, Cancel) with the following message: `IMPORT ERROR! Unfortunately the given file is in wrong format. Please try another one!`
    - If the user clicks on `OK` button then the window closes without any further action
    - If the user click on `Cancel` all dialog and modal window closes
    - In case the file is in the required format, the game loads the state, and navigates on the map to the point where the user left the game with its inventory



## General requirements

None

## Hints

- Open the project in IntelliJ IDEA. This is a Maven project, so you will need to
open `pom.xml`. The project is using JavaFX, use the `javafx` maven plugin to
build and run the program. Build: `mvn javafx:compile`, run: `mvn javafx:run`.
And the used database is in PostgreSQL. The application respects the environment variables.


