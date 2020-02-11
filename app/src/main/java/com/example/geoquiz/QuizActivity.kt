package com.example.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.true_false_response.*

private const val LOG_TAG = "448.QuizActivity"
private const val KEY_INDEX = "index"
private const val KEY_SCORE = "score"
private const val KEY_DID_CHEAT = "KEY_DID_CHEAT"
private const val REQUEST_CODE_CHEAT = 0

class QuizActivity : AppCompatActivity() {

    private lateinit var quizViewModel: QuizViewModel
    private lateinit var scoreTextView: TextView
    private lateinit var questionTextView: TextView
    private lateinit var responseFrame: FrameLayout
    private lateinit var trueFalseResponseLL: LinearLayout
    private lateinit var multipleChoiceResponseLL: LinearLayout
    private lateinit var textInputResponseLL: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate() called")
        setContentView(R.layout.activity_quiz)

        val factory = QuizViewModelFactory()
        quizViewModel = ViewModelProvider(this@QuizActivity, factory).get(QuizViewModel::class.java)

        quizViewModel.currentQuestionIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?:0
        quizViewModel.score = savedInstanceState?.getInt(KEY_SCORE, 0) ?:0
//        userDidCheat = savedInstanceState?.getBoolean(KEY_DID_CHEAT, false) ?:false


        scoreTextView = findViewById(R.id.score_text_view)
        questionTextView = findViewById(R.id.question_text_view)

        val trueButton = findViewById<Button>(R.id.true_button)
        val falseButton = findViewById<Button>(R.id.false_button)
//        trueButton.setOnClickListener { checkAnswer(true) }
//        falseButton.setOnClickListener { checkAnswer(false) }

        val prevButton = findViewById<Button>(R.id.prev_button)
        val nextButton = findViewById<Button>(R.id.next_button)
        prevButton.setOnClickListener { moveToQuestion(-1) }
        nextButton.setOnClickListener { moveToQuestion(1) }

        val cheatButton = findViewById<Button>(R.id.cheat_button)
//        cheatButton.setOnClickListener { launchCheat() }

        //get objects for swappable layouts, holding frame
        trueFalseResponseLL = layoutInflater.inflate(R.layout.true_false_response, null) as LinearLayout
        multipleChoiceResponseLL = layoutInflater.inflate(R.layout.multiple_choice_response, null) as LinearLayout
        textInputResponseLL = layoutInflater.inflate(R.layout.text_input_response, null) as LinearLayout
//        trueFalseResponse = findViewById(R.id.true_false_response)
        responseFrame = findViewById(R.id.response_holder)


//        cheatButton.setOnClickListener { swapViews(responseFrame, multipleChoiceResponseLL)}

        updateQuestion()
    }


//    private fun launchCheat() {
//        val intent = CheatActivity.createIntent(baseContext, quizViewModel.getCurrentAnswer())
//        startActivityForResult(intent, REQUEST_CODE_CHEAT)
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d(LOG_TAG, "onActivityResult() called (requestCode: %s, resultCode: %s)".format(requestCode, resultCode))

        if((resultCode == Activity.RESULT_OK) and (requestCode == REQUEST_CODE_CHEAT) and (data != null)){
            Log.d(LOG_TAG, "the user ${ when(CheatActivity.didUserCheat(data)){
                true -> "cheated"
                false -> "did not cheat"
            } }")
            quizViewModel.didCheatOnQuestion = CheatActivity.didUserCheat(data)

        }
    }

    //removes any views in parent and adds new view
    private fun swapViews(parent: FrameLayout, newView: LinearLayout) {
        parent.removeAllViews()
        parent.addView(newView)
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
        when(quizViewModel.currentQuestionType){
            Question.QuestionType.TRUE_FALSE -> swapViews(responseFrame, trueFalseResponseLL)
            Question.QuestionType.TEXT -> swapViews(responseFrame, textInputResponseLL)
            Question.QuestionType.MULTIPLE_CHOICE -> swapViews(responseFrame, multipleChoiceResponseLL)
        }
        setUpFunction()
    }

    private fun setUpFunction() {
        //set up to check answers
        when(quizViewModel.currentQuestionType) {
            Question.QuestionType.TRUE_FALSE -> {
                val trueButton = trueFalseResponseLL.findViewById<Button>(R.id.true_button)
                val falseButton = trueFalseResponseLL.findViewById<Button>(R.id.false_button)

                trueButton.setOnClickListener {
                    val answer = (quizViewModel.currentQuestion as TrueFalseQuestion).checkAnswer(true)
                    activityHandleBooleanAnswer(answer)
                }
                falseButton.setOnClickListener {
                    val answer = (quizViewModel.currentQuestion as TrueFalseQuestion).checkAnswer(false)
                    activityHandleBooleanAnswer(answer)
                }
            }
            Question.QuestionType.TEXT -> {
                val editText = textInputResponseLL.findViewById<EditText>(R.id.text_response_edit_text)
                val submitButton = textInputResponseLL.findViewById<Button>(R.id.submit_button)
                submitButton.setOnClickListener {
                    val answer = (quizViewModel.currentQuestion as FillBlankQuestion).checkAnswer(editText.text.toString())
                    activityHandleBooleanAnswer(answer)
                }
            }
            Question.QuestionType.MULTIPLE_CHOICE -> {
                val button1 = multipleChoiceResponseLL.findViewById<Button>(R.id.button1)
                val button2 = multipleChoiceResponseLL.findViewById<Button>(R.id.button2)
                val button3 = multipleChoiceResponseLL.findViewById<Button>(R.id.button3)
                val button4 = multipleChoiceResponseLL.findViewById<Button>(R.id.button4)
                var buttons = arrayListOf<Button>(button1, button2, button3, button4)
                for(button in buttons){
                    val buttonIndex = when(button.id){
                        R.id.button1 -> 0
                        R.id.button2 -> 1
                        R.id.button3 -> 2
                        R.id.button4 -> 3
                        else -> -1
                    }
                    val currentQuestion = (quizViewModel.currentQuestion as MultipleChoiceQuestion)
                    button.text = getString(currentQuestion.getResIdOfAnswerAtIndex(buttonIndex))
                    button.setOnClickListener {
                        val answer = currentQuestion.checkAnswer(buttonIndex)
                        activityHandleBooleanAnswer(answer)
                    }
                }
            }

        }
    }

    private fun activityHandleBooleanAnswer(answer: Boolean) {

        when(quizViewModel.currentQuestion.hasAnswered){
            false -> { //only show toast if not answered before
                val toastStringID: Int = when (answer){
                    true -> R.string.correct_toast
                    false -> R.string.incorrect_toast
                }
                //show toast
                Toast.makeText(baseContext, toastStringID, Toast.LENGTH_SHORT).show()
            }
        }
        quizViewModel.handleAnswer(answer)
        setCurrentScoreText()


    }


    private fun setCurrentScoreText() {
        scoreTextView.text = quizViewModel.currentScore.toString()
    }

//    private fun checkAnswer(answer: Boolean) {
//        if (quizViewModel.didCheatOnQuestion) {
//            Toast.makeText(baseContext, R.string.cheaters_no_prosper, Toast.LENGTH_SHORT).show()
//        }
//        else{
//            val toastStringID: Int = when (quizViewModel.isAnswerCorrect(answer)){
//                true -> R.string.correct_toast
//                false -> R.string.incorrect_toast
//            }
//            //show toast
//            Toast.makeText(baseContext, toastStringID, Toast.LENGTH_SHORT).show()
//
//            //update the score
//            setCurrentScoreText()
//        }
//    }

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
