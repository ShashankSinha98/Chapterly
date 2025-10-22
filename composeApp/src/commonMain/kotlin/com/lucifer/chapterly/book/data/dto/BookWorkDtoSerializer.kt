package com.lucifer.chapterly.book.data.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

/**
 *  Description response of API can be String or Object with 'value' field.
 *  Custom serializer to handle both cases.
 * */
object BookWorkDtoSerializer: KSerializer<BookWorkDto> {

    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor(
            BookWorkDto::class.simpleName!!
        ) {
            element<String?>("description")
        }

    // Not used in our case as we only deserialize description from API
    override fun serialize(
        encoder: Encoder,
        value: BookWorkDto
    ) = encoder.encodeStructure(
        descriptor
    ) {
        value.description?.let {
            encodeStringElement(descriptor, 0, it)
        }
    }

    // Custom deserialization logic to handle both String and Object cases for description
    override fun deserialize(decoder: Decoder): BookWorkDto = decoder.decodeStructure(descriptor) {
        var description: String?= null

        while (true) {
            when(val index = decodeElementIndex(descriptor)) {
                // Index for 'description' field
                0 -> {
                    val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException(
                        "This decoder only works with JSON"
                    )

                    val element = jsonDecoder.decodeJsonElement()
                    description = if(element is JsonObject) { // Description is an object
                        decoder.json.decodeFromJsonElement<DescriptionDto>(
                            element = element,
                            deserializer = DescriptionDto.serializer()
                        ).value
                    } else if(element is JsonPrimitive && element.isString) { // Description is a string
                        element.content
                    } else null
                }
                CompositeDecoder.DECODE_DONE -> break
                else -> throw SerializationException("Unknown index $index")
            }
        }
        return@decodeStructure BookWorkDto(description)
    }
}