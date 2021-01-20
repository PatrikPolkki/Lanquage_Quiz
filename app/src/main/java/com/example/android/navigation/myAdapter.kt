//Patrik Pölkki
//1901921
package com.example.android.navigation

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.navigation.database.MyDatabase
import com.example.android.navigation.database.RoomWord
import com.example.android.navigation.database.WordDatabaseDao

class MyAdapter(private val words: Set<Word>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    //variable for holding RoomWord data
    var list: List<RoomWord> = listOf()
        //tells recyclerView that data is changed
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false) as View

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //setting data for the viewholder
        holder.view.findViewById<TextView>(R.id.word).text = words.elementAt(position).text
        holder.view.findViewById<TextView>(R.id.total_guess).text =
                "Total guesses: ${list.find { it.text == words.elementAt(position).text }?.guesses ?: 0}"
        holder.view.findViewById<TextView>(R.id.right_guess).text =
                "Right guesses: ${list.find { it.text == words.elementAt(position).text }?.rightGuesses ?: 0}"

        Log.i("TAG", list.toString())
    }

    //RecyclerView needs to know how many items the adapter has for it to display
    override fun getItemCount() = words.size

}