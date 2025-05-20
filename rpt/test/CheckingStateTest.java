package vise.tool;

import junit.framework.TestCase;
import vise.tests.FakeSectionStore;
import java.util.ArrayList;
import java.util.List;

public class CheckingStateTest extends TestCase {
    
    private FakeSectionStore store;
    private CheckingState state;
    private static final String TEST_SECTION = "testSection";
    
    protected void setUp() {
        store = new FakeSectionStore();
        List<RecordedGrip> values = new ArrayList<RecordedGrip>();
        values.add(new RecordedGrip("value1", "label1"));
        values.add(new RecordedGrip("value2", "label2"));
        values.add(new RecordedGrip("value3"));
        store.saveSection(TEST_SECTION, values);
        
        state = new CheckingState(store);
    }
    
    public void testIsRecordingReturnsFalse() {
        assertFalse(state.isRecording());
    }
    
    public void testOpenSectionLoadsStoredValues() {
        state.openSection(TEST_SECTION);
        assertEquals(3, state.grippedValues.size());
    }
    
    public void testGripWithMatchingValueSucceeds() {
        state.openSection(TEST_SECTION);
        
        try {
            state.grip("value1", "label1");
            state.grip("value2", "label2");
            state.grip("value3", "");
            // If we get here, the test passes
        } catch (ViseException e) {
            fail("Grip should not have thrown an exception: " + e.getMessage());
        }
    }
    
    public void testGripWithNonMatchingValueThrowsException() {
        state.openSection(TEST_SECTION);
        
        try {
            state.grip("value1", "label1");
            state.grip("wrong value", "label2"); // Should throw exception
            fail("Expected ViseException but none was thrown");
        } catch (ViseException e) {
            // Expected exception - contains reference to both expected and actual values
            assertTrue(e.getMessage().contains("value2"));
            assertTrue(e.getMessage().contains("wrong value"));
        }
    }
    
    public void testGripWithNoRemainingValuesThrowsException() {
        state.openSection(TEST_SECTION);
        
        // Consume all values
        state.grip("value1", "label1");
        state.grip("value2", "label2");
        state.grip("value3", "");
        
        try {
            state.grip("extra value", "");
            fail("Expected ViseException but none was thrown");
        } catch (ViseException e) {
            // Expected exception
            assertTrue(e.getMessage().contains("can't find any more values"));
        }
    }
    
    public void testCloseSectionSucceedsWhenAllValuesUsed() {
        state.openSection(TEST_SECTION);
        
        // Consume all values
        state.grip("value1", "label1");
        state.grip("value2", "label2");
        state.grip("value3", "");
        
        try {
            state.closeSection();
            // If we get here, the test passes
        } catch (ViseException e) {
            fail("closeSection should not have thrown an exception: " + e.getMessage());
        }
    }
    
    public void testCloseSectionThrowsExceptionWhenValuesRemain() {
        state.openSection(TEST_SECTION);
        
        // Only consume two values
        state.grip("value1", "label1");
        state.grip("value2", "label2");
        
        try {
            state.closeSection();
            fail("Expected ViseException but none was thrown");
        } catch (ViseException e) {
            // Expected exception
            assertTrue(e.getMessage().contains("values with no remaining grip calls"));
        }
    }
}