package com.example.kolkoikrzyzyk

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIconDefaults.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kolkoikrzyzyk.ui.theme.KolkoIKrzyzykTheme
import com.example.kolkoikrzyzyk.ui.theme.Purple200


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KolkoIKrzyzykTheme {
                val viewModel: TTTViewModel = viewModel()
                // A surface container using the 'background' color from the theme
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                // Update UI elements
                val inputvalue = uiState.TFval.value//remember { mutableStateOf(TextFieldValue) }
                Column( modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(50.dp)) {

                    Row(modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp),
                        horizontalArrangement = Arrangement.Center,) {

                        TextField(
                            value = inputvalue,
                            onValueChange = { newValue ->
                                viewModel.onTFChanged(newValue)
                            },
                            trailingIcon = {
                                IconButton(onClick = {
                                    if (viewModel.checkValue() != null) {
                                        //println("haloo")
                                        val i = Intent(applicationContext, TTTGameActivity::class.java)
                                        i.putExtra("size", viewModel.checkValue()!!)
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(applicationContext, i, null)
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = "Check Icon"
                                    )
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
    }
}
