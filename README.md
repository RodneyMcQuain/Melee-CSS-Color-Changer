# Melee-CSS-Color-Changer

The Melee CSS Color Changer is used to change various colors on the character select screen of Super Smash Bros. Melee. The app supports changing the:
* Bottom frame 
* Top frame
* Cursor 
* Rules
* Background
* Selects in the background

of the CSS.

All that needs to be done is provide a valid MnSlChr.*sd (MnSlChr.usd, MnSlChr.0sd, etc.) file, pick your desired colors, and replace that file back into a Melee ISO. I recommend using [DAT Texture Wizard](https://smashboards.com/threads/dat-texture-wizard-current-version-5-5.373777/) to get and replace that file.

The program does not currently suppport reading the color of background "selects." When updating the other colors and you don't want to overwrite your background "selects" color(s) then set the option to "Do Nothing" for the "Background Selects:" combo box.

## Example Input and Output of the Application
Program input and output:  
![](https://imgur.com/e6IcL6A.png)
![](https://i.imgur.com/rtecYi0.png)

Transparent Background with "Background Selects" at "Alternate" with color 1 as white and color 2 as black
![](https://thumbs.gfycat.com/HideousParallelAruanas.webp)

Transparent Background with "Background Selects" at "Tri" with color 1 as white and color 2 as black
![](https://thumbs.gfycat.com/UniqueOddballBrontosaurus.webp)

Transparent Background with "Background Selects" at "Random"
![](https://thumbs.gfycat.com/ConcreteGraciousCuscus.webp)

Some other examples  
![](https://media.giphy.com/media/YmaxEUjhXJQU5jHanY/giphy.gif)
![](https://media.giphy.com/media/jsCGchgNEuuDRfAKEa/giphy.gif)
![](https://media.giphy.com/media/kboTn0eF0dPeaGM6zL/giphy.gif)
![](https://media.giphy.com/media/mDT65dpD2ZenaZzB6G/giphy.gif)
![](https://media.giphy.com/media/cJSerfJeOrSXpMIapF/giphy.gif)

## MnSlChr.usd Starting Offset Documentation  
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
