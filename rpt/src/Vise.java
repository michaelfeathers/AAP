package vise.tool;


public class Vise {
	public static ViseMechanics mechanics = new DefaultViseMechanics(new FileBasedStore());
	
	public static void openSection(String section) {
		mechanics.openSection(section);
	}
	
	public static void grip(Object object) {
		mechanics.grip(object, "");
	}
	
	public static void grip(Object object, String label) {
		mechanics.grip(object, label);
	}
	
	public static void inspect() {
		mechanics.inspect();
	}
	
	public static void closeSection() {
		mechanics.closeSection();
	}
	
	public static void release() {
		mechanics.release();
	}
	
	public static void grip(byte value) {
		mechanics.grip(Byte.valueOf(value), "");
	}	
	
	public static void grip(byte value, String label) {
		mechanics.grip(Byte.valueOf(value), label);
	}	
	
	public static void grip(short value) {
		mechanics.grip(Short.valueOf(value), "");
	}
	
	public static void grip(short value, String label) {
		mechanics.grip(Short.valueOf(value), label);
	}
	
	public static void grip(int value) {
		mechanics.grip(Integer.valueOf(value), "");
	}
	
	public static void grip(int value, String label) {
		mechanics.grip(Integer.valueOf(value), label);
	}
	
	public static void grip(long value) {
		mechanics.grip(Long.valueOf(value), "");
	}
	
	public static void grip(long value, String label) {
		mechanics.grip(Long.valueOf(value), label);
	}
	
	public static void grip(float value) {
		mechanics.grip(Float.valueOf(value), "");
	}
	
	public static void grip(float value, String label) {
		mechanics.grip(Float.valueOf(value), label);
	}
	
	public static void grip(double value) {
		mechanics.grip(Double.valueOf(value), "");
	}	
	
	public static void grip(double value, String label) {
		mechanics.grip(Double.valueOf(value), label);
	}	
	
	public static void grip(char value) {
		mechanics.grip(Character.valueOf(value), "");
	}	
	
	public static void grip(char value, String label) {
		mechanics.grip(Character.valueOf(value), label);
	}	
	
	public static void grip(boolean value) {
		mechanics.grip(Boolean.valueOf(value), "");
	}		

	public static void grip(boolean value, String label) {
		mechanics.grip(Boolean.valueOf(value), label);
	}			
}
