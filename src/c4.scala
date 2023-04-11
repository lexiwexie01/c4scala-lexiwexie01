// Alexis Webster

import scala.io.StdIn
import scala.util.control.Breaks._
import scala.util.control.NonLocalReturns._


var rows = 6
var cols = 7
var winLength = 4


// Main method for playing the game (and getting command-line args)
def main(args: Array[String]): Unit = {
    val numArgs = args.length
    var dimensions: Array[String] = null

    if numArgs >= 1 then
        dimensions = args(0).split("x")
        if dimensions == null then
            println("Board size " + args(0) + " is not formatted properly.");
            System.exit(1)
        else
            rows = dimensions(0).toInt
            cols = dimensions(1).toInt

    // if there is an argument present for win length, use that
    if numArgs == 2 then
        winLength = args(1).toInt

    val c4 = Connect4(rows, cols, winLength).play()
}


class Connect4(var numRows: Int, var numColumns: Int, var winLength: Int):
    // private variables that will be used throughout
    private var board = Array.ofDim[Int](numRows, numColumns)
    private var player: Int = 1
    private var win: Boolean = false
    private var moveLetter: Char = _
    private var moveNum: Int = _
    private var counter: Int = 0

    def header(): String =
        "A B C D E F G H I J K L M N O P".substring(0, numColumns*2-1)

    def printBoard() =
        // the 's' in front of the string makes it so you can inject variable values
        println(s"C4 --- Board Dimensions: $numRows by $numColumns --- Win Length: $winLength")

        println(header())

        // loop through each space on the board and display it
        for row <- board do
            for element <- row do
                print(s"$element ")
            println()

    def play() = 
        for row <- 0 to numRows-1 do
            for col <- 0 to numColumns-1 do
                board(row)(col) = 0

        // makes it so you can use "break" to come back to here anywhere within this block
        breakable {
            // while there is no winner, keep playing
            while !win do
                printBoard()

                // have the player enter their move
                println(s"Player $player, enter a column: ")
                moveLetter = StdIn.readChar()

                // if player enters 'q', quit
                if moveLetter == 'q' then
                    printf("Goodbye.")
                    sys.exit(0)

                // convert the move to a number that corresponds to the column
                moveNum = moveLetter - 97
            
                // if the column is not valid, display an error message
                if moveNum < 0 || moveNum >= numColumns then
                    printf(s"There is no column called $moveLetter \n Please try again.\n")
                // if the column is full, display an error message
                else if board(0)(moveNum) != 0 then
                    printf(s"The $moveLetter column is full. Please try again.\n")
                // if there are no errors, place the piece
                else
                    breakable {
                        for row <- numRows-1 to 0 by -1 do

                            // place the piece if the slot is not full,
                            // leave loop once piece is placed
                            if board(row)(moveNum) == 0 then
                                board(row)(moveNum) = player
                                break
                    }
                    // check for a win
                    win = checkForWin()

                    // exit the loop if there's a winner
                    if win then break

                    counter += 1

                    // check for a draw
                    if counter == numRows*numColumns then
                        printf("It's a Draw!")
                        sys.exit(0)

                    // switch players
                    player = player%2 + 1
        }
        // this is only reached if there is a winner
        // print the board and the winner
        printBoard()
        printf(s"Congratulations, Player $player. You win.")
        sys.exit(0)

    
    def checkForWin(): Boolean = 
        var inARow: Int = 0
        var winFound: Boolean = false

        // check columns for a vertical winner
        for col <- 0 to numColumns-1 do

            // check all slots in column
            for row <- numRows-1 to 0 by -1 do
                inARow = verticalHorizontalChecker(row, col, inARow)
                if inARow == winLength then winFound = true
            inARow = 0

        // check columns for horizontal winner
        for row <- 0 to numRows-1 if !winFound do

            // check all slots in row
            for col <- 0 to numColumns-1 do
                inARow = verticalHorizontalChecker(row, col, inARow)
                if inARow == winLength then winFound = true
            inARow = 0

        // check for antidiagonal (/)
        for row <- numRows-1 to 0 by -1 if !winFound do
            breakable {
                for col <- 0 to numColumns-1 do

                    // stop checking for a diagonal outside of the board's bounds
                    if (row <= numRows-winLength) || (col > numColumns-winLength) then
                        break

                    // if the player has a piece in that slot
                    if board(row)(col) == player then
                        inARow = diagonalChecker(row, col, 0)
                        if inARow == winLength then winFound = true
            }
        
        // check for leading diagonal (\)
        for row <- 0 to numRows-1 if !winFound do
            breakable {
                for col <- 0 to numColumns-1 do

                    // stop checking for a diagonal outside of the board's bounds
                    if (row > numRows-winLength) || (col > numColumns-winLength) then
                        break
                    
                    if board(row)(col) == player then
                        inARow = diagonalChecker(row, col, 1)
                        if inARow == winLength then winFound = true
            }
        winFound

    def verticalHorizontalChecker(row: Int, col: Int, inARow: Int): Int =

        // add to the inARow variable if the slot is occupied by the player
        if (board(row)(col) == player) inARow+1 else 0

    // diagType represents the type of diagonal we are checking for
    // 0 for antidiagonal(/), any nonzero int for leading diagonal(\)
    def diagonalChecker(row: Int, col: Int, diagType: Int): Int =

        // checking in diagonal direction from this slot,
        // so there is only 1 piece in a row to start
        var inARow: Int = 1

        
        breakable {
            // loop through all antidiagonal spots
            for i <- 1 to winLength-1 do
                if inARow == winLength then
                    break

    
                // adjust logic depending on type of diagonal
                var check: Int = if (diagType == 0) board(row-i)(col+i) else board (row+i)(col+i)

                // if the current slot contains the player's piece,
                // add one to the inARow counter
                if check == player then
                    inARow += 1

                    // if there are enough diagonal slots filled,
                    // return the winning number in a row
                    if inARow == winLength then
                        break
                else
                    inARow = 0
                    break
        }
        inARow

end Connect4