package netsfinal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *
 * @param <V>
 *            {@inheritDoc}
 * @param <Key>
 *            {@inheritDoc}
 *
 * @author joncho, 16sp
 */
public class BinaryMinHeapImpl<V, Key extends Comparable<Key>> {
	List<Entry<V, Key>> heap;
	Map<V, Integer> locate;

	/**
	 * {@inheritDoc}
	 */
	public int size() {
		return heap.size();
	}

	public BinaryMinHeapImpl() {
		locate = new HashMap<V, Integer>();
		heap = new ArrayList<Entry<V, Key>>();
	}

	public boolean isEmpty() {
		return heap.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean containsValue(V value) {
		return locate.containsKey(value);
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(V value, Key key) {
		if (!locate.containsKey(value) && key != null) {
			Entry<V, Key> entry = new Entry<V, Key>(value, key);
			locate.put(value, heap.size());
			heap.add(entry);
			heapifyUp(heap.size() - 1);
		}
	}

	private void heapifyUp(int i) {
		while (i > 0) {
			int parent = Math.floorDiv(i, 2);
			if (heap.get(parent).getKey().compareTo(heap.get(i).getKey()) > 0) {
				swap(parent, i);
				i = parent;
				parent = Math.floorDiv(i, 2);
			} else {
				break;
			}
		}
	}

	private void swap(int parent, int i) {
		Entry<V, Key> childEntry = heap.get(i);
		Entry<V, Key> parentEntry = heap.get(parent);
		heap.set(i, parentEntry);
		heap.set(parent, childEntry);
		locate.put(parentEntry.getValue(), i);
		locate.put(childEntry.getValue(), parent);
	}

	/**
	 * {@inheritDoc}
	 */
	public void decreaseKey(V value, Key newKey) {
		if (!locate.containsKey(value) || newKey == null) {
			throw new IllegalArgumentException();
		}
		int index = locate.get(value);
		Entry<V, Key> entry = heap.get(index);
		if (entry.getKey().compareTo(newKey) < 0) {
			throw new IllegalArgumentException();
		}
		entry.setKey(newKey);
		heapifyUp(index);
	}

	/**
	 * {@inheritDoc}
	 */
	public V peek() {
		return heap.get(0).getValue();
	}

	/**
	 * {@inheritDoc}
	 */
	public V extractMin() {
		if (heap.isEmpty()) {
			throw new NoSuchElementException();
		}
		Entry<V, Key> min = heap.get(0);
		Entry<V, Key> last = heap.get(heap.size() - 1);
		heap.set(0, last);
		locate.put(last.value, 0);
		locate.remove(min.value);
		heap.remove(heap.size() - 1);
		heapify(0);
		return min.value;
	}

	public Key getKey(V v) {
		return heap.get(locate.get(v)).getKey();
	}

	private void heapify(int i) {
		int leftChild = i * 2;
		int rightChild = leftChild + 1;
		if (rightChild < heap.size()) {
			Key left = heap.get(leftChild).getKey();
			Key right = heap.get(rightChild).getKey();
			Key self = heap.get(i).getKey();
			if (left.compareTo(self) < 0 || right.compareTo(self) < 0) {
				if (left.compareTo(right) < 0) {
					swap(i, leftChild);
					heapify(leftChild);
				} else {
					swap(i, rightChild);
					heapify(rightChild);
				}
			}
		} else if (leftChild < heap.size()) {
			if (heap.get(leftChild).getKey().compareTo(heap.get(i).getKey()) < 0) {
				swap(i, leftChild);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<V> values() {
		return locate.keySet();
	}

	/**
	 * Helper entry class for maintaining value-key pairs. The underlying
	 * indexed list for your heap will contain these entries.
	 *
	 * You are not required to use this, but we recommend it.
	 */
	class Entry<A, B> {

		private A value;
		private B key;

		public Entry(A value, B key) {
			this.value = value;
			this.key = key;
		}

		/**
		 * @return the value stored in the entry
		 */
		public A getValue() {
			return this.value;
		}

		/**
		 * @return the key stored in the entry
		 */
		public B getKey() {
			return this.key;
		}

		/**
		 * Changes the key of the entry.
		 *
		 * @param key
		 *            the new key
		 * @return the old key
		 */
		public B setKey(B key) {
			B oldKey = this.key;
			this.key = key;
			return oldKey;
		}

	}

}
