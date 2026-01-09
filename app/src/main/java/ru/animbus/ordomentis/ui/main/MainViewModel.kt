package ru.animbus.ordomentis.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.animbus.ordomentis.data.repositories.AppRepository
import ru.animbus.ordomentis.domain.models.UserData

class MainViewModel(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<UserData?>(null)
    val currentUser: StateFlow<UserData?> = _currentUser

    private val _currentSection = MutableStateFlow(MainSection.ITEMS)
    val currentSection: StateFlow<MainSection> = _currentSection

    private val _isProfileMenuExpanded = MutableStateFlow(false)
    val isProfileMenuExpanded: StateFlow<Boolean> = _isProfileMenuExpanded

    private val _syncState = MutableStateFlow(SyncState.IDLE)
    val syncState: StateFlow<SyncState> = _syncState

    init {
        loadCurrentUser()
    }

    private fun loadCurrentUser() {
        viewModelScope.launch {
            // TODO: Реализовать загрузку текущего пользователя
            // Например: _currentUser.value = userRepository.getCurrentUser()
        }
    }

    fun setCurrentSection(section: MainSection) {
        _currentSection.value = section
    }

    fun toggleProfileMenu() {
        _isProfileMenuExpanded.value = !_isProfileMenuExpanded.value
    }

    fun closeProfileMenu() {
        _isProfileMenuExpanded.value = false
    }

    fun performSync() {
        if (_syncState.value == SyncState.SYNCING) return

        viewModelScope.launch {
            _syncState.value = SyncState.SYNCING
            val userId = _currentUser.value?.userId ?: "demo_user"

            try {
                val success = appRepository.performFullSync(userId)
                _syncState.value = if (success) SyncState.SUCCESS else SyncState.ERROR

                // Автоматически сбрасываем статус через 3 секунды
                viewModelScope.launch {
                    kotlinx.coroutines.delay(3000)
                    if (_syncState.value != SyncState.SYNCING) {
                        _syncState.value = SyncState.IDLE
                    }
                }
            } catch (e: Exception) {
                _syncState.value = SyncState.ERROR

                // Автоматически сбрасываем статус через 3 секунды
                viewModelScope.launch {
                    kotlinx.coroutines.delay(3000)
                    if (_syncState.value != SyncState.SYNCING) {
                        _syncState.value = SyncState.IDLE
                    }
                }
            }
        }
    }

    fun onLoginClick() {
        closeProfileMenu()
        // TODO: Реализовать логику входа
        println("Login clicked")
    }

    fun onRegisterClick() {
        closeProfileMenu()
        // TODO: Реализовать логику регистрации
        println("Register clicked")
    }

    fun onProfileClick() {
        closeProfileMenu()
        // TODO: Реализовать переход в профиль
        println("Profile clicked")
    }

    fun onLogoutClick() {
        closeProfileMenu()
        // TODO: Реализовать выход
        _currentUser.value = null
        println("Logout clicked")
    }

    fun setCurrentUser(user: UserData?) {
        _currentUser.value = user
    }
}

enum class MainSection {
    ITEMS, UNITS, USERS
}

enum class SyncState {
    IDLE,        // Неактивна, белый цвет
    SYNCING,     // В процессе синхронизации, анимация
    SUCCESS,     // Успешно, зеленый цвет
    ERROR        // Ошибка, красный цвет
}