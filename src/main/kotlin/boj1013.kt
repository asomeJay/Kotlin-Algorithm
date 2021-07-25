fun main(args:Array<String>) {
    val testCase = readLine()!!.toInt()
    val regex1 = Regex("(100+1+|01)+")
    for (i in 1..testCase){
        println(if (regex1.matches(readLine()!!.toString())) "YES" else "NO")
    }
}