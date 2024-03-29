package com.example.musicsync.providers

import com.example.musicsync.data.Track
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class PolarisAuthRequest(
    val username: String,
    val password: String,
)

data class PolarisAuthResponse(
    val username: String,
    val token: String,
    @SerializedName("is_admin")
    val isAdmin: Boolean,
)

interface PolarisAPI {
    @GET("flatten")
    suspend fun getAllTracks(
        @Header("Authorization") authorization: String,
    ): Response<List<Track>>

    @POST("auth")
    suspend fun auth(
        @Body data: PolarisAuthRequest,
    ): Response<PolarisAuthResponse>
}

class Polaris : Provider, PasswordAuth {
    private var authCache: AuthCache
    private var api: PolarisAPI
    private var token: String? = null

    init {
        val retrofit =
            Retrofit.Builder()
                .baseUrl("http://192.168.114.239:5050/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        api = retrofit.create(PolarisAPI::class.java)

        authCache = AuthCache()
        runBlocking {
            token = authCache.getFor(AuthCacheKeys.POLARIS_TOKEN)
        }
    }

    @Throws
    override suspend fun auth(
        username: String,
        password: String,
    ) {
        val res = api.auth(PolarisAuthRequest(username, password))

        if (!res.isSuccessful) {
            throw Exception("${res.code()}: ${res.message()}")
        }

        if (res.body() == null) {
            throw Exception("could not authenticate")
        }

        val tkn = res.body()?.token
        if (tkn != null) {
            token = tkn
            authCache.setFor(AuthCacheKeys.POLARIS_TOKEN, tkn)
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
}
