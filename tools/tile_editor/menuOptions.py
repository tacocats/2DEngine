from tileio import *

def newMenu():
    print ("Crete new level")

def openMenu():
    print ("open level")

# level is passed to exporter
def saveMenu(levelName, level):
    exporter().exportLevel(levelName, level)
def saveAsMenu():
    print ("Save as menu")

def clearMenu():
    print ("clear the level")

def selectTileset():
    print("select the tileset")
