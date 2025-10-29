package KtsBrawl.Stream

import java.util.zip.Deflater

open class Byte(messageBuffer: ByteArray, unknown: Int = 0) : ChecksumEncoder() {
    var messagePayload: ByteArray = messageBuffer
    var bitoffset: Int = 0
    var offset: Int = 0
    var length: Int = messagePayload.size

    fun readBoolean(): Boolean {
        val bitoffset = this.bitoffset
        val offset = this.offset + (8 - bitoffset shr 3)
        this.offset = offset
        this.bitoffset = (bitoffset + 1) and 7
        return (1 shl (bitoffset and 31) and messagePayload[offset - 1].toInt()) != 0
    }

    fun readByte(): Int {
        this.bitoffset = 0
        val result = messagePayload[this.offset].toInt() and 0xFF
        this.offset += 1
        return result
    }

    fun readDataReference(): List<Int>? {
        return ByteHelper.readDataReference(this)
    }

    fun readBytes(length: Int, max: Int = 1000): ByteArray {
        this.bitoffset = 0
        if ((length.toLong() and 0x80000000L) != 0L) {
            if (length != -1) {
            }
        } else if (length <= max) {
            val result = messagePayload.copyOfRange(this.offset, this.offset + length)
            this.offset += length
            return result
        } else {
        }
        return byteArrayOf()
    }

    fun readString(max: Int = 900000): String {
        this.bitoffset = 0
        var length = (messagePayload[this.offset].toInt() and 0xFF) shl 24
        length += (messagePayload[this.offset + 1].toInt() and 0xFF) shl 16
        length += (messagePayload[this.offset + 2].toInt() and 0xFF) shl 8
        length += messagePayload[this.offset + 3].toInt() and 0xFF
        this.offset += 4
        if (length <= -1) {
            if (length != -1) {
            }
            return ""
        } else if (length > max) {
            return ""
        }
        val result = messagePayload.copyOfRange(this.offset, this.offset + length).toString(Charsets.UTF_8)
        this.offset += length
        return result
    }

    fun readInt(): Int {
        this.bitoffset = 0
        var result = (messagePayload[this.offset].toInt() and 0xFF) shl 24
        result += (messagePayload[this.offset + 1].toInt() and 0xFF) shl 16
        result += (messagePayload[this.offset + 2].toInt() and 0xFF) shl 8
        result += messagePayload[this.offset + 3].toInt() and 0xFF
        this.offset += 4
        return result
    }

    fun readLong(logicLong: LogicLong? = null): List<Int> {
        val long = logicLong ?: LogicLong(0, 0)
        long.decode(this)
        return listOf(long.high, long.low)
    }

    fun readVInt(): Int {
        var offset = this.offset
        this.bitoffset = 0
        val v4 = offset + 1
        this.offset = offset + 1
        var result = messagePayload[offset].toInt() and 0x3F
        if ((messagePayload[offset].toInt() and 0x40) != 0) {
            if ((messagePayload[offset].toInt() and 0x80) != 0) {
                this.offset = offset + 2
                val v7 = messagePayload[v4].toInt()
                result = (result and -57601) or ((v7 and 0x7F) shl 6)
            }
        }
        return result
    }

    override fun writeBoolean(value: Boolean) {
        super.writeBoolean(value)
        val tempBuf = messagePayload.toMutableList()
        if (this.bitoffset == 0) {
            tempBuf.add(0)
            this.offset += 1
        }
        if (value) {
            tempBuf[this.offset - 1] = (tempBuf[this.offset - 1].toInt() or (1 shl (this.bitoffset and 31))).toByte()
        }
        this.bitoffset = (this.bitoffset + 1) and 7
        messagePayload = tempBuf.toByteArray()
    }

    override fun writeByte(value: Int) {
        super.writeByte(value)
        this.bitoffset = 0
        val tempBuf = messagePayload.toMutableList()
        tempBuf.add((value and 0xFF).toByte())
        messagePayload = tempBuf.toByteArray()
        this.offset += 1
    }

    override fun writeInt(value: Int) {
        super.writeInt(value)
        writeIntToByteArray(value)
    }

    fun writeIntLittleEndian(value: Int) {
        this.bitoffset = 0
        val tempBuf = messagePayload.toMutableList()
        tempBuf.add((value and 0xFF).toByte())
        tempBuf.add((value shr 8 and 0xFF).toByte())
        tempBuf.add((value shr 16 and 0xFF).toByte())
        tempBuf.add((value shr 24 and 0xFF).toByte())
        messagePayload = tempBuf.toByteArray()
        this.offset += 4
    }

    fun writeIntToByteArray(value: Int) {
        this.bitoffset = 0
        val tempBuf = messagePayload.toMutableList()
        tempBuf.add((value shr 24 and 0xFF).toByte())
        tempBuf.add((value shr 16 and 0xFF).toByte())
        tempBuf.add((value shr 8 and 0xFF).toByte())
        tempBuf.add((value and 0xFF).toByte())
        messagePayload = tempBuf.toByteArray()
        this.offset += 4
    }

    fun writeLong(high: Int, low: Int) {
        writeIntToByteArray(high)
        writeIntToByteArray(low)
    }

    override fun writeString(value: String?) {
        super.writeString(value)
        this.bitoffset = 0
        if (value != null) {
            val strBytes = LogicStringUtil.getBytes(value)
            val strLength = LogicStringUtil.getByteLength(strBytes)
            if (strLength < 900001) {
                writeIntToByteArray(strLength)
                messagePayload += strBytes
                this.offset += strLength
            } else {
                writeIntToByteArray(-1)
            }
        } else {
            writeIntToByteArray(-1)
        }
    }

    override fun writeStringReference(value: String) {
        super.writeStringReference(value)
        this.bitoffset = 0
        val strBytes = LogicStringUtil.getBytes(value)
        val strLength = LogicStringUtil.getByteLength(strBytes)
        if (strLength < 900001) {
            writeIntToByteArray(strLength)
            messagePayload += strBytes
            this.offset += strLength
        } else {
            writeIntToByteArray(-1)
        }
    }

    override fun writeVInt(value: Int) {
        this.bitoffset = 0
        var final = byteArrayOf()
        if ((value.toLong() and 0x80000000L) != 0L) {
            if (value >= -63) {
                final += (value and 0x3F or 0x40).toByte()
                this.offset += 1
            } else {
                final += (value and 0x3F or 0xC0).toByte()
                final += ((value ushr 6) and 0x7F or 0x80).toByte()
                final += ((value ushr 13) and 0x7F or 0x80).toByte()
                final += ((value ushr 20) and 0x7F or 0x80).toByte()
                final += ((value ushr 27) and 0xF).toByte()
                this.offset += 5
            }
        } else {
            if (value <= 63) {
                final += (value and 0x3F).toByte()
                this.offset += 1
            } else {
                final += (value and 0x3F or 0x80).toByte()
                final += ((value ushr 6) and 0x7F or 0x80).toByte()
                final += ((value ushr 13) and 0x7F or 0x80).toByte()
                final += ((value ushr 20) and 0x7F or 0x80).toByte()
                final += ((value ushr 27) and 0xF).toByte()
                this.offset += 5
            }
        }
        messagePayload += final
    }

    override fun writeVLong(high: Int, low: Int) {
        super.writeVLong(high, low)
        this.bitoffset = 0
        writeVInt(high)
        writeVInt(low)
    }

    fun encodeIntList(intList: List<Int>) {
        ByteHelper.encodeIntList(this, intList)
    }

    fun writeCompressedString(data: String) {
        this.bitoffset = 0
        val compressedText = Deflater().run {
            setInput(data.toByteArray(Charsets.UTF_8))
            finish()
            val output = ByteArray(1024)
            val compressedLength = deflate(output)
            output.copyOf(compressedLength)
        }
        writeInt(compressedText.size + 4)
        writeIntLittleEndian(data.length)
        messagePayload += compressedText
    }
}
