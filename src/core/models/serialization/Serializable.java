package core.models.serialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Serializable model to deserialize and serialize JSON files.
 *
 * @author Sian Lodde
 */
public class Serializable {
    private Object buffer = null;
    private Object arrayBufferKey = null;
    private List<Object> arrayBuffer = null;
    private Serializable previousObject;

    private Map<Object, Object> pairs = new HashMap<>();
    private Map<Object, Serializable> objects = new HashMap<>();
    private Map<Object, List<Object>> arrays = new HashMap<>();

    /**
     * get data in either the pairs map, the objects map or the arrays map.
     *
     * @param path The full path with the key included, relative from the Serializable you are calling it on.
     * @return The data associated as value with the provided key.
     */
    public Object getPath(Object... path) {
        Object key = path[path.length - 1];
        Serializable object = null;

        for (int i = 0; i < path.length - 1; i++) {
            object = object.getObject(path[i]);
        }

        if (object != null) {
            return ((Serializable) path[0]).get(key);
        }
        else if (contains(key)) {
            return get(key);
        }

        throw new IllegalArgumentException("Cannot find path \"" + path + "\" in sterialized data!");
    }

    public Object find(Object key) {
        return findRecursive(key);
    }

    private Object findRecursive(Object key) {
        if (hasKey(key))
            return get(key);

        for (Serializable o : objects.values()) {
            Object result = o.findRecursive(key);

            if (result != null)
                return result;
        }

        return null;
    }

    public Object popRecursive(Object key) {
        if (hasKey(key))
            return pop(key);

        for (Serializable o : objects.values()) {
            Object result = o.findRecursive(key);

            if (result != null)
                return result;
        }

        return null;
    }

    public boolean isEmpty() {
        return pairs.size() <= 0 && arrays.size() <= 0 && objects.size() <= 0;
    }

    public Object get(Object key) {
        if (hasPair(key)) return getPair(key);
        if (hasArray(key)) return getArray(key);
        if (hasObject(key)) return getObject(key);

        return null;
    }

    public Object pop(Object key) {
        Set<Object> keySet = objects.keySet();
        if (keySet.size() == 1 && keySet.contains(null)) {
            Serializable serializable = objects.get(null);
            if (serializable.hasPair(key)) return serializable.popPair(key);
            if (serializable.hasArray(key)) return serializable.popArray(key);
            if (serializable.hasObject(key)) return serializable.popObject(key);
        }

        if (hasPair(key)) return popPair(key);
        if (hasArray(key)) return popArray(key);
        if (hasObject(key)) return popObject(key);

        return null;
    }

    public void append(Serializable serializable) {
        objects.putAll(serializable.objects);
        arrays.putAll(serializable.arrays);
        pairs.putAll(serializable.pairs);
    }

    public boolean contains(Object key) {
        return hasKey(key);
    }

    public boolean hasKey(Object key) {
        return hasPair(key) || hasObject(key) || hasArray(key);
    }

    /**
     * Recursively finds an object in the serializable.
     *
     * @param key object to find
     * @return object
     */
    public Serializable findObject(Object key) {
        if (hasObject(key)) {
            return getObject(key);
        }

        for (Serializable o : objects.values()) {
            o.findObject(key);
        }

        throw new IllegalArgumentException("Cannot find key " + key + " in sterialized data!");
    }

    public Object getPair(Object key) {
        Set<Object> keySet = objects.keySet();
        if (keySet.size() == 1 && keySet.contains(null)) {
            Serializable serializable = objects.get(null);
            if (serializable.hasPair(key)) return serializable.getPair(key);
        }

        return pairs.get(key);
    }

    public Object popPair(Object key) {
        Object value = getPair(key);
        pairs.remove(key);

        return value;
    }

    public boolean hasPair(Object key) {
        Set<Object> keySet = objects.keySet();
        if (keySet.size() == 1 && keySet.contains(null)) {
            Serializable serializable = objects.get(null);
            return serializable.hasPair(key);
        }

        return pairs.containsKey(key);
    }

    public Serializable getObject(Object key) {
        Set<Object> keySet = objects.keySet();
        if (keySet.size() == 1 && keySet.contains(null)) {
            Serializable serializable = objects.get(null);
            if (serializable.hasObject(key)) return serializable.getObject(key);
        }

        return objects.get(key);
    }

    public boolean hasObject(Object key) {
        Set<Object> keySet = objects.keySet();
        if (keySet.size() == 1 && keySet.contains(null)) {
            Serializable serializable = objects.get(null);
            return serializable.hasObject(key);
        }

        return objects.containsKey(key);
    }

    public Serializable popObject(Object key) {
        Serializable object = objects.get(key);
        objects.remove(key);

        return object;
    }

    public boolean hasArray(Object key) {
        Set<Object> keySet = objects.keySet();
        if (keySet.size() == 1 && keySet.contains(null)) {
            Serializable serializable = objects.get(null);
            return serializable.hasArray(key);
        }

        return arrays.containsKey(key);
    }

    public List<Object> getArray(Object key) {
        Set<Object> keySet = objects.keySet();
        if (keySet.size() == 1 && keySet.contains(null)) {
            Serializable serializable = objects.get(null);
            if (serializable.hasArray(key)) return serializable.getArray(key);
        }

        return arrays.get(key);
    }

    public Object popArray(Object key) {
        List<Object> array = arrays.get(key);
        arrays.remove(key);

        return array;
    }

    public Map<Object, Object> getPairs() {
        return pairs;
    }

    public Map<Object, List<Object>> getArrays() {
        return arrays;
    }

    public Map<Object, Serializable> getObjects() {
        return objects;
    }

    public int getSize() {
        return pairs.size() + arrays.size() + objects.size();
    }

    public Serializable openObject() {
        return openObject(buffer);
    }

    public Serializable openObject(Object name) {
        Serializable serializable = new Serializable();
        serializable.previousObject = this;

        // Set the name of the object / array to the key stored in the buffer
        objects.put(name, serializable);
        buffer = null;

        return serializable;
    }

    public Serializable closeObject() {
        if (previousObject.getSize() == 1 && previousObject.objects.containsKey(null)) {
            return this;
        }

        return previousObject;
    }

    public Serializable openArray() {
        return openArray(buffer);
    }

    public Serializable openArray(Object name) {
        arrayBuffer = new ArrayList<>();
        arrayBufferKey = name;
        buffer = null;

        return this;
    }

    public Serializable closeArray() {
        arrays.put(arrayBufferKey, arrayBuffer);
        arrayBuffer = null;
        // arrayBufferKey = null; // No need for this
        return this;
    }

    public Serializable close() {
        if (arrayBuffer != null) {
            arrays.put(arrayBufferKey, arrayBuffer);
            arrayBuffer = null;
            // arrayBufferKey = null; // No need for this
            return this;
        }

        return previousObject;
    }

    public void add(Object object) {
        if (arrayBuffer != null) {
            arrayBuffer.add(object);

            return;
        }

        // Store the key in the buffer if the next character is going to open
        // an array or an object
        if (buffer == null) {
            buffer = object;

            return;
        }

        pairs.put(buffer, object);
        buffer = null;
    }

    public void add(Object key, Object value) {
        assert (buffer == null);

        pairs.put(key, value);
    }
}