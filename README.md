# Staccato

A lightweight state management library for Compose Multiplatform.

## Basic Usage

```kotlin
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StaccatoHost(
                // Optional parameter, only required when running on Android.
                isChangingConfigurations = { this@MainActivity.isChangingConfigurations }
            ) {
                // Creates a single instance shared by all scopes of StaccatoHost.
                val httpClient = singleton { HttpClient() }

                // Creates a new scope.
                StaccatoScope {
                    // Creates a ViewModel inside the `StaccatoScope`
                    // if the scope is disposed, so is the ViewModel.
                    val viewModel = scoped = { MyViewModel1(httpClient) }
                    Component1(viewModel) // renders the UI.
                }
            }
        }
    }
}
```

Note that configuration changes will not affect the `httpClient` nor `viewModel` instances.

## Advanced Usage

```kotlin
val HttpClientProvider = Provider {
    singleton { HttpClient() }
}

val MyViewModelProvider = Provider {
    val httpClient = HttpClientProvider.get()
    scoped { MyViewModel(httpClient) }
}

// Previous example
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StaccatoHost(isChangingConfigurations = { this@MainActivity.isChangingConfigurations }) {
                StaccatoScope {
                    val viewModel = MyViewModelProvider.get()
                    Component1(viewModel) // renders the UI.
                }
            }
        }
    }
}

```

## Navigation Integration

```
@Composable
private fun NavRouting() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "launch") {
        composable("launch") { navBackStackEntry ->
            // Now, the StaccatoScope will connect to the `navBackStackEntry` and only
            // remove from the memory if the `navBackStackEntry` is also removed.
            StaccatoScope(navBackStackEntry) {
                // Same API.
                val viewModel = MyViewModelProvider.get()
                Component1(viewModel) // renders the UI.
            }
        }
    }
}
```

## Testing

```
@Before
fun setUp() {
    HttpClientProvider.stub {
        // Creates fake version of HttpClient.
    }
    // Now ViewModel can run normally in the instrumentation tests.
}
```
