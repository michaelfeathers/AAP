package vise.tool;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;


public class FileBasedStore implements SectionStore {
	private static final String FILE_SUFFIX = ".vise";
	private String directoryBase = "";
	
	public FileBasedStore() {
		this(".");
	}
	
	public FileBasedStore(String directoryBase) {
		this.directoryBase = normalizeDirectoryBase(directoryBase);
	}

	private String normalizeDirectoryBase(String baseName) {
		if (baseName.endsWith("/") || baseName.endsWith("\\"))
			return baseName;
		return baseName += "/";
	}

	String fileName(String sectionName) {
		return directoryBase + sectionName + FILE_SUFFIX;
	}
	
	public boolean hasSection(String sectionName) {
		return new File(fileName(sectionName)).exists();
	}

	public void saveSection(String sectionName, List<RecordedGrip> values) {
		try {
			FileOutputStream fileStream = new FileOutputStream(fileName(sectionName));
			ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
			objectStream.writeObject(values);
			objectStream.close();		
		} catch (Exception e) {
			throw new ViseException("persistence error: " + e.getMessage());
		}
	}

	public int size(String sectionName) {
		return retrieveSection(sectionName).size();
	}

	@SuppressWarnings("unchecked")
	public List<RecordedGrip> retrieveSection(String sectionName) {
		List<RecordedGrip> result;
		try {
			FileInputStream fileStream = new FileInputStream(fileName(sectionName));
			ObjectInputStream objectStream = new ObjectInputStream(fileStream);
			result = (List<RecordedGrip>)objectStream.readObject();
			objectStream.close();
		} catch (Exception e) {
			throw new ViseException("persistence error: " + e.getMessage());
		}
		return result;
	}
	
	public void clear() {
		File [] files = new File(directoryBase).listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.getName().endsWith(FILE_SUFFIX);
			}
		});
		for (int n = 0; n < files.length; n++) {
			files[n].delete();
		}
	}

}
