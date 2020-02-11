package com.example.geoquiz

abstract class Question(_textResId: Int, type: QuestionType) {
    enum class QuestionType{
        TRUE_FALSE, MULTIPLE_CHOICE, TEXT
    }

    var questionType = type
    val textResId = _textResId
    var hasCheated = false
    var hasAnswered = false

//    abstract fun checkAnswer(): Boolean
}