from tkinter import *
from menuOptions import *
from PIL import Image, ImageFilter, ImageTk
from tileset_module import tilesetManipulation
from scene_module import levelManipulation, tile, level

# Globals############################################

# Wether or not the user is drawing on the screen
isDrawing = False

tileset = None
scene = None

levelManip = levelManipulation()
tilesetManip = tilesetManipulation()
######################################################

def keypress(key):
    print (key)

def mousePress(event):
    #print("x - " + str(event.x) + " y - " + str(event.y))
    #print ("fixed - " + str(levelManip.determinePos(event.x, event.y)))
    #event.widget.create_rectangle(20, 20, 50, 50, fill="blue")
    #levelManip.displayLevel(event.widget)

    global isDrawing
    isDrawing = True
    canvas = event.widget
    if (canvas == scene):
        x = canvas.canvasx(event.x)
        y = canvas.canvasy(event.y)

        tileX, tileY, tileImage = tilesetManip.getSelectedTile()
        print (tileX)
        print (tileY)
        levelManip.drawTile(event.widget, x, y, tileImage, tileX, tileY)
    elif (canvas == tileset):
        print("tileset")
        x = canvas.canvasx(event.x)
        y = canvas.canvasy(event.y)
        tilesetManip.setSelectedTile(x, y)

def release(event):
    global isDrawing
    isDrawing = False

def motion(event):
    if (event.widget == scene):
        if (isDrawing == True):
            canvas = event.widget
            x = canvas.canvasx(event.x)
            y = canvas.canvasy(event.y)
            tileX, tileY, tileImage = tilesetManip.getSelectedTile()
            levelManip.drawTile(event.widget, x, y, tileImage, tileX, tileY)

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
    fileMenu.add_command(label="Save", command=lambda:saveMenu("testzzz", levelManip.levelz))
    fileMenu.add_command(label="Save As", command=saveAsMenu)
    menubar.add_cascade(label="File", menu=fileMenu)

    editMenu = Menu(menubar, tearoff=0)
    editMenu.add_command(label="Clear", command=clearMenu)
    menubar.add_cascade(label="Edit", menu=editMenu)

    tilesetMenu = Menu(menubar, tearoff=0)
    tilesetMenu.add_command(label="Select tileset", command=selectTileset)
    menubar.add_cascade(label="Tileset", menu=tilesetMenu)

    # Add a scrollbar to canvas and tileset
    sScrollbarV = Scrollbar(sceneContainer, orient=VERTICAL)
    sScrollbarV.pack(side=LEFT, fill=Y)
    sScrollbarH = Scrollbar(sceneContainer, orient=HORIZONTAL)
    sScrollbarH.pack(side=BOTTOM, fill=X)

    tScrollbarV = Scrollbar(tilesetContainer, orient=VERTICAL)
    tScrollbarV.pack(side=LEFT, fill=Y)
    tScrollbarH = Scrollbar(tilesetContainer, orient=HORIZONTAL)
    tScrollbarH.pack(side=BOTTOM, fill=X)


    # Canvas for the scene
    global scene
    scene = Canvas(sceneContainer, height=500, width=700, bd=0, highlightthickness=0, relief='ridge',scrollregion=(0,0,1000,1000), yscrollcommand=sScrollbarV.set, xscrollcommand=sScrollbarH.set, bg="black")

    # Key bindings for the scene
    scene.bind("<Button-1>", mousePress)
    scene.bind("<Key>", keypress)
    scene.bind("<Motion>", motion)
    scene.bind("<ButtonRelease-1>", release)

    scene.pack(side=RIGHT, fill=BOTH, expand=True)
    sScrollbarV.config(command = scene.yview)
    sScrollbarH.config(command = scene.xview)

    # Canvas for the tileset
    global tileset
    tileset = Canvas(tilesetContainer, height=50, width=50, bg="black", yscrollcommand=tScrollbarV.set, xscrollcommand=tScrollbarH.set)
    tileset.pack(side=RIGHT, fill=BOTH, expand=True)
    tScrollbarV.config(command = tileset.yview)
    tScrollbarH.config(command = tileset.xview)

    # Key bindings for the tileset
    tileset.bind("<Button-1>", mousePress)
    tileset.bind("<Key>", keypress)
    tileset.bind("<Motion>", motion)
    tileset.bind("<ButtonRelease-1>", release)


    root.config(menu=menubar)

    #Set title
    root.wm_title("Tile Editor")

    # Create the opening level
    levelManip.createLevel(50, 50, scene)
    levelManip.displayLevel()

    # Set the tileset
    tilesetManip.setTileset(tileset, "tileset.png")

    root.mainloop()

window = createWindow()
