//Patrik Pölkki
//1901921
package com.example.android.navigation

import android.util.Log

class WordsNetworkRepository {
    suspend fun getWords() = setTranslation(WordsApi.retrofitService.getWords())


    private fun setTranslation(ws: Set<Word>): Set<Word> {
        return ws.apply {
            forEach {

                it.addTranslations(it.answers.toSet())
            }
        }
    }
}