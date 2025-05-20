package vise.tool;

import junit.framework.TestCase;
import vise.tests.FakeSectionStore;
import java.util.List;

public class RecordingStateTest extends TestCase {
    
    private FakeSectionStore store;
    private RecordingState state;
    private static final String TEST_SECTION = "testSection";
    
    protected void setUp() {
        store = new FakeSectionStore();
        state = new RecordingState(store);
    }
    
    public void testIsRecordingReturnsTrue() {
        assertTrue(state.isRecording());
    }
    
    public void testOpenSectionSetsSection() {
        state.openSection(TEST_SECTION);
        // Add a grip and close section to verify section name was set correctly
        state.grip("test value", "test label");
        state.closeSection();
        
        assertTrue(store.hasSection(TEST_SECTION));
    }
    
    public void testGripAddsRecordedGripToValues() {
        state.grip("test value", "test label");
        
        assertEquals(1, state.grippedValues.size());
        RecordedGrip grip = (RecordedGrip)state.grippedValues.get(0);
        assertEquals("test value", grip.value);
        assertEquals("test label", grip.label);
    }
    
    public void testGripWithMultipleValuesMaintainsOrder() {
        state.grip("value1", "label1");
        state.grip("value2", "label2");
        state.grip("value3", "label3");
        
        assertEquals(3, state.grippedValues.size());
        assertEquals("value1", ((RecordedGrip)state.grippedValues.get(0)).value);
        assertEquals("value2", ((RecordedGrip)state.grippedValues.get(1)).value);
        assertEquals("value3", ((RecordedGrip)state.grippedValues.get(2)).value);
    }
    
    public void testCloseSectionStoresValues() {
        state.openSection(TEST_SECTION);
        state.grip("value1", "label1");
        state.grip("value2", "label2");
        state.closeSection();
        
        List storedValues = store.retrieveSection(TEST_SECTION);
        assertEquals(2, storedValues.size());
        assertEquals("value1", ((RecordedGrip)storedValues.get(0)).value);
        assertEquals("value2", ((RecordedGrip)storedValues.get(1)).value);
    }
}