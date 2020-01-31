package com.example.geoquiz

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
//    private val theQuestion = Question(R.string.question1, false)
    private val questionBank: MutableList<Question> = mutableListOf()
    private var score = 0
    private var currentQuestionIndex: Int = 0

    init {
        questionBank.add(Question(R.string.question1, false))
        questionBank.add(Question(R.string.question2, true))
        questionBank.add(Question(R.string.question3, false))
    }

    private val currentQuestion: Question
        //return the question from the list at the current index
        get() = questionBank[currentQuestionIndex]

    val currentQuestionTextId: Int
        get() = currentQuestion.textResId
    val currentQuestionAnswer: Boolean
        get() = currentQuestion.isAnswerTrue
    val currentScore: Int
        get() = score

    fun isAnswerCorrect(check: Boolean): Boolean {
        if(check == currentQuestionAnswer){
            score++
            return true
        }
        return false
    }

    fun moveToNextQuestion() {
        currentQuestionIndex++
        if (currentQuestionIndex == questionBank.size){
            //wrap around to first question
            currentQuestionIndex = 0
        }
    }

    fun moveToPreviousQuestion() {
        if (currentQuestionIndex == 0) {
            //wrap around to last question
            currentQuestionIndex = questionBank.size - 1
        }
        else{
            currentQuestionIndex--
        }
    }

}