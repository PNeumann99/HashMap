import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HashMap<Key, Value> {
    private static final int m = 997;
    private Bucket[] T;

    public HashMap() {
        this.T = new Bucket[m];
    }

    public void put(Key key, Value value) {
        int h = getHash(key);
        if (T[h] == null) T[h] = new Bucket();
        if (!this.contains(key)) {
            T[h].addEntry(new Entry<>(key, value));
        } else {
            for (int i = 0; i < T[h].getEntries().size(); i++) {
                if (T[h].getEntries().get(i).getKey().equals(key)) T[h].getEntries().get(i).value = value;
            }
        }
    }

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

    public void delete(Key key) {
        int h = getHash(key);
        for (int i = 0; i < T[h].getEntries().size(); i++) {
            if (T[h].getEntries().get(i).getKey().equals(key)) T[h].getEntries().get(i).value = null;
        }
    }

    private int getHash(Key key) {
        return (key.hashCode() & 0xfffffff) % m;
    }

    public static void main(String[] args) throws FileNotFoundException {
        HashMap<String, Integer> map = new HashMap<>();
        String path = args[0];
        Scanner read = new Scanner(new File(path)).useDelimiter("[ ,:'/!;?.]+");

        String word;
        while (read.hasNext()) {
            word = read.next();
            if (map.contains(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        }

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

        System.out.println("\nListenlängen X_1 bis X_m: ");
        for (int i = 0; i < map.T.length; i++) {
            if (map.T[i] != null) System.out.println("X_" + (i + 1) + " Länge: " + map.T[i].size());
        }


    }
}
