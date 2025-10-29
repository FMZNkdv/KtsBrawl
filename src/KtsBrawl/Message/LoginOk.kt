package KtsBrawl.Message

import KtsBrawl.Stream.PiranhaMessage

class LoginOk(messageData: ByteArray) : PiranhaMessage(messageData) {
    init {
        messageVersion = 1
    }

    override fun encode(fields: Map<String, Any>) {
        this.writeLong(0, 1)
        this.writeLong(0, 1)
        this.writeString("")
        this.writeString(null)
        this.writeString(null)
        this.writeInt(55)
        this.writeInt(211)
        this.writeInt(1)
        this.writeString("dev")
        this.writeInt(0)
        this.writeInt(0)
        this.writeInt(0)
        this.writeString(null)
        this.writeString(null)
        this.writeString(null)
        this.writeInt(0)
        this.writeString(null)
        this.writeString("RU")
        this.writeString(null)
        this.writeInt(0)
        this.writeString(null)
        this.writeInt(2)
        this.writeString("https://game-assets.brawlstarsgame.com")
        this.writeString("http://a678dbc1c015a893c9fd-4e8cc3b1ad3a3c940c504815caefa967.r87.cf2.rackcdn.com")
        this.writeInt(2)
        this.writeString("https://event-assets.brawlstars.com")
        this.writeString("https://24b999e6da07674e22b0-8209975788a0f2469e68e84405ae4fcf.ssl.cf2.rackcdn.com/event-assets")
        this.writeVInt(0)
        this.writeCompressedString("")
        this.writeBoolean(true)
        this.writeBoolean(false)
        this.writeString(null)
        this.writeString(null)
        this.writeString(null)
        this.writeString("https://play.google.com/store/apps/details?id=com.supercell.brawlstars")
        this.writeString(null)
        this.writeBoolean(false)
        this.writeBoolean(false)
        this.writeString(null)
        this.writeBoolean(false)
        this.writeString(null)
        this.writeBoolean(false)
        this.writeString(null)
        this.writeBoolean(false)
        this.writeString(null)
    }

    override fun decode(): Map<String, Any> {
        return mapOf()
    }

    override fun execute(connection: Any, fields: Map<String, Any>) {
        // No execution needed for LoginOk
    }

    override fun getMessageType(): Int {
        return 20104
    }
}
