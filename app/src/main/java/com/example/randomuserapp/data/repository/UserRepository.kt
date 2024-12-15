package com.example.randomuserapp.data.repository

import com.example.randomuserapp.core.utils.Resource
import com.example.randomuserapp.data.model.User

interface UserRepository {
    suspend fun getRandomUsers(count: Int): Resource<List<User>>
}