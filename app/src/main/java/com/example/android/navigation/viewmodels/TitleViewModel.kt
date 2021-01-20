//Patrik Pölkki
//1901921
package com.example.android.navigation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.navigation.Game
import com.example.android.navigation.Word
import com.example.android.navigation.WordsNetworkRepository
import com.example.android.navigation.database.MyDatabase
import com.example.android.navigation.database.RoomWord
import kotlinx.coroutines.launch

//viewmodel for keeping data
class TitleViewModel(application: Application) : AndroidViewModel(application) {
    var words: Set<Word> = setOf()

    init {//coroutine
        viewModelScope.launch {
            //try/catch blok to handle excecptions
            try {//set words from json using WordsNetworkRepository().getWords()
                words = WordsNetworkRepository().getWords()
                //sets words to game object words and randomize them
                Game.words = words.shuffled().toSet()
                words.forEach {
                    try {
                        MyDatabase.getInstance(application.baseContext).wordDatabaseDao.insert(
                                RoomWord(it.text, it.lang)
                        )
                    } catch (e: Exception) {
                        Log.e("TAG", e.toString())
                    }
                }
                Log.i("TAG", words.toString())
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
        }
    }
}