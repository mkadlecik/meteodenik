# Oprávnění k poloze - 6.2

## Čtyři stavy žádosti o oprávnění

| Stav | Co uživatel udělal | Co aplikace zobrazí |
|---|---|---|
| NEZNAMY | ještě nic | tlačítko "Zjistit moji polohu" |
| ODMITNUTO | jednou Don't allow | vysvětlení + "Rozumím, pokračovat" |
| ODMITNUTO_NATRVALO | dvakrát Don't allow | text „Bez přístupu k poloze funguje vše ostatní…“ + tlačítko "Otevřít nastavení aplikace" |
| UDELENO | While using the app / Only this time, nebo povolení v Nastavení | souřadnice, nebo hlášku o nedostupnosti |

### Snímky obrazovky jednotlivých stavů:

* [Screenshot 1 - Systémový dialog](stav1-dialog.PNG)
* [Screenshot 2 - Naše vysvětlení (Rationale)](stav2-vysvetleni.PNG)
* [Screenshot 3 - Trvalé zamítnutí a tlačítko Nastavení](stav3-nastaveni.PNG)
* [Screenshot 4 - Zobrazené souřadnice](stav4-povoleno.PNG)

## Proč používáme ACCESS_COARSE_LOCATION a ne ACCESS_FINE_LOCATION?

Přesná poloha (FINE) dokáže určit pozici s přesností na zhruba 50 metrů, zatímco přibližná (COARSE) zhruba na 3 km². Jelikož nám v MeteoDeníku jde jen o zjištění nejbližšího města, takto široká oblast je naprosto dostačující. 

Vyžadování přesné polohy navíc víc vybíjí baterii (GPS čip) a uživatelé jsou na něj z pohledu soukromí citlivější, což by zbytečně vedlo k častějšímu zamítnutí žádosti. Pro schválení na Google Play bychom navíc museli obhajovat, proč pro obyčejnou předpověď počasí potřebujeme vědět polohu s přesností na metry.