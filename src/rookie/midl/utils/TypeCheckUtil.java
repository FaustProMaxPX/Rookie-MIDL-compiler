package rookie.midl.utils;

import java.util.regex.Pattern;

public class TypeCheckUtil {

    /**
     * check if the value can be converted to int
     * @param decl the string value to be converted
     * @param bitc the bit count of expected format
     * @param unsigned if unsigned
     * */
    public static boolean checkInt(String decl, int bitc, boolean unsigned) {
        int num = 0;
        long val = 0;
        try {
            if (bitc != 64) {
                num = unsigned ? Integer.parseUnsignedInt(decl) : Integer.parseInt(decl);
            } else {
                val = unsigned ? Long.parseUnsignedLong(decl) : Long.parseLong(decl);
            }
        } catch (NumberFormatException e) {
            return false;
        }
        switch (bitc) {
            case 8 -> {
                if (unsigned && num >= 0 && num <= 255) return true;
                if (num >= -128 && num <= 127) return true;
            }
            case 16 -> {
                if (unsigned && num >= 0 && num <= 65535) return true;
                if (num >= -32768 && num <= 32767) return true;
            }
            case 32 -> {
                if (unsigned && Integer.compareUnsigned(num, 0) >= 0 && Integer.compareUnsigned(num, Integer.MAX_VALUE) <= 0) {
                    return true;
                }
                return true;
            }
            case 64 -> {
                if (unsigned && Long.compareUnsigned(val, 0) >= 0 && Long.compareUnsigned(val, Long.MAX_VALUE) <= 0) {
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * check weather the declarator is corresponding to its type
     * @param type type name
     * @param decl result of the declarator
     * @return declarator is corresponding to its type or not
     * */
    public static boolean checkDeclarator(String type, String decl) {
        boolean unsigned = false;
        int bitc = -1;
        switch (type) {
            case "float": {
                Pattern p = Pattern.compile("^[-+]?[0-9]*\\.?[0-9]+[fF]?$");
                return p.matcher(decl).matches();
            }
            case "double":
            case "long double": {
                Pattern p = Pattern.compile("^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?[fFdD]?$");
                return p.matcher(decl).matches();
            }
            case "boolean":
                return decl.equals("true") || decl.equals("false");
            case "char":
                return decl.charAt(0) == '\'' && decl.charAt(decl.length() - 1) == '\'' && decl.length() <= 3;
            case "string":
                return true;
        }
        if (type.startsWith("unsigned") || type.startsWith("uint")) {
            if (decl.startsWith("-")) return false;
            unsigned = true;
        }
        if (type.contains("int")) {
            String t = type.trim();
            bitc = Integer.parseInt(t.substring(t.length() - 2));
        }
        if (type.contains("short")) bitc = 16;
        if (type.contains("long")) bitc = 32;
        if (type.contains("long long")) bitc = 64;
        return checkInt(decl, bitc, unsigned);
    }
}
