package models.headout;

import utils.Strings;
import utils.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by madki on 17/06/16.
 */
public class GuestDetails {
    private static final Pattern nameValidityRegex = Pattern.compile("\\s*[^\\s]+\\s+[^\\s]+.*");
    public static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    public static final Pattern PHONE
            = Pattern.compile(                      // sdd = space, dot, or dash
            "(\\+[0-9]+[\\- \\.]*)?"        // +<digits><sdd>*
                    + "(\\([0-9]+\\)[\\- \\.]*)?"   // (<digits>)<sdd>*
                    + "([0-9][0-9\\- \\.]+[0-9])"); // <digit><digit|sdd>+<digit>


    public String name;
    public String email;
    public String phone;

    public boolean isValid() {
        return isValidName() && isValidEmail() && isValidPhone();
    }

    public boolean isValidName() {
        return isValidName(name);
    }

    public boolean isValidEmail() {
        return isValidEmail(email);
    }

    public boolean isValidPhone() {
        return isValidPhone(phone);
    }

    public static boolean isValidName(String s) {
        return !TextUtils.isEmpty(s)
                && nameValidityRegex.matcher(s).matches()
                && s.length() >= 3
                && s.length() <= 80;
    }

    public static boolean isValidEmail(String s) {
        return !TextUtils.isEmpty(s)
                && s.length() >= 5
                && EMAIL_ADDRESS.matcher(s).matches();
    }

    public static boolean isValidPhone(String s) {
        return !TextUtils.isEmpty(s)
                && s.length() <= 40
                && PHONE.matcher(s).matches();
    }}
