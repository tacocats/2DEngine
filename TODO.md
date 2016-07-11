----------
**TODO**

This is a to do page covering the current plans and features that need to be completed.

----------
**Major**
Things that need to be done first

 - Clean up client and server code
 - Create a trello page for this

----------

**Server**

 - Server needs to be updated to take requests from various clients and create threads for each of them, currently it only supports one client with way it is written
 - MySQL datbase connection
 - Basic login system eventually
 - Authentication for requests such as movement

----------

**Client**

 - Movement System needs to be rewritten in it's own class, with various variables left open and editable for the future API
 - Add various events such as mouse events 

----------
**Levels and Level Editor**

 - Decide on how levels should be formatted (Text files)
 - Create a basic parser for them
 - Create a basic image file that will be used as a tileset template, defining which areas player can/can't walk on etc

----------
**Animation/Sprite Editor**

 - Decide on format for them
 - Create basic parser for them


----------
**API**
This is currently still being decided on how it should be implement. Most likely create a thread for each script inside a script folder on server and run those, while allowing them to interact with basically everything.

 ----------
 **Debugging tools**

 - Simple GUI to grab basic client information and display it
 - Simple cli tool to communicate with server for testing

 ----------
 **Remote control tool**

 - Allow connecting to server and managing files etc.

