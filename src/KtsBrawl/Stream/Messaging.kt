package KtsBrawl.Stream

import java.net.Socket
import java.io.OutputStream

object Messaging {
    fun writeHeader(message: PiranhaMessage, payloadLen: Int) {
        message.messageBuffer += message.getMessageType().toBigEndianByteArray(2)
        message.messageBuffer += payloadLen.toBigEndianByteArray(3)
        message.messageBuffer += message.messageVersion.toBigEndianByteArray(2)
    }

    fun readHeader(headerBytes: ByteArray): List<Int> {
        val messageType = headerBytes.copyOfRange(0, 2).toBigEndianInt()
        val payloadLen = headerBytes.copyOfRange(2, 5).toBigEndianInt()
        return listOf(messageType, payloadLen)
    }

    fun sendMessage(messageType: Int, fields: Map<String, Any>) {
        val message = LogicLaserMessageFactory.createMessageByType(messageType, byteArrayOf())
        if (message != null) {
            message.encode(fields)
            writeHeader(message, message.messagePayload.size)
            message.messageBuffer += message.messagePayload
            
            try {
                when (val socketObj = fields["Socket"]) {
                    is Socket -> {
                        socketObj.getOutputStream().write(message.messageBuffer)
                    }
                    is java.io.OutputStream -> {
                        socketObj.write(message.messageBuffer)
                    }
                    else -> {
                        val connection = fields["Connection"] as? Connection
                        connection?.socket?.getOutputStream()?.write(message.messageBuffer)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

private fun Int.toBigEndianByteArray(size: Int): ByteArray {
    val result = ByteArray(size)
    for (i in 0 until size) {
        result[size - 1 - i] = (this ushr (8 * i) and 0xFF).toByte()
    }
    return result
}

private fun ByteArray.toBigEndianInt(): Int {
    var result = 0
    for (i in indices) {
        result = result shl 8 or (this[i].toInt() and 0xFF)
    }
    return result
}

object MessageManager {
    fun receiveMessage(connection: Connection, messageType: Int, messagePayload: ByteArray) {
        val message = LogicLaserMessageFactory.createMessageByType(messageType, messagePayload)
        if (message != null) {
            try {
                if (message.isServerToClient()) {
                    message.encode()
                } else {
                    val fields = message.decode()
                    message.execute(connection, fields)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
