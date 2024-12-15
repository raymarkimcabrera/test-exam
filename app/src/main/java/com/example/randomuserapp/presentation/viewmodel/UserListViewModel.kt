package com.example.randomuserapp.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuserapp.core.utils.Resource
import com.example.randomuserapp.domain.usecase.GetRandomUsersUseCase
import com.example.randomuserapp.presentation.ui.state.UserListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUsersUseCase: GetRandomUsersUseCase
) : ViewModel() {

    private val _usersState = mutableStateOf<UserListState>(UserListState.Initial)
    val usersState: State<UserListState> = _usersState

    fun getRandomUsers(count: Int = 50) {
        viewModelScope.launch {
            _usersState.value = UserListState.Loading

            val result = getUsersUseCase(count)

            _usersState.value = when (result) {
                is Resource.Success -> UserListState.Success(result.data)
                is Resource.Error -> UserListState.Error(result.message)
                is Resource.Loading -> UserListState.Loading
            }

        }
    }
}

