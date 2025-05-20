package vise.tool;

import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.Serializable;

public class FileBasedStoreTest extends TestCase {
    
    private FileBasedStore store;
    private static final String TEST_SECTION = "testSection";
    private static final String TEST_DIR = "testvise";
    
    protected void setUp() {
        // Create test directory
        new File(TEST_DIR).mkdir();
        store = new FileBasedStore(TEST_DIR);
    }
    
    protected void tearDown() {
        // Clean up test files
        store.clear();
        new File(TEST_DIR).delete();
    }
    
    public void testHasSectionReturnsFalseForNonExistentSection() {
        assertFalse(store.hasSection(TEST_SECTION));
    }
    
    public void testHasSectionReturnsTrueForExistingSection() {
        saveTestSection();
        assertTrue(store.hasSection(TEST_SECTION));
    }
    
    public void testSaveSectionCreatesFile() {
        saveTestSection();
        File sectionFile = new File(TEST_DIR + "/" + TEST_SECTION + ".vise");
        assertTrue(sectionFile.exists());
    }
    
    public void testRetrieveSectionGetsStoredValues() {
        List<RecordedGrip> testValues = createTestValues();
        store.saveSection(TEST_SECTION, testValues);
        
        List<RecordedGrip> retrievedValues = store.retrieveSection(TEST_SECTION);
        assertEquals(testValues.size(), retrievedValues.size());
        
        for (int i = 0; i < testValues.size(); i++) {
            RecordedGrip expected = testValues.get(i);
            RecordedGrip actual = retrievedValues.get(i);
            assertEquals(expected.value, actual.value);
            assertEquals(expected.label, actual.label);
        }
    }
    
    public void testSizeReturnsCorrectNumberOfValues() {
        List<RecordedGrip> testValues = createTestValues();
        store.saveSection(TEST_SECTION, testValues);
        
        assertEquals(testValues.size(), store.size(TEST_SECTION));
    }
    
    public void testClearRemovesAllViseFiles() {
        // Create multiple test sections
        store.saveSection(TEST_SECTION + "1", createTestValues());
        store.saveSection(TEST_SECTION + "2", createTestValues());
        
        assertTrue(store.hasSection(TEST_SECTION + "1"));
        assertTrue(store.hasSection(TEST_SECTION + "2"));
        
        store.clear();
        
        assertFalse(store.hasSection(TEST_SECTION + "1"));
        assertFalse(store.hasSection(TEST_SECTION + "2"));
    }
    
    public void testFileNameGeneratesCorrectPath() {
        assertEquals(TEST_DIR + "/" + TEST_SECTION + ".vise", store.fileName(TEST_SECTION));
    }
    
    // Helper methods
    private void saveTestSection() {
        store.saveSection(TEST_SECTION, createTestValues());
    }
    
    private List<RecordedGrip> createTestValues() {
        List<RecordedGrip> values = new ArrayList<RecordedGrip>();
        values.add(new RecordedGrip("value1", "label1"));
        values.add(new RecordedGrip("value2", "label2"));
        values.add(new RecordedGrip("value3"));
        return values;
    }
}