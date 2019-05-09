# Melee-CSS-Color-Changer

This program is used to change various colors on the character select screen of Super Smash Bros. Melee. The user just provides the appropriate MnSlChr.usd and adjusts the colors for background, top frame, bottom frame, rules, the many "selects" in the background, and more. 

The program does not currently suppport reading the color of background "selects." When updating the other colors and you don't want to overwrite your background "selects" color(s) then set the option to "None" for the "Background Selects:" combo box.

## Example Input and Output of the Application
Transparent Background with "Background Selects" at "Alternate" with color 1 as white and color 2 as black
![Alt Text](https://thumbs.gfycat.com/HideousParallelAruanas.webp)

Transparent Background with "Background Selects" at "Tri" with color 1 as white and color 2 as black
![Alt Text](https://thumbs.gfycat.com/UniqueOddballBrontosaurus.webp)

Transparent Background with "Background Selects" at "Random"
![Alt Text](https://thumbs.gfycat.com/ConcreteGraciousCuscus.webp)

Some ugly colors to show program input and output  
![Alt Text](https://i.imgur.com/MD3RyE1.png)
![Alt Text](https://i.imgur.com/7TAQWhM.png)


## MnSlChr.usd Start Offset Documentation  
42 48 Format    | Starting Offset
--------------- | ---------------
Background | 0x000958  
Selects in Background | 0x000984 to 0x0019D8  

07 07 07 Format | Starting Offset
--------------- | ---------------
Bottom Frame | 0x348E88  
Cursor | 0x01005C  
Top Frame | 0x349008  
Rules | 0x348F48  
