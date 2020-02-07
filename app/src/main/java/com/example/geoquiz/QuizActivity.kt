package com.example.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.util.logging.Logger.global

private const val LOG_TAG = "448.QuizActivity"
private const val KEY_INDEX = "index"
private const val KEY_SCORE = "score"

class QuizActivity : AppCompatActivity() {

    private lateinit var quizViewModel: QuizViewModel
    private lateinit var scoreTextView: TextView
    private lateinit var questionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate() called")
        setContentView(R.layout.activity_quiz)

        val factory = QuizViewModelFactory()
        quizViewModel = ViewModelProvider(this@QuizActivity, factory).get(QuizViewModel::class.java)

        quizViewModel.currentQuestionIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?:0
        quizViewModel.score = savedInstanceState?.getInt(KEY_SCORE, 0) ?:0

        scoreTextView = findViewById(R.id.score_text_view)
        questionTextView = findViewById(R.id.question_text_view)

        val trueButton = findViewById<Button>(R.id.true_button)
        val falseButton = findViewById<Button>(R.id.false_button)
        trueButton.setOnClickListener { checkAnswer(true) }
        falseButton.setOnClickListener { checkAnswer(false) }

        val prevButton = findViewById<Button>(R.id.prev_button)
        val nextButton = findViewById<Button>(R.id.next_button)
        prevButton.setOnClickListener { moveToQuestion(-1) }
        nextButton.setOnClickListener { moveToQuestion(1) }

        val cheatButton = findViewById<Button>(R.id.cheat_button)
        cheatButton.setOnClickListener { launchCheat() }

        updateQuestion()
    }

    private fun launchCheat() {
        val intent = CheatActivity.createIntent(baseContext, quizViewModel.getCurrentAnswer())
        startActivity(intent)
    }



    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG, "onResume() called")
    }


    override fun onPause() {
        Log.d(LOG_TAG, "onPause() called")
        super.onPause()
    }

    override fun onStop() {
        Log.d(LOG_TAG, "onStop() called")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(LOG_TAG, "onDestroy() called")
        super.onDestroy()
    }

    private fun updateQuestion() {
        setCurrentScoreText()
        questionTextView.text = resources.getString(quizViewModel.currentQuestionTextId)
    }

    private fun setCurrentScoreText() {
        scoreTextView.text = quizViewModel.currentScore.toString()
    }

    private fun checkAnswer(answer: Boolean) {
        val toastStringID: Int = when (quizViewModel.isAnswerCorrect(answer)){
            true -> R.string.correct_toast
            false -> R.string.incorrect_toast
        }
        //show toast
        Toast.makeText(baseContext, toastStringID, Toast.LENGTH_SHORT).show()

        //update the score
        setCurrentScoreText()
    }

    private fun moveToQuestion(direction: Int) {
        when(direction>0){
            true -> quizViewModel.moveToNextQuestion()
            false -> quizViewModel.moveToPreviousQuestion()
        }
        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(LOG_TAG, "onSaveInstanceState() called")
        outState.putInt(KEY_INDEX, quizViewModel.currentQuestionIndex)
        outState.putInt(KEY_SCORE, quizViewModel.currentScore)
    }


}
