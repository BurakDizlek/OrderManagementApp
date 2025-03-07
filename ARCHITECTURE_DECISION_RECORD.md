3rd Party Library Selection
Although this project was started as an Android project, it is aimed to use libraries that will not
cause problems in the transition to multiplatform(KMM) in the future.

HTTP Client Decision
Retrofit vs Ktor
*Both of them have pros and cons.
*While Retrofit is a mature and widely-used library with a long history, Ktor is a newer,
promising option gaining popularity.
*Retrofit is simpler to use.
*Ktor is giving more control.
*Ktor was written in Kotlin, Retrofit in Java.
*The most important thing is Ktor support KMM, Retrofit does not.
-> Ktor client will be used.

Dependency Injection Library Decision
Koin vs Dagger Hilt
*Both are popular libraries that have been in use for a long time.
*We will work with Koin because it has KMM support.

Image Loading Library Decision
Glide vs Coil
*Both of them have pros and cons.
*Glide has been written in Java but Coil is written in Kotlin.
*Coil has simpler API.
*We will use Coil.