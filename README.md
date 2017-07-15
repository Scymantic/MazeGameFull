# MazeGameFull
A simple puzzle game whereby one recreates a maze using its unordered tile representation


Adrian Barberis

Tuesday Nov. 29, 2016, 5:22PM

This program recognizes both JPG,JPEG,PNG,MZE,SAV extensions the last 2 are "proprietary" an example of witch is in the src folder. SAV is really just textual representation of the various data that the game outputs.  Dont worry about the MZE format.
Note that only SAV files for this game can be used I know some games also choose to save their files with a .sav tag those WILL NOT WORK on here obviously.

Any JPEG,JPG,PNG once saved will be saved in this SAV format.  The intial file browser should open 
to the location where you saved the exec/project therefore I reccomend saving everything there.

You may experience some hang when initializing and after selecting the wrong intial "default" file the game will probably crash I have no idea why this happens and I believe its just a problem that converting the Java project to a java exec created.  I will eventually try and solve it but for now a simple restart whould work just fine.

Note that YOU MUST load a DEFUALT file first.  This unfortunately was the specification for this project I may change
it in the future but since I included a default in the src folder I dont really know when I'll do that. In the mean time simply rename whatever file you want to "defaultXYZ" as long as the filename contains "default" you can have anything else after or before and it will still be recognized as a "default file".

This program also allows you to load a background image just for fun. :)

## Controls:

Left Click and Left Click to place a tile you can do this either way i.e. going to the board or going to the shelves.

  You cannot replace one tile with another again another specification might remove at later date.
  If the tile is highlighted blue then the next click will move it.  If it's read then that is an illegal move.
  Reset will reset all tiles to their postion and orientation at last load.
  
Right Click to rotate the tile
  You can rotatea full 360 degrees just Right Click 4 times
  
The game will automatically tell you if youe won i.e. the maze is correct

Cheers and Enjoy!

AMA and other questions:
14abarberis@gmail.com

