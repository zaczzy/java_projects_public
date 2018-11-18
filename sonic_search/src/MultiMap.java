package netsfinal;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of a {@link RecordInvertedIndex}. For testing purposes, this
 * class must provide a public default no-argument constructor. This means that
 * you do not need to write any constructor at all; if you do write other
 * constructors, be sure you have one that is public and takes no arguments.
 *
 * @param <T> the type of the values in the inverted index
 * @author davix
 */
public class MultiMap<T> {
	Map<String, Set<T>> map;
    public MultiMap() {
		map = new HashMap<>();
	}

	public void addRecord(String key, T t) {
        if (!map.containsKey(key)) {
        	Set<T> s = new HashSet<>();
        	s.add(t);
        	map.put(key, s);
        } else {
        	Set<T> s = map.get(key);
        	s.add(t);
        	map.put(key, s);
        }
    }

    public Set<T> getRecords(String key) {
        return map.get(key);
    }

    public Set<T> deleteRecords(String key) {
        return map.remove(key);
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public Set<String> keySet() {
        return map.keySet();
    }

    public int size() {
        return map.size();
    }

    public int count(String key) {
    	if(key == null || !map.containsKey(key)) {
    		return 0;
    	}
        return map.get(key).size();
    }
}
