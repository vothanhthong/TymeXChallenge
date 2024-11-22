package com.example.currencyconverter.ui.mainactivity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.ui.currencyselectionactivity.getCountryCodeFromCurrencyCode

@Composable
fun CurrencyRow(
    currencyCode: String, // Currency code (e.g., "USD", "EUR")
    amount: String, // Converted amount
    onCurrencySelect: () -> Unit, // Callback for selecting a currency
    onAmountBoxClick: () -> Unit, // Callback for clicking the amount box
    isBaseCurrency: Boolean = false, // Whether this row is the base currency
    boxBackgroundColor: Color = Color.White.copy(alpha = 0.2f), // Default background color
    textColor: Color = Color.White, // Text color (default is white)
    buttonWidth: Dp = 100.dp, // Fixed width for the button
    totalRows: Int = 4 // Total rows displayed on the screen
) {
    // Adjust row height dynamically based on the total number of rows
    val rowHeight = when (totalRows) {
        2 -> 80.dp // Taller rows for 2 rows
        3 -> 60.dp // Medium height for 3 rows
        else -> 50.dp // Default height for 4 rows or more
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        verticalAlignment = Alignment.CenterVertically // Align items vertically in the center
    ) {
        // Currency selection button with flag and code
        Box(
            modifier = Modifier
                .height(rowHeight)
                .width(buttonWidth) // Set a fixed width for uniformity
                .background(
                    color = boxBackgroundColor,
                    shape = RoundedCornerShape(12.dp) // Rounded corners for the button
                )
                .clickable { onCurrencySelect() }, // Handle click to select currency
            contentAlignment = Alignment.Center // Center content inside the button
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center, // Center content horizontally within the row
                modifier = Modifier.fillMaxWidth() // Ensure the row takes up the full width
            ) {
                // Add the flag emoji
                Text(
                    text = getFlagEmojiFromCurrencyCode(currencyCode), // Get the flag emoji
                    fontSize = 20.sp, // Font size for the emoji
                    modifier = Modifier.padding(end = 8.dp) // Spacing between flag and text
                )

                Text(
                    text = currencyCode, // Display the currency code
                    fontWeight = FontWeight.Bold,
                    color = textColor, // Customizable text color
                    textAlign = TextAlign.Start,
                    maxLines = 1 // Ensure the text stays in a single line
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp)) // Add spacing between the button and the amount box

        // Box displaying the converted amount
        Box(
            modifier = Modifier
                .weight(1f) // Take up remaining horizontal space
                .height(rowHeight) // Match the dynamic height
                .background(
                    color = if (isBaseCurrency) Color(0xFFFCD790).copy(alpha = 0.2f) else boxBackgroundColor,
                    shape = RoundedCornerShape(8.dp) // Rounded corners
                )
                .clickable { onAmountBoxClick() }, // Handle click to set base currency
            contentAlignment = Alignment.CenterEnd // Align text to the right inside the box
        ) {
            Text(
                text = amount, // Display the converted amount
                fontSize = 20.sp, // Font size for the text
                fontWeight = FontWeight.Bold,
                color = textColor, // Customizable text color
                textAlign = TextAlign.Right, // Align the text to the right
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp) // Padding from the right edge
            )
        }
    }
}

// Helper function to map currency codes to flag emojis
fun getFlagEmojiFromCurrencyCode(currencyCode: String): String {
    val countryCode = getCountryCodeFromCurrencyCode(currencyCode)
    return countryCode.toFlagEmoji()
}

// Extension function to convert country code to flag emoji
fun String.toFlagEmoji(): String {
    if (this.length != 2) return "🏳" // Default to white flag for invalid codes
    val offset = 0x1F1E6 // Regional indicator symbol letter A
    val base = 'A'.code
    val firstChar = this[0].uppercaseChar().code - base + offset
    val secondChar = this[1].uppercaseChar().code - base + offset
    return String(intArrayOf(firstChar, secondChar), 0, 2)
}


