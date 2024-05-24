// All definitions taken from https://firebase.google.com/docs/reference/js
@file:JsModule("@firebase/auth")

package com.kyledahlin.sample.external

import kotlin.js.Promise

external fun getAuth(): Auth

external fun signInWithRedirect(auth: Auth, provider: GoogleAuthProvider)

external fun getRedirectResult(auth: Auth): Promise<UserCredential?>

external fun onAuthStateChanged(auth: Auth, observer: (User?) -> Unit)

external fun signOut(auth: Auth): Promise<JsAny>

external interface UserCredential : JsAny {
    val providerId: String
}

external interface OAuthCredential : JsAny {

}

external interface Auth : JsAny {
    var currentUser: User?
}

external interface User : UserInfo {

}

external interface UserInfo : JsAny {
    val uid: String
}

external class GoogleAuthProvider {
    companion object {
        fun credentialFromResult(credential: UserCredential): OAuthCredential?
    }
}