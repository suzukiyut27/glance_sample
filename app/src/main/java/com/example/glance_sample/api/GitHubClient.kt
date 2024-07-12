package com.example.glance_sample.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class GitHubClient {
    suspend fun getGitHubUser(username: String): GitHubUser {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        val url = "https://api.github.com/users/$username"
        val user: GitHubUser = client.get(url).body()
        client.close()
        return user
    }
}
