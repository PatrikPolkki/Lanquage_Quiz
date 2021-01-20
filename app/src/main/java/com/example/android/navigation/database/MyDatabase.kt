//Patrik Pölkki
//1901921
package com.example.android.navigation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RoomWord::class], version = 1, exportSchema = false)
//abstract class that extends RoomDatabase
abstract class MyDatabase : RoomDatabase() {
    //declaring abstract value returns WordDatabaseDao
    abstract val wordDatabaseDao: WordDatabaseDao


    companion object {
        //make sure that value of instance is always up to date.
        //changes made by one thread to instance are visible to all other thread immediately
        @Volatile
        //declares a private nullable variable INSTANCE for the database and initialize it to null
        //The INSTANCE variable will keep a reference to the database, once one has been created.
        //  This helps you avoid repeatedly opening connections to the database, which is expensive.
        private var INSTANCE: MyDatabase? = null

        //needs Context for database builder
        //creates an instance of the database if the database doesn't exist
        fun getInstance(context: Context): MyDatabase {
            //only one thread of execution at a time can enter this block of code,
            // which makes sure the database only gets initialized once.
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            MyDatabase::class.java,
                            "word_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}