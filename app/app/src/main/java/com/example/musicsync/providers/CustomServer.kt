package com.example.musicsync.providers

import com.example.musicsync.data.Track
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class CustomServerAuthRequest(
    val username: String,
    val password: String,
)

data class CustomServerAuthResponse(
    val token: String,
)

interface CustomServerAPI {
    @GET("music")
    suspend fun getAllTracks(
        @Header("Authorization") authorization: String,
    ): Response<List<Track>>

    @POST("music")
    suspend fun addTrack(
        @Header("Authorization") authorization: String,
        @Body data: Track,
    ): Response<String>

    @POST("login")
    suspend fun auth(
        @Body data: CustomServerAuthRequest,
    ): Response<CustomServerAuthResponse>
}

class CustomServer : Provider, PasswordAuth {
    private var authCache: AuthCache
    private var api: CustomServerAPI
    private var token: String? = null

    init {
        val retrofit =
            Retrofit.Builder()
                .baseUrl("http://192.168.68.119:3000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        api = retrofit.create(CustomServerAPI::class.java)

        authCache = AuthCache()
        runBlocking {
            token = authCache.getFor(AuthCacheKeys.CUSTOM_SERVER_TOKEN)
        }
    }

    @Throws
    override suspend fun auth(
        username: String,
        password: String,
    ) {
        val res = api.auth(CustomServerAuthRequest(username, password))

        if (!res.isSuccessful) {
            throw Exception("${res.code()}: ${res.message()}")
        }

        if (res.body() == null) {
            throw Exception("could not authenticate")
        }

        val tkn = res.body()?.token
        if (tkn != null) {
            token = tkn
            authCache.setFor(AuthCacheKeys.CUSTOM_SERVER_TOKEN, tkn)
        }
    }

    override fun isAuthenticated(): Boolean = token != null

    @Throws
    override suspend fun getAllTracks(): List<Track> {
        if (token == null) {
            throw Exception("log in first")
        }

        val res = api.getAllTracks("Bearer $token")

        if (!res.isSuccessful) {
            throw Exception("${res.code()}: ${res.message()}")
        }

        return res.body() ?: emptyList()
    }

    override suspend fun addTrack(track: Track) {
        if (token == null) {
            throw Exception("log in first")
        }

        val res = api.addTrack("Bearer $token", track)

        if (!res.isSuccessful) {
            throw Exception("${res.code()}: ${res.message()}")
        }

        throw Exception("failed to add track")
    }
}
