package KtsBrawl.Stream

import java.net.Socket
import java.io.InputStream

class Connection(val socket: Socket, private val address: String) : Thread() {
    private var timeout: Long = System.currentTimeMillis()

    private fun recv(n: Int): ByteArray {
        val input: InputStream = socket.getInputStream()
        val data = ByteArray(n)
        var received = 0
        
        while (received < n) {
            val packet = input.read(data, received, n - received)
            if (packet == -1) {
                return byteArrayOf()
            }
            received += packet
        }
        return data
    }

    override fun run() {
        try {
            while (true) {
                sleep(1)
                val messageHeader = recv(7)
                if (messageHeader.size >= 7) {
                    val headerData = Messaging.readHeader(messageHeader)
                    timeout = System.currentTimeMillis()
                    val packetPayload = recv(headerData[1])
                    val packetID = headerData[0]
                    MessageManager.receiveMessage(this, packetID, packetPayload)
                }
            }
        } catch (e: Exception) {
            socket.close()
            println("[C] Connection closed: $address")
        }
    }
}
