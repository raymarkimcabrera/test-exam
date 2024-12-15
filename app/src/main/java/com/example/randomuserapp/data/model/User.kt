package com.example.randomuserapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class User(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val login: Login,
    val dob: Dob,
    val registered: Registered,
    val phone: String,
    val cell: String,
    val picture: Picture,
    val nat: String
) : Parcelable  {
    companion object{
        fun createMockUser(
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
    }
}

@Parcelize
data class Name(val title: String, val first: String, val last: String) : Parcelable

@Parcelize
data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
    val coordinates: Coordinates,
    val timezone: Timezone
) : Parcelable

@Parcelize
data class Street(val number: Int, val name: String) : Parcelable

@Parcelize
data class Coordinates(val latitude: String, val longitude: String) : Parcelable

@Parcelize
data class Timezone(val offset: String, val description: String) : Parcelable

@Parcelize
data class Login(val uuid: String, val username: String, val password: String) : Parcelable

@Parcelize
data class Dob(val date: String, val age: Int) : Parcelable

@Parcelize
data class Registered(val date: String, val age: Int) : Parcelable

@Parcelize
data class Picture(val large: String, val medium: String, val thumbnail: String) : Parcelable