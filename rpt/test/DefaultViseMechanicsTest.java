package vise.tool;

import junit.framework.TestCase;
import java.util.List;
import vise.tests.FakeSectionStore;

public class DefaultViseMechanicsTest extends TestCase {
    
    private FakeSectionStore store;
    private DefaultViseMechanics mechanics;
    private static final String TEST_SECTION = "testSection";
    
    protected void setUp() {
        store = new FakeSectionStore();
        mechanics = new DefaultViseMechanics(store);
    }
    
    public void testInitialStateIsNotRecording() {
        assertFalse(mechanics.isRecording());
    }
    
    public void testOpenNonExistentSectionSwitchesToRecordingState() {
        mechanics.openSection(TEST_SECTION);
        assertTrue(mechanics.isRecording());
    }
    
    public void testOpenExistingSectionSwitchesToCheckingState() {
        // Setup - create a section first
        mechanics.openSection(TEST_SECTION);
        mechanics.closeSection();
        
        // Test
        mechanics.openSection(TEST_SECTION);
        assertFalse(mechanics.isRecording());
    }
    
    public void testGripRecordsValueInRecordingState() {
        mechanics.openSection(TEST_SECTION);
        mechanics.grip("test value");
        mechanics.closeSection();
        
        List values = store.retrieveSection(TEST_SECTION);
        assertEquals(1, values.size());
        RecordedGrip grip = (RecordedGrip)values.get(0);
        assertEquals("test value", grip.value);
    }
    
    public void testGripWithLabelRecordsValueAndLabel() {
        mechanics.openSection(TEST_SECTION);
        mechanics.grip("test value", "test label");
        mechanics.closeSection();
        
        List values = store.retrieveSection(TEST_SECTION);
        RecordedGrip grip = (RecordedGrip)values.get(0);
        assertEquals("test value", grip.value);
        assertEquals("test label", grip.label);
        assertTrue(grip.hasLabel());
    }
    
    public void testCloseReturnsMechanicsToOutsideState() {
        mechanics.openSection(TEST_SECTION);
        assertTrue(mechanics.isRecording());
        mechanics.closeSection();
        assertFalse(mechanics.isRecording());
        
        try {
            mechanics.grip("test value");
            fail("Expected ViseException but none was thrown");
        } catch (ViseException e) {
            // Expected
        }
    }
    
    public void testReleaseClearsStore() {
        mechanics.openSection(TEST_SECTION);
        mechanics.grip("test value");
        mechanics.closeSection();
        assertTrue(store.hasSection(TEST_SECTION));
        
        mechanics.release();
        assertFalse(store.hasSection(TEST_SECTION));
    }
}