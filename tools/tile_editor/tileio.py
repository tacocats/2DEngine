# Simple exporter, simply pass it the level array and a filename/location and it will export there
# Currently it will override files, this needs to be updated eventually to check
class exporter():
    def exportLevel(self, fileName, level):
        f = open(fileName + '.2d', 'w')
        f.write(str(fileName) + "\n")
        f.write(str(level.levelSizeX) + "\n")
        f.write(str(level.levelSizeY) + "\n")

        for i in level.levelTiles:
            f.write(str(i.x) + " " + str(i.y) + "\n")

class importer():
    def importLevel():
        print ("Import file")
