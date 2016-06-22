from tkinter import *
from tileio import *
from menuOptions import *

# A tile
class tile():
    x = 0
    y = 0

# A level
class level():
    levelSizeX = 0
    levelSizeY = 0
    levelTiles = []

    def setLevelSize(self, x, y):
        levelSizeX = x
        levelSizeY = y

# Class for manipulating tiles
class levelManipulation():
    # Place a tile at the x, y, z pos. Z can be used for layeros
    def placeTile(x, y, z):
       print ("place tile")

    # Determines where to place the tile
    def determinePos(x, y):
        fixedX = (x - x%32)
        fixedY = (y- y%32)
        return (fixedX, fixedY)

    # Create a new level
    def createLevel(self, sizeX, sizeY):
        tiles = []
        # Loops through adding all the tiles
        for total in range(0, sizeX*sizeY):
            tiles.append(tile)

        level.levelTiles = tiles
        level.levelSizeX = sizeX
        level.levelSizeY = sizeY

    def displayLevel(widget):
        #Position to place the tile at
        posX = 0
        posY = 0

        # Checks when to start positioning tiles from right to left
        for i in range(0, len(level.levelTiles)):
            print (level.levelSizeX)
            if (i > level.levelSizeX - 2 and (i % level.levelSizeX) == 1):
                widget.create_rectangle(posX, posY, posX+32, posY+32, fill="black")
                posX = 0
                posY += 32
            else:
                widget.create_rectangle(posX, posY, posX+32, posY+32, fill="black")
                posX += 32

    def drawTile(self, widget, x, y):
        newX, newY = self.determinePos(x, y)
        widget.create_rectangle(newX, newY, newX+32, newY+32, fill="red", width=0)

def keypress(key):
    print (key)

def mousePress(event):
    #print("x - " + str(event.x) + " y - " + str(event.y))
    #print ("fixed - " + str(levelManipulation.determinePos(event.x, event.y)))
    #event.widget.create_rectangle(20, 20, 50, 50, fill="blue")
    #levelManipulation.displayLevel(event.widget)
    levelManipulation.drawTile(levelManipulation, event.widget, event.x, event.y)

# Create the window
def createWindow():
    root = Tk()

    # Paned windows
    pScene = PanedWindow(root, orient=HORIZONTAL)
    pScene.pack(expand=True)

    # The menubar
    menubar = Menu(root)
    fileMenu = Menu(menubar, tearoff=0)
    fileMenu.add_command(label="New", command=newMenu)
    fileMenu.add_command(label="Open", command=openMenu)
    fileMenu.add_command(label="Save", command=saveMenu)
    fileMenu.add_command(label="Save As", command=saveAsMenu)
    menubar.add_cascade(label="File", menu=fileMenu)

    editMenu = Menu(menubar, tearoff=0)
    editMenu.add_command(label="Clear", command=clearMenu)
    menubar.add_cascade(label="Edit", menu=editMenu)


    # Canvas for the scene
    scene = Canvas(pScene, height=500, width=700, bd=0, highlightthickness=0, relief='ridge')
    pScene.add(scene)
    scene.bind("<Button-1>", mousePress)
    root.bind("<Key>", keypress)


    # Canvas for the tileset
    tileset = Canvas(pScene, bg="red", height=50, width=50)
    pScene.add(tileset)

    root.config(menu=menubar)

    # Create the opening level
    levelManipulation.createLevel(levelManipulation, 50, 50)
    root.mainloop()

window = createWindow()
