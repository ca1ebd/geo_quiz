package com.example.geoquiz

class TrueFalseQuestion(_textResid: Int, _isAnswerTrue: Boolean): Question(_textResid) {
    val isAnswerTrue = _isAnswerTrue

    fun checkAnswer(test_answer: Boolean): Boolean{
        super.hasAnswered = true
        return test_answer == isAnswerTrue
    }
}