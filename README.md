# Staccato

A lightweight state management library for Compose Multiplatform.

> The term staccato (pronounced "stuh-caw-toe") means detached, or separated, notes . Staccato notes have space, or silence, between them. There are different degrees of staccato notes.

**This project is currently experimental and the API subject to breaking changes without notice.**

## Download

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.marcellogalhardo.staccato:staccato-core:Tag'
    implementation 'com.github.marcellogalhardo.staccato:staccato-navigation:Tag'
}
```

(Please replace `{Tag}` with the [latest version numbers](https://github.com/marcellogalhardo/staccato/releases))

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
                    val viewModel = scoped { MyViewModel1(httpClient) }
                    Component1(viewModel) // renders the UI.
                }
            }
        }
    }
}
```

Note that configuration changes will not affect the `httpClient` nor `viewModel` instances.

### Supported Retentions

- `retainInHost`: retains it with the `StaccatoHost` (single instance per host).
- `retainInScope`: retains it with the parent `StaccatoScope` (one instance per scope, multiple per host).

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

## Advanced Usage: Navigation Integration

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

License
-------

    Copyright 2021 Marcello Galhardo

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
