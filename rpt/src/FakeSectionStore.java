package vise.tests;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vise.tool.RecordedGrip;
import vise.tool.SectionStore;


public class FakeSectionStore implements SectionStore {
	private Map<String, List<RecordedGrip>> sections = new HashMap<String, List<RecordedGrip>>();
	
	public boolean hasSection(String sectionName) {
		return sections.containsKey(sectionName);
	}

	public void saveSection(String sectionName, List<RecordedGrip> values) {
		sections.put(sectionName, values);
	}
	
	public int size(String sectionName) {
		List<RecordedGrip> section = sections.get(sectionName);
		return section != null ? section.size() : 0;
	}

	public List<RecordedGrip> retrieveSection(String sectionName) {
		return sections.get(sectionName);
	}

	public void clear() {
		sections = new HashMap<String, List<RecordedGrip>>();
	}
}
