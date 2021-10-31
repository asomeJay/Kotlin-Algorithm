import Storage.Companion.M
import Storage.Companion.S
import Storage.Companion.directions
import Storage.Companion.fishEggMap
import Storage.Companion.fishMap
import Storage.Companion.rc
import Storage.Companion.shark
import Storage.Companion.smellMap

class Storage {
    companion object {
        val fishMap: Array<Array<IntArray>> = Array(5) { Array(5) { IntArray(8) { 0 } } }
        val fishEggMap: Array<Array<IntArray>> = Array(5) { Array(5) { IntArray(8) { 0 } } }

        val smellMap = Array(5) { Array(5) { Smell(0, false) } }
        var S: Int = 0
        var M: Int = 0
        val directions =
            arrayOf(
                Point(0, -1),
                Point(-1, -1),
                Point(-1, 0),
                Point(-1, 1),
                Point(0, 1),
                Point(1, 1),
                Point(1, 0),
                Point(1, -1))
        val rc = arrayOf(Point(-1, 0), Point(0, -1), Point(1, 0), Point(0, 1))
        var shark = Shark(Point(-1, -1))
    }
}


data class Point(var r: Int, var c: Int)

data class Shark(var p: Point) {
    fun isExist(r: Int, c: Int): Boolean = p.r == r && p.c == c

    fun move() {
        val list = generateMoveList()
        for (i in 0..2) {
            // 상어가 움직인다
            p = Point(list[i].r, list[i].c)
            // 격자에서 제외되는 물고기는 냄새를 남긴다.
            if (fishMap[p.r][p.c].sum() != 0) {
                smellMap[p.r][p.c].trace = true
                smellMap[p.r][p.c].disappearCount = 2
            }

            // 물고기 격자에서 제외하기
            fishMap[p.r][p.c].fill(0)
        }
    }

    fun generateMoveList(): List<Point> {
        lateinit var list: MutableList<Point>
        var maximum = Integer.MIN_VALUE
        fun calculateAcc(isVisited: Array<BooleanArray>, acc: Int, point: Point): Int {
            if (!isVisited[point.r][point.c]) {
                isVisited[point.r][point.c] = true
                return acc + fishMap[point.r][point.c].sum()
            }
            return acc
        }

        fun selectMoveList(tempList: MutableList<Point>) {
            val isVisited: Array<BooleanArray> = Array(5) { BooleanArray(5) { false } }
            val experimental = tempList.fold(0) { acc, point -> (calculateAcc(isVisited, acc, point)) }
            if (maximum < experimental) {
                list = tempList.toMutableList()
                maximum = experimental
            }
            return
        }

        fun moveListArbiter(curR: Int, curC: Int, cnt: Int, tempList: MutableList<Point>) {
            if (cnt == 0) {
                selectMoveList(tempList)
                return
            }

            for (i in 0..3) {
                val nr = curR + rc[i].r
                val nc = curC + rc[i].c
                if (isRange(nr, nc)) {
                    tempList.add(Point(nr, nc))
                    moveListArbiter(nr, nc, cnt - 1, tempList)
                    tempList.removeLast()
                }
            }
        }

        moveListArbiter(p.r, p.c, 3, mutableListOf())
        return list
    }
}

data class Smell(var disappearCount: Int, var trace: Boolean) {
    companion object {
        fun disappear() {
            for (i in 1..4) {
                for (j in 1..4) {
                    if (smellMap[i][j].disappearCount == 0) {
                        smellMap[i][j].trace = false
                    }
                }
            }
        }

        fun downCount() {
            for (i in 1..4) {
                for (j in 1..4) {
                    if (smellMap[i][j].disappearCount > 0) {
                        smellMap[i][j].disappearCount--
                    }
                }
            }
        }
    }
}

fun isRange(r: Int, c: Int) = r in (1..4) && c in (1..4)

fun main() {
    input()
    for (i in 0 until S) {
        proceed()
    }
    output()
}

fun output() {
    var sum = 0
    for (i in 1..4) {
        for (j in 1..4) {
            sum += fishMap[i][j].sum()
        }
    }
    println(sum)
}

fun proceed() {
    // 복제마법을 시전한다.
    castCloneSpell()

    // 물고기가 움직인다.
    fishMove()

    // 상어가 움직인다.
    shark.move()

    // 두번전 연습에서 생긴 물고기의 냄새가 격자에서 사라진다.
    Smell.disappear()

    // 1에서 사용한 복제 마법이 완료된다. 모든 복제된 물고기는 1에서의 위치와 방향을 그대로 갖게된다.
    completeCloneSpell()

    Smell.downCount()
}

fun castCloneSpell() {
    for (i in 1..4) {
        for (j in 1..4) {
            for(k in 0..7){
                fishEggMap[i][j][k] = fishMap[i][j][k]
            }
        }
    }
}

fun fishMove() {
    val fishMoveMap: Array<Array<IntArray>> = Array(5) { Array(5) { IntArray(8) { 0 } } }

    for (i in 1..4) {
        for (j in 1..4) {
            for(k in 0..7){
                var dir = k

                for(d in 0..7){
                    val nextR = i + directions[dir].r
                    val nextC = j + directions[dir].c

                    if (!isRange(nextR, nextC) || shark.isExist(nextR, nextC) || smellMap[nextR][nextC].trace) {
                        dir = if (dir == 0) 7 else (dir - 1)
                        continue
                    }
                    fishMoveMap[nextR][nextC][dir] += fishMap[i][j][k]
                    fishMap[i][j][k] = 0
                    break
                }
            }
        }
    }
    for(i in 1..4){
        for(j in 1..4){
            for(k in 0..7){
                fishMap[i][j][k] += fishMoveMap[i][j][k]
            }
        }
    }
}

fun completeCloneSpell() {
    for (i in 1..4) {
        for (j in 1..4) {
            for(k in 0..7){
                fishMap[i][j][k] += fishEggMap[i][j][k]
                fishEggMap[i][j][k] = 0
            }
        }
    }
}

fun input() {
    val (m, s) = readLine()!!.split(" ").map { it.toInt() }
    M = m
    S = s
    for (i in 0 until M) {
        val (r, c, d) = readLine()!!.split(" ").map { it.toInt() }
        fishMap[r][c][d-1]++
    }

    val (r, c) = readLine()!!.split(" ").map { it.toInt() }
    shark.p = Point(r, c)
}
