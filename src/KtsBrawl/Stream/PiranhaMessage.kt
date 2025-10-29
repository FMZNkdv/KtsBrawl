package KtsBrawl.Stream

open class PiranhaMessage(messageData: ByteArray) : Byte(messageData) {
    var messageBuffer: ByteArray = messageData
    var fields: Map<String, Any> = mapOf()
    var messageVersion: Int = 1

    open fun decode(): Map<String, Any> {
        return mapOf()
    }

    open fun encode(fields: Map<String, Any> = mapOf()) {}

    open fun execute(connection: Any, fields: Map<String, Any>) {}

    open fun getMessageType(): Int {
        return 0
    }

    fun isServerToClient(): Boolean {
        val messageType = getMessageType()
        return (messageType in 20000..29999) || messageType == 40000
    }
}
