package vise.tool;


public class RecordingState extends MechanicsState {
	private String sectionName = "";
	
	public RecordingState(SectionStore store) {
		super(store);
	}
	
	public void openSection(String sectionName) {
		this.sectionName = sectionName;
	}

	public void grip(Object object, String label) {
		grippedValues.add(new RecordedGrip(object, label));		
	}

	public void closeSection() {
		store.saveSection(sectionName, grippedValues);		
	}
	
	public boolean isRecording() {
		return true;
	}

}
