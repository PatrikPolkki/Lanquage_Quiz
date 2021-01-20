//Patrik Pölkki
//1901921
package com.example.android.navigation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.navigation.database.MyDatabase
import com.example.android.navigation.database.RoomWord
import kotlinx.coroutines.launch

class AboutViewModel(application: Application) : AndroidViewModel(application) {
    private val database = MyDatabase.getInstance(application).wordDatabaseDao
    lateinit var databaseWords: List<RoomWord>

    fun getAll(onCompleted: () -> Unit) {
        viewModelScope.launch {
            try {
                databaseWords = database.getAll()
                Log.i("TAG", databaseWords.toString())
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
        }.invokeOnCompletion {
            onCompleted()
        }
    }

}