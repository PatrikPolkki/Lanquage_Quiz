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

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.fragments.GameWonFragmentArgs
import com.example.android.navigation.fragments.GameWonFragmentDirections
import com.example.android.navigation.R
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    private lateinit var binding: FragmentGameWonBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)

        binding.finish.setOnClickListener { view: View ->

            binding.finish.setOnClickListener {
                view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToTitleFragment())
            }
        }
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        Log.i("gameover", args.toString())
        //Set score texxt
        binding.score.text = "you scored  ${args.numCorrect} out of ${args.numQuestions}"
        //set comment of your success
        if (args.numCorrect < 4) {
            binding.congratulations.text = "EPIC FAIL"
            binding.congratulations2.text = "better luck next time"
        }
        if (args.numCorrect > 7) {
            binding.congratulations.text = "GREAT JOB"
            binding.congratulations2.text = "you answered most of the questions correctly"
        }
        if (args.numCorrect == 10) {
            binding.congratulations.text = "HEY\n CONGRATULATIONS"
            binding.congratulations2.text = "you answered all the questions correctly"
        }
        if (args.numCorrect in 4..7) {
            binding.congratulations.text = "GOOD JOB"
            binding.congratulations2.text = "but you can do better"
        }

        setHasOptionsMenu(true)
        return binding.root
    }


    // Creating our Share Intent
    private fun getShareIntent(): Intent {
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
        return shareIntent
    }

    // Starting an Activity with our new Intent
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    // Showing the Share Menu Item Dynamically
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)
        // check if the activity resolves
        if (null == getShareIntent().resolveActivity(requireActivity().packageManager)) {
            // hide the menu item if it doesn't resolve
            menu.findItem(R.id.share)?.isVisible = false
        }
    }

    // Sharing from the Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}
