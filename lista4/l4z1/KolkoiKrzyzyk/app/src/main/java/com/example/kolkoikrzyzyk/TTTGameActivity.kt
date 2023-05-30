package com.example.kolkoikrzyzyk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kolkoikrzyzyk.ui.theme.KolkoIKrzyzykTheme

class TTTGameActivity : ComponentActivity() {
    //@OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        var gamesize: String? = null
        if (extras != null) {
            gamesize = extras.getString("size")
            println("here gamesize is taken $gamesize")
            //The key argument here must match that used in the other activity
        }
        setContent {
            KolkoIKrzyzykTheme {
                val gameviewModel: GameViewModel = viewModel()
                gameviewModel.setgameSize(gamesize)

                GameScreen(gameviewModel)

            }
        }
    }

    @Composable
    fun Circle() {
        Canvas(
            modifier = Modifier
                .size(60.dp)
                .padding(5.dp)
        ) {
            drawCircle(
                color = Color.Blue,
                style = Stroke(width = 20f)
            )
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun GameScreen(
        viewModel: GameViewModel
    ) {

        val state by viewModel.state.collectAsStateWithLifecycle()

        Column(
            modifier = Modifier
                .fillMaxSize()
                //.background(GrayBackground)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Player 'O' : ${state.playerCircleCount.value}", fontSize = 16.sp)
                Text(text = "Draw : ${state.drawCount.value}", fontSize = 16.sp)
                Text(text = "Player 'X' : ${state.playerCrossCount.value}", fontSize = 16.sp)
            }
            Text(
                text = "Tic Tac Toe",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                color = Color.Blue
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                BoardBase(viewModel,state)
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .aspectRatio(1f),
                    columns = GridCells.Fixed(state.gameSize.value)
                ) {
                    viewModel.boardItems.forEach { (cellNo, boardCellValue) ->
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .clickable(
                                        interactionSource = MutableInteractionSource(),
                                        indication = null
                                    ) {
                                        viewModel.onAction(
                                            UserAction.BoardTapped(cellNo)
                                        )
                                    },
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                AnimatedVisibility(
                                    visible = viewModel.boardItems[cellNo] != BoardCellValue.NONE,
                                    enter = scaleIn(tween(1000))
                                ) {
                                    if (boardCellValue == BoardCellValue.CIRLCE) {
                                        Circle()
                                    } else if (boardCellValue == BoardCellValue.CROSS) {
                                        Cross()
                                    }
                                }
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AnimatedVisibility(
                        visible = state.hasWon.value,
                        enter = fadeIn(tween(2000))
                    ) {
                        //DrawVictoryLine(state = state)
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = state.hintText.value,
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Italic
                )
                Button(
                    onClick = {
                        viewModel.onAction(
                            UserAction.PlayAgainButtonClicked
                        )
                    },
                    shape = RoundedCornerShape(5.dp),
                    elevation = ButtonDefaults.elevation(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Play Again",
                        fontSize = 16.sp
                    )
                }

            }
        }
    }

    @Composable
    fun Cross() {
        Canvas(
            modifier = Modifier
                .size(60.dp)
                .padding(5.dp)
        ) {
            drawLine(
                color = Color.Green,
                strokeWidth = 20f,
                cap = StrokeCap.Round,
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = size.width, y = size.height)
            )
            drawLine(
                color = Color.Green,
                strokeWidth = 20f,
                cap = StrokeCap.Round,
                start = Offset(x = 0f, y = size.height),
                end = Offset(x = size.width, y = 0f)
            )
        }
    }

    @Composable
    fun BoardBase(viewModel: GameViewModel, state: GameState) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
        ) {
            viewModel.boardItems.forEach{ (cellNo, boardCellValue) ->

                if(cellNo < state.gameSize.value) {
                    drawLine(
                        color = Color.Gray,
                        strokeWidth = 5f,
                        cap = StrokeCap.Round,
                        start = Offset(
                            x = size.width * cellNo / state.gameSize.value,
                            y = 0f
                        ),
                        end = Offset(
                            x = size.width * cellNo / state.gameSize.value,
                            y = size.height
                        )
                    )

                    drawLine(
                        color = Color.Gray,
                        strokeWidth = 5f,
                        cap = StrokeCap.Round,
                        start = Offset(
                            x = 0f,
                            y = size.height * cellNo / state.gameSize.value
                        ),
                        end = Offset(
                            x = size.width,
                            y = size.height * cellNo / state.gameSize.value
                        )
                    )
                }

            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview2() {
        Cross()
    }
}