import Storage.Companion.N
import Storage.Companion.M
import Storage.Companion.K
import Storage.Companion.dc
import Storage.Companion.dice
import Storage.Companion.dr
import Storage.Companion.map
import Storage.Companion.score

class Storage {
    companion object {
        var N: Int = 0
        var M: Int = 0
        var K: Int = 0
        lateinit var map: Array<IntArray>
        var dice: Dice = Dice()
        var score: Int = 0
        val dr = intArrayOf(-1, 0, 1, 0)
        val dc = intArrayOf(0, 1, 0, -1)
    }
}

const val L = 0
const val R = 1
const val U = 2
const val D = 3

data class Dice(var r: Int = 1, var c: Int = 1, var dir: Int = 1) {
    var horizon: IntArray = intArrayOf(4, 1, 3, 6)
    var vertical: IntArray = intArrayOf(5, 1, 2, 6)

    fun move() {
        when (dir) {
            L -> left()
            R -> right()
            U -> up()
            D -> down()
        }
    }

    fun turn(B: Int) {
        when {
            horizon[3] > B -> turnRight()
            horizon[3] < B -> turnLeft()
        }
    }

    private fun turnLeft() {
        dir = when (dir) {
            L -> D
            D -> R
            R -> U
            U -> L
            else -> throw IllegalStateException("")
        }
    }

    private fun turnRight() {
        dir = when (dir) {
            L -> U
            U -> R
            R -> D
            D -> L
            else -> throw IllegalStateException("")
        }
    }

    private fun changeDirection() {
        dir = when (dir) {
            L -> R
            R -> L
            U -> D
            D -> U
            else -> throw IllegalStateException("")
        }
    }

    private fun left() {
        c--
        if (!isRange(r, c)) {
            changeDirection()
            c++
            return move()
        }
        val temp = horizon[0]
        horizon[0] = horizon[1]
        horizon[1] = horizon[2]
        horizon[2] = horizon[3]
        horizon[3] = temp

        vertical[1] = horizon[1]
        vertical[3] = horizon[3]
    }

    private fun right() {
        c++
        if (!isRange(r, c)) {
            changeDirection()
            c--
            return move()
        }
        val temp = horizon[3]
        horizon[3] = horizon[2]
        horizon[2] = horizon[1]
        horizon[1] = horizon[0]
        horizon[0] = temp

        vertical[1] = horizon[1]
        vertical[3] = horizon[3]
    }

    private fun up() {
        r--
        if (!isRange(r, c)) {
            changeDirection()
            r++
            return move()
        }
        val temp = vertical[3]
        vertical[3] = vertical[2]
        vertical[2] = vertical[1]
        vertical[1] = vertical[0]
        vertical[0] = temp

        horizon[1] = vertical[1]
        horizon[3] = vertical[3]
    }

    private fun down() {
        r++
        if (!isRange(r, c)) {
            changeDirection()
            r--
            return move()
        }
        val temp = vertical[0]
        vertical[0] = vertical[1]
        vertical[1] = vertical[2]
        vertical[2] = vertical[3]
        vertical[3] = temp

        horizon[1] = vertical[1]
        horizon[3] = vertical[3]
    }

    fun debug() {
        println()
        println("     ${vertical[2]}      ")
        println("   ${horizon[0]} ${horizon[1]} ${horizon[2]}      ")
        println("     ${vertical[0]}      ")
        println("     ${vertical[3]}      ")
        println()
    }
}


fun main() {
    input()
    proceed()
    output()
}

fun output() {
    println(score)
}

fun proceed() {
    repeat(K) {
        dice.move()
        earnPoints()
        dice.turn(map[dice.r][dice.c])
    }
}

data class P(val r: Int, val c: Int, val acc: Int)

fun earnPoints() {
    val B = map[dice.r][dice.c]
    var C = 0
    val q = mutableListOf<P>()
    val isVisited = Array(map.size) { BooleanArray(map[0].size) { false } }

    q.add(P(dice.r, dice.c, 1))
    isVisited[dice.r][dice.c] = true

    while (q.isNotEmpty()) {
        val p = q.removeAt(0)
        C++
        for (i in 0..3) {
            val nr = p.r + dr[i]
            val nc = p.c + dc[i]
            if (isRange(nr, nc) && !isVisited[nr][nc] && map[nr][nc] == B) {
                isVisited[nr][nc] = true
                q.add(P(nr, nc, p.acc + 1))
            }
        }
    }
    score += (B * C)
}

fun input() {
    val list = readLine()!!.split(" ").map { it.toInt() }
    N = list[0]
    M = list[1]
    K = list[2]

    map = Array(N + 1) { IntArray(M + 1) { 0 } }
    for(i in 1..N){
        val row = readLine()!!.split(" ").map { it.toInt() }
        for(j in 1..M){
            map[i][j] = row[j - 1]
        }
    }
}

fun isRange(r: Int, c: Int): Boolean {
    return (r in 1..N) && (c in 1..M)
}
