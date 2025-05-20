package vise.tool;


public interface ViseMechanics {
	void openSection(String sectionName);
	void closeSection();
	boolean isRecording();
	void grip(Object object);
	void grip(Object object, String label);
	void inspect();
	void release();
}
