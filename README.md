Game Features

#Two Game Modes:
* Classic: The traditional Yacht experience.

* Extended: Features special rules like the "Downward Phase" (forced categories) and modified roll limits.

#Dynamic Player Support: Play solo or with multiple players.

#Randomized Turn Order: Players are shuffled at the start to ensure fairness.

#Deterministic Mode: Support for "seeds" to allow reproducible game results.

#Persistent Scoring: Option to save final results and rankings into a .txt file.

Technical Highlights

#Architecture: Separated into clear layers: ui (User Interface), logic (Game Engine), model (Data), and util (Helpers).

#Polymorphism: Scoring rules are implemented using a ScoringRule interface, making the system easily extensible.

#Custom Exception Handling: Uses a domain-specific YachtGameException for robust error management.

#Java NIO.2: Modern File I/O implementation for saving game results with UTF-8 encoding.

#Input Sanitization: Advanced input parsing using Regular Expressions (Regex) and try-catch blocks to prevent crashes.

How to Run
#Prerequisites: Java JDK 11 or higher installed.

#Compilation: Navigate to the src directory and run:

** Windows (CMD/PowerShell):
dir /s /b *.java > sources.txt
javac -d . @sources.txt

** Linux/macOs
javac -d . $(find . -name "*.java")

#Execution
Run the main class with optional arguments(mode and seed):
java -cp . upo.yacht.ui.YachtGame --mode classic / extended --seed 123

Project Structure
* upo.yacht.ui: Handles terminal interaction and rule display.

* upo.yacht.logic: Contains the GameEngine and scoring logic.

* upo.yacht.model: Data structures for Player, Die, and Scoreboard.

* upo.yacht.exceptions: Custom error handling.

* upo.yacht.util: Helper classes like DiceManager.

