from PIL import Image, ImageFilter, ImageTk

class selectedTile():
    x = 0
    y = 0

class tilesetManipulation():

    # Tile that is currently selected to draw with
    userTile = selectedTile
    tImage = None

    # Get tile user clicked
    def determinePos(self, x, y):
        fixedX = (x - x%16)
        fixedY = (y- y%16)
        return (fixedX, fixedY)
    # Splits up the tileset and draws it onto the canvas. It also adds the list of tiles to the tile array
    def setTileset(self, widget, tset):
        self.tImage = Image.open(open('C:/Users/Jack/Desktop/tile_editor/tileset.png', 'rb'))
        fixedImage = ImageTk.PhotoImage(self.tImage)
        self.fixedImage = fixedImage
        theimage= widget.create_image(0, 0, image=fixedImage, anchor="nw")

        # Set scollable size for tileset
        tWidth, tHeight = self.tImage.size
        widget.configure(scrollregion=(0, 0, tWidth, tHeight))

    # Return a tile
    def getTile (self, x, y):
        self.tile = self.tImage.crop((x, y, x+16, y+16))
        selectedTile = ImageTk.PhotoImage(self.tile)
        self.selectedTile = selectedTile
        return(self.selectedTile)

    # Set the tile the user will draw with
    def setSelectedTile(self, x, y):
        self.userTile.x, self.userTile.y = self.determinePos(x, y)

    # Return the current selected tile image and the position of that image
    def getSelectedTile(self):
        return (self.userTile.x, self.userTile.y, self.getTile(self.userTile.x, self.userTile.y))
