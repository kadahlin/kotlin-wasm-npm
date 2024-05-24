package com.kyledahlin.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import kotlinx.coroutines.flow.Flow

@Composable
fun App(onGoogleSignIn: () -> Unit, onSignOut: () -> Unit) {
    val uid by currentUid().collectAsState(null)

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        uid?.let {
            SignedIn(uid = it, onSignOut = onSignOut)
        } ?: SignedOut(onGoogleSignIn = onGoogleSignIn)
    }
}

@Composable
fun SignedOut(onGoogleSignIn: () -> Unit) {
    Column {
        Button(onClick = onGoogleSignIn) {
            Text("Sign in with Google")
        }
    }
}

@Composable
fun SignedIn(uid: String, onSignOut: () -> Unit) {
    Column {
        Text("Currently logged in with uid: [$uid]")
        Button(onClick = onSignOut) {
            Text("Sign out")
        }
    }
}

//Observe currently logged-in user id from the underlying platform
expect fun currentUid(): Flow<String?>