package com.example.currencyconverter.ui.mainactivity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A reusable composable function to display an informational dialog.
 *
 * @param onDismiss Callback triggered when the user dismisses the dialog.
 */
@Composable
fun InfoDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss, // Handle the dismissal of the dialog
        title = {
            // Title section of the dialog
            Text(
                text = "How to Use the App", // Title text
                fontSize = 20.sp, // Font size for the title
                fontWeight = FontWeight.Bold, // Bold text for emphasis
                color = Color.White // White text color
            )
        },
        text = {
            // Main content of the dialog
            Column(
                modifier = Modifier
                    .fillMaxWidth() // Fill the available width
                    .padding(8.dp) // Add padding around the content
            ) {
                // Introduction message
                Text(
                    text = "Welcome to the Currency Converter app! Follow these steps to use the app:",
                    fontSize = 16.sp, // Slightly larger font size for the intro
                    color = Color.LightGray // Light gray color for readability
                )
                Spacer(modifier = Modifier.height(12.dp)) // Add spacing below the intro text

                // Step-by-step instructions
                Text(
                    text = """
                        1. Tap on a currency row to change the currency.
                        2. Click on the button with the flag and currency code to select a different currency.
                        3. Use the number pad at the bottom to input or edit amounts.
                        4. Tap the reload button to refresh exchange rates.
                        5. Use the settings icon to configure the number of rows displayed.
                    """.trimIndent(), // Use trimIndent to format the multiline string
                    fontSize = 14.sp, // Font size for step-by-step instructions
                    color = Color.LightGray // Light gray color for consistency
                )
                Spacer(modifier = Modifier.height(16.dp)) // Add spacing before the divider

                // Divider for visual separation
                Divider(color = Color.Gray, thickness = 0.5.dp)

                Spacer(modifier = Modifier.height(16.dp)) // Add spacing after the divider

                // Section for app information
                Text(
                    text = "About the App", // Subtitle for the app info
                    fontSize = 18.sp, // Slightly larger font size for the subtitle
                    fontWeight = FontWeight.SemiBold, // Semi-bold text for emphasis
                    color = Color.White // White text color
                )
                Spacer(modifier = Modifier.height(8.dp)) // Add spacing below the subtitle

                // App developer and version information
                Text(
                    text = """
                        Developer: Vo Thanh Thong
                        Version: 1.0.0
                        Contact: vothanhthong.work@gmail.com
                    """.trimIndent(), // Format the information properly
                    fontSize = 14.sp, // Font size for additional information
                    color = Color.LightGray // Light gray color for subtle appearance
                )
            }
        },
        confirmButton = {
            // OK button for dismissing the dialog
            TextButton(onClick = onDismiss) {
                Text(
                    text = "OK", // Button label
                    color = Color(0xFFFCD790) // Custom color for the button text
                )
            }
        },
        containerColor = Color.Black, // Black background for the dialog
        tonalElevation = 4.dp, // Slight elevation for better appearance
        shape = RoundedCornerShape(12.dp) // Rounded corners for a modern design
    )
}
