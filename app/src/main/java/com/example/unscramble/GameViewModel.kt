package com.example.unscramble

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {

    private var _uiState = MutableStateFlow(GameUiState())
    val uiState : StateFlow<GameUiState> get() = _uiState.asStateFlow()

    private var usedWords : MutableSet<String> = mutableSetOf()

    private lateinit var currentWord : String

    private fun getRandomWord() : String {
        currentWord = allWords.random()
        if(usedWords.contains(currentWord)) {
            return getRandomWord()
        }
        else {
            usedWords.add(currentWord)
            return shuffleWord(currentWord)
        }
    }

    private fun shuffleWord(word : String) : String {
        val temp = word.toCharArray()
        temp.shuffle()
        while(String(temp).equals(word)) {
            temp.shuffle()
        }

        return String(temp)
    }

     init {
        resetGame()
     }

    private fun resetGame() {
        usedWords.clear()

    }
}