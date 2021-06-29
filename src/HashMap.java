import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Eine einfache Implementierung einer HashMap.
 *
 * @param <Key>   Datentyp des Schluessels
 * @param <Value> Datentyp des Wertes
 */
public class HashMap<Key, Value> {
    private static final int m = 997; // initialisiere m mit 997.
    private Bucket[] T; // T ist das Array der Buckets (die verketteten Listen)

    /**
     * Erzeugt eine neue, leere HashMap der Tabellengröße m.
     */
    public HashMap() {
        this.T = new Bucket[m];
    }

    /**
     * Fügt ein neues Element in die HashMap ein. Falls ein Element mit gleichem Schluessel existiert,
     * wird der Wert des bereits existierenden Eintrags mit dem des neuen ueberschrieben.
     *
     * @param key   der Wert des Schluessels des neuen Elements
     * @param value der Wert des neuen Elements
     */
    public void put(Key key, Value value) {
        int h = getHash(key);
        if (T[h] == null) T[h] = new Bucket(); // falls noch keine Liste an Index h existiert, erzeuge eine neue

        if (!this.contains(key)) { // falls der Eintrag noch nicht existiert, füge ihn in die HashMap ein.
            T[h].addEntry(new Entry<>(key, value));
        } else { // falls doch, suche nach dem Eintrag und ersetze den Wert.
            for (int i = 0; i < T[h].getEntries().size(); i++) {
                if (T[h].getEntries().get(i).getKey().equals(key)) T[h].getEntries().get(i).value = value;
            }
        }
    }

    /**
     * Sucht in der Hashmap nach einem Element mit dem angegebenen Schluesselwert.
     *
     * @param key der Schluesselwert, nach dem gesucht werden soll.
     * @return gibt genau dann true zurueck, wenn es einen Eintrag gibt, dessen Schluesselwert dem gesuchten gleicht
     * und dem ein Wert zugewiesen ist.
     */
    public boolean contains(Key key) {
        int h = getHash(key);
        if (this.T[h] != null) {
            for (int i = 0; i < T[h].size(); i++) {
                if (T[h].getEntries().get(i).getKey().equals(key) && T[h].getEntries().get(i).getValue() != null)
                    return true;
            }
        }
        return false;
    }

    /**
     * Sucht nach dem Element mit dem angegebenen Schluesselwert und gibt den zugewiesenen Wert zurueck.
     *
     * @param key der Schluesselwert, nach dem gesucht werden soll.
     * @return den Wert des Eintrages, sollte dieser existieren und null, falls nicht.
     */
    public Value get(Key key) {
        if (this.contains(key)) {
            int h = getHash(key);
            for (int i = 0; i < T[h].getEntries().size(); i++) {
                if (T[h].getEntries().get(i).getKey().equals(key)) {
                    return (Value) T[h].getEntries().get(i).value;
                }
            }
        }
        return null;
    }

    /**
     * Loescht den Wert eines Eintrages in der HashMap.
     *
     * @param key der Schluessel des Eintrages, dessen Wert geloescht werden soll.
     */
    public void delete(Key key) {
        int h = getHash(key);
        for (int i = 0; i < T[h].getEntries().size(); i++) {
            if (T[h].getEntries().get(i).getKey().equals(key)) T[h].getEntries().get(i).value = null;
        }
    }

    /**
     * Berechnet den Hashwert eines Schluessels
     *
     * @param key der Schluessel, dessen Hashwert berechnet werden soll.
     * @return der berechnete Hashwert.
     */
    private int getHash(Key key) {
        return (key.hashCode() & 0xfffffff) % m;
    }

    /**
     * Ein Programmm, welches eine Textdatei Wort fuer Wort einliest, die Haeufigkeit jedes vorkommenden Wortes zaehlt
     * und die haeufigsten 10 Woerter anschliessend auf dem Bildschirm ausgibt.
     * Gibt am Ende ausserdem die Laengen der Listen X_1 bis X_m der HashMap aus.
     *
     * @param args der Dateipfad zu jener, die eingelesen werden soll.
     * @throws FileNotFoundException falls die Datei nicht gefunden werden kann.
     */
    public static void main(String[] args) throws FileNotFoundException {
        HashMap<String, Integer> map = new HashMap<>(); // neue HashMap erzeugen
        String path = args[0]; // Dateipfad aus Kommandozeilenparameter uebernehmen
        Scanner read = new Scanner(new File(path)).useDelimiter("[ ,:'/!;?.]+"); // Satzzeichen etc. werden nicht beachtet

        // Datei Wort fuer Wort einlesen und in die HashMap einfuegen.
        String word;
        while (read.hasNext()) {
            word = read.next();
            if (map.contains(word)) { // falls das Wort schon einmal gelesen wurde, erhoehe den Zaehler
                map.put(word, map.get(word) + 1);
            } else { // falls nicht, fuege es in die HashMap ein.
                map.put(word, 1);
            }
        }

        // Die 10 hauefigsten Woerter nach und nach bestimmen und auf dem Bildschirm ausgeben.
        System.out.println("Die 10 häufigsten Wörter:\n");
        Entry<String, Integer>[] words = new Entry[10];
        for (int i = 0; i < words.length; i++) {

            for (int j = 0; j < map.T.length; j++) {
                for (int k = 0; k < map.T[j].getEntries().size(); k++) {

                    if (words[i] == null) { // falls Array noch nicht voll ist: hinzufügen
                        words[i] = map.T[j].getEntries().get(k);

                    } else if (words[i].getValue() < (Integer) map.T[j].getEntries().get(k).getValue()) { // falls ein Wort mit höherer Häufigkeit gefunden wurde

                        // überprüfen, ob wort schon in Array ist
                        boolean added = false;
                        for (int x = 0; x < i; x++) {
                            if (words[x].equals(map.T[j].getEntries().get(k))) {
                                added = true;
                                break;
                            }
                        }
                        if (!added) words[i] = map.T[j].getEntries().get(k); // falls nicht: hinzufügen
                    }
                }
            }
            System.out.println(words[i].key + " Häufigkeit: " + words[i].value);
        }

        // Listenlaengen auf dem Bildschirm ausgeben
        System.out.println("\nListenlängen X_1 bis X_m: ");
        for (int i = 0; i < map.T.length; i++) {
            if (map.T[i] != null) System.out.println("X_" + (i + 1) + " Länge: " + map.T[i].size());
        }

    }
}
