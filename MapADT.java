import java.util.*;
/**
 * A simplified version of Java's Map interface.
 * 
 * @author John Donaldson
 */

public interface MapADT<K,V> {

    /**
     * Determines if this Map is empty.
     * @return true if the Map is empty, false otherwise
     */   
    public boolean isEmpty();

    /**
     * Removes all entries from this Map.
     */   
    public void clear();

    /**
     * Determines the number of entries in this Map.
     * @return a count of the number of key-value pairs in this Map
     */   
    public int size();

    /**
     * Retrieves the value associated with the given key.  If no mapping
     * exists for this key, null is returned.
     * @param key the key to search for
     * @return the value associated with this key, if any; null otherwise
     */   
   public V get(K key);

    /**
     * Inserts a key-value pair into the map.  If the key is already present,
     * its value is replaced with the specified value.
     * @param key the key to insert
     * @param value the value to insert
     * @return the previous value associated with this key, if
     * any; null otherwise
     */   
   public V put(K key, V value);

    /**
     * Creates an inorder iterator for all keys in the map.
     * @return the iterator
     */   
   public Iterator<K> keys();

    /**
     * Creates an inorder iterator for all key-value pairs in the map.
     * @return the iterator
     */   
   public Iterator<Map.Entry<K,V>> entries();
   
}