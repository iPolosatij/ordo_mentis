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