package utils;

/**
 * Created by madki on 17/06/16.
 */
public final class Strings {

    private Strings() {
        throw new AssertionError("No instances");
    }

    public static String toString(Object object) {
        try {
            if (object != null) {
                if (object instanceof Iterable) {
                    StringBuilder sb = new StringBuilder("[");
                    for (Object o : (Iterable) object) {
                        sb.append(toString(o)).append(", ");
                    }
                    sb.append("]");
                    return sb.toString();
                } else {
                    return object.toString();
                }
            }
            return "null";
        } catch (Exception e) {
            return "null";
        }
    }
}
