package cz.utb.meteodenik.ui.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
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
import cz.utb.meteodenik.model.MeteoZaznam
import cz.utb.meteodenik.model.StavPocasi
import cz.utb.meteodenik.ui.textProUzivatele
import cz.utb.meteodenik.ui.theme.MeteoDenikTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
    val casFormater = DateTimeFormatter.ofPattern("HH:mm")

    Scaffold(modifier = modifier) { insety ->
        Column(
            modifier = Modifier
                .padding(insety)
                .padding(16.dp),
        ) {
            if (stav.nacitaSe) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp))
            }

            val mesto = stav.mesto
            val pocasi = stav.pocasi

            if (mesto != null) {
                Text(
                    text = mesto.nazev,
                    style = MaterialTheme.typography.headlineLarge,
                )
                Spacer(modifier = Modifier.height(12.dp))

                if (pocasi != null) {
                    Text(
                        text = stringResource(R.string.teplota_format, pocasi.teplotaC),
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = stringResource(pocasi.stav.textProUzivatele()),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.detail_vlhkost, pocasi.vlhkostProcent),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.detail_vitr, pocasi.rychlostVetruKmH),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.detail_cas_mereni, pocasi.cas.format(casFormater)),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                } else {
                    Text(
                        text = stringResource(R.string.teplota_chybi),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
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
            stav = DetailUiState(
                mesto = Mesto(1, "Brno", 49.1951, 16.6068),
                pocasi = MeteoZaznam(
                    cas = LocalDateTime.now(),
                    teplotaC = 23.1,
                    vlhkostProcent = 58,
                    rychlostVetruKmH = 14.5,
                    stav = StavPocasi.POLOJASNO,
                ),
            ),
            onZpet = {},
        )
    }
}
