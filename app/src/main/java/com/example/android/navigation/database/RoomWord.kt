//Patrik Pölkki
//1901921
package com.example.android.navigation.database

import androidx.room.Entity
import androidx.room.PrimaryKey

//name the table
@Entity(tableName = "words_table")
//Room database includes text, lang, guesses and rightguesses
data class RoomWord(
        //set text to primarykey
        @PrimaryKey val text: String,
        val lang: String,

        var guesses: Int = 0,
        var rightGuesses: Int = 0
)