package com.example.currencyconverter.data.network

import com.example.currencyconverter.data.models.FXRatesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface defining API endpoints for FXRatesAPI.
 */
interface FXRatesApi {
    /**
     * Fetches the latest currency exchange rates.
     *
     * @param apiKey Your FXRatesAPI key.
     * @param base The base currency to get rates for (default is USD).
     * @param symbols Comma-separated list of target currencies (optional).
     * @return A [Call] that fetches the exchange rates.
     */
    @GET("latest")
    fun getLatestRates(
        @Query("access_key") apiKey: String,
        @Query("base") base: String = "USD",
        @Query("symbols") symbols: String? = null
    ): Call<FXRatesResponse>

    /**
     * Fetches historical currency exchange rates for a specific date.
     *
     * @param apiKey Your FXRatesAPI key.
     * @param date The historical date in format YYYY-MM-DD.
     * @param base The base currency to get rates for (optional).
     * @param symbols Comma-separated list of target currencies (optional).
     * @return A [Call] that fetches the historical exchange rates.
     */
    @GET("historical")
    fun getHistoricalRates(
        @Query("access_key") apiKey: String,
        @Query("date") date: String,
        @Query("base") base: String = "USD",
        @Query("symbols") symbols: String? = null
    ): Call<FXRatesResponse>
}
