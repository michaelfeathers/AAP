public class DefaultViseMechanics implements ViseMechanics {
    private final SectionStore store;
    private String currentSection;
    private boolean isRecording;
    private List<Object> currentGrips;

    public DefaultViseMechanics(SectionStore store) {
        this.store = store;
        this.isRecording = true;
    }

    public void openSection(String section) {
        this.currentSection = section;
        this.currentGrips = store.getSection(section);
        this.isRecording = (currentGrips == null);
        if (isRecording) {
            currentGrips = new ArrayList<>();
        }
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void grip(Object value) {
        if (isRecording) {
            currentGrips.add(value);
        } else {
            if (currentGrips.isEmpty() || !currentGrips.get(0).equals(value)) {
                throw new ViseException("Vise expected grip with <" + currentGrips.get(0) + "> but was grip with <" + value + ">");
            }
            currentGrips.remove(0);
        }
    }

    public void closeSection() {
        if (!isRecording && !currentGrips.isEmpty()) {
            throw new ViseException("Vise has values with no remaining grip calls.");
        }
        if (isRecording) {
            store.saveSection(currentSection, currentGrips);
        }
        currentSection = null;
        currentGrips = null;
    }

    public void inspect() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vise contains ").append(currentGrips.size()).append(" grips\n");
        for (int i = 0; i < currentGrips.size(); i++) {
            sb.append(i).append(": <").append(currentGrips.get(i)).append(">\n");
        }
        throw new ViseException(sb.toString());
    }

    public void release() {
        isRecording = true;
    }

    public void reset() {
        currentSection = null;
        currentGrips = null;
        isRecording = true;
    }
}