package com.example.unscramble.ui

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscramble.GameViewModel
import com.example.unscramble.R

@Preview(showSystemUi = true)
@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel()
) {

    val gameUiState by gameViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(15.dp)
    ) {
        GameStatus(
            wordCount = gameUiState.currentWordCount,
            score = gameUiState.score
        )
        GameLayout(
            currentScrambledWord = gameUiState.currentScrambledWord,
            userGuess = gameViewModel.userGuess,
            onUserGuessChanged = { gameViewModel.updateUserGuess(it) },
            onKeyboardDone = { gameViewModel.checkUserGuess() },
            isUserWrong = gameUiState.isGuessedWordWrong
        )

        Row(
            modifier = Modifier
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(
                onClick = { gameViewModel.skipWord() },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 5.dp)
            ) {
                Text(stringResource(R.string.skip))
            }

            Button(
                onClick = { gameViewModel.checkUserGuess() },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp)
            ) {
                Text(stringResource(R.string.submit))
            }
        }
    }

    if (gameUiState.isGameOver) {
        FinalScoreDialog(
            onPlayAgain = { gameViewModel.resetGame() },
            score = gameUiState.score
        )
    }
}

@Composable
fun GameStatus(
    wordCount: Int,
    score: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = stringResource(R.string.word_count, wordCount),
            fontSize = 20.sp
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            text = stringResource(id = R.string.score, score),
            fontSize = 20.sp
        )
    }
}

@Composable
fun GameLayout(
    currentScrambledWord: String,
    userGuess: String,
    onUserGuessChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    //modifier: Modifier = Modifier,
    isUserWrong: Boolean
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = currentScrambledWord,
            fontSize = 30.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(id = R.string.instructions),
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = userGuess,
            onValueChange = onUserGuessChanged,
            label = {
                if (isUserWrong) {
                    Text(stringResource(id = R.string.wrong_guess))
                } else {
                    Text(stringResource(id = R.string.enter_your_word))
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = isUserWrong,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onKeyboardDone() }
            )
        )
    }
}

@Composable
fun FinalScoreDialog(
    onPlayAgain: () -> Unit,
    score: Int
) {

    val activity = LocalContext.current as Activity

    AlertDialog(
        onDismissRequest = {
        },
        title = { Text(stringResource(id = R.string.congratulations)) },
        text = { Text(stringResource(id = R.string.you_scored, score)) },
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ) {
                Text(stringResource(id = R.string.exit))
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onPlayAgain()
                }
            ) {
                Text(stringResource(id = R.string.play_again))
            }
        }
    )
}