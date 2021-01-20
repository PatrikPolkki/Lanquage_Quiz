//Patrik Pölkki
//1901921
package com.example.android.navigation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.navigation.database.MyDatabase
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val database = MyDatabase.getInstance(application).wordDatabaseDao

    fun updateGuesses(text: String) {
        viewModelScope.launch {
            try {
                database.updateGuesses(text)
                Log.i("TAG", database.get(text).toString())
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
        }
    }

    fun updateRightGuesses(text: String) {
        viewModelScope.launch {
            try {
                database.updateRightGuesses(text)
                Log.i("TAG", database.get(text).toString())
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
        }
    }

}