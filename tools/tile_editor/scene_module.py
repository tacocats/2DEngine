# A tile
class tile():
    def __init__(self):
        self.x = 0
        self.y = 0
        self.image = None

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

    def __init__(self):
        self.levelz = level()
        self.canvas = None
    # Determines where to place the tile
    def determinePos(self, x, y):
        fixedX = (x - x%16)
        fixedY = (y- y%16)
        return (fixedX, fixedY)

    # Create a new level
    def createLevel(self, sizeX, sizeY, widget):
        self.tiles = []
        # Loops through adding all the tiles
        for total in range(0, sizeX*sizeY):
            self.tiles.append(tile())

        self.levelz.levelTiles = self.tiles
        self.levelz.levelSizeX = sizeX
        self.levelz.levelSizeY = sizeY

        self.canvas = widget

        # Set the canvas size to size of the level
        self.canvas.configure(width = self.levelz.levelSizeX * 16, height = self.levelz.levelSizeY * 16)

        # Set the scrollable size
        self.canvas.configure(scrollregion=(0, 0, self.levelz.levelSizeX * 16, self.levelz.levelSizeY * 16))

    def displayLevel(self):
        posX = 0
        posY = 0

        # Checks when to start positioning tiles from right to left
        for i in range(0, len(self.levelz.levelTiles)):
            if (i % self.levelz.levelSizeX == 49):
                #self.canvas.create_rectangle(posX, posY, posX+16, posY+16, fill="red", width=0)
                self.canvas.create_image(posX, posY, image=self.levelz.levelTiles[i].image, anchor="nw")
                posY += 16
                posX = 0
            else:
                self.canvas.create_image(posX, posY, image=self.levelz.levelTiles[i].image, anchor="nw")
                #self.canvas.create_rectangle(posX, posY, posX+16, posY+16, fill="red", width=0)
                posX += 16


    #### The below code is a bit of a shit show (If the rest of it already isn't)

    # Draw the tile on the canvas and add it to the list
    # The parameters are the following
    # widget - The widget to draw the image on
    # x - The x cordinate to place the image
    # y - The y cordinate to place the image
    # selectedTile - the  image of the tile to place
    # imageLocX - the x cordinate of the image on the tileset (This is added to the array)
    # imageLocY - the y cordinate of the image on the tileset (This is added to the array)
    def drawTile(self, widget, x, y, selectedTile, imageLocX, imageLocY):
        newX, newY = self.determinePos(x, y)
        widget.copyImage = selectedTile

        # Add the tile to the array in the correct position using simple algoriphm to find where to set it
        # We know the level size in the x direction so we can use a similar way to find where to place tile in array
        # This may be removed in future and replaced with a better technique, since this is probably a bad way of doing it

        # Now check for correct position
        newX, newY = self.determinePos(newX, newY)
        # Simple check to make sure tile is actually allowed to be place in this level
        if (0 <= newX < self.levelz.levelSizeX*16 and 0 <=newY < self.levelz.levelSizeY*16 ):

            # Than devide that by 16 since it's 16x16
            divnewX = newX / 16
            divnewY = newY / 16
            # Than we muliply the x + y*xmax since y is xtotal*y tiles down
            self.levelz.levelTiles[int(divnewX + self.levelz.levelSizeX*divnewY)].x = imageLocX
            self.levelz.levelTiles[int(divnewX + self.levelz.levelSizeX*divnewY)].y = imageLocY
            self.levelz.levelTiles[int(divnewX + self.levelz.levelSizeX*divnewY)].image = selectedTile

            # Display the level
            #self.displayLevel()
            self.canvas.create_image(newX, newY, image=selectedTile, anchor="nw")
