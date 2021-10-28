package dev.marcellogalhardo.staccato.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavBackStackEntry
import dev.marcellogalhardo.staccato.core.InternalStaccatoApi
import dev.marcellogalhardo.staccato.core.StaccatoScope
import dev.marcellogalhardo.staccato.navigation.internal.StaccatoViewModel

@OptIn(InternalStaccatoApi::class)
@Composable
fun StaccatoScope(navBackStackEntry: NavBackStackEntry, content: @Composable () -> Unit) {
    val vmProvider = remember {
        ViewModelProvider(navBackStackEntry, navBackStackEntry.defaultViewModelProviderFactory)
    }
    val vm = vmProvider.get<StaccatoViewModel>()
    StaccatoScope(
        onClearedHandler = { _, clear -> SideEffect { vm.onClearedListeners += clear } },
        content = content,
    )
}
