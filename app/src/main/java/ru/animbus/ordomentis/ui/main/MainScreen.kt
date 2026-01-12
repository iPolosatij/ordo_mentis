package ru.animbus.ordomentis.ui.main

import SyncButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import ru.animbus.ordomentis.ui.items.ItemsTab
import ru.animbus.ordomentis.ui.units.UnitsTab
import ru.animbus.ordomentis.ui.users.UsersTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel()
) {
    val currentSection by viewModel.currentSection.collectAsState()
    val isProfileMenuExpanded by viewModel.isProfileMenuExpanded.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val syncState by viewModel.syncState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (currentSection) {
                            MainSection.ITEMS -> "Задачи"
                            MainSection.UNITS -> "Группы"
                            MainSection.USERS -> "Пользователи"
                        }
                    )
                },
                navigationIcon = {
                    SyncButton(
                        syncState = syncState,
                        onSyncClick = { viewModel.performSync() }
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleProfileMenu() }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Профиль"
                        )
                    }

                    DropdownMenu(
                        expanded = isProfileMenuExpanded,
                        onDismissRequest = { viewModel.closeProfileMenu() }
                    ) {
                        if (currentUser == null) {
                            // Меню для неавторизованного пользователя
                            DropdownMenuItem(
                                text = { Text("Войти") },
                                onClick = { viewModel.onLoginClick() }
                            )
                            DropdownMenuItem(
                                text = { Text("Зарегистрироваться") },
                                onClick = { viewModel.onRegisterClick() }
                            )
                        } else {
                            // Меню для авторизованного пользователя
                            DropdownMenuItem(
                                text = { Text("Профиль") },
                                onClick = { viewModel.onProfileClick() }
                            )
                            DropdownMenuItem(
                                text = { Text("Выйти") },
                                onClick = { viewModel.onLogoutClick() }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentSection == MainSection.ITEMS,
                    onClick = { viewModel.setCurrentSection(MainSection.ITEMS) },
                    icon = {
                        Icon(
                            Icons.Outlined.Menu,
                            contentDescription = "Задачи"
                        )
                    },
                    label = { Text("Задачи") }
                )
                NavigationBarItem(
                    selected = currentSection == MainSection.UNITS,
                    onClick = { viewModel.setCurrentSection(MainSection.UNITS) },
                    icon = {
                        Icon(
                            Icons.Outlined.Home,
                            contentDescription = "Группы"
                        )
                    },
                    label = { Text("Группы") }
                )
                NavigationBarItem(
                    selected = currentSection == MainSection.USERS,
                    onClick = { viewModel.setCurrentSection(MainSection.USERS) },
                    icon = {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = "Пользователи"
                        )
                    },
                    label = { Text("Пользователи") }
                )
            }
        }
    ) { innerPadding ->
        when (currentSection) {
            MainSection.ITEMS -> ItemsTab(modifier.padding(innerPadding))
            MainSection.UNITS -> UnitsTab(modifier.padding(innerPadding))
            MainSection.USERS -> UsersTab(modifier.padding(innerPadding))
        }
    }
}
