import random


class Room:

    def __init__(self,roomNumber,roomsNearby,roomType):
        self.roomNumber = roomNumber
        self.roomsNearby = roomsNearby
        self.roomType = roomType

class Maze:
    '''The class room contains all the attributes that are
       needed for the different roomobjects'''

    def __init__(self):
        self.northSouth = list(range(1,21))
        self.eastWest = list(range(1,21))
        random.shuffle(self.northSouth)
        random.shuffle(self.eastWest)

def bindRooms(roomNumber):

    '''Takes in the roomnumber and returns the rooms around it
     in the order North,South,East,West'''

    gameMaze = Maze()
    northsouthIndex = gameMaze.northSouth.index(roomNumber)
    eastwestIndex = gameMaze.eastWest.index(roomNumber)
    nsIndex19 = northsouthIndex == 19
    ewIndex19 = eastwestIndex == 19

    if nsIndex19 or ewIndex19:
        if nsIndex19 and ewIndex19:
            return gameMaze.northSouth[0],gameMaze.northSouth[northsouthIndex - 1],\
                   gameMaze.eastWest[0],gameMaze.eastWest[eastwestIndex -1]
        elif nsIndex19:
            return gameMaze.northSouth[0], gameMaze.northSouth[northsouthIndex - 1],\
                   gameMaze.eastWest[eastwestIndex + 1],gameMaze.eastWest[eastwestIndex - 1]

        elif ewIndex19:
            return gameMaze.northSouth[northsouthIndex + 1], gameMaze.northSouth[northsouthIndex - 1],\
                   gameMaze.eastWest[0], gameMaze.eastWest[eastwestIndex - 1]
    else:

        return gameMaze.northSouth[northsouthIndex + 1],gameMaze.northSouth[northsouthIndex - 1],\
               gameMaze.eastWest[eastwestIndex + 1],gameMaze.eastWest[eastwestIndex - 1]

def createRooms():

    '''Creates all the objects of the class Room,adds and binds them with a number
    to a key in the roomdictionary. The key and the roomnumber for the roomobject
    connected to it is always the same. Makes the right amount of all of the
    different roomtypes'''

    allRooms = list(range(1,21))

    random.shuffle(allRooms)

    for number in range(0,20):
        roomDictionary[allRooms[number]] = Room(allRooms[number],list(bindRooms(allRooms[number])),"Wumpus")

    for number in range(1,7):
        roomDictionary[allRooms[number]] = Room(allRooms[number],list(bindRooms(allRooms[number])), "Bats")

    for number in range(7, 12):
        roomDictionary[allRooms[number]] = Room(allRooms[number],list(bindRooms(allRooms[number])), "Hole")

    for number in range(12,20):
        roomDictionary[allRooms[number]] = Room(allRooms[number],list(bindRooms(allRooms[number])), "Nothing")


def choice(question,acceptableAnswers):

    '''Takes in a question and the acceptable answers and
    returns the answer if it is among the acceptable answers'''

    answer = input(question).upper()
    while True:

        if answer in acceptableAnswers:
            return answer
        else:
            answer = input("Acceptable answers are: "+str(acceptableAnswers)+ "\n" "Please answer again: ").upper()

def describeRoom(roomNumber):

    '''

    :param roomNumber:
    :return:
    '''

    roomsNear = roomDictionary[roomNumber].roomsNearby
    testifClose = []
    print("From here you can get to rooms: " + str(roomsNear))

    for room in roomsNear:
        if "Wumpus" in roomDictionary[room].roomType:
            testifClose.append("Wumpus")

        if 'Bats' in roomDictionary[room].roomType:
            testifClose.append("Bats")

        if 'Hole' in roomDictionary[room].roomType:
            testifClose.append("Hole")

    if "Wumpus" in testifClose:
        print("I can smell a nasty Wumpus!")

    if "Bats" in testifClose:
        print("I can hear the sound of bats!")

    if "Hole" in testifClose:
        print("I can feel the breeze from a bottomless hole!")



def highScore(countMoves):

    '''Makes a highscorelist in a file with
    your name and how many moves you made
    before killing Wumpus'''

    highscoreName = str(input("What is your name?: "))
    highscoreFile = open("highscore.txt","a",encoding="UTF-8")
    highscoreFile.write("\n" + "Name:" + highscoreName + " Moves: " + str(countMoves))
    highscoreFile.close


def runGame():

    '''Pieces together all the functions in the game in the right order to execute
    it properly.Holds the variables for counting moves and how many arrows you have
    left and handles the movement of the arrow and the player.'''

    createRooms()

    playerPosition = random.randint(1,20)
    movementIndex = {"N": 0, "S": 1, "E": 2, "W": 3}
    arrowRooms = ["first", 'second', 'third']
    countMoves = 0
    countArrows = 5
    gameFinished = False

    while not gameFinished:
        print("-" * 50)
        print("You are currently in room " + str(playerPosition)+".")

        describeRoom(playerPosition)

        answer1 = choice("Do you want to move or shoot?(M/S)?: ", ["M", "S"])

        #Handles shooting the arrow

        if answer1 == "S" and countArrows > 0:
            countArrows -= 1
            arrowPosition = playerPosition

            for roomsTraveled in range(3):
                arrowDirection = choice("The arrow is leaving the " + arrowRooms[roomsTraveled] + \
                                        " room. Which direction do you want to shoot? (N, S, E, W) : ",
                                        ["N", "S", "E", "W"])

                arrowPosition = roomDictionary[arrowPosition].roomsNearby[movementIndex[arrowDirection]]

                if roomDictionary[arrowPosition].roomType == "Wumpus":
                    print("You hear a terrifying roar as your arrow penetrates the heart of the Wumpus.\nIt´s body falling lifelessly to the ground...")
                    print("You did it,the Wumpus is dead!")
                    highScore(countMoves)
                    gameFinished = True
                    break

                elif roomDictionary[arrowPosition].roomNumber == playerPosition:
                    print("The arrow came back and shot you in the face! You are dead!")
                    gameFinished = True
                    break

            if not gameFinished:
                print("You hit nothing...")

        if answer1 == "S" and countArrows <= 0:
            print("You have used up all your arrows!")

        #Handles moving the player

        if answer1 == "M":
            answer2 = choice("Which direction do you want to go? (N, S, E, W) : ", ["N", "S", "E", "W"])

            playerPosition = roomDictionary[playerPosition].roomsNearby[movementIndex[answer2]]

            countMoves += 1

            if roomDictionary[playerPosition].roomType == "Wumpus":
                print("The nasty Wumpus finds you rips you to pieces and eats you alive!" + "\n" + "Better luck next time!")
                gameFinished = True

            elif roomDictionary[playerPosition].roomType == "Bats":
                newPosition = random.randint(1,20)
                print("You can hear the wings of a giant bat clapping...\nIt picks you and drops you down in room " + str(newPosition) + ".")
                playerPosition = newPosition

            elif roomDictionary[playerPosition].roomType == "Hole":
                print("You stumble and fall into a bottomless pit...... ")
                gameFinished = True


# Starts the gameloop

intro = open("introduction.txt", "r")
print(intro.read())
intro.close()

while True:

    roomDictionary = {}
    runGame()
    answer = choice("Try again? (Y,N)",["Y","N"])
    if answer == "N":
        print("Exiting...")
        break


