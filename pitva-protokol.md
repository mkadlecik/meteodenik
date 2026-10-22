# Dekompilační protokol (Cvičení 4.1)

## 1. Tabulka konstrukcí

| Co jsem napsal v Kotlinu | Jak se jmenuje generovaný člen v Javě (přesný podpis) | Kolik řádků bych psal v Javě ručně |
| :--- | :--- | :--- |
| `data class Zapis(...)` | `public final class Zapis`<br>gettery: `getMesto()`, `getTeplota()`, `getPoznamka()`<br>další: `component1()`, `component2()`, `component3()`, `copy()`, `toString()`, `hashCode()`, `equals(Object)` | cca 50–70 řádků (třída, atributy, konstruktor, gettery, equals, hashCode a toString) |
| `object Prevody { const val ABSOLUTNI_NULA = -273.15; fun naFahrenheita(...) }` | `public static final Prevody INSTANCE;`<br>`public static final double ABSOLUTNI_NULA = -273.15;`<br>`public final double naFahrenheita(double celsia)` | cca 15–20 řádků (klasický Singleton s privátním konstruktorem a statickou instancí) |
| `fun Double.zaokrouhlenoNaDesetiny(): Double` | `public static final double zaokrouhlenoNaDesetiny(double $this$zaokrouhlenoNaDesetiny)` | cca 5 řádků (statická pomocná metoda v utility třídě) |
| `fun prumer(zapisy: List<Zapis>, vyber: (Zapis) -> Double): Double` | `public static final double prumer(List zapisy, Function1 vyber)` | cca 10–15 řádků (musel bych použít funkční rozhraní z Javy nebo si napsat vlastní) |
| `suspend fun nactiPoZdrzeni(zapisy: List<Zapis>): List<Zapis>` | `public static final Object nactiPoZdrzeni(List zapisy, Continuation $completion)` | cca 40–50 řádků (ruční stavový automat a práce s callbackem) |

---

## 2. Odpovědi na otázky

### 1. Kolik metod má dekompilovaná třída Zapis a které z nich nemají ve zdrojovém kódu Kotlinu ani náznak?
Když nepočítám konstruktory, třída má celkem 11 metod. Patří sem gettery pro jednotlivé proměnné, metody `component1()`, `component2()` a `component3()`, dvě varianty metody `copy()` a trojice `equals()`, `hashCode()` a `toString()`. V původním kódu Kotlinu vůbec nejsou vidět metody pro destrukci (`componentN`), kopírování (`copy`) ani přepsané metody z `Objectu` (`equals`, `hashCode`, `toString`). Kompilátor je všechny vygeneroval sám jen proto, že jsme třídu označili slovem `data`.

### 2. Proč má Zapis v dekompilované podobě konstruktor navíc — ten s parametry int a DefaultConstructorMarker? S čím to souvisí v kotlinovém zdrojáku?
Souvisí to s tím, že parametr `poznamka` má v Kotlinu nastavenou výchozí hodnotu `""`. Java ale výchozí parametry neumí, takže kompilátor vyrobí pomocný konstruktor. V něm předává `int` jako bitovou masku, která říká, jaké parametry jsme vynechali a mají se vzít z výchozích hodnot. Typ `DefaultConstructorMarker` je tam jen jako prázdná značka, aby se podpis tohoto konstruktoru nepobil s nějakým normálním konstruktorem.

### 3. Jaký je první parametr metody zaokrouhlenoNaDesetiny a co z toho plyne pro to, jestli je funkce polymorfní?
Prvním parametrem je původní číslo před tečkou, v kódu pojmenované jako `$this$zaokrouhlenoNaDesetiny` typu `double`. Z dekompilace je vidět, že kompilátor udělal obyčejnou statickou metodu (`public static final`). Z toho plyne, že funkce není polymorfní — nerozhoduje se za běhu podle skutečného typu objektu (jako virtuální metody), ale volá se na tvrdo staticky už při překladu.

### 4. Jaký typ má druhý parametr funkce prumer a co to prozrazuje o tom, jak JVM reprezentuje funkci jako hodnotu?
Parametr má typ `Function1`. Z toho je jasné, že JVM sama o sobě neumí pracovat s funkcí jako s proměnnou, takže ji Kotlin musí obalit do obyčejného javovského objektu (instance rozhraní). V dekompilovaném kódu se pak tato předaná funkce nespouští přímo závorkami, ale zavoláním metody `vyber.invoke(...)`.

### 5. Co se stalo s návratovým typem funkce nactiPoZdrzeni a proč se v ní objevil switch?
Místo původního seznamu `List<Zapis>` vrací metoda obecný `Object`. Je to proto, že funkce může buď vrátit hotový výsledek, nebo speciální konstantu `COROUTINE_SUSPENDED`, když se zrovna pozastaví a čeká. Příkaz `switch` uvnitř funguje jako stavový automat řízený proměnnou `label` — díky němu metoda po probuzení z `delay` ví, v jakém stavu skončila, a skočí přesně na místo, kde byla předtím přerušena.