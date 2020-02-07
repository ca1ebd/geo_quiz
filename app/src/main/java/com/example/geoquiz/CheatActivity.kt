package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_cheat.*
import org.w3c.dom.Text

private const val LOG_TAG = "448.CheatActivity"
private const val EXTRA_ANSWER_IS_TRUE = "CORRECT_ANSWER_KEY"
private const val EXTRA_HAS_CHEATED = "HAS_CHEATED_KEY"



class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView

    companion object {

        fun createIntent(context: Context, isAnswerTrue: Boolean): Intent {
            val intent = Intent(context, CheatActivity::class.java)

            //put boolean extra on intent
            intent.putExtra(EXTRA_ANSWER_IS_TRUE, isAnswerTrue)
            return intent
        }

        fun didUserCheat(intent: Intent?): Boolean { return intent!!.getBooleanExtra(EXTRA_HAS_CHEATED, false) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        val isAnswerTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        answerTextView = findViewById(R.id.answer_text_view)
        answerTextView.text = when(isAnswerTrue){
            true -> getString(R.string.trueString)
            false -> getString(R.string.falseString)
        }

        val cheatButton = findViewById<Button>(R.id.show_answer_button)
        cheatButton.setOnClickListener {
            showAnswer()
            setCheated()
        }

    }

    private fun showAnswer(){
        answerTextView.visibility = View.VISIBLE
    }

    private fun setCheated() {
        val intent = Intent(baseContext, QuizActivity::class.java)
        intent.putExtra(EXTRA_HAS_CHEATED, true)
        setResult(Activity.RESULT_OK, intent)
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

}
