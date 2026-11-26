package cz.utb.meteodenik.ui.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.utb.meteodenik.R
import cz.utb.meteodenik.model.Mesto
import cz.utb.meteodenik.ui.theme.MeteoDenikTheme

@Composable
fun DetailObrazovka(
    onZpet: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(),
) {
    val stav by viewModel.uiState.collectAsStateWithLifecycle()
    DetailObsah(
        stav = stav,
        onZpet = onZpet,
        modifier = modifier,
    )
}

@Composable
private fun DetailObsah(
    stav: DetailUiState,
    onZpet: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = modifier) { insety ->
        Column(
            modifier = Modifier
                .padding(insety)
                .padding(16.dp),
        ) {
            val mesto = stav.mesto
            if (mesto != null) {
                Text(
                    text = mesto.nazev,
                    style = MaterialTheme.typography.headlineMedium,
                )
            } else {
                Text(
                    text = stringResource(R.string.mesto_nezname),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onZpet) {
                Text(stringResource(R.string.zpet))
            }
        }
    }
}

@Preview(name = "Detail — světlá", showBackground = true)
@Preview(name = "Detail — tmavá", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DetailObrazovkaPreview() {
    MeteoDenikTheme {
        DetailObsah(
            stav = DetailUiState(mesto = Mesto(1, "Brno", 49.1951, 16.6068)),
            onZpet = {},
        )
    }
}

@Preview(name = "Detail — neexistující město", showBackground = true)
@Composable
private fun DetailObrazovkaNeznamyPreview() {
    MeteoDenikTheme {
        DetailObsah(
            stav = DetailUiState(mesto = null),
            onZpet = {},
        )
    }
}
