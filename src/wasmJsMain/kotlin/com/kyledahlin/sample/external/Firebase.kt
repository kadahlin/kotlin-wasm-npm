@file:JsModule("firebase/app")

package com.kyledahlin.sample.external

external interface FirebaseApp : JsAny {
    val name: String
}

external fun initializeApp(options: FirebaseOptions): FirebaseApp

external interface FirebaseError : JsAny {
    val code: String
}

external interface FirebaseOptions : JsAny {
    var apiKey: String
    var authDomain: String
    var projectId: String
    var storageBucket: String
    var messagingSenderId: String
    var appId: String
    var measurementId: String
}
