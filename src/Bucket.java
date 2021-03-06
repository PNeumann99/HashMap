import java.util.LinkedList;
import java.util.List;

/**
 * Eine einfache Implementierung der verketteten Listen für die HashMap
 */
public class Bucket {
    private List<Entry> entries;

    public Bucket() {
        entries = new LinkedList<>();
    }

    public int size() {
        if (entries == null) return -1;
        return entries.size();
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public List<Entry> getEntries() {
        if (!entries.isEmpty()) return entries;
        return null;
    }

    public void addEntry(Entry entry) {
        this.entries.add(entry);
    }

    public void removeEntry(Entry entry) {
        this.entries.remove(entry);
    }
}
