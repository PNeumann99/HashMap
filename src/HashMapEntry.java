public class HashMapEntry<Key, Value> {
    public final Key key;
    public Value value;
    public HashMapEntry<Key, Value> next;
    int hash;

    public HashMapEntry(Key key, Value value, HashMapEntry<Key, Value> next){
        this.key = key;
        this.value = value;
        this.next = next;
    }

}
