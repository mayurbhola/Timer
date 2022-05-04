package com.mayurbhola.timer.ui.composables

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TimeInputField(
    hintText: String,
    hintLabel: String,
    modifier: Modifier = Modifier,
    callback: (returnInput: Int) -> Unit,
    textValue: String? = ""
) {
    var input by remember(textValue) { mutableStateOf(textValue) }
    val maxLength = 4
    val allowedDigitOnly = "0123456789"
    TextField(
        value = input ?: "",
        onValueChange = {
            if (it.isBlank()) {
                input = it
            }
            if (it.length <= maxLength &&
                it.isNotBlank() &&
                allowedDigitOnly.contains(it.last(), true)
            ) input = it
            callback(input?.toIntOrNull() ?: 0)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeholder = {
            Text(
                text = hintLabel,
                maxLines = 1
            )
        },
        label = { Text(hintText) },
        modifier = modifier
    )
}

@Preview
@Composable
fun AddGroupButtonPreview() {
    TimeInputField(
        hintText = "24",
        hintLabel = "00h",
        modifier = Modifier.defaultMinSize(minWidth = 100.dp), {}
    )
}