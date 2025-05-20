package vise.tool;

import java.util.Iterator;

public class CheckingState extends MechanicsState {
	private boolean hasThrownInCurrentSection = false;
	private Iterator currentPosition;
	
	public CheckingState(SectionStore store) {
		super(store);
	}
	
	public void openSection(String sectionName) {
		grippedValues = store.retrieveSection(sectionName);
		currentPosition = grippedValues.iterator();		
	}

	public void grip(Object object, String label) {
		if(!currentPosition.hasNext()) {
			throwFromGrip("Vise can't find any more values.  All have been matched.");
			return;
		} 
		RecordedGrip grip = (RecordedGrip)currentPosition.next();
		if (!grip.value.equals(object)) {
			throwFromGrip("Vise expected grip "
					+ (grip.hasLabel() ? ("\"" + grip.label + "\" ") : "")
					+ "with <" + grip.value  + "> but was grip with <" + object + ">");
		}			
	}

	private void throwFromGrip(String message) {
		hasThrownInCurrentSection = true;
		throw new ViseException(message);
	}

	public void closeSection() {
		if (currentPosition.hasNext() && hasThrownInCurrentSection == false) {
			throw new ViseException("Vise has values with no remaining grip calls.");
		}
	}

	public boolean isRecording() {
		return false;
	}

}
