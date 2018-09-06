SmartMacro README
--------------------------------
SmartMacro is a tool used to automate various tasks involving clicks and keystrokes.

Usage:
In the Action box on the top left of the Script window, you may configure an action consisting of the following fields: the action itself, delay time, x and y coordinates, repeat count, and the note. Each time an action is added, it will collect the data from these fields and add it as a row to the main script on the right.

	Action 
		The actual command that will be run. If Left click, Right click, or Double click 
		are added using the add button, then it will collect all data, including x and y, 			
		from the fields and add it as a new row. If a click is added via the assigned 		
		hotkey (default F6), then it will collect data from the delay, repeat, and note 		
		fields, but will get the X and Y coordinates from the mouse pointers current 		
		location. This way, you can move the mouse to a desired location, press the add 			
		action hotkey, and immediately have a click in the script at that location.

	Delay 
		This is the delay time in milliseconds that will take place before its action is
	    executed.

	Repeat
		This represents the number of times the action will execute. A repeat time of 1 			
		will run the action once, a repeat time of 2 will run it twice, etc...
		Each repetition will wait for the delay time specified in the action.

	X, Y Coordinates
		These will only contain data when a click is added (Left, Right, Double).
		The click will execute at these coordinates.
	
	Note 
		The note section is used to mark actions with a more human-readable tag. A left click
		could have a note such as "Into first name field" followed by a CTRL+C with a note 
		"copy first name". The note can also be used in the "type note" action. If you must 
		search for something in a drop down menu, simply activate the menu, then run a 		"type note" action with the desired search term.

Action Buttons
----------------------------------
There are four buttons underneath the action panel that will help with managing the script. 

	The add button will collect data from the action box, turn it into a row, and add it to the script. 

	The remove button will remove the currently selected action.

	The clear button will empty the script of all actions, after double checking with the user.

	The Get Position button will collect the coordinates of the next left mouse click into the X and Y fields in the action box.

Additional features
-----------------------

Wait for pixel 
	This feature sets SmartMacro apart from every other macro scripting program. With wait for pixel, you can set an aciton that will pause script execution until a certain pixel turns a specified color. You can then specify an additional delay time after the pixel changes until the next script item runs. This is useful if you are doing data entry that involves working with a web browser. Often pages will take arbitrary amounts of time to load, making it difficult to program a script to account for that. Adding a long delay might work, but if the page takes too long to load, the script will become totally out of sync with the workspace. 

Read from file
	Similar to type note, but will read characters and simulate their key presses from file input rather than a text field. (Note that neither action can be used to run key commands such as CTRL+C. The script will type out each character it reads, not combine them).

Clicking rows in the script
	Single clicking a row in the script will allow it to be deleted via the remove button, or dragged to a new location in the script. 
	Double clicking a row in the script will open up a window where its properties can be edited (same properties as the action box).
		This is more efficient than removing the item, creating a new one, and dragging it up in place.

Settings window
	The hotkeys to add an action and to start/stop the script can be configured in the settings window.


KNOWN BUGS
--------------------

- Due to a bug in the Java AWT Robot class, clicks will not occur at the specified locations. Hopefully this will be fixed soon
- The Wait for Pixel / Read From File functions have not yet been implemented in the Java version of this program
- The Open/Save functions do not work yet
- Rapidly clicking the get position button will sometimes cause an exception to be thrown