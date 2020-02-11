package com.example.geoquiz

abstract class Question(_textResId: Int) {
    val textResId = _textResId
    var hasCheated = false
    var hasAnswered = false

//    abstract fun checkAnswer(): Boolean
}