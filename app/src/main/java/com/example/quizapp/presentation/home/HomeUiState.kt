data class HomeUiState(
    val username: String = "Guest",
    val liveQuizTitle: String? = null,
    val progressPercent: Int = 0,
    val ranking: Int = 0,
    val totalScore: Int = 0,
    val quizzesAttempted: Int = 0,
    val coins: Int = 0,
    val dailyChallenge: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
