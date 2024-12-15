package com.example.randomuserapp.presentation.ui.state

import com.example.randomuserapp.data.model.User

sealed class UserListState {
    object Initial : UserListState()
    object Loading : UserListState()
    data class Success(val users: List<User>) : UserListState()
    data class Error(val message: String) : UserListState()
}