package com.example.movil2.data.remote

import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Header

interface SicenetService {

    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: \"http://tempuri.org/accesoLogin\""
    )
    @POST("ws/wsalumnos.asmx")
    suspend fun login(@Body body: RequestBody): Response<ResponseBody>

    @Headers(
        "Content-Type: text/xml; charset=utf-8",
        "SOAPAction: \"http://tempuri.org/getAlumnoAcademicoWithLineamiento\""
    )
    @POST("ws/wsalumnos.asmx")
    suspend fun getProfile(
        @Header("Cookie") session: String,
        @Body body: RequestBody
    ): Response<ResponseBody>
}

object RetrofitClient {
    private const val BASE_URL = "https://sicenet.surguanajuato.tecnm.mx/"

    val service: SicenetService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(SicenetService::class.java)
    }
}
