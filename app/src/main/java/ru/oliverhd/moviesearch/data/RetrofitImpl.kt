package ru.oliverhd.moviesearch.data

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RetrofitImpl {

    fun getMovieDetail(): MovieDetailsApi =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().setLenient().create())
            )
            .client(createOkHttpClient(MovieInfoApiInterceptor()))
            .build()
            .create(MovieDetailsApi::class.java)

    fun getPopularMovieList(): MovieListPopularApi =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().setLenient().create())
            )
            .client(createOkHttpClient(MovieInfoApiInterceptor()))
            .build()
            .create(MovieListPopularApi::class.java)

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()
    }

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/"
    }

    private inner class MovieInfoApiInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            return chain.proceed(chain.request())
        }
    }
}