package com.example.movil2.data.repository

import com.example.movil2.data.remote.RetrofitClient
import com.example.movil2.data.remote.SoapRequestBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SicenetRepository {

    private var sessionCookie: String? = null

    suspend fun login(matricula: String, contrasenia: String, tipoUsuario: String): Boolean {
        return withContext(Dispatchers.IO) {
            val body = SoapRequestBuilder.buildLoginBody(matricula, contrasenia, tipoUsuario)
            val response = RetrofitClient.service.login(body)

            if (response.isSuccessful) {
                sessionCookie = response.headers()["Set-Cookie"]
                val responseBody = response.body()?.string()
                responseBody?.contains("<accesoLoginResult>OK</accesoLoginResult>") == true
            } else {
                false
            }
        }
    }

    suspend fun getProfile(): String? {
        return withContext(Dispatchers.IO) {
            sessionCookie?.let { cookie ->
                val body = SoapRequestBuilder.buildProfileBody()
                val response = RetrofitClient.service.getProfile(cookie, body)
                if (response.isSuccessful) {
                    response.body()?.string()
                } else null
            }
        }
    }
}
