package com.example.currencyconverter.data.models

import com.google.gson.annotations.SerializedName

/**
 * Data model representing the API response from FXRatesAPI.
 */
data class FXRatesResponse(
    @SerializedName("success") val success: Boolean,         // API success status
    @SerializedName("timestamp") val timestamp: Long,        // Timestamp of the rates
    @SerializedName("base") val base: String,               // Base currency
    @SerializedName("date") val date: String,               // Date of the rates
    @SerializedName("rates") val rates: Map<String, Double> // Currency rates against the base
)
