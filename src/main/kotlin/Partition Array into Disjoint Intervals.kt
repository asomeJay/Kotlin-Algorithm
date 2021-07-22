import java.util.*
fun main(args:Array<String>) {
    val nums:IntArray = intArrayOf(1,1,1,0,6,12)
    println(`Partition Array into Disjoint Intervals`.partitionDisjoint(nums))
}

object `Partition Array into Disjoint Intervals` {
    fun partitionDisjoint(nums: IntArray): Int  {
        val left:MutableList<Int> = mutableListOf()
        val right:MutableList<Int> = nums.toMutableList()
        left.add(nums[0]); right.removeAt(0)

        for(i in 1 until nums.size - 1) {
            if (left.maxOrNull() ?: 0 > right.minOrNull() ?: 0) {
                left.add(nums[i])
                right.removeAt(0)
            }
            else
                break
        }

        return left.size
    }
}