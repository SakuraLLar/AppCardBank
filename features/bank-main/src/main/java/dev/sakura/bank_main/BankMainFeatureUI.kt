package dev.sakura.bank_main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.sakura.bank.uikit.BankTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun BankMainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Bank Card") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BankTheme.colorScheme.primaryContainer,
                    titleContentColor = BankTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "bin_entry",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("bin_entry") { BinEntryScreen(navController = navController) }
            composable("bin_history") { BinHistoryScreen(navController = navController) }
        }
    }
}


@Composable
internal fun BinEntryScreen(
    navController: NavController,
    binEntryViewModel: BinEntryViewModel = hiltViewModel(),
) {
    val bin by binEntryViewModel.bin.collectAsState()
    val binInfo by binEntryViewModel.binInfo.collectAsState()
    val error by binEntryViewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = bin,
            onValueChange = { binEntryViewModel.updateBin(it) },
            label = { Text("Введите BIN") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { binEntryViewModel.fetchBinInfo(bin) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Поиск")
            }
            Button(
                onClick = { navController.navigate("bin_history") },
                modifier = Modifier.weight(1f)
            ) {
                Text("История")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (binInfo != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Информация о BIN:", style = BankTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Схема: ${binInfo?.scheme}", style = BankTheme.typography.bodyMedium)
                    Text("Тип: ${binInfo?.type}", style = BankTheme.typography.bodyMedium)
                    Text("Страна: ${binInfo?.country?.name}", style = BankTheme.typography.bodyMedium)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (error != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = BankTheme.colorScheme.errorContainer)
            ) {
                Text(
                    "Ошибка: $error",
                    color = BankTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
internal fun BinHistoryScreen(
    navController: NavController,
    binHistoryViewModel: BinHistoryViewModel = hiltViewModel(),
) {
    val history by binHistoryViewModel.history.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "История запросов",
            style = BankTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Button(onClick = { showDialog = true }) {
            Text("Очистить")
        }

        LazyColumn {
            items(history) { item ->
                var expanded by remember { mutableStateOf(false) }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { expanded = !expanded },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "BIN: ${item.bin}", style = BankTheme.typography.bodyLarge)
                        if (expanded) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Схема: ${item.scheme ?: "N/A"}")
                            Text(text = "Тип: ${item.type ?: "N/A"}")
                            Text(text = "Бренд: ${item.brand ?: "N/A"}")
                            Text(text = "Страна: ${item.country ?: "N/A"}")
                            Text(text = "Банк: ${item.bank ?: "N/A"}")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Очистить историю") },
            text = { Text("Вы уверены, что хотите очистить историю?") },
            confirmButton = {
                TextButton(onClick = {
                    binHistoryViewModel.clearHistory()
                    showDialog = false
                }) {
                    Text("Очистить")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}
