public interface ViseMechanics {
    void openSection(String section);
    boolean isRecording();
    void grip(Object value);
    void closeSection();
    void inspect();
    void release();
    void reset();
}