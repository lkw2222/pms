package kr.co.kepco.pms.common;

public class CamelUtil {

    public static String convert2CamelCase(String underScore) {
        if(underScore.indexOf('_') < 0 && Character.isLowerCase(underScore.charAt(0))) {
            return underScore;
        }

        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        int len = underScore.length();

        for(int i = 0; i < len; i++) {
            char currentChar = underScore.charAt(i);

            if(currentChar == '_') {
                nextUpper = true;
            } else {
                if(nextUpper) {
                    result.append(Character.toUpperCase(currentChar));
                } else {
                    result.append(Character.toLowerCase(currentChar));
                }
            }
        }

        return result.toString();
    }
}
