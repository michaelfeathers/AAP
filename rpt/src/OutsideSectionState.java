package vise.tool;


public class OutsideSectionState extends MechanicsState {
	private static final String PREFIX = "invalid state error: ";

	public OutsideSectionState(SectionStore store) {
		super(store);
	}
	
	public void openSection(String sectionName) {
	}

	public void grip(Object object, String label) {
		throw new ViseException(PREFIX + "grip called without a call to openSection");
	}

	public void closeSection() {
		throw new ViseException(PREFIX + "closeSection called without a call to openSection");
	}

	public boolean isRecording() {
		return false;
	}

}
