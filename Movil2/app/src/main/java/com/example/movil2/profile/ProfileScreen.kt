package com.example.movil2.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.json.JSONObject

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val uiState by remember { derivedStateOf { viewModel.uiState } }

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        //Text("DEBUG: Estado actual = ${uiState::class.simpleName}")

        Text(
            "Perfil Académico",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (uiState) {
            is ProfileUiState.Loading -> {
                CircularProgressIndicator()
            }
            is ProfileUiState.Error -> {
                Text((uiState as ProfileUiState.Error).message, color = MaterialTheme.colorScheme.error)
            }
            is ProfileUiState.Success -> {
                val json = (uiState as ProfileUiState.Success).json
                val nombre = json.optString("nombre")
                val matricula = json.optString("matricula")
                val carrera = json.optString("carrera")
                val especialidad = json.optString("especialidad")
                val semestre = json.optInt("semActual")
                val cdtosAcumulados = json.optInt("cdtosAcumulados")
                val cdtosActuales = json.optInt("cdtosActuales")
                val estatus = json.optString("estatus")
                val fechaReins = json.optString("fechaReins")

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Nombre: $nombre")
                    Text("Matrícula: $matricula")
                    Text("Carrera: $carrera")
                    Text("Especialidad: $especialidad")
                    Text("Semestre actual: $semestre")
                    Text("Créditos acumulados: $cdtosAcumulados")
                    Text("Créditos actuales: $cdtosActuales")
                    Text("Estatus: $estatus")
                    Text("Fecha de reinscripción: $fechaReins")
                }
            }
        }
    }
}
 