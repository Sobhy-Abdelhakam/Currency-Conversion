package dev.sobhy.bmproject.data.remote

import dev.sobhy.bmproject.data.model.CompareResponse
import dev.sobhy.bmproject.data.model.ConvertResponse
import dev.sobhy.bmproject.data.model.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1")
    suspend fun getCurrencies(): CurrencyResponse
    @GET("v1/comparison")
    suspend fun getComparisonResponse(
        @Query("amount")
        amount: Double,
        @Query("from")
        from: String,
        @Query("list")
        list: List<String>
    ) : CompareResponse
    @GET("v1/conversion")
    suspend fun getConvertResponse(
        @Query("from")
        from: String,
        @Query("to")
        to: String,
        @Query("amount")
        amount: Double
    ): ConvertResponse

    @GET("v1/conversion")
    suspend fun getConvertResponse1(
        @Query("from")
        from: String,
        @Query("to")
        to: String,
        @Query("amount")
        amount: Double
    ): ConvertResponse

}