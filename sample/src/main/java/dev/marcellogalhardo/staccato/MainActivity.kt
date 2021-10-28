package dev.marcellogalhardo.staccato

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import dev.marcellogalhardo.staccato.core.Provider
import dev.marcellogalhardo.staccato.core.StaccatoHost
import dev.marcellogalhardo.staccato.core.StaccatoScope
import dev.marcellogalhardo.staccato.core.retainInScope
import dev.marcellogalhardo.staccato.core.retainInHost
import dev.marcellogalhardo.staccato.ui.theme.StaccatoTheme

val screenToggleProvider = Provider {
    retainInHost { mutableStateOf(true) }
}

val counterProvider = Provider {
    retainInScope { mutableStateOf(1) }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StaccatoTheme {
                StaccatoHost(
                    isChangingConfigurations = { isChangingConfigurations }
                ) {
                    val screenToggle: Boolean by screenToggleProvider.get()
                    if (screenToggle) {
                        Counter("First", "Second")
                    } else {
                        Counter("Second", "First")
                    }
                }
            }
        }
    }
}

@Composable
fun Counter(name: String, other: String) {
    StaccatoScope {
        var screenToggle: Boolean by screenToggleProvider.get()
        var counter: Int by counterProvider.get()
        Column {
            Text(text = "$name Screen, Counter: $counter")
            Button(onClick = { counter++ }) {
                Text(text = "Counter ++")
            }
            Button(onClick = { counter-- }) {
                Text(text = "Counter--")
            }
            Button(onClick = { screenToggle = !screenToggle }) {
                Text(text = "Go to $other")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StaccatoTheme {
        Greeting("Android")
    }
}
