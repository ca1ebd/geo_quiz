package com.example.geoquiz

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
//    private val theQuestion = Question(R.string.question1, false)
    private val questionBank: MutableList<Question> = mutableListOf()
    public var score = 0
    public var currentQuestionIndex: Int = 0

    init {
        questionBank.add(TrueFalseQuestion(R.string.TF_question1, false))
        questionBank.add(MultipleChoiceQuestion(
            R.string.MC_question4,
            arrayListOf<Int>(
                R.string.MC_question4_a1,
                R.string.MC_question4_a2,
                R.string.MC_question4_a3,
                R.string.MC_question4_a4),
            1))
        questionBank.add(TrueFalseQuestion(R.string.TF_question2, true))
        questionBank.add(TrueFalseQuestion(R.string.TF_question3, false))
        questionBank.add(FillBlankQuestion(R.string.FB_question5, "Denver"))
    }

    private val currentQuestion: Question
        //return the question from the list at the current index
        get() = questionBank[currentQuestionIndex]

    val currentQuestionTextId: Int
        get() = currentQuestion.textResId
//    val currentQuestionAnswer: Boolean
//        get() = currentQuestion.isAnswerTrue
    val currentScore: Int
        get() = score
    var didCheatOnQuestion: Boolean
        get() = currentQuestion.hasCheated
        set(value) {currentQuestion.hasCheated = value}


//    fun isAnswerCorrect(check: Boolean): Boolean {
//        if(check == currentQuestionAnswer){
//            score++
//            return true
//        }
//        return false
//    }

//    fun getCurrentAnswer() = currentQuestion.isAnswerTrue

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