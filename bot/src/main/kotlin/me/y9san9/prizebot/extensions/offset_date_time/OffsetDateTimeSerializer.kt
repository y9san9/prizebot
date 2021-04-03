@file:Suppress("EXPERIMENTAL_API_USAGE")

package me.y9san9.prizebot.extensions.offset_date_time

import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.OffsetDateTime


@Serializer(forClass = OffsetDateTime::class)
class OffsetDateTimeSerializer  {
    override val descriptor = PrimitiveSerialDescriptor (
        serialName = "offset_date_time", PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: OffsetDateTime) =
        encoder.encodeString(value.toString())

    override fun deserialize(decoder: Decoder): OffsetDateTime =
        OffsetDateTime.parse(decoder.decodeString())
}
