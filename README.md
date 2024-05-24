## Kotlin WASM NPM Sample

This project shows how NPM dependencies can be bound and used by Kotlin Multiplatform in JS-WASM context.
By using the `external` keyword and `@file:JsModule` we can call third party javascript libraries from kotlin code. The
example project will initialize Firebase Authentication in the browser and shows a login flow where compose kotlin
code requests Google Sign in and observes authentication state from the browser. Only the web domain code is added but other platform such as mobile
and desktop can provide their authentication bindings and use the same login screen.

For this sample web portal we would like to observe the authentication state
of our user in a type safe, idiomatic, Kotlin way. Our common code declares an `expect`
function that the platform must implement to show the workflow.

`expect fun currentUid(): Flow<String?>`

Our `jsWasmMain` source set has knowledge of the web domain and declares an `external`
method to bind the Firebase authentication state observer to Kotlin code.

`external fun onAuthStateChanged(auth: Auth, observer: (User?) -> Unit)`

It then complies with requirement of the common code and implements the `actual` version of the 
expected method.

```kotlin
actual fun currentUid(): Flow<String?> = callbackFlow {
    onAuthStateChanged(getAuth()) { user ->
        trySend(user?.uid)
    }
    awaitClose { }
}
```

The core compose code collects this flow as state and updates the UI accordingly.

`val uid by currentUid().collectAsState(null)`

### Build and run

The project can be built and ran from CLI as long as valid credentials are supplied under `src/wasmJsMain/com/kyledahlin/sample/FirebaseOptions.kt`. This
function creates the `FirebaseOptions: JsAny` externally typed object used to initialize the JS Firebase SDK.

```kotlin
fun createOptions(): FirebaseOptions = js(
    """
    ({
        "apiKey": "X",
        "authDomain": "X",
        "projectId": "X",
        "storageBucket": "X",
        "messagingSenderId": "X",
        "appId": "X",
        "measurementId": "X"
    })
"""
)
```

Once this is populated the normal KMP commands will succeed and you can run the site in your local browser.

`./gradlew wasmJsBrowserRun -t`

#### Troubleshooting

If you encounter `e: java.lang.OutOfMemoryError: Java heap space` then increase your daemon size in `local.properties` or `gradle.properties`

```
kotlin.daemon.jvmargs=-Xmx4096m
```