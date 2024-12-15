package com.example.randomuserapp.data.repository

import com.example.randomuserapp.core.utils.Resource
import com.example.randomuserapp.data.model.User
import com.example.randomuserapp.data.remote.RemoteInterface
import okio.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteInterface: RemoteInterface
): UserRepository {
    override suspend fun getRandomUsers(count: Int) : Resource<List<User>> {
        return try {
            val response = remoteInterface.getRandomUsers(
                count = count,
            )

            when {
                response.isSuccessful -> {
                    val users = response.body()?.results
                    if (!users.isNullOrEmpty()) {
                        Resource.Success(users)
                    } else {
                        Resource.Error("No users found")
                    }
                }
                response.code() == 429 -> {
                    Resource.Error("Rate limit exceeded. Please try again later.")
                }
                response.code() == 404 -> {
                    Resource.Error("Resource not found")
                }
                else -> {
                    Resource.Error("Network error: ${response.code()} ${response.message()}")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("Network connection error", e)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Unexpected error occurred", e)
        }
    }
}