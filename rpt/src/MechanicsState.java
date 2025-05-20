package vise.tool;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public abstract class MechanicsState {
	protected List<RecordedGrip> grippedValues = new LinkedList<RecordedGrip>();
	protected SectionStore store;

	public abstract void openSection(String sectionName);
	public abstract void grip(Object object, String label);
	public abstract void closeSection();
	public abstract boolean isRecording();
	
	public MechanicsState(SectionStore store) {
		this.store = store;
	}
	
	public void inspect() {
		int index = 0;
		class Int { public int value = 0; }
		HashMap<String, Int> labelOccurrences = new HashMap<String, Int>();

		int size = grippedValues.size();
		String message = "Vise contains " 
				+ size + " grip" + (size != 1 ? "s" : "") + "\n";
		
		for(RecordedGrip grip : grippedValues) {
			Object object = grip.value;
			message += index + ": <" + object + ">";
			if (grip.hasLabel()) {
				if (!labelOccurrences.containsKey(grip.label)) {
					labelOccurrences.put(grip.label, new Int());
				}
				Int occurrences = labelOccurrences.get(grip.label);
				occurrences.value++;
				
				message += " " + grip.label + " (" + (occurrences.value - 1) + ")"; 
			}
			message += "\n";
			index++;
		}
		throw new ViseException(message);
	}
}
