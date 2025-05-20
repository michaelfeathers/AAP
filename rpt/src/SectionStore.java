package vise.tool;
import java.util.List;


public interface SectionStore {
	public boolean hasSection(String sectionName);
	public void saveSection(String sectionName, List<RecordedGrip> values);
	public List<RecordedGrip> retrieveSection(String sectionName);
	public int size(String sectionName);
	public void clear();
}
