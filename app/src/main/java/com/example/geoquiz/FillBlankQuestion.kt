package com.example.geoquiz

import android.provider.Settings.Global.getString

class FillBlankQuestion(_textResid: Int, _answer: String): Question(_textResid, QuestionType.TEXT) {
    val answer = _answer

    fun checkAnswer(testAnswer: String): Boolean{
//        super.hasAnswered = true
        return testAnswer.toLowerCase()== answer.toLowerCase()
    }
}