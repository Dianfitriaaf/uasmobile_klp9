package com.bladerlaiga.catanime.network

import com.bladerlaiga.catanime.AnimeDetail
import com.bladerlaiga.catanime.AnimeOverview
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeService {
  @GET("{type}/{id}")
  suspend fun getSeason(
    @Path("type") type: String = "anime",
    @Path("id") id: Long
  ): AnimeDetail
  @GET("season/{year}/{season}")
  suspend fun getOverview(
    @Path("year") year: Int = 2018,
    @Path("season") season: String = "winter"
  ): AnimeOverview
  companion object {
    private const val BASE_URL= "https://api.jikan.moe/v3/"
    var service: AnimeService? = null
    fun getInstance() : AnimeService {
      if (service == null) {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
          .addInterceptor(logger)
          .build()
        service = Retrofit.Builder()
          .baseUrl(BASE_URL)
          .client(client)
          .addConverterFactory(GsonConverterFactory.create())
          .build()
          .create(AnimeService::class.java)
      }
      return service!!
    }
  }
}