package com.example.randomuserapp.presentation.ui.screen
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.randomuserapp.data.model.User
import com.example.randomuserapp.presentation.ui.component.UserCard
import com.example.randomuserapp.presentation.ui.state.UserListState
import com.example.randomuserapp.presentation.viewmodel.UserListViewModel

@Composable
fun UserListScreen(
    viewModel: UserListViewModel = hiltViewModel(),
    onUserSelected: (User) -> Unit
) {
    val userCountText by viewModel.textFieldValue

    Column {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = userCountText,
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isDigit() }
                    viewModel.updateTextField(filteredValue)
                },
                label = { Text("Number of Users") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Button(
                onClick = { viewModel.getRandomUsers(userCountText.toInt()) },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Fetch Users")
            }
        }

        when (val state = viewModel.usersState.value) {
            is UserListState.Initial -> {
                Text("Enter a number and fetch users",
                    modifier = Modifier.padding(16.dp))
            }
            is UserListState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(16.dp)
                )
            }
            is UserListState.Success -> {
                LazyColumn {
                    items(state.users) { user ->
                        Log.e("User","User $user")
                        UserCard(
                            user = user,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { onUserSelected(user) }
                        )
                    }
                }
            }
            is UserListState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}