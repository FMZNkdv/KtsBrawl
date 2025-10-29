package KtsBrawl.Stream

object ByteHelper {
    fun readDataReference(byteStream: Byte): List<Int>? {
        val result = mutableListOf<Int>()
        val v1 = byteStream.readVInt()
        result.add(v1)
        if (v1 == 0) {
            return null
        }
        val v2 = byteStream.readVInt()
        result.add(v2)
        return result
    }

    fun encodeIntList(byteStream: Byte, intList: List<Int>) {
        val length = intList.size
        byteStream.writeVInt(length)
        for (i in intList) {
            byteStream.writeVInt(i)
        }
    }
}
