package com.example.randomuserapp.data.repository

import com.example.randomuserapp.core.utils.Resource
import com.example.randomuserapp.data.model.RandomUserResponse
import com.example.randomuserapp.data.model.User
import com.example.randomuserapp.data.remote.RemoteInterface
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class UserRepositoryImplTest {

    @Mock
    private lateinit var mockRemoteInterface: RemoteInterface

    private lateinit var userRepository: UserRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        userRepository = UserRepositoryImpl(mockRemoteInterface)
    }



    @Test
    fun `getRandomUsers returns success when response is successful and users are not empty`() =
        runTest {
            val mockUsers = listOf(
                User.createMockUser(),
                User.createMockUser()
            )
            val mockResponse = Response.success(RandomUserResponse(results = mockUsers))

            `when`(mockRemoteInterface.getRandomUsers(count = 2)).thenReturn(mockResponse)

            val result = userRepository.getRandomUsers(2)

            assertTrue(result is Resource.Success)
            assertEquals(mockUsers, (result as Resource.Success).data)
        }

    @Test
    fun `getRandomUsers returns error when response is empty`() = runTest {
        val mockResponse = Response.success(RandomUserResponse(results = emptyList()))

        `when`(mockRemoteInterface.getRandomUsers(count = 2)).thenReturn(mockResponse)

        val result = userRepository.getRandomUsers(2)

        assertTrue(result is Resource.Error)
        assertEquals("No users found", (result as Resource.Error).message)
    }

    @Test
    fun `getRandomUsers returns error when rate limit is exceeded`() = runTest {
        val mockResponse = Response.error<RandomUserResponse>(429, byteArrayOf().toResponseBody())

        `when`(mockRemoteInterface.getRandomUsers(count = 2)).thenReturn(mockResponse)

        val result = userRepository.getRandomUsers(2)

        assertTrue(result is Resource.Error)
        assertEquals(
            "Rate limit exceeded. Please try again later.",
            (result as Resource.Error).message
        )
    }

    @Test
    fun `getRandomUsers returns error when resource is not found`() = runTest {

        val mockResponse = Response.error<RandomUserResponse>(404, byteArrayOf().toResponseBody())

        `when`(mockRemoteInterface.getRandomUsers(count = 2)).thenReturn(mockResponse)

        val result = userRepository.getRandomUsers(2)

        assertTrue(result is Resource.Error)
        assertEquals("Resource not found", (result as Resource.Error).message)
    }

    @Test
    fun `getRandomUsers returns error on network connection failure`() = runTest {
        `when`(mockRemoteInterface.getRandomUsers(count = 2)).thenThrow(
            IOException(
                "Network " +
                        "connection error"
            )
        )

        val result = userRepository.getRandomUsers(2)

        assertTrue(result is Resource.Error)
        assertEquals("Network connection error", (result as Resource.Error).message)
    }

    @Test
    fun `getRandomUsers returns error on unexpected exception`() = runTest {
        `when`(mockRemoteInterface.getRandomUsers(count = 2)).thenThrow(RuntimeException("Unexpected error"))

        val result = userRepository.getRandomUsers(2)

        assertTrue(result is Resource.Error)
        assertEquals("Unexpected error occurred", (result as Resource.Error).message)
    }
}