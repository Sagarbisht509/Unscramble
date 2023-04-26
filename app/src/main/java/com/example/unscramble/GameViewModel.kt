package com.example.unscramble

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {

    private var _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> get() = _uiState.asStateFlow()

    private var usedWords: MutableSet<String> = mutableSetOf()

    var userGuess by mutableStateOf("")

    private lateinit var currentWord: String

    private fun getRandomWord(): String {
        currentWord = allWords.random()
        return if (usedWords.contains(currentWord)) {
            getRandomWord()
        } else {
            usedWords.add(currentWord)
            shuffleWord(currentWord)
        }
    }

    private fun shuffleWord(word: String): String {
        val temp = word.toCharArray()
        temp.shuffle()
        while (String(temp) == word) {
            temp.shuffle()
        }

        return String(temp)
    }

    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

    init {
        resetGame()
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = getRandomWord())
    }

    fun checkUserGuess() {
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            val newScore = _uiState.value.score.plus(10)
            updateGameStatus(newScore)
        } else {
            _uiState.update {
                it.copy(isGuessedWordWrong = true)
            }
        }
    }

    fun skipWord() {
        updateGameStatus(_uiState.value.score)
        updateUserGuess("")
    }

    private fun updateGameStatus(newScore: Int) {
        if (usedWords.size == 10) {
            _uiState.update {
                it.copy(
                    isGameOver = true,
                    isGuessedWordWrong = false,
                    score = newScore
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    isGuessedWordWrong = false,
                    score = newScore,
                    currentScrambledWord = getRandomWord(),
                    currentWordCount = it.currentWordCount.inc()
                )
            }
        }

        updateUserGuess("")
    }
}