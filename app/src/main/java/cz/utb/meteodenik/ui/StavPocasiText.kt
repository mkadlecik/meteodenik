package cz.utb.meteodenik.ui

import androidx.annotation.StringRes
import cz.utb.meteodenik.R
import cz.utb.meteodenik.model.StavPocasi

@StringRes
fun StavPocasi.textProUzivatele(): Int = when (this) {
    StavPocasi.JASNO -> R.string.pocasi_jasno
    StavPocasi.POLOJASNO -> R.string.pocasi_polojasno
    StavPocasi.ZATAZENO -> R.string.pocasi_zatazeno
    StavPocasi.MLHA -> R.string.pocasi_mlha
    StavPocasi.MRHOLENI -> R.string.pocasi_mrholeni
    StavPocasi.DEST -> R.string.pocasi_dest
    StavPocasi.SNEZENI -> R.string.pocasi_snezeni
    StavPocasi.PREHANKY -> R.string.pocasi_prehanky
    StavPocasi.BOURKA -> R.string.pocasi_bourka
    StavPocasi.NEZNAMY -> R.string.pocasi_neznamy
}
