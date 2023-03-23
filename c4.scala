import c4_engine._


val rows = 6
val cols = 7
val winLength = 4


// Main method for playing the game (and getting command-line args)
def main(args: Array[String]): Unit = {
    val numArgs = args.length
    val dimensions = undefined

    if numArgs >= 1 then
        dimensions = args(0).split("x")
        if (dimensions == Null) then
            println("Board size " + args(0) + " is not formatted properly.");
            System.exit(1)
        else
            rows = dimensions(0).toInt
            cols = dimensions(1).toInt

    // if there is an argument present for win length, use that
    if numArgs == 2 then
        winLength = args(1).toInt
}

