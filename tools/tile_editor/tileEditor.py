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

# Wether or not the user is drawing on the screen
isDrawing = False

def keypress(key):
    print (key)

def mousePress(event):
    #print("x - " + str(event.x) + " y - " + str(event.y))
    #print ("fixed - " + str(levelManipulation.determinePos(event.x, event.y)))
    #event.widget.create_rectangle(20, 20, 50, 50, fill="blue")
    #levelManipulation.displayLevel(event.widget)

    global isDrawing
    isDrawing = True

    canvas = event.widget
    x = canvas.canvasx(event.x)
    y = canvas.canvasy(event.y)
    levelManipulation.drawTile(levelManipulation, event.widget, x, y)

def release(event):
    print("released!")
    global isDrawing
    isDrawing = False

def motion(event):
    if (isDrawing == True):
        canvas = event.widget
        x = canvas.canvasx(event.x)
        y = canvas.canvasy(event.y)
        levelManipulation.drawTile(levelManipulation, event.widget, x, y)

# Create the window
def createWindow():
    root = Tk()

    # ToolBox Container
    toolbox = Frame(root)
    toolbox.pack(side=BOTTOM)
    greenbutton = Button(toolbox, text="Tool", fg="brown")
    greenbutton.pack( side = LEFT )

    # Paned windows
    pScene = PanedWindow(root, orient=HORIZONTAL)
    pScene.pack(fill=BOTH, expand=True)

    # Scene container
    sceneContainer = Frame(pScene)
    pScene.add(sceneContainer)

    # Tileset Container
    tilesetContainer = Frame(pScene)
    pScene.add(tilesetContainer)

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

    # Add a scrollbar to canvas and tileset
    tScrollbarV = Scrollbar(sceneContainer, orient=VERTICAL)
    tScrollbarV.pack(side=LEFT, fill=Y)
    tScrollbarH = Scrollbar(sceneContainer, orient=HORIZONTAL)
    tScrollbarH.pack(side=BOTTOM, fill=X)

    # Canvas for the scene
    scene = Canvas(sceneContainer, height=500, width=700, bd=0, highlightthickness=0, relief='ridge',scrollregion=(0,0,1000,1000), yscrollcommand=tScrollbarV.set, xscrollcommand=tScrollbarH.set, bg="black")

    # Key bindings for the scene
    scene.bind("<Button-1>", mousePress)
    scene.bind("<Key>", keypress)
    scene.bind("<Motion>", motion)
    scene.bind("<ButtonRelease-1>", release)

    scene.pack(side=RIGHT, fill=BOTH, expand=True)
    tScrollbarV.config(command = scene.yview)
    tScrollbarH.config(command = scene.xview)


    # Canvas for the tileset
    tileset = Canvas(tilesetContainer, bg="red", height=50, width=50)
    tileset.pack(side=RIGHT, fill=BOTH, expand=True)

    root.config(menu=menubar)

    #Set title
    root.wm_title("Tile Editor")

    # Create the opening level
    levelManipulation.createLevel(levelManipulation, 50, 50)
    root.mainloop()

window = createWindow()
