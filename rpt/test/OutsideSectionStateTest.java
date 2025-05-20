package vise.tool;

import junit.framework.TestCase;
import vise.tests.FakeSectionStore;

public class OutsideSectionStateTest extends TestCase {
    
    private FakeSectionStore store;
    private OutsideSectionState state;
    
    protected void setUp() {
        store = new FakeSectionStore();
        state = new OutsideSectionState(store);
    }
    
    public void testIsRecordingReturnsFalse() {
        assertFalse(state.isRecording());
    }
    
    public void testOpenSectionDoesNothing() {
        // Just verify no exception is thrown
        state.openSection("test");
    }
    
    public void testGripThrowsException() {
        try {
            state.grip("test value", "test label");
            fail("Expected ViseException but none was thrown");
        } catch (ViseException e) {
            // Expected
            assertTrue(e.getMessage().contains("invalid state error"));
            assertTrue(e.getMessage().contains("grip called without a call to openSection"));
        }
    }
    
    public void testCloseSectionThrowsException() {
        try {
            state.closeSection();
            fail("Expected ViseException but none was thrown");
        } catch (ViseException e) {
            // Expected
            assertTrue(e.getMessage().contains("invalid state error"));
            assertTrue(e.getMessage().contains("closeSection called without a call to openSection"));
        }
    }
}