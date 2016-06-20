package utils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String capsAndUnderscore(String s) {
        if (!TextUtils.isEmpty(s)) {
            return s.replace("-", "_").toUpperCase();
        } else {
            return s;
        }
    }

    public static String toProperCase(String constantCase) {
        if (TextUtils.isEmpty(constantCase)) return constantCase;
        String result = constantCase.toLowerCase();
        Pattern p = Pattern.compile("_(.)");
        Matcher m = p.matcher(result);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, " " + m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        result = sb.toString();
        String firstChar = Character.toString(result.charAt(0));
        return result.replaceFirst(firstChar, firstChar.toUpperCase());
    }

    public static String toUrlParam(String constantCase) {
        if (TextUtils.isEmpty(constantCase)) return constantCase;
        return constantCase.replaceAll("_", "-").toUpperCase(Locale.getDefault());
    }

    public static String join(Iterable<String> strings, String joiner) {
        if (strings == null) return null;
        if (joiner == null) joiner = "";

        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            sb.append(s);
            sb.append(joiner);
        }
        return sb.substring(0, sb.lastIndexOf(joiner));
    }

    public static String[] splitLines(String text) {
        return text.split("\r\n");
    }

}
