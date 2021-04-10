package com.example.blisstest.util.data.api.converter

import com.example.blisstest.util.data.model.Emoji
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class EmojiConverterFactory: JsonDeserializer<List<Emoji>> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): List<Emoji> {

        json ?: throw JsonParseException("Content is null")
        if (!json.isJsonObject) throw JsonParseException("Content not parsable")

        val jsonObject = json.asJsonObject

        val listOfEmojis = mutableListOf<Emoji>()
        jsonObject.entrySet().forEach { entry ->
            entry.value ?: return@forEach
            if (!entry.value.isJsonPrimitive) return@forEach

            val valuePrimitive = entry.value.asJsonPrimitive

            listOfEmojis.add(Emoji(entry.key, valuePrimitive.asString))
        }

        return listOfEmojis
    }

}