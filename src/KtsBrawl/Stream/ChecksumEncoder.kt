package KtsBrawl.Stream

open class ChecksumEncoder {
    var checksum: Int = 0
    var checksum2: Int = 0
    var checksumEnabled: Boolean = true

    open fun writeBoolean(value: Boolean) {
        val integer = if (value) 13 else 7
        checksum = integer + CPPDefs.__ROR4__(checksum, 31)
    }

    open fun writeByte(value: Int) {
        checksum = CPPDefs.__ROR4__(checksum, 31) + value + 11
    }

    open fun writeInt(value: Int) {
        checksum = CPPDefs.__ROR4__(checksum, 31) + value + 9
    }

    open fun writeString(value: String?) {
        val checksum = CPPDefs.__ROR4__(checksum, 31)
        this.checksum = checksum + 27
    }

    open fun writeStringReference(value: String) {
        checksum = value.length + CPPDefs.__ROR4__(checksum, 31) + 38
    }

    open fun writeVInt(value: Int) {
        checksum = value + CPPDefs.__ROR4__(checksum, 31) + 33
    }

    open fun writeVLong(high: Int, low: Int) {
        checksum = low + CPPDefs.__ROR4__(high + CPPDefs.__ROR4__(checksum, 31) + 65, 31) + 88
    }
}
