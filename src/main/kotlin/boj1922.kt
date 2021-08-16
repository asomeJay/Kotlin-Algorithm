object boj1922 {
    data class Edge(val src: Int, val dst: Int, val weight: Int)

    @JvmStatic
    fun main(args: Array<String>) {
        val nCom: Int = readLine()!!.toInt()
        val mLine: Int = readLine()!!.toInt()

        val edges: MutableCollection<Edge> = mutableListOf()
        val parent: Array<Int> = Array<Int>(nCom + 1) { i -> i  }
        repeat(mLine) {
            val (src, dst, weight) = readLine()!!.split(" ").map { it.toInt() }
            edges.add(Edge(src, dst, weight))
        }
        val sortedBy: List<Edge> = edges.sortedBy { it.weight }

        println(kruskal(sortedBy, parent))
    }

    private fun kruskal(edges: List<Edge>, parent: Array<Int>):Int {
        var result:Int = 0
        for (edge in edges) {
            if (!isFamily(parent, edge.src, edge.dst)) {
                result += edge.weight
                unionParent(parent, edge.src, edge.dst)
            }
        }
        return result
    }

    private fun isFamily(lineage: Array<Int>, a: Int, b: Int): Boolean {
        return getParent(lineage, a) == getParent(lineage, b)
    }

    private fun unionParent(lineage: Array<Int>, target_a: Int, target_b: Int): Unit {
        val a = getParent(lineage, target_a)
        val b = getParent(lineage, target_b)

        if (a < b) lineage[b] = a
        else lineage[a] = b

    }

    private fun getParent(lineage: Array<Int>, x: Int): Int {
        if (lineage[x] != x) lineage[x] = getParent(lineage, lineage[x])
        return lineage[x]
    }
}


