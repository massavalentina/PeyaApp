package com.example.peya_ecommerce_app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchFilterBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onFilterClick: (String) -> Unit,
    selectedCategory: String,
    availableCategories: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = query,
            onValueChange = { onQueryChanged(it) },
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            placeholder = { Text("Buscar") },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFF0F0F0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Box {
            IconButton(
                onClick = { expanded = true },
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colors.primary, shape = MaterialTheme.shapes.small)
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filtrar",
                    tint = Color.White
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                availableCategories.forEach { category ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        onFilterClick(category)
                    }) {
                        Text(text = category)
                    }
                }
            }
        }
    }
}


