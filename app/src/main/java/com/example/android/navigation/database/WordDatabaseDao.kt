//Patrik Pölkki
//1901921
package com.example.android.navigation.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

//data access object, which provides method for upating database
@Dao
interface WordDatabaseDao {

    @Insert
    //takes an instance of the Entity class RoomWord
    suspend fun insert(word: RoomWord)

    //return only one element from text matches to text
    @Query("SELECT * FROM words_table WHERE text = :text")
    //takes String text and returns nullable RoomWord
    suspend fun get(text: String): RoomWord?

    //return all columns in words_table
    @Query("SELECT * FROM words_table")
    //returns list of Roomword
    suspend fun getAll(): List<RoomWord>

    //plus guesses by one using update
    @Query("UPDATE words_table SET guesses = guesses + 1 WHERE text = :text")
    suspend fun updateGuesses(text: String)

    //plus rightguesses by one using update
    @Query("UPDATE words_table SET rightGuesses = rightGuesses + 1 WHERE text = :text")
    suspend fun updateRightGuesses(text: String)

}