package com.example.blisstest.util.data.api.converter;

import com.example.blisstest.util.data.model.Emoji;
import com.example.blisstest.util.data.model.Emoji;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Fazendo com Kotlin por algum motivo ele retorna o Tipo como List < extends Emoji> e nao com o
 * tipo especifo assim nao sendo chamado apos a resposta da API
 */
public class TypeTokenCreator {

    public static Type createListEmoji() {
        return new TypeToken<List<Emoji>>() {}.getType();
    }

}
