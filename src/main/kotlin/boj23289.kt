//import Storage.Companion.R
//import Storage.Companion.C
//import Storage.Companion.K
//import Storage.Companion.W
//import Storage.Companion.blowers
//import Storage.Companion.board
//import Storage.Companion.chocolate
//import Storage.Companion.room
//import Storage.Companion.target
//import Storage.Companion.walls
//
//const val EMPTY = 0
//const val RIGHT = 1
//const val LEFT = 2
//const val UP = 3
//const val DOWN = 4
//const val INVESTIGATE = 5
//
//class Storage {
//    companion object {
//        var R: Int = 0
//        var C: Int = 0
//        var K: Int = 0    // 온도가 상승하는 정도
//        var W: Int = 0    // 벽의 개수
//        lateinit var board: Array<IntArray>
//        lateinit var room: Array<IntArray>
//        lateinit var walls: Array<Wall>
//        var blowers: MutableList<Blower> = mutableListOf()
//        var target: MutableList<Point> = mutableListOf()
//        var chocolate: Int = 0
//    }
//}
//
//data class Event(var r: Int, var c: Int, var value: Int)
//data class Point(var r: Int, var c: Int)
//data class Blower(var r: Int, var c: Int, var direction: Int)
//data class Wall(var r: Int, var c: Int, var t: Int)
//
//fun main() {
//    input()
//    process()
//    output()
//}
//
///**
// * 1. 집에 있는 모든 온풍기에서 바람이 한 번 나옴
// * 2. 온도가 조절됨.
// * 3. 온도가 1 이상인 가장 바깥쪽 칸의 온도가 1씩 감소
// * 4. 초콜릿을 하나 먹는다.
// * 5. 조사하는 모든 칸의 온도가 K 이상이 되었는지 검사. 모든 칸의 온도가 K이상이면 테스트를 중단하고, 아니면 1부터 다시 시작한다.
// */
//fun process() {
//    blowing()
//    eatChocolate()
//    if (!investigate()) {
//        process()
//    }
//}
//
//fun investigate(): Boolean {
//    return target.find { room[it.r][it.c] < K } == null || chocolate > 100
//}
//
//fun eatChocolate() {
//    chocolate++
//}
//
//fun isRange(r: Int, c: Int): Boolean {
//    return (r in 1..R) && (c in 1..C)
//}
//
//fun isWall(r: Int, c: Int, dir: Int): Boolean {
//    if (!isRange(r, c)) return true
//
//    val result = when (dir) {
//        UP -> walls.find { it == Wall(r = r, c = c, t = 0) }
//        RIGHT -> walls.find { it == Wall(r = r, c = c, t = 1) }
//        DOWN -> walls.find { it == Wall(r = r + 1, c = c, t = 0) }
//        LEFT -> walls.find { it == Wall(r = r, c = c - 1, t = 1) }
//        else -> throw IllegalStateException("FUCKING")
//    }
//    return result != null
//}
//
//fun blowing() {
//    var events = mutableListOf<Event>()
//    val isVisited = Array(R + 3) { BooleanArray(C + 3) { false } }
//
//    fun blow(initEvent: Event, dir: Int) {
//        val q = mutableListOf<Event>()
//        q.add(initEvent)
//
//        while (q.isNotEmpty()) {
//            val event = q.removeFirst()
//            if (isRange(event.r, event.c) && event.value != 0 && !isVisited[event.r][event.c]) {
//                isVisited[event.r][event.c] = true
//                room[event.r][event.c] += event.value
//
//                when (dir) {
//                    UP -> {
//                        if (!isWall(event.r, event.c, UP) && !isVisited[event.r - 1][event.c]) {
//                            q.add(Event(event.r - 1, event.c, event.value - 1))
//                        }
//                        if (!isWall(event.r, event.c, LEFT) && !isWall(event.r,
//                                event.c - 1,
//                                UP) && !isVisited[event.r - 1][event.c - 1]
//                        ) {
//                            q.add((Event(event.r - 1, event.c - 1, event.value - 1)))
//                        }
//                        if (!isWall(event.r, event.c, RIGHT) && !isWall(event.r,
//                                event.c + 1,
//                                UP) && !isVisited[event.r - 1][event.c + 1]
//                        ) {
//                            q.add((Event(event.r - 1, event.c + 1, event.value - 1)))
//                        }
//                    }
//                    DOWN -> {
//                        if (!isWall(event.r, event.c, DOWN)&& !isVisited[event.r + 1][event.c]) {
//                            q.add((Event(event.r + 1, event.c, event.value - 1)))
//                        }
//                        if (!isWall(event.r, event.c, LEFT) && !isWall(event.r, event.c - 1, DOWN) && !isVisited[event.r + 1][event.c - 1]) {
//                            q.add((Event(event.r + 1, event.c - 1, event.value - 1)))
//                        }
//                        if (!isWall(event.r, event.c, RIGHT) && !isWall(event.r, event.c + 1, DOWN)&& !isVisited[event.r + 1][event.c + 1]) {
//                            q.add((Event(event.r + 1, event.c + 1, event.value - 1)))
//                        }
//                    }
//                    LEFT -> {
//                        if (!isWall(event.r, event.c, LEFT)&& !isVisited[event.r][event.c - 1]) {
//                            q.add((Event(event.r, event.c - 1, event.value - 1)))
//                        }
//                        if (!isWall(event.r, event.c, UP) && !isWall(event.r - 1, event.c, LEFT)&& !isVisited[event.r - 1 ][event.c - 1]) {
//                            q.add((Event(event.r - 1, event.c - 1, event.value - 1)))
//                        }
//                        if (!isWall(event.r, event.c, DOWN) && !isWall(event.r + 1, event.c, LEFT)&& !isVisited[event.r + 1][event.c - 1]) {
//                            q.add((Event(event.r + 1, event.c - 1, event.value - 1)))
//                        }
//                    }
//                    RIGHT -> {
//                        if (!isWall(event.r, event.c, RIGHT)&& !isVisited[event.r][event.c + 1]) {
//                            q.add((Event(event.r, event.c + 1, event.value - 1)))
//                        }
//                        if (!isWall(event.r, event.c, UP) && !isWall(event.r - 1, event.c, RIGHT)&& !isVisited[event.r-1 ][event.c + 1]) {
//                            q.add((Event(event.r - 1, event.c + 1, event.value - 1)))
//                        }
//                        if (!isWall(event.r, event.c, DOWN) && !isWall(event.r + 1, event.c, RIGHT)&& !isVisited[event.r + 1][event.c + 1]) {
//                            q.add((Event(event.r + 1, event.c + 1, event.value - 1)))
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    blowers.forEach { it ->
//        isVisited.map { booleans -> booleans.fill(false) }
//        when (it.direction) {
//            UP -> blow(Event(it.r - 1, it.c, 5), UP)
//            DOWN -> blow(Event(it.r + 1, it.c, 5), DOWN)
//            LEFT -> blow(Event(it.r, it.c - 1, 5), LEFT)
//            RIGHT -> blow(Event(it.r, it.c + 1, 5), RIGHT)
//        }
//        events.forEach { room[it.r][it.c] += it.value }
//        events = mutableListOf()
//    }
//
//    controlTemperature()
//    lowerTemperature()
//}
//
//fun controlTemperature() {
//    val events = mutableListOf<Event>()
//    for (i in 1..R) {
//        for (j in 1..C) {
//            val pairs = Array<Pair<Int, Int>>(5) { Pair(0, 0) }
//            pairs[UP] = Pair(i - 1, j)
//            pairs[RIGHT] = Pair(i, j + 1)
//            pairs[DOWN] = Pair(i + 1, j)
//            pairs[LEFT] = Pair(i, j - 1)
//
//            for (k in 1..4) {
//                if (!isRange(pairs[k].first, pairs[k].second) || isWall(i, j, k)) continue
//                if (room[i][j] > room[pairs[k].first][pairs[k].second]) {
//                    val diff = room[i][j] - room[pairs[k].first][pairs[k].second]
//                    events.add(Event(r = i, c = j, value = -(diff / 4)))
//                    events.add(Event(r = pairs[k].first, c = pairs[k].second, value = diff / 4))
//                }
//            }
//        }
//    }
//    events.forEach { room[it.r][it.c] += it.value }
//}
//
//fun lowerTemperature() {
//    for (i in 1..C) {
//        if (room[1][i] >= 1) room[1][i]--
//        if (room[R][i] >= 1) room[R][i]--
//    }
//    for (j in 2 until R) {
//        if (room[j][1] >= 1) room[j][1]--
//        if (room[j][C] >= 1) room[j][C]--
//    }
//}
//
//fun input() {
//    val list = readLine()!!.split(" ").map { it.toInt() }
//    R = list[0]
//    C = list[1]
//    K = list[2]
//
//    board = Array(R + 1) { IntArray(C + 1) { EMPTY } }
//    room = Array(R + 1) { IntArray(C + 1) { EMPTY } }
//
//    for (i in 1..R) {
//        val instant = readLine()!!.split(" ").map { it.toInt() }
//        for (j in 1..C) {
//            board[i][j] = instant[j - 1]
//
//            when (instant[j - 1]) {
//                INVESTIGATE -> target.add(Point(i, j))
//                RIGHT, UP, DOWN, LEFT -> blowers.add(Blower(i, j, instant[j - 1]))
//            }
//        }
//    }
//
//    W = readLine()!!.toInt()
//    walls = Array(W + 1) { Wall(0, 0, 0) }
//    for (i in 1..W) {
//        val (r, c, t) = readLine()!!.split(" ").map { it.toInt() }
//        walls[i] = Wall(c = c, r = r, t = t)
//    }
//}
//
//
//fun output() {
//    if (chocolate > 100)
//        chocolate = 101
//
//    println(chocolate)
//}
