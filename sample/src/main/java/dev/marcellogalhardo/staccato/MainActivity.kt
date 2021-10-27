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
import dev.marcellogalhardo.staccato.core.StaccatoConfigurations
import dev.marcellogalhardo.staccato.core.StaccatoHost
import dev.marcellogalhardo.staccato.core.StaccatoScope
import dev.marcellogalhardo.staccato.core.scoped
import dev.marcellogalhardo.staccato.core.singleton
import dev.marcellogalhardo.staccato.ui.theme.StaccatoTheme

val screenToggleProvider = @Composable {
    singleton { mutableStateOf(true) }
}

val counterProvider = @Composable {
    scoped { mutableStateOf(1) }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StaccatoTheme {
                StaccatoHost(
                    configurations = StaccatoConfigurations(
                        isChangingConfigurations = { isChangingConfigurations }
                    )
                ) {
                    val screenToggle: Boolean by screenToggleProvider.invoke()
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
        var screenToggle: Boolean by screenToggleProvider.invoke()
        var counter: Int by counterProvider.invoke()
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
