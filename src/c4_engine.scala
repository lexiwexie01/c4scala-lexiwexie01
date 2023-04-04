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

    
    def checkForWin: Boolean = 
        inARow = 0

        // check columns for a vertical winner
        for col <- 0 to numColumns-1:

            // check all slots in column
            for row <- numRows-1 to 0 by -1:
                inARow = verticalHorizontalChecker(row, col, inARow)
                if inARow == winLength:
                    return true
            inARow = 0

        // check columns for horizontal winner
        for row <- 0 to numRows-1:

            // check all slots in row
            for col <- 0 to numColumns-1:
                inARow = verticalHorizontalChecker(row, col, inARow)
                if inARow == winLength:
                    return true
            inARow = 0

        // check for antidiagonal (/)
        for row <- numRows-1 to 0 by -1:
            breakable:
                for col <- 0 to numColumns-1:

                    // stop checking for a diagonal outside of the board's bounds
                    if (row <= numRows-winLength) || (col > numColumns-winLength):
                        break

                    // if the player has a piece in that slot
                    if board(row)(col) == player:
                        inARow = diagonalChecker(row, col, 0)
                        if inARow == winLength:
                            return true
        
        // check for leading diagonal (\)
        for row <- 0 to numRows-1:
            breakable:
                for col <- 0 to numColumns-1:

                    // stop checking for a diagonal outside of the board's bounds
                    if (row > numRows-winLength) || (col > numColumns-winLength):
                        break
                    
                    if board(row)(col) == player:
                        inARow = diagonalChecker(row, col, 1)
                        if inARow == winLength:
                            return true
        return false

    def verticalHorizontalChecker(row, col, inARow): Int =

        // add to the inARow variable if the slot is occupied by the player
        if (board(row)(col) == player) inARow+1 else 0

    // diagType represents the type of diagonal we are checking for
    // 0 for antidiagonal(/), any nonzero int for leading diagonal(\)
    def diagonalChecker(row, col, diagType): Int =

        // checking in diagonal direction from this slot,
        // so there is only 1 piece in a row to start
        inARow = 1

        
        breakable:
            // loop through all antidiagonal spots
            for i <- 1 to winLength-1:
                if inARow == winLength:
                    return inARow

    
                // adjust logic depending on type of diagonal
                check = if (diagType == 0) board(row-i)(col+i) else board (row+i)(col+i)

                // if the current slot contains the player's piece,
                // add one to the inARow counter
                if check == player:
                    inARow += 1

                    // if there are enough diagonal slots filled,
                    // return the winning number in a row
                    if inARow == winLength:
                        return inARow
                else:
                    break
            return 0

end Connect4