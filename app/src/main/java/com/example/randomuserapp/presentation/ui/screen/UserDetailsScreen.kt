package com.example.randomuserapp.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.randomuserapp.core.utils.toHumanReadableDate
import com.example.randomuserapp.data.model.User
import com.example.randomuserapp.presentation.ui.component.GlideImage

@Composable
fun UserDetailsScreen(user: User) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Picture
        GlideImage(user.picture.large)

        Spacer(modifier = Modifier.height(16.dp))

        // Name
        Text(
            text = "${user.name.title} ${user.name.first} ${user.name.last}",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Email
        Text(
            text = user.email,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Location
        SectionCard(
            title = "Location",
            content = """
                ${user.location.street.number} ${user.location.street.name},
                ${user.location.city}, ${user.location.state},
                ${user.location.country} - ${user.location.postcode}
            """.trimIndent()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Contact
        SectionCard(
            title = "Contact",
            content = """
                Phone: ${user.phone}
                Cell: ${user.cell}
            """.trimIndent()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Login
        SectionCard(
            title = "Login",
            content = """
                Username: ${user.login.username}
                UUID: ${user.login.uuid}
            """.trimIndent()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Date of Birth
        SectionCard(
            title = "Date of Birth",
            content = "${user.dob.date.toHumanReadableDate()} (${user.dob.age} years old)"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Registered
        SectionCard(
            title = "Registered",
            content = "${user.registered.date.toHumanReadableDate()} (${user.registered.age} years ago)"
        )
    }
}

@Composable
fun SectionCard(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
