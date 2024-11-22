package com.example.currencyconverter.currencyselectionactivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.currencyconverter.R
import com.example.currencyconverter.ui.currencyselectionactivity.CurrencyItem

class CurrencySelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the status bar and navigation bar to black
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.Black.toArgb()
        window.navigationBarColor = Color.Black.toArgb()

        // Get currency data passed via Intent
        val currencyList = intent.getStringArrayListExtra("CURRENCY_LIST") ?: emptyList()
        val usedCurrencies = intent.getStringArrayListExtra("USED_CURRENCIES") ?: emptyList()
        val rowIndex = intent.getIntExtra("ROW_INDEX", -1)

        setContent {
            MaterialTheme {
                // Set up the main surface
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    CurrencySelectionScreen(
                        currencyList = currencyList, // List of available currencies
                        usedCurrencies = usedCurrencies, // Currencies already in use
                        onCurrencySelected = { currency ->
                            // Return the selected currency to the main activity
                            val resultIntent = Intent().apply {
                                putExtra("SELECTED_CURRENCY", currency)
                                putExtra("ROW_INDEX", rowIndex)
                            }
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CurrencySelectionScreen(
    currencyList: List<String>, // List of available currencies
    usedCurrencies: List<String>, // Currencies already used in the main activity
    onCurrencySelected: (String) -> Unit // Callback when a currency is selected
) {
    // Access the current activity
    val activity = LocalContext.current as? Activity

    // Map currency codes to their respective country names
    val currencyToCountryName = remember { mapOf(
        "VND" to "Vietnam",
        "USD" to "United States",
        "EUR" to "Eurozone",
        "JPY" to "Japan",
        "GBP" to "United Kingdom",
        "AUD" to "Australia",
        "CAD" to "Canada",
        "INR" to "India",
        "AED" to "United Arab Emirates",
        "AFN" to "Afghanistan",
        "ALL" to "Albania",
        "AMD" to "Armenia",
        "ANG" to "Netherlands Antilles",
        "AOA" to "Angola",
        "ARS" to "Argentina",
        "AWG" to "Aruba",
        "AZN" to "Azerbaijan",
        "BAM" to "Bosnia and Herzegovina",
        "BBD" to "Barbados",
        "BDT" to "Bangladesh",
        "BGN" to "Bulgaria",
        "BHD" to "Bahrain",
        "BIF" to "Burundi",
        "BND" to "Brunei",
        "BOB" to "Bolivia",
        "BRL" to "Brazil",
        "BSD" to "Bahamas",
        "BTN" to "Bhutan",
        "BWP" to "Botswana",
        "BYN" to "Belarus",
        "BZD" to "Belize",
        "CHF" to "Switzerland",
        "CLP" to "Chile",
        "CNY" to "China",
        "COP" to "Colombia",
        "CRC" to "Costa Rica",
        "CZK" to "Czech Republic",
        "DKK" to "Denmark",
        "DOP" to "Dominican Republic",
        "DZD" to "Algeria",
        "EGP" to "Egypt",
        "ETB" to "Ethiopia",
        "FJD" to "Fiji",
        "GEL" to "Georgia",
        "GHS" to "Ghana",
        "GIP" to "Gibraltar",
        "GNF" to "Guinea",
        "GTQ" to "Guatemala",
        "GYD" to "Guyana",
        "HKD" to "Hong Kong",
        "HNL" to "Honduras",
        "HRK" to "Croatia",
        "HUF" to "Hungary",
        "IDR" to "Indonesia",
        "ILS" to "Israel",
        "IQD" to "Iraq",
        "IRR" to "Iran",
        "ISK" to "Iceland",
        "JMD" to "Jamaica",
        "JOD" to "Jordan",
        "KES" to "Kenya",
        "KGS" to "Kyrgyzstan",
        "KHR" to "Cambodia",
        "KMF" to "Comoros",
        "KRW" to "South Korea",
        "KWD" to "Kuwait",
        "KZT" to "Kazakhstan",
        "LAK" to "Laos",
        "LBP" to "Lebanon",
        "LKR" to "Sri Lanka",
        "LRD" to "Liberia",
        "LSL" to "Lesotho",
        "MAD" to "Morocco",
        "MDL" to "Moldova",
        "MGA" to "Madagascar",
        "MKD" to "North Macedonia",
        "MMK" to "Myanmar",
        "MNT" to "Mongolia",
        "MOP" to "Macau",
        "MUR" to "Mauritius",
        "MVR" to "Maldives",
        "MWK" to "Malawi",
        "MXN" to "Mexico",
        "MYR" to "Malaysia",
        "MZN" to "Mozambique",
        "NAD" to "Namibia",
        "NGN" to "Nigeria",
        "NOK" to "Norway",
        "NPR" to "Nepal",
        "NZD" to "New Zealand",
        "OMR" to "Oman",
        "PAB" to "Panama",
        "PEN" to "Peru",
        "PHP" to "Philippines",
        "PKR" to "Pakistan",
        "PLN" to "Poland",
        "PYG" to "Paraguay",
        "QAR" to "Qatar",
        "RON" to "Romania",
        "RSD" to "Serbia",
        "RUB" to "Russia",
        "RWF" to "Rwanda",
        "SAR" to "Saudi Arabia",
        "SCR" to "Seychelles",
        "SDG" to "Sudan",
        "SEK" to "Sweden",
        "SGD" to "Singapore",
        "SHP" to "Saint Helena",
        "SLL" to "Sierra Leone",
        "SOS" to "Somalia",
        "SRD" to "Suriname",
        "STD" to "São Tomé and Príncipe",
        "SYP" to "Syria",
        "SZL" to "Eswatini",
        "THB" to "Thailand",
        "TJS" to "Tajikistan",
        "TMT" to "Turkmenistan",
        "TND" to "Tunisia",
        "TOP" to "Tonga",
        "TRY" to "Turkey",
        "TTD" to "Trinidad and Tobago",
        "TWD" to "Taiwan",
        "TZS" to "Tanzania",
        "UAH" to "Ukraine",
        "UGX" to "Uganda",
        "UYU" to "Uruguay",
        "UZS" to "Uzbekistan",
        "VEF" to "Venezuela",
        "VUV" to "Vanuatu",
        "WST" to "Samoa",
        "XAF" to "Central African States",
        "XCD" to "Eastern Caribbean States",
        "XOF" to "West African States",
        "YER" to "Yemen",
        "ZAR" to "South Africa",
        "ZMW" to "Zambia",
        "ZWL" to "Zimbabwe"
    ) }

    // Main column layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header row with a back button and title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { activity?.finish() }, // Close the activity when clicked
                alignment = Alignment.Center,
                colorFilter = ColorFilter.tint(Color(0xFFFCD790)) // Set the tint for the back button
            )

            // Title
            Text(
                text = "Select a Currency",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 40.dp), // Adjust padding for alignment
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFFFCD790) // Title color
            )
        }

        // List of currencies
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Add spacing between list items
        ) {
            items(currencyList) { currency ->
                // Render each currency item
                CurrencyItem(
                    currencyCode = currency,
                    countryName = currencyToCountryName[currency] ?: "Virtual Currency", // Default to "Virtual Currency"
                    isUsed = currency in usedCurrencies, // Highlight if currency is already in use
                    onItemClicked = { onCurrencySelected(currency) } // Handle item click
                )
            }
        }
    }
}