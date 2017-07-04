Steps to run the Game are as follows:-
1) Unzip the folder and setup Java 8 on build path.
2) Put the input.txt on D: drive (can find sample file from project root path). Battle.java file reads input file from D: drive.
2) Goto root of the project on cmd prompt and try the command within double quotes "mvn clean install" to build the JAR.
3) Goto TARGET folder to get the generated executable JAR and try command "java -jar Game-0.0.1-SNAPSHOT.jar" to run the Game.

Validation covered are as follows :-
1) Input Row 1 : BattleGround row should be between A to Z (used uppercase to convert smallcase chars as well).
2) Input Row 1 : Battleground col should be between 1 to 9.
3) Input Row 2 : Ship number should be digit.
4) Input Row 3 onwards till ship positions  are given : first cell should be P or Q for valid ship types.
5) Input Row 3 onwards till ship positions  are given : 2nd & 3rd cell should be digit.
6) Input Row 3 onwards till ship positions  are given : fourth onwards cell should be between A1 to Z9
7) Input Row 3 onwards till ship positions  are given : forth onwards cells should be equal to number of players.
8) Input Row n from last to last row where n is no. of players : Missile target of 1st player should be between A1 to Z9.