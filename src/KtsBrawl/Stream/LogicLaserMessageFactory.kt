package KtsBrawl.Stream

import KtsBrawl.Message.Login
import KtsBrawl.Message.LoginOk
import KtsBrawl.Message.OwnHomeData

object LogicLaserMessageFactory {
    val messagesList = mapOf(
        10101 to Login::class.java,
        20104 to LoginOk::class.java,
        24101 to OwnHomeData::class.java
    )

    fun messageExist(messageType: Int): Boolean {
        return messagesList.containsKey(messageType)
    }

    fun createMessageByType(messageType: Int, messagePayload: ByteArray): PiranhaMessage? {
        if (messageExist(messageType)) {
            val messageClass = messagesList[messageType]
            if (messageClass != null) {
                println("[S] $messageType Sent")
                return messageClass.getDeclaredConstructor(ByteArray::class.java).newInstance(messagePayload)
            }
        } else {}
        return null
    }
}
