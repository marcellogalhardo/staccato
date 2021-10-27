# Staccato

A lightweight state management library for Compose Multiplatform.

**This project is currently experimental and the API subject to breaking changes without notice.**

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

### Supported Retentions

- `singleton`: retains it with the `StaccatoHost` (single instance).
- `scoped`: retains it with the parent `StaccatoScope`.
- `reusable` (TODO): similar to `singleton` but can be removed from memory if not in use.

## Advanced Usage: Providers (TODO)

```kotlin
val HttpClientProvider = Provider {
    singleton { HttpClient() }
}

val MyViewModelProvider = Provider {
    val httpClient = HttpClientProvider.get()
    scoped { MyViewModel(httpClient) }
}

val MyViewModelProvider = {
    val httpClient = HttpClientProvider.get()
    scoped { MyViewModel(httpClient) }
}

val MyViewModelProvider = { id: Int ->
    val httpClient = HttpClientProvider.get()
    scoped(key1 = id) { MyViewModel(id, httpClient) }
}

// Previous example
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isChangingConfigurations = { isChangingConfigurations }
        setContent {
            StaccatoHost(isChangingConfigurations) {
                StaccatoScope {
                    val viewModel = MyViewModelProvider.get()
                    Component1(viewModel) // renders the UI.
                }
            }
        }
    }
}
```

## Advanced Usage: Navigation Integration (TODO)

```kotlin
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

## Advanced Usage: Process Death

```kotlin
val MyViewModelProvider = Provider {
    val httpClient = HttpClientProvider.get()
    val saver = MyViewModelSaver()
    scoped(saver) { MyViewModel(httpClient) }
}
```

## Advanced Usage: Closeable (TODO)

```kotlin
val MyViewModelProvider = Provider {
    val httpClient = HttpClientProvider.get()
    scoped(onClose = { vm -> vm.close() }) { MyViewModel(httpClient) }
}
```

Note that if `MyViewModel` implements `Closeable`, it will be automatically invoked.

## Testing with Providers (TODO)

```kotlin
@Before
fun setUp() {
    HttpClientProvider.replace {
        // Creates fake version of HttpClient or whatever.
    }
    // Now `HttpClientProvider` will always return the return you choose.
}
```
