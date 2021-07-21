
fun main(args:Array<String>){
    val testCase:Int = readLine()!!.toInt()
    for (i in 1..testCase) {
        val dp = Array(31) { IntArray(31) {0} }
        for (i in 1..30) {
            dp[i][1] = i
            dp[i][0] = 1
            dp[0][i] = 1
            dp[i][i] = 1
        }
        for (i in 2..30) {
            for (j in 1 until i) dp[i][j] = dp[i - 1][j - 1] + dp[i-1][j]
        }
        val (left, right) = readLine()!!.split(" ").map { it.toInt() }
        println(dp[right][left])
    }
}