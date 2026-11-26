package cz.utb.meteodenik.ui.mesta

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.utb.meteodenik.R
import cz.utb.meteodenik.model.Mesto
import cz.utb.meteodenik.model.MeteoZaznam
import cz.utb.meteodenik.model.StavPocasi
import cz.utb.meteodenik.ui.textProUzivatele
import cz.utb.meteodenik.ui.theme.MeteoDenikTheme
import java.time.LocalDateTime

@Composable
fun MestoKarta(
    radek: MestoSPocasim,
    onMestoKliknuto: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = { onMestoKliknuto(radek.mesto.id) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = radek.mesto.nazev, style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = radek.pocasi?.let { stringResource(R.string.teplota_format, it.teplotaC) }
                        ?: stringResource(R.string.teplota_chybi),
                    style = MaterialTheme.typography.bodyMedium,
                )
                if (radek.pocasi != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "•", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(radek.pocasi.stav.textProUzivatele()),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MestoKartaPreview() {
    MeteoDenikTheme {
        MestoKarta(
            radek = MestoSPocasim(
                mesto = Mesto(1, "Zlín", 49.2265, 17.6683),
                pocasi = MeteoZaznam(
                    cas = LocalDateTime.now(),
                    teplotaC = 21.4,
                    vlhkostProcent = 65,
                    rychlostVetruKmH = 12.0,
                    stav = StavPocasi.POLOJASNO,
                ),
            ),
            onMestoKliknuto = {},
        )
    }
}
