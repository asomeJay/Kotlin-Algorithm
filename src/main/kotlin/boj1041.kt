fun calculateThree(list: List<Long>): List<Long> {
    val acc = mutableListOf<Long>()

    acc.add(list[0] + list[1] + list[2])
    acc.add(list[0] + list[1] + list[3])

    acc.add(list[0] + list[2] + list[4])
    acc.add(list[0] + list[3] + list[4])

    acc.add(list[1] + list[3] + list[5])
    acc.add(list[1] + list[2] + list[5])

    acc.add(list[2] + list[4] + list[5])
    acc.add(list[3] + list[4] + list[5])

    return acc.sorted()
}

fun calculateTwo(list: List<Long>): List<Long> {
    val acc = mutableListOf<Long>()

    acc.add(list[0] + list[1])
    acc.add(list[0] + list[2])
    acc.add(list[0] + list[3])
    acc.add(list[0] + list[4])

    acc.add(list[1] + list[2])
    acc.add(list[1] + list[3])
    acc.add(list[1] + list[5])

    acc.add(list[2] + list[4])
    acc.add(list[2] + list[5])

    acc.add(list[3] + list[4])
    acc.add(list[3] + list[5])

    acc.add(list[4] + list[5])

    return acc.sorted()
}


fun calculateDimension(N: Long): MutableList<Long> {
    val list: MutableList<Long> = MutableList<Long>(4) { 0 }
    list[1] = ((N - 2) * (N - 1) * 4) + ((N - 2) * (N - 2))
    list[2] = (N - 1) * 4 + (N - 2) * 4
    list[3] = 4

    return list
}

fun main() {
    val N = readLine()!!.toLong()
    val split = readLine()!!.split(" ").map { it.toLong() }

    val list = MutableList<List<Long>>(4) { mutableListOf() }

    list[1] = split.sorted()
    list[2] = calculateTwo(split)
    list[3] = calculateThree(split)

    val result =
        if (N == 1L) split.sorted().sum() - split.sorted()[5]
        else {
            val dimension = calculateDimension(N)
            dimension[1] * list[1][0] + dimension[2] * list[2][0] + dimension[3] * list[3][0]
        }

    print(result)
}