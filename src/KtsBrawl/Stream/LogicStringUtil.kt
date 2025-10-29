package KtsBrawl.Stream

object LogicStringUtil {
    fun getBytes(string: String): ByteArray {
        return string.toByteArray(Charsets.UTF_8)
    }

    fun getByteLength(bytes: ByteArray): Int {
        return bytes.size
    }
}
