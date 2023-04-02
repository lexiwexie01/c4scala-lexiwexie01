// Alexis Webster
import scala.io.StdIn
import scala.util.control.Breaks._


class Connect4(val numRows: Int, val numColumns: Int, val winLength: Int):
    // private variables that will be used throughout
    private val board
    private val player = 1
    private val win = false
    private val moveLetter
    private val moveNum
    private val counter = 0

    def header(): String =
        "A B C D E F G H I J K L M N O P".substring(0, numColumns*2)

    def makeBoard() = 
        board = Array.ofDim[Int](numRows, numColumns)

    def printBoard() =
        // the 's' in front of the string makes it so you can inject variable values
        println(s"C4 --- Board Dimensions: $numRows by $numColumns --- Win Length: $winLength")

        println(header())

        // loop through each space on the board and display it
        for row <- board:
            for element <- row:
                print(s"$element ")
            println()

    def play() = 
        for row <- 0 to numRows-1:
            for col <- 0 to numColumns-1:
                board(row)(col) = 0

        // makes it so you can use "break" to come back to here anywhere within this block
        breakable:
            // while there is no winner, keep playing
            while !win:
                printBoard()

                // have the player enter their move
                moveLetter = StdIn.readLine(s"Player $player, enter a column: ")

                // if player enters 'q', quit
                if moveLetter == 'q':
                    printf("Goodbye.")
                    sys.exit(0)

                // convert the move to a number that corresponds to the column
                moveNum = moveLetter - 97
            
                // if the column is not valid, display an error message
                if moveNum < 0 || moveNum >= numColumns:
                    printf(s"There is no column called $moveLetter \n Please try again.")
                // if the column is full, display an error message
                else if board(0)(moveNum) != 0:
                    printf(s"The $moveLetter column is full. Please try again.")
                // if there are no errors, place the piece
                else:
                    breakable:
                        for row <- numRows-1 to 0 by -1:

                            // place the piece if the slot is not full,
                            // leave loop once piece is placed
                            if board(row)(moveNum) == 0:
                                board(row)(moveNum) = player
                                break
                    
                    // check for a win
                    win = checkForWin()

                    // exit the loop if there's a winner
                    if win: break

                    counter += 1

                    // check for a draw
                    if counter == numRows*numColumns:
                        printf("It's a Draw!")
                        sys.exit(0)

                    // switch players
                    player = player%2 + 1
        
        // this is only reached if there is a winner
        // print the board and the winner
        printBoard()
        printf(s"Congratulations, Player $player. You win.")
        sys.exit(0)

    
    def checkForWin() = 



end Connect4