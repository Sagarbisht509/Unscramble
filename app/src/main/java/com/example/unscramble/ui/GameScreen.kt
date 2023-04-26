package com.example.unscramble.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showSystemUi = true)
@Composable
fun GameScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(15.dp)
    ) {
        GameStatus()
        GameLayout()

        Row(
            modifier = Modifier
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 5.dp)
            ) {
                Text(text = "Skip")
            }

            Button(
                onClick = { },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp)
            ) {
                Text(text = "Submit")
            }
        }
    }
}

@Composable
fun GameStatus(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = "Current"
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            text = "Score"
        )
    }
}

@Composable
fun GameLayout(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "word",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = "text",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = "Enter word") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

