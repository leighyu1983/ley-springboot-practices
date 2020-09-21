package hc.logging.type;

public class KeyValuePair<T, Q> {
    private T key;
    private Q value;

    public KeyValuePair(T key, Q value) {
        this.key = key;
        this.value = value;
    }

    public T getKey() {
        return this.key;
    }

    public Q getValue () {
        return this.value;
    }

    @Override
    public String toString() {
        return String.format("[KeyValuePair key: {}, value: {}]", key, value);
    }
}
