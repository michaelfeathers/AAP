import java.util.*;

public class FileBasedStore {
    private final String directoryBase;
    private final Map<String, List<Integer>> store;

    public FileBasedStore(String directoryBase) {
        this.directoryBase = directoryBase;
        this.store = new HashMap<>();
    }

    public boolean hasSection(String section) {
        return store.containsKey(section);
    }

    public void saveSection(String section, List<Integer> values) {
        store.put(section, new ArrayList<>(values));
    }

    public List<Integer> retrieveSection(String section) {
        return store.get(section);
    }

    public void clear() {
        store.clear();
    }
}