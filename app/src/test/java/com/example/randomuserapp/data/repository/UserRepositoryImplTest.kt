package com.example.randomuserapp.data.repository

import com.example.randomuserapp.core.utils.Resource
import com.example.randomuserapp.data.model.Coordinates
import com.example.randomuserapp.data.model.Dob
import com.example.randomuserapp.data.model.Location
import com.example.randomuserapp.data.model.Login
import com.example.randomuserapp.data.model.Name
import com.example.randomuserapp.data.model.Picture
import com.example.randomuserapp.data.model.RandomUserResponse
import com.example.randomuserapp.data.model.Registered
import com.example.randomuserapp.data.model.Street
import com.example.randomuserapp.data.model.Timezone
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
import java.util.UUID

class UserRepositoryImplTest {

    @Mock
    private lateinit var mockRemoteInterface: RemoteInterface

    private lateinit var userRepository: UserRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        userRepository = UserRepositoryImpl(mockRemoteInterface)
    }

    private fun createMockUser(
        gender: String = "male",
        firstName: String = "John",
        lastName: String = "Doe",
        email: String = "john.doe@gmail.com",
        phone: String = "09123456789",
        cell: String = "09123546474",
        nationality: String = "PH"
    ): User {
        return User(

            gender = gender,

            // Name
            name = Name(
                title = "Mr.",
                first = firstName,
                last = lastName
            ),

            // Location with comprehensive details
            location = Location(
                street = Street(
                    number = 123,
                    name = "San Jose Street"
                ),
                city = "Marilao",
                state = "Bulacan",
                country = "Philippines",
                postcode = "12345",
                coordinates = Coordinates(
                    latitude = "12.123123123",
                    longitude = "12.123123123"
                ),
                timezone = Timezone(
                    offset = "-08:00",
                    description = "Pacific Time"
                )
            ),

            // Contact information
            email = email,
            phone = phone,
            cell = cell,
            nat = nationality,

            // Login details
            login = Login(
                uuid = UUID.randomUUID().toString(),
                username = "${firstName.lowercase()}.${lastName.lowercase()}",
                password = "MockPassword123!"
            ),

            // Date of Birth
            dob = Dob(
                date = "1990-01-15T10:30:00Z",
                age = 33
            ),

            // Registration details
            registered = Registered(
                date = "1990-01-15T10:30:00Z",
                age = 3
            ),

            // Profile pictures
            picture = Picture(
                large = "https://randomuser.me/api/portraits/men/1.jpg",
                medium = "https://randomuser.me/api/portraits/med/men/1.jpg",
                thumbnail = "https://randomuser.me/api/portraits/thumb/men/1.jpg"
            )
        )
    }

    @Test
    fun `getRandomUsers returns success when response is successful and users are not empty`() =
        runTest {
            val mockUsers = listOf(
                createMockUser(),
                createMockUser()
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