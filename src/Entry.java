/** Eine einfache Implementierung von Einträgen für unsere HashMap
 * @param <Key>  Daten des Schluesselwertes
 * @param <Value> Datentyp des Wertes
 */
public class Entry<Key, Value> {
    final Key key; // der Schluesselwert
    Value value; // der dem Schluessel zugeordnete Wert des Eintrags

    public Entry(Key key, Value value) {
        this.key = key;
        this.value = value;
    }

    public Key getKey() {
        return this.key;
    }

    public Value getValue() {
        return this.value;
    }

}
