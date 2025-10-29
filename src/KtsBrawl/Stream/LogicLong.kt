package KtsBrawl.Stream

class LogicLong(var high: Int = 0, var low: Int = 0) {
    fun decode(bytestream: Byte) {
        high = bytestream.readInt()
        low = bytestream.readInt()
    }
}
