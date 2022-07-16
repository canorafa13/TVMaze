package com.mx.cano.tvmaze

import com.google.gson.GsonBuilder
import com.mx.cano.tvmaze.connection.api.TVMazeApi
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object DroidApp {

    fun getRetrofitTVMAZE(): TVMazeApi = Retrofit.Builder()
        .client(clientOkHttp())
        .baseUrl("http://api.tvmaze.com/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().serializeNulls().create()
            )
        )
        .build()
        .create(TVMazeApi::class.java)

    private val sslContext: SSLContext = SSLContext.getInstance("SSL")
    private fun clientOkHttp(retry: Boolean = true): OkHttpClient {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()

        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        val sslSocketFactory = sslContext.socketFactory

        builder.connectTimeout(TIME_OUT_APP, TimeUnit.MINUTES)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_APP, TimeUnit.MINUTES)
            .addInterceptor(httpLoggingInterceptor)
            .protocols(Collections.singletonList(Protocol.HTTP_1_1))
            .retryOnConnectionFailure(retry)
        builder.hostnameVerifier { _, _ -> true }
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        return builder.build()
    }


    const val TIME_OUT_APP: Long = 10

    private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(
            chain: Array<java.security.cert.X509Certificate>,
            authType: String
        ) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(
            chain: Array<java.security.cert.X509Certificate>,
            authType: String
        ) {
        }

        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
            return arrayOf()
        }
    })
}