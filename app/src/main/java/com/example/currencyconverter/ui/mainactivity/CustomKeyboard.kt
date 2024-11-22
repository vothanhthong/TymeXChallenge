package com.example.currencyconverter.ui.mainactivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.R

/**
 * A composable function that renders a custom numeric keyboard with actions.
 *
 * @param onKeyPress Callback function triggered when a key is pressed.
 * @param onDelete Callback function triggered when the delete action is performed.
 * @param onClear Callback function triggered when the clear action is performed.
 */
@Composable
fun CustomKeyboard(
    onKeyPress: (String) -> Unit, // Callback for key press
    onDelete: () -> Unit, // Callback for delete action
    onClear: () -> Unit // Callback for clear action
) {
    // Define the layout of the keyboard as a list of rows
    val buttonRows = listOf(
        listOf("C", "delete", "percent", "divide"), // First row
        listOf("7", "8", "9", "multiply"),         // Second row
        listOf("4", "5", "6", "subtract"),         // Third row
        listOf("1", "2", "3", "add"),              // Fourth row
        listOf("0", ".", "equal")                  // Fifth row
    )

    // Define colors for different types of buttons
    val lastColumnColor = Color(0xFFFEA00A) // Orange for operation buttons
    val specialButtonColor = Color(0xFFA5A5A5) // Gray for special buttons like clear/delete/percent
    val defaultButtonColor = Color.White.copy(alpha = 0.2f) // Transparent white for numeric buttons

    // Map icons to specific button labels
    val iconMap = mapOf(
        "delete" to R.drawable.ic_del, // Icon for delete
        "percent" to R.drawable.ic_percentage, // Icon for percent
        "divide" to R.drawable.ic_divider, // Icon for divide
        "multiply" to R.drawable.ic_cross, // Icon for multiply
        "subtract" to R.drawable.ic_minus, // Icon for subtract
        "add" to R.drawable.ic_add, // Icon for add
        "equal" to R.drawable.ic_equals // Icon for equals
    )

    // Create the layout with constraints for maximum keyboard height
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(bottom = 40.dp)
    ) {
        // Set a maximum height for the keyboard (e.g., 60% of the screen height)
        val maxKeyboardHeight = maxHeight * 0.6f
        val buttonHeight = maxKeyboardHeight / 6 // Divide the height evenly for rows

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = maxKeyboardHeight) // Set the maximum height
                .background(Color.Black), // Black background for the keyboard
            verticalArrangement = Arrangement.spacedBy(8.dp) // Space between rows
        ) {
            // Iterate through each row in the keyboard layout
            buttonRows.forEachIndexed { rowIndex, row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between buttons
                ) {
                    // Iterate through each button in the row
                    row.forEachIndexed { columnIndex, buttonLabel ->
                        val isIconButton = iconMap.containsKey(buttonLabel) // Check if the button has an icon

                        // Set button colors based on its type
                        val buttonColors = when {
                            buttonLabel in listOf("C", "delete", "percent") ->
                                ButtonDefaults.buttonColors(containerColor = specialButtonColor)
                            isIconButton ->
                                ButtonDefaults.buttonColors(containerColor = lastColumnColor)
                            else ->
                                ButtonDefaults.buttonColors(containerColor = defaultButtonColor)
                        }

                        // Render the button with appropriate behavior and styling
                        Button(
                            onClick = {
                                when (buttonLabel) {
                                    "C" -> onClear() // Trigger clear action
                                    "delete" -> onDelete() // Trigger delete action
                                    else -> onKeyPress(buttonLabel) // Handle key press
                                }
                            },
                            colors = buttonColors, // Apply button colors
                            modifier = Modifier
                                .weight(if (buttonLabel == "0" && rowIndex == buttonRows.lastIndex) 2f else 1f) // Make "0" button wider
                                .height(buttonHeight) // Set the button height
                        ) {
                            // Display the icon or text inside the button
                            if (isIconButton) {
                                Icon(
                                    painter = painterResource(id = iconMap[buttonLabel]!!), // Load the icon
                                    contentDescription = buttonLabel, // Accessibility description
                                    tint = Color.White, // White color for icons
                                    modifier = Modifier.size(20.dp) // Icon size
                                )
                            } else {
                                Text(
                                    text = buttonLabel, // Display the button label
                                    color = Color.White, // White text color
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontSize = 20.sp, // Font size for text
                                        fontWeight = FontWeight.Bold // Bold text for emphasis
                                    ),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
