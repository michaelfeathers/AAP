package vise.tool;

import java.io.Serializable;


public class RecordedGrip implements Serializable {
	public Object value;
	public String label;
	
	public RecordedGrip(Object value) {
		this(value,"");
	}
	
	public RecordedGrip(Object value, String label) {
		this.value = value;
		this.label = label;
	}

	public boolean hasLabel() {
		return label.length() != 0;
	}
}
