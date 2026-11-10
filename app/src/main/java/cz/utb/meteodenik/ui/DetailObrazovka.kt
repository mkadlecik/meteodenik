package cz.utb.meteodenik.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.utb.meteodenik.R

@Composable
fun DetailObrazovka(mesto: String, teplota: Double) {
    val context = LocalContext.current
    val teplotaJeZnama = !teplota.isNaN()
    val textKeSdileni =
        if (teplotaJeZnama) stringResource(R.string.sdileny_text, mesto, teplota) else mesto

    Scaffold { insety ->
        Column(modifier = Modifier.padding(insety).padding(16.dp)) {

            Text(
                text = stringResource(R.string.detail_titulek),
                style = MaterialTheme.typography.labelLarge
            )
            Text(text = mesto, style = MaterialTheme.typography.headlineMedium)
            Text(
                text = if (teplotaJeZnama) {
                    stringResource(R.string.teplota_format, teplota)
                } else {
                    stringResource(R.string.teplota_chybi)
                },
                style = MaterialTheme.typography.titleLarge
            )
            if (teplotaJeZnama) {
                Text(
                    text = stringResource(R.string.detail_souhrn, mesto, teplota),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Button(
                onClick = {
                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, textKeSdileni)
                    }
                    try {
                        context.startActivity(Intent.createChooser(sendIntent, null))
                    } catch (e: ActivityNotFoundException) {
                        Log.w("Cyklus", "Nic na tomto zařízení neumí přijmout text", e)
                    }
                },
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text(stringResource(R.string.sdilet_zaznam))
            }
        }
    }
}