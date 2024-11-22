package com.example.currencyconverter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.activity.result.contract.ActivityResultContracts
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.currencyconverter.currencyselectionactivity.CurrencySelectionActivity
import com.example.currencyconverter.data.network.RetrofitClient
import com.example.currencyconverter.ui.mainactivity.CurrencyRow
import com.example.currencyconverter.ui.mainactivity.CustomKeyboard
import com.example.currencyconverter.ui.mainactivity.InfoDialog
import com.example.currencyconverter.ui.mainactivity.RowSelectionDialog
import com.example.currencyconverter.ui.mainactivity.Toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.round

class MainActivity : ComponentActivity() {

    // API configuration and shared preferences keys
    private val appId = BuildConfig.FX_RATES_API
    private val prefsFile = "currency_prefs"
    private val ratesKey = "rates"
    private val isFirstLaunchKey = "is_first_launch"
    private val lastFetchTimestampKey = "last_fetch_timestamp"
    private var shouldForceReload = false // Flag to indicate if we should force reload rates

    private lateinit var prefs: SharedPreferences
    private val gson = Gson()

    // State variables
    private var selectedCurrencies = mutableStateListOf("USD", "VND", "EUR", "JPY")
    private var baseCurrency by mutableStateOf("USD")
    private var baseCurrencyRowIndex by mutableStateOf(0)
    private var isLoading by mutableStateOf(false)
    private var rates by mutableStateOf<Map<String, Double>>(emptyMap())
    private var lastUpdatedTime by mutableStateOf<String?>(null)
    private var numberOfRowsToShow by mutableStateOf(4) // Default number of rows

    // Variables for calculation
    private var currentOperand by mutableStateOf("0") // Tracks the current number being typed
    private var firstOperand by mutableStateOf<Double?>(null) // Tracks the first operand
    private var currentOperator by mutableStateOf<String?>(null) // Tracks the selected operator

    // Launches a currency selection activity
    private val currencySelectionLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedCurrency = result.data?.getStringExtra("SELECTED_CURRENCY")
                val rowIndex = result.data?.getIntExtra("ROW_INDEX", -1) ?: -1
                if (selectedCurrency != null && rowIndex >= 0) {
                    updateCurrency(rowIndex, selectedCurrency)
                    if (rowIndex == baseCurrencyRowIndex) {
                        fetchRatesWithCheck() // Fetch new rates when the base currency changes
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the status bar and navigation bar to black
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.Black.toArgb()
        window.navigationBarColor = Color.Black.toArgb()

        // Initialize shared preferences
        prefs = getSharedPreferences(prefsFile, Context.MODE_PRIVATE)

        // Check if it's the user's first launch
        val isFirstLaunch = prefs.getBoolean(isFirstLaunchKey, true)

        // Main UI content
        setContent {
            var isInfoDialogVisible by remember { mutableStateOf(false) } // Info dialog visibility
            val isDialogVisible = remember { mutableStateOf(false) } // Settings dialog visibility

            if (isInfoDialogVisible) {
                InfoDialog(onDismiss = { isInfoDialogVisible = false }) // Show info dialog
            }

            if (isFirstLaunch) {
                // Fetch rates on first launch and update the preference
                fetchRatesWithCheck {
                    prefs.edit().putBoolean(isFirstLaunchKey, false).apply()
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                if (isDialogVisible.value) {
                    // Show the row selection dialog
                    RowSelectionDialog(
                        currentRows = numberOfRowsToShow,
                        onRowsSelected = { newRows ->
                            numberOfRowsToShow = newRows.coerceIn(2, 4)
                        },
                        onDismiss = { isDialogVisible.value = false }
                    )
                }

                if (isLoading) {
                    // Show loading indicator when data is being fetched
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFFFCD790))
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        Toolbar(
                            onReloadClicked = { forceFetchExchangeRates() },
                            onSettingsClicked = { isDialogVisible.value = true },
                            onInfoClicked = { isInfoDialogVisible = true },
                            isLoading = isLoading,
                            lastUpdatedTime = lastUpdatedTime
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Display currency rows
                        selectedCurrencies.take(numberOfRowsToShow).forEachIndexed { index, currency ->
                            val isBaseCurrency = index == baseCurrencyRowIndex
                            val convertedAmount = if (isBaseCurrency) currentOperand else calculateConvertedAmount(
                                currentOperand.toDoubleOrNull() ?: 0.0,
                                rates[currency] ?: 0.0
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                CurrencyRow(
                                    currencyCode = currency,
                                    amount = if (isBaseCurrency) currentOperand else "%.2f".format(convertedAmount),
                                    onCurrencySelect = { navigateToCurrencySelection(index) },
                                    onAmountBoxClick = {
                                        if (baseCurrency != currency) {
                                            baseCurrency = currency
                                            baseCurrencyRowIndex = index
                                            currentOperand = "0"
                                            forceFetchExchangeRates()
                                        }
                                    },
                                    isBaseCurrency = isBaseCurrency,
                                    totalRows = numberOfRowsToShow,
                                )
                            }

                            if (index < numberOfRowsToShow - 1) {
                                Spacer(modifier = Modifier.weight(0.5f))
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        // Custom keyboard with calculation logic
                        CustomKeyboard(
                            onKeyPress = { handleKeyPress(it) },
                            onDelete = { handleDelete() },
                            onClear = { clearAll() }
                        )
                    }
                }
            }
        }
    }

    private fun handleKeyPress(key: String) {
        when (key) {
            "add", "subtract", "multiply", "divide", "percent" -> {
                firstOperand = currentOperand.toDoubleOrNull()
                currentOperator = key
                currentOperand = "0"
            }
            "equal" -> {
                val secondOperand = currentOperand.toDoubleOrNull() ?: 0.0
                val result = performCalculation(firstOperand, secondOperand, currentOperator)
                currentOperand = result?.toString() ?: "Error"
                firstOperand = null
                currentOperator = null
            }
            else -> {
                if (key == "." && currentOperand.contains(".")) return
                currentOperand = if (currentOperand == "0" && key != ".") key else currentOperand + key
            }
        }
    }

    private fun handleDelete() {
        currentOperand = currentOperand.dropLast(1).ifEmpty { "0" }
    }

    private fun clearAll() {
        currentOperand = "0"
        firstOperand = null
        currentOperator = null
    }

    private fun performCalculation(firstOperand: Double?, secondOperand: Double, operator: String?): Double? {
        return when (operator) {
            "add" -> (firstOperand ?: 0.0) + secondOperand
            "subtract" -> (firstOperand ?: 0.0) - secondOperand
            "multiply" -> (firstOperand ?: 0.0) * secondOperand
            "divide" -> if (secondOperand != 0.0) (firstOperand ?: 0.0) / secondOperand else null
            "percent" -> (firstOperand ?: 0.0) * (secondOperand / 100)
            else -> null
        }
    }

    override fun onResume() {
        super.onResume()
        fetchRatesWithCheck() // Fetch rates when the activity resumes
    }

    private fun navigateToCurrencySelection(rowIndex: Int) {
        val intent = Intent(this, CurrencySelectionActivity::class.java).apply {
            putStringArrayListExtra("CURRENCY_LIST", ArrayList(loadRatesFromLocalStorage()?.keys ?: emptyList()))
            putStringArrayListExtra("USED_CURRENCIES", ArrayList(selectedCurrencies))
            putExtra("ROW_INDEX", rowIndex)
        }
        currencySelectionLauncher.launch(intent)
    }

    private fun updateCurrency(rowIndex: Int, newCurrency: String) {
        selectedCurrencies[rowIndex] = newCurrency
        if (rowIndex == baseCurrencyRowIndex && baseCurrency != newCurrency) {
            baseCurrency = newCurrency
            shouldForceReload = true // Mark for reload
        }
    }

    private fun fetchRatesWithCheck(onComplete: (() -> Unit)? = null) {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No internet connection. Using cached rates.", Toast.LENGTH_SHORT).show()
            rates = loadRatesFromLocalStorage() ?: emptyMap()
            onComplete?.invoke()
            return
        }

        val lastFetchTimestamp = prefs.getLong(lastFetchTimestampKey, 0L)
        val currentTime = System.currentTimeMillis()
        val shouldFetch = shouldForceReload || (currentTime - lastFetchTimestamp) > 5 * 60 * 1000

        if (shouldFetch) {
            isLoading = true
            shouldForceReload = false
            fetchExchangeRates { fetchedRates ->
                rates = fetchedRates
                lastUpdatedTime = getCurrentDateTime()
                prefs.edit().putLong(lastFetchTimestampKey, currentTime).apply()
                isLoading = false
                onComplete?.invoke()
                Toast.makeText(this, "Rates updated successfully", Toast.LENGTH_SHORT).show()
            }
        } else {
            rates = loadRatesFromLocalStorage() ?: emptyMap()
            onComplete?.invoke()
        }
    }

    private fun forceFetchExchangeRates() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "No internet connection. Using cached rates.", Toast.LENGTH_SHORT).show()
            rates = loadRatesFromLocalStorage() ?: emptyMap()
            return
        }

        isLoading = true
        fetchExchangeRates { fetchedRates ->
            rates = fetchedRates
            lastUpdatedTime = getCurrentDateTime()
            prefs.edit().putLong(lastFetchTimestampKey, System.currentTimeMillis()).apply()
            isLoading = false
            Toast.makeText(this, "Rates updated successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchExchangeRates(onRatesFetched: (Map<String, Double>) -> Unit) {
        val api = RetrofitClient.instance
        api.getLatestRates(appId, baseCurrency).enqueue(object : Callback<com.example.currencyconverter.data.models.FXRatesResponse> {
            override fun onResponse(
                call: Call<com.example.currencyconverter.data.models.FXRatesResponse>,
                response: Response<com.example.currencyconverter.data.models.FXRatesResponse>
            ) {
                if (response.isSuccessful) {
                    val fetchedRates = response.body()?.rates ?: emptyMap()
                    saveRatesToLocalStorage(fetchedRates)
                    onRatesFetched(fetchedRates)
                } else {
                    Log.e("API_ERROR", "Failed to fetch rates: ${response.errorBody()?.string()}")
                }
                isLoading = false
            }

            override fun onFailure(call: Call<com.example.currencyconverter.data.models.FXRatesResponse>, t: Throwable) {
                Log.e("API_ERROR", "Failed to fetch rates: ${t.message}")
                isLoading = false
            }
        })
    }

    private fun saveRatesToLocalStorage(rates: Map<String, Double>) {
        val json = gson.toJson(rates)
        prefs.edit().putString(ratesKey, json).apply()
    }

    private fun loadRatesFromLocalStorage(): Map<String, Double>? {
        val json = prefs.getString(ratesKey, null) ?: return null
        return gson.fromJson(json, object : TypeToken<Map<String, Double>>() {}.type)
    }

    private fun calculateConvertedAmount(amount: Double, rate: Double): Double {
        return if (rate > 0) round(amount * rate * 100) / 100 else 0.0
    }

    private fun getCurrentDateTime(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
