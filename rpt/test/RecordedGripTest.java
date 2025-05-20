package vise.tool;

import junit.framework.TestCase;

public class RecordedGripTest extends TestCase {
    
    public void testConstructorSetsValueAndLabel() {
        RecordedGrip grip = new RecordedGrip("test value", "test label");
        assertEquals("test value", grip.value);
        assertEquals("test label", grip.label);
    }
    
    public void testConstructorWithoutLabelSetsEmptyLabel() {
        RecordedGrip grip = new RecordedGrip("test value");
        assertEquals("test value", grip.value);
        assertEquals("", grip.label);
    }
    
    public void testHasLabelReturnsTrueForNonEmptyLabel() {
        RecordedGrip grip = new RecordedGrip("test value", "test label");
        assertTrue(grip.hasLabel());
    }
    
    public void testHasLabelReturnsFalseForEmptyLabel() {
        RecordedGrip grip = new RecordedGrip("test value", "");
        assertFalse(grip.hasLabel());
        
        grip = new RecordedGrip("test value");
        assertFalse(grip.hasLabel());
    }
    
    public void testIsSerializable() {
        // Just verify that RecordedGrip implements Serializable
        RecordedGrip grip = new RecordedGrip("test value", "test label");
        assertTrue(grip instanceof java.io.Serializable);
    }
}