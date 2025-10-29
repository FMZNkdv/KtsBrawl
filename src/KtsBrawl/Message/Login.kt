package KtsBrawl.Message

import KtsBrawl.Stream.Messaging
import KtsBrawl.Stream.PiranhaMessage

class Login(messageData: ByteArray) : PiranhaMessage(messageData) {
    init {
        messageVersion = 0
    }

    override fun encode(fields: Map<String, Any>) {}

    override fun decode(): Map<String, Any> {
        val fields = mutableMapOf<String, Any>()
        fields["AccountID"] = this.readLong()
        fields["PassToken"] = this.readString()
        fields["ClientMajor"] = this.readInt()
        fields["ClientMinor"] = this.readInt()
        fields["ClientBuild"] = this.readInt()
        fields["ResourceSha"] = this.readString()
        fields["Device"] = this.readString()
        fields["PreferredLanguage"] = this.readDataReference() ?: listOf(0, 0)
        fields["PreferredDeviceLanguage"] = this.readString()
        fields["OSVersion"] = this.readString()
        fields["isAndroid"] = this.readBoolean()
        fields["IMEI"] = this.readString()
        fields["AndroidID"] = this.readString()
        fields["isAdvertisingEnabled"] = this.readBoolean()
        fields["AppleIFV"] = this.readString()
        fields["RndKey"] = this.readInt()
        fields["AppStore"] = this.readVInt()
        fields["ClientVersion"] = this.readString()
        fields["TencentOpenId"] = this.readString()
        fields["TencentToken"] = this.readString()
        fields["TencentPlatform"] = this.readVInt()
        fields["DeviceVerifierResponse"] = this.readString()
        fields["AppLicensingSignature"] = this.readString()
        if (fields.containsKey("DeviceVerifierResponse")) {
            this.readString()
        }
        return fields
    }

    override fun execute(connection: Any, fields: Map<String, Any>) {
        val mutableFields = fields.toMutableMap()
        mutableFields["Connection"] = connection
        Messaging.sendMessage(20104, mutableFields)
        Messaging.sendMessage(24101, mutableFields)
    }

    override fun getMessageType(): Int {
        return 10101
    }
}
