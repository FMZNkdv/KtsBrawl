package KtsBrawl

import java.net.ServerSocket
import KtsBrawl.Stream.Connection

fun main(args: Array<String>) {
    val serverAddress = "0.0.0.0"
    val serverPort = 9339
    
    val server = ServerSocket(serverPort)
    println("[S] Server started on $serverAddress:$serverPort")
    
    while (true) {
        try {
            val socket = server.accept()
            val address = socket.inetAddress.hostAddress
            println("[C] New connection: $address")
            
            val connection = Connection(socket, address)
            connection.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
