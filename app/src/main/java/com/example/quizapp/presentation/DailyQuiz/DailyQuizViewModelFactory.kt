package com.example.quizapp.presentation.DailyQuiz



import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.data.repository.DailyQuizRepository

class DailyQuizViewModelFactory(
    private val dailyQuizRepository: DailyQuizRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyQuizViewModel::class.java)) {
            return DailyQuizViewModel(dailyQuizRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
