package vise.tool;

import junit.framework.TestCase;
import vise.tests.FakeSectionStore;

public class ViseTest extends TestCase {
    
    private ViseMechanics originalMechanics;
    private FakeSectionStore store;
    private DefaultViseMechanics testMechanics;
    
    protected void setUp() {
        // Store the original mechanics
        originalMechanics = Vise.mechanics;
        
        // Replace with our test mechanics
        store = new FakeSectionStore();
        testMechanics = new DefaultViseMechanics(store);
        Vise.mechanics = testMechanics;
    }
    
    protected void tearDown() {
        // Restore the original mechanics
        Vise.mechanics = originalMechanics;
    }
    
    public void testOpenSectionDelegatesToMechanics() {
        String sectionName = "testSection";
        Vise.openSection(sectionName);
        
        // Verify section was created
        Vise.grip("test value");
        Vise.closeSection();
        assertTrue(store.hasSection(sectionName));
    }
    
    public void testGripDelegatesToMechanics() {
        String sectionName = "testSection";
        Vise.openSection(sectionName);
        
        Vise.grip("test value");
        Vise.closeSection();
        
        assertEquals(1, store.size(sectionName));
    }
    
    public void testGripWithLabelDelegatesToMechanics() {
        String sectionName = "testSection";
        Vise.openSection(sectionName);
        
        Vise.grip("test value", "test label");
        Vise.closeSection();
        
        RecordedGrip grip = (RecordedGrip)store.retrieveSection(sectionName).get(0);
        assertEquals("test value", grip.value);
        assertEquals("test label", grip.label);
    }
    
    public void testInspectDelegatesToMechanics() {
        Vise.openSection("testSection");
        Vise.grip("test value");
        
        try {
            Vise.inspect();
            fail("Expected ViseException but none was thrown");
        } catch (ViseException e) {
            // Expected - verify it contains the gripped value
            assertTrue(e.getMessage().contains("<test value>"));
        }
    }
    
    public void testCloseSectionDelegatesToMechanics() {
        String sectionName = "testSection";
        Vise.openSection(sectionName);
        Vise.grip("test value");
        
        // If closeSection wasn't properly delegated, this would fail
        Vise.closeSection();
        assertTrue(store.hasSection(sectionName));
    }
    
    public void testReleaseDelegatesToMechanics() {
        String sectionName = "testSection";
        Vise.openSection(sectionName);
        Vise.grip("test value");
        Vise.closeSection();
        
        assertTrue(store.hasSection(sectionName));
        
        Vise.release();
        assertFalse(store.hasSection(sectionName));
    }
    
    public void testPrimitiveGripsAreSavedAsWrapperObjects() {
        Vise.openSection("primitives");
        
        Vise.grip(true, "boolean");
        Vise.grip('c', "char");
        Vise.grip(1, "int");
        Vise.grip(2L, "long");
        Vise.grip(3.0f, "float");
        Vise.grip(4.0, "double");
        Vise.grip((byte)5, "byte");
        Vise.grip((short)6, "short");
        
        Vise.closeSection();
        
        java.util.List grips = store.retrieveSection("primitives");
        assertEquals(8, grips.size());
        
        RecordedGrip grip = (RecordedGrip)grips.get(0);
        assertTrue(grip.value instanceof Boolean);
        assertEquals(true, ((Boolean)grip.value).booleanValue());
        
        grip = (RecordedGrip)grips.get(1);
        assertTrue(grip.value instanceof Character);
        assertEquals('c', ((Character)grip.value).charValue());
        
        // And so on for other primitive types...
    }
}