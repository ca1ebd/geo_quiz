package com.example.geoquiz

abstract class Question(_textResId: Int) {
    enum class QuestionType{
        TRUE_FALSE, MULTIPLE_CHOICE, TEXT
    }

    val textResId = _textResId
    var hasCheated = false
    var hasAnswered = false

//    abstract fun checkAnswer(): Boolean
}