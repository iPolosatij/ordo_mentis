package ru.animbus.ordomentis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.animbus.ordomentis.ui.main.MainScreen
import ru.animbus.ordomentis.ui.theme.OrdoMentisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OrdoMentisTheme {
                MainScreen()
            }
        }
    }
}