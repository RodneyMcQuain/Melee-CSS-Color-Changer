# Melee-CSS-Color-Changer

The Melee CSS (Character Select Screen) Color Changer is used to change various colors on the character select screen of Super Smash Bros. Melee. The app supports changing the:
* Bottom frame 
* Top frame
* Cursor 
* Rules
* Background
* Selects in the background

of the CSS. For more information on the process involved for creating an application like this, [check out my blog post](https://rodneymcquain.com/blog-posts/coloring-in-a-20-year-old-video-game).

## How to Use
There are a few ways to use this program, but this section will cover how to use it with [DAT Texture Wizard](https://smashboards.com/threads/dat-texture-wizard-current-version-5-5.373777/).

1. Have a Super Smash Bros. Melee (Melee) ISO
1. Download [DAT Texture Wizard](https://www.mediafire.com/file/dzay2wgw2fa6f0v/DAT_Texture_Wizard_-_v5.5_%28x64%29.zip/file)
2. Open DAT Texture Wizard
3. Click "File" in the top left
4. Click "Open Disc" in the newly appeared "File" menu
5. Locate and Open your Melee ISO from the file explorer
6. While under the "Disc File Tree" tab Locate MnSlChr file (note: the file extension changes based on what version of Melee your ISO is, e.g. MnSlChr.usd for vanilla Melee, MnSlChr.0sd for 20XX 4.07++, etc.)
7. Click the MnSlChr that you just found
8. Click "Export" on the right side of the application
9. Save the file to your desired location
10. [Download the newest .jar release for CSS Color Changer](https://github.com/RodneyMcQuain/Melee-CSS-Color-Changer/releases)
11. Open the CSS Color Changer
12. Click "Choose a File to Modify"
13. Select the MnSlChr file that you recently saved (from DAT Texture Wizard)
14. Pick your desired colors and modifications to the Melee CSS
15. Once you finish making your changes click "Update File"
16. Switch back to the DAT Texture Wizard application
17. If you closed your ISO from earlier refer back to steps 2-6 to get back to where you need to be
18. While highlighting the MnSlChr file Click "Import" on the right side of the application
19. Locate and Open the MnSlChr file that you saved earlier
21. Press Ctrl+s or go to "File" -> "Save" to save your changes

Your ISO now has the changes you just made to the CSS and you can run it on your console or emulator!

## Limitations
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
Multiplayer Top Frame | 0x349008 
Multiplayer Bottom Frame | 0x348E88
Single Player Top Frame | 0x382F04
Single Player Bottom Frame | 0x382E44
Cursor | 0x01005C   
Rules | 0x348F48  
