package com.example.quizapp.presentation.quiz

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.domain.models.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.BufferedReader

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    val currentQuestion: StateFlow<Question?> =
        MutableStateFlow(null)

    private val _timeLeft = MutableStateFlow(30)
    val timeLeft: StateFlow<Int> = _timeLeft

    init {
        loadQuestionsFromAssets()
    }

    private fun loadQuestionsFromAssets() {
        viewModelScope.launch(Dispatchers.IO) {
            val context = getApplication<Application>().applicationContext
            val inputStream = context.assets.open("questions.json")
            val bufferedReader = BufferedReader(inputStream.reader())
            val jsonString = bufferedReader.use { it.readText() }

            val jsonArray = JSONArray(jsonString)
            val loadedQuestions = mutableListOf<Question>()

            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val id = obj.getInt("id")
                val questionText = obj.getString("question")
                val optionsArray = obj.getJSONArray("options")
                val options = mutableListOf<String>()
                for (j in 0 until optionsArray.length()) {
                    options.add(optionsArray.getString(j))
                }
                val answerIndex = obj.getInt("answerIndex")

                loadedQuestions.add(
                    Question(
                        id = id,
                        question = questionText,
                        options = options,
                        answerIndex = answerIndex
                    )
                )
            }

            _questions.value = loadedQuestions
            if (loadedQuestions.isNotEmpty()) {
                (currentQuestion as MutableStateFlow).value = loadedQuestions[0]
            }
        }
    }

    fun nextQuestion() {
        val list = _questions.value
        if (list.isNotEmpty() && _currentIndex.value < list.size - 1) {
            _currentIndex.value += 1
            (currentQuestion as MutableStateFlow).value = list[_currentIndex.value]
            resetTimer()
        }
    }

     fun resetTimer() {
        _timeLeft.value = 30
        // TODO: add actual countdown logic if needed
    }

    fun tick() {
        val current = _timeLeft.value
        if (current > 0) {
            _timeLeft.value = current - 1
        } else {
            nextQuestion() // move to next question when time runs out
        }
    }

}
