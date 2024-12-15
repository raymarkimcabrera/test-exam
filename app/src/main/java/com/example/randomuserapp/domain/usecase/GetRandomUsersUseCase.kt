package com.example.randomuserapp.domain.usecase

import com.example.randomuserapp.core.utils.Resource
import com.example.randomuserapp.data.model.User
import com.example.randomuserapp.data.repository.UserRepository
import javax.inject.Inject

class GetRandomUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(count: Int): Resource<List<User>> {
        return userRepository.getRandomUsers(count)
    }
}