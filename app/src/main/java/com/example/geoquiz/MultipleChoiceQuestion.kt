package com.example.geoquiz

class MultipleChoiceQuestion(_textResid: Int, _answerListRes: ArrayList<Int>, _correctIndex: Int): Question(_textResid, QuestionType.MULTIPLE_CHOICE) {
    val answerList = _answerListRes
    val correctIndex = _correctIndex

    fun checkAnswer(index: Int): Boolean {
//        super.hasAnswered = true
        return index == correctIndex
    }

    fun getResIdOfAnswerAtIndex(index: Int): Int {
        return answerList[index]
    }
}