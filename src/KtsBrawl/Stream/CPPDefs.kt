package KtsBrawl.Stream

object CPPDefs {
    fun rotl8(value: Int, count: Int): Int {
        return (value shl count) or (value ushr (8 - count))
    }

    fun rotl32(value: Int, count: Int): Int {
        return (value shl count) or (value ushr (32 - count))
    }

    fun rotr32(value: Int, count: Int): Int {
        return (value ushr count) or (value shl (32 - count))
    }

    fun __ROR4__(value: Int, count: Int): Int {
        return rotr32(value, count)
    }
}
