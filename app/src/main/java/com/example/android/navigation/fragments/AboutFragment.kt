/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//Patrik Pölkki
//1901921
package com.example.android.navigation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.navigation.Game
import com.example.android.navigation.MyAdapter
import com.example.android.navigation.R
import com.example.android.navigation.database.MyDatabase
import com.example.android.navigation.databinding.FragmentAboutBinding
import com.example.android.navigation.viewmodels.AboutViewModel

//
class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    private lateinit var viewModel: AboutViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProvider(this).get(AboutViewModel::class.java)

        binding = DataBindingUtil.inflate<FragmentAboutBinding>(
                inflater, R.layout.fragment_about, container, false)
        // Inflate the layout for this fragment
        val viewManager = LinearLayoutManager(context)
        val viewAdapter = MyAdapter(Game.words)

        viewModel.getAll { updateAdapter() }

        binding.recylerView.apply {
            layoutManager = viewManager

            adapter = viewAdapter
        }

        return binding.root
    }

    private fun updateAdapter() {
        (binding.recylerView.adapter as MyAdapter).list = viewModel.databaseWords
    }
}
