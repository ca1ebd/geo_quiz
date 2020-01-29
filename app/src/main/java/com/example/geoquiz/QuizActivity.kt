package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.logging.Logger.global

class QuizActivity : AppCompatActivity() {

    private lateinit var scoreTextView: TextView
    private lateinit var questionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

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

        updateQuestion()
    }

    private fun updateQuestion() {
        setCurrentScoreText()
        questionTextView.text = resources.getString(QuizMaster.currentQuestionTextId)
    }

    private fun setCurrentScoreText() {
        scoreTextView.text = QuizMaster.currentScore.toString()
    }

    private fun checkAnswer(answer: Boolean) {
        val toastStringID: Int = when (QuizMaster.isAnswerCorrect(answer)){
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
            true -> QuizMaster.moveToNextQuestion()
            false -> QuizMaster.moveToPreviousQuestion()
        }
        updateQuestion()
    }

}
