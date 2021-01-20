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

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.android.navigation.Game
import com.example.android.navigation.fragments.GameFragmentDirections
import com.example.android.navigation.R
import com.example.android.navigation.Word
import com.example.android.navigation.database.MyDatabase
import com.example.android.navigation.database.WordDatabaseDao
import com.example.android.navigation.databinding.FragmentGameBinding
import com.example.android.navigation.viewmodels.GameViewModel

//GameFragment also includes View.onClickListener, so textviews works like buttons
class GameFragment : Fragment(), View.OnClickListener {

    private var mCurrentPosition: Int = 1

    //Game.words includes JSON
    private var mQuestionList = Game.words
    private var mSelectedOptionPosition: Int = 0
    private lateinit var currentWord: Word
    private var mCorrectAnswers: Int = 0
    private var numquestions = mQuestionList.size
    private lateinit var selectedAnswerView: TextView
    private lateinit var options: ArrayList<TextView>
    private lateinit var shuffled: List<String>

    private lateinit var binding: FragmentGameBinding

    private lateinit var viewModel: GameViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater, R.layout.fragment_game, container, false)

        // Shuffles the questions and sets the question index to the first question.

        // Bind this fragment class to the layout


        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.game = this

        setQuestion()

        binding.submitButton.setOnClickListener(this)


        return binding.root
    }

    //
    private fun setQuestion() {

        currentWord = mQuestionList.elementAt(mCurrentPosition - 1)
        setImage()

        binding.optionOne.setOnClickListener(this)
        binding.optionTwo.setOnClickListener(this)
        binding.optionThree.setOnClickListener(this)
        binding.optionFour.setOnClickListener(this)

        defaultOptionView()

        viewModel.updateGuesses(currentWord.text)

        if (mCurrentPosition == mQuestionList.size) {
            binding.submitButton.text = "FINISH"
        } else {
            binding.submitButton.text = "SUBMIT"
        }

        binding.progressbar.progress = mCurrentPosition
        binding.progress.text = "$mCurrentPosition" + "/" + binding.progressbar.max

        binding.questionText.text = "What is ${currentWord.text} in finnish"

        shuffled = currentWord.translations.shuffled()

        binding.optionOne.text = shuffled[0]
        binding.optionTwo.text = shuffled[1]
        binding.optionThree.text = shuffled[2]
        binding.optionFour.text = shuffled[3]
    }

    private fun setImage() {
        val imageUri = currentWord.image.toUri().buildUpon().scheme("https").build()
        Glide.with(binding.questionImage.context)
                .load(imageUri)
                .into(binding.questionImage)
    }

    //add defaultOptionView to answers options
    private fun defaultOptionView() {
        options = ArrayList()
        options.add(0, binding.optionOne)
        options.add(1, binding.optionTwo)
        options.add(2, binding.optionThree)
        options.add(3, binding.optionFour)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = context?.let { ContextCompat.getDrawable(it, R.drawable.option_default) }
        }

    }

    //add selectedOptionView to chosen answer
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionView()
        mSelectedOptionPosition = selectedOptionNum
        selectedAnswerView = tv

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = context?.let { ContextCompat.getDrawable(it, R.drawable.selected_option_default) }
    }

    //define what happens when textview is pressed
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.option_one -> {
                selectedOptionView(binding.optionOne, 1)
            }
            R.id.option_two -> {
                selectedOptionView(binding.optionTwo, 2)
            }
            R.id.option_three -> {
                selectedOptionView(binding.optionThree, 3)
            }
            R.id.option_four -> {
                selectedOptionView(binding.optionFour, 4)
            }
            R.id.submitButton -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            view?.findNavController()?.navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numquestions, mCorrectAnswers))
                        }
                    }
                } else {
                    binding.optionOne.setOnClickListener(null)
                    binding.optionTwo.setOnClickListener(null)
                    binding.optionThree.setOnClickListener(null)
                    binding.optionFour.setOnClickListener(null)

                    //if answer is wrong, chosen textview is red and right one is green
                    if (currentWord.editDistance(shuffled[mSelectedOptionPosition - 1]) != 0) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_answer)

                        options.forEachIndexed { index, textView ->
                            if (currentWord.editDistance(textView.text.toString()) == 0) {
                                answerView(index + 1, R.drawable.correct_answer)
                            }
                        }
                        //if answer is not wrong textview is green and correctanswers++
                    } else {
                        mCorrectAnswers++
                        answerView(mSelectedOptionPosition, R.drawable.correct_answer)
                        viewModel.updateRightGuesses(currentWord.text)
                    }

                    if (mCurrentPosition == mQuestionList.size) {
                        binding.submitButton.text = "FINISH"
                    } else {
                        binding.submitButton.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                binding.optionOne.background = context?.let { ContextCompat.getDrawable(it, drawableView) }
            }
            2 -> {
                binding.optionTwo.background = context?.let { ContextCompat.getDrawable(it, drawableView) }
            }
            3 -> {
                binding.optionThree.background = context?.let { ContextCompat.getDrawable(it, drawableView) }
            }
            4 -> {
                binding.optionFour.background = context?.let { ContextCompat.getDrawable(it, drawableView) }
            }
        }
    }


}
