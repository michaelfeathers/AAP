package vise.tool;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
    
    public static Test suite() {
        TestSuite suite = new TestSuite("Test suite for vise.tool");
        
        // Add all test classes here
        suite.addTestSuite(ViseTest.class);
        suite.addTestSuite(DefaultViseMechanicsTest.class);
        suite.addTestSuite(FileBasedStoreTest.class);
        suite.addTestSuite(RecordingStateTest.class);
        suite.addTestSuite(CheckingStateTest.class);
        suite.addTestSuite(OutsideSectionStateTest.class);
        suite.addTestSuite(RecordedGripTest.class);
        
        return suite;
    }
    
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }
}