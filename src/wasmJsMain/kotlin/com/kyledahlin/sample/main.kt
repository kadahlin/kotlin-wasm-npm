package com.kyledahlin.sample

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.kyledahlin.sample.external.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    // Initialize the Web specific SDK before calling our common UI
    initializeApp(createOptions())
    val auth = getAuth()

    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        LaunchedEffect(true) {
            getRedirectResult(auth).then { credential ->
                credential?.let { GoogleAuthProvider.credentialFromResult(it) }
                null
            }.catch { e ->
                println("failed to redirect, $e")
                null
            }
        }

        App(onGoogleSignIn = {
            signInWithRedirect(auth, GoogleAuthProvider())
        }, onSignOut = {
            signOut(auth)
        })
    }
}

// Convert the external JS method into a Flow that common code can use
actual fun currentUid(): Flow<String?> = callbackFlow {
    onAuthStateChanged(getAuth()) { user ->
        trySend(user?.uid)
    }
    awaitClose { }
}