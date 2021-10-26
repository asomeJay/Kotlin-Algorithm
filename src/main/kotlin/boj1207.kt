//import Storage.Companion.board
//import Storage.Companion.pieces
//
//
//const val EMPTY = '.'
//
//class Storage {
//    companion object {
//        lateinit var board: Array<CharArray>
//        val pieces = mutableListOf<Array<CharArray>>()
//    }
//}
//
//fun main() {
//    input()
//    toNextPiece(pieceIndex = -1)
//
//    if (isComplete()) {
//        board.forEach { chars -> chars.forEach { print(it) };println() }
//    } else {
//        print("gg")
//    }
//
//
//}
//
//private fun toNextPiece(
//    pieceIndex: Int,
//) {
//    for (i in board.indices) {
//        for (j in board[i].indices) {
//            if (isRange(i,j, pieces[pieceIndex + 1])) {
//                fill(i,j, pieceIndex + 1)
//                if (isComplete()) {
//                    return
//                }
//            }
//        }
//    }
//}
//
//private fun input() {
//    val L = readLine()!!.toInt()
//    board = Array(L) { CharArray(L) { EMPTY } }
//
//    repeat(5) {
//        val (r, c) = readLine()!!.split(" ").map { it.toInt() }
//        val splicedPiece: Array<CharArray> = Array(r) { CharArray(c) { EMPTY } }
//
//        for (i in 0 until r) {
//            splicedPiece[i] = readLine()!!.toCharArray()
//        }
//
//        pieces.add(splicedPiece)
//    }
//    pieces.add(Array(0) { charArrayOf() })
//}
//
//fun rollback(
//    startRow: Int,
//    startCol: Int,
//    finishRow: Int,
//    finishCol: Int,
//    currentPiece: Array<CharArray>,
//) {
//    for (i in startRow until finishRow) {
//        for (j in currentPiece[i - startRow].indices) {
//            if (currentPiece[i - startRow][j] != EMPTY) {
//                board[i][j + startRow] = EMPTY
//            }
//        }
//    }
//    for (j in 0..(finishCol - startCol)) {
//        if (currentPiece[finishRow - startRow][j] != EMPTY) {
//            board[finishRow][j + startCol] = EMPTY
//        }
//    }
//}
//
//fun fill(
//    row: Int,
//    col: Int,
//    curPiecesIndex: Int,
//) {
//    val currentPiece = pieces[curPiecesIndex]
//    if (isComplete()) return
//
//    for (i in currentPiece.indices) {
//        for (j in currentPiece[i].indices) {
//            val curR = row + i
//            val curC = col + j
//
//            if (board[curR][curC] == EMPTY) {
//                if (currentPiece[i][j] != EMPTY) board[curR][curC] = (curPiecesIndex + 1 + 48).toChar()
//            } else if (currentPiece[i][j] != EMPTY) {
//
//                val temporal = board[curR][curC]
//                rollback(row, col, curR, curC, currentPiece)
//                board[curR][curC] = temporal
//                return
//            }
//        }
//    }
//
//    toNextPiece(curPiecesIndex)
//    if (!isComplete()) {
//        rollback(
//            row,
//            col,
//            row + currentPiece.size - 1,
//            col + currentPiece[0].size - 1,
//            currentPiece)
//    }
//}
//
//fun isRange(r: Int, c: Int, currentPiece: Array<CharArray>): Boolean {
//    if (currentPiece.isEmpty()) return false
//    return r + currentPiece.size - 1 < board.size &&
//            c + currentPiece[0].size - 1 < board[0].size
//}
//
////data class Point(
////    var r: Int,
////    var c: Int,
////) {
////    fun ok() = r != -1
////    fun forward(): Point {
////        if (c + 1 >= board.size) {
////            if (r + 1 >= board.size)
////                return Point(-1, -1)
////            return Point(r + 1, 0)
////        }
////        return Point(r, c + 1)
////    }
////}
//
//fun isComplete(): Boolean {
////    src.forEach { chars -> chars.forEach { print(it) };println() }
////    println()
//
//    val check = Array(6) { false }
//    check[0] = true
//
//    board.forEach { chars -> chars.forEach { if (it != '.') check[it.digitToInt()] = true } }
//    val mapNotNull = board.mapNotNull { chars -> chars.find { it == EMPTY } }
//    return mapNotNull.isEmpty() && !check.contains(false)
//}