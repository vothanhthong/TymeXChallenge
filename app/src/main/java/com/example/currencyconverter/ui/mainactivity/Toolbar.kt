package com.example.currencyconverter.ui.mainactivity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    onReloadClicked: () -> Unit, // Callback for the reload button action
    onSettingsClicked: () -> Unit, // Callback for the settings button action
    onInfoClicked: () -> Unit, // Callback for the information button action
    isLoading: Boolean, // State to manage button interaction
    lastUpdatedTime: String? // Last updated time to display as a subtitle
) {
    TopAppBar(
        title = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Main Title
                Text(
                    text = "Currency Converter",
                    color = Color.White, // White text color for the title
                    fontSize = 18.sp, // Font size for the title
                    fontWeight = FontWeight.Bold, // Bold font weight for emphasis
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start // Align title to the start
                )

                // Subtitle for last updated time
                lastUpdatedTime?.let {
                    Text(
                        text = "Updated: $it",
                        color = Color.Gray, // Gray color for the subtitle
                        fontSize = 14.sp, // Smaller font size for the subtitle
                        textAlign = TextAlign.Start, // Align subtitle to the start
                        modifier = Modifier.fillMaxWidth().padding(top = 2.dp)
                    )
                }
            }
        },
        actions = {
            // Reload button with dynamic color based on loading state
            IconButton(
                onClick = { if (!isLoading) onReloadClicked() }, // Prevent clicks during loading
                enabled = !isLoading // Disable button when loading
            ) {
                Surface(
                    modifier = Modifier.size(24.dp), // Circular button with size
                    shape = CircleShape,
                    color = if (isLoading) Color.Gray.copy(alpha = 0.1f) else Color.Transparent
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_reload),
                        contentDescription = "Reload", // Accessibility description
                        tint = if (isLoading) Color.Gray else Color(0xFFFCD790), // Tint color
                        modifier = Modifier.size(24.dp) // Icon size
                    )
                }
            }

            // Information button
            IconButton(onClick = onInfoClicked) {
                Surface(
                    modifier = Modifier.size(24.dp), // Circular button for information
                    shape = CircleShape,
                    color = Color.Transparent
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_info), // Replace with your question mark icon
                        contentDescription = "Information", // Accessibility description
                        tint = Color(0xFFFCD790), // Accent color for the info icon
                        modifier = Modifier.size(24.dp) // Icon size
                    )
                }
            }

            // Settings button
            IconButton(onClick = onSettingsClicked) {
                Surface(
                    modifier = Modifier.size(24.dp), // Circular button for settings
                    shape = CircleShape,
                    color = Color.Transparent
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_setting),
                        contentDescription = "Settings", // Accessibility description
                        tint = Color(0xFFFCD790), // Accent color for the settings icon
                        modifier = Modifier.size(24.dp) // Icon size
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black, // Black background for the toolbar
            titleContentColor = Color.White // White color for the title text
        ),
        modifier = Modifier.fillMaxWidth()
    )
}