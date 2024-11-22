package com.example.currencyconverter.ui.currencyselectionactivity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CurrencyItem(
    currencyCode: String,
    countryName: String,
    isUsed: Boolean = false,
    onItemClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isUsed) Color(0xFFFCD790).copy(alpha = 0.2f)
                else Color.White.copy(alpha = 0.2f)
            )
            .clickable { onItemClicked() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Emoji flag representation
                Text(
                    text = getFlagEmojiFromCurrencyCode(currencyCode),
                    fontSize = 24.sp, // Adjust font size for the emoji flag
                    modifier = Modifier.padding(end = 8.dp)
                )

                // Country name
                Text(
                    text = countryName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isUsed) Color(0xFFFCD790) else Color.White
                )
            }

            // Currency code
            Text(
                text = currencyCode,
                fontSize = 14.sp,
                color = if (isUsed) Color(0xFFFCD790) else Color.White
            )
        }
    }
}

/**
 * Returns the flag emoji based on the currency code.
 */
fun getFlagEmojiFromCurrencyCode(currencyCode: String): String {
    val countryCode = getCountryCodeFromCurrencyCode(currencyCode)
    return countryCode.toUpperCase().map { char ->
        0x1F1E6 + (char - 'A') // Convert to Unicode regional indicator symbols
    }.joinToString("") { String(Character.toChars(it)) }
}

/**
 * Maps currency codes to corresponding country codes.
 */
fun getCountryCodeFromCurrencyCode(currencyCode: String): String {
    val currencyToCountryMap = mapOf(
        "USD" to "US", "EUR" to "EU", "JPY" to "JP", "VND" to "VN",
        "AED" to "AE", "AFN" to "AF", "ALL" to "AL", "AMD" to "AM",
        "ANG" to "NL", "AOA" to "AO", "ARS" to "AR", "AUD" to "AU",
        "AWG" to "AW", "AZN" to "AZ", "BAM" to "BA", "BBD" to "BB",
        "BDT" to "BD", "BGN" to "BG", "BHD" to "BH", "BIF" to "BI",
        "BMD" to "BM", "BND" to "BN", "BOB" to "BO", "BRL" to "BR",
        "BSD" to "BS", "BTC" to "UN", "BTN" to "BT", "BWP" to "BW",
        "BYN" to "BY", "CAD" to "CA", "CDF" to "CD", "CHF" to "CH",
        "CLP" to "CL", "CNY" to "CN", "COP" to "CO", "CRC" to "CR",
        "CUC" to "CU", "CZK" to "CZ", "DKK" to "DK", "DOP" to "DO",
        "DZD" to "DZ", "EGP" to "EG", "ERN" to "ER", "ETB" to "ET",
        "FJD" to "FJ", "FKP" to "FK", "GBP" to "GB", "GEL" to "GE",
        "GHS" to "GH", "GIP" to "GI", "GMD" to "GM", "GNF" to "GN",
        "GTQ" to "GT", "GYD" to "GY", "HKD" to "HK", "HNL" to "HN",
        "HRK" to "HR", "HTG" to "HT", "HUF" to "HU", "IDR" to "ID",
        "ILS" to "IL", "INR" to "IN", "IQD" to "IQ", "IRR" to "IR",
        "ISK" to "IS", "JMD" to "JM", "JOD" to "JO", "KES" to "KE",
        "KGS" to "KG", "KHR" to "KH", "KMF" to "KM", "KRW" to "KR",
        "KWD" to "KW", "KZT" to "KZ", "LAK" to "LA", "LBP" to "LB",
        "LKR" to "LK", "LRD" to "LR", "MAD" to "MA", "MDL" to "MD",
        "MGA" to "MG", "MKD" to "MK", "MMK" to "MM", "MNT" to "MN",
        "MOP" to "MO", "MUR" to "MU", "MXN" to "MX", "MYR" to "MY",
        "MZN" to "MZ", "NAD" to "NA", "NGN" to "NG", "NIO" to "NI",
        "NOK" to "NO", "NPR" to "NP", "NZD" to "NZ", "OMR" to "OM",
        "PAB" to "PA", "PEN" to "PE", "PGK" to "PG", "PHP" to "PH",
        "PKR" to "PK", "PLN" to "PL", "PYG" to "PY", "QAR" to "QA",
        "RON" to "RO", "RSD" to "RS", "RUB" to "RU", "RWF" to "RW",
        "SAR" to "SA", "SCR" to "SC", "SDG" to "SD", "SEK" to "SE",
        "SGD" to "SG", "SLL" to "SL", "SOS" to "SO", "SRD" to "SR",
        "SYP" to "SY", "THB" to "TH", "TJS" to "TJ", "TMT" to "TM",
        "TND" to "TN", "TRY" to "TR", "TTD" to "TT", "TWD" to "TW",
        "TZS" to "TZ", "UAH" to "UA", "UGX" to "UG", "UYU" to "UY",
        "UZS" to "UZ", "VEF" to "VE", "VUV" to "VU", "XAF" to "CM",
        "XCD" to "AG", "XOF" to "BF", "XPF" to "PF", "YER" to "YE",
        "ZAR" to "ZA", "ZMW" to "ZM"
    )

    return currencyToCountryMap[currencyCode] ?: "UN" // Default to "UN" for unknown codes
}

