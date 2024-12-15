package com.example.randomuserapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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
) : Parcelable

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