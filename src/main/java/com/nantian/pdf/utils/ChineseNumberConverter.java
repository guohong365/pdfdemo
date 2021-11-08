package com.nantian.pdf.utils;

public class ChineseNumberConverter {
    final static private String[] NUMBER = {"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    final static private String[] U1 = {"", "十", "百", "千"};
    private static final String[] U2 = { "", "万", "亿" };
    public static String int2ReadableChinese(String integer) {
        StringBuilder buffer = new StringBuilder();
        if(integer.length()==2 && integer.charAt(0) =='1'){
            buffer.append(U1[1]);
            if(integer.charAt(1)!='0'){
                buffer.append(NUMBER[integer.charAt(1) - '0']);
            }
            return buffer.toString();
        }
        // 从个位数开始转换
        int i, j;
        for (i = integer.length() - 1, j = 0; i >= 0; i--, j++) {
            char n = integer.charAt(i);
            if (n == '0') {
                // 当 n 是 0 且 n 的右边一位不是 0 时，插入[零]
                if (i < integer.length() - 1 && integer.charAt(i + 1) != '0') {
                    buffer.append(NUMBER[0]);
                }
                // 插入[万]或者[亿]
                if (j % 4 == 0) {
                    if (i > 0 && integer.charAt(i - 1) != '0' || i > 1 && integer.charAt(i - 2) != '0'
                            || i > 2 && integer.charAt(i - 3) != '0') {
                        buffer.append(U2[j / 4]);
                    }
                }
            } else {
                if (j % 4 == 0) {
                    buffer.append(U2[j / 4]); // 插入[万]或者[亿]
                }
                buffer.append(U1[j % 4]); // 插入[拾]、[佰]或[仟]
                buffer.append(NUMBER[n - '0']); // 插入数字
            }
        }
        return buffer.reverse().toString();
    }
    public static String convert(int number) {
        StringBuilder result = new StringBuilder();
        if(number<0){
            result.append("负");
        }
        result.append(int2ReadableChinese(String.valueOf(Math.abs(number))));
        return result.toString();
    }
    public static String capitalization(String number) {
        StringBuilder result = new StringBuilder();
        for(int i= 0; i< number.length(); i++){
            char ch=number.charAt(i);
            result.append(NUMBER[ch - '0']);
        }
        return result.toString();
    }
}

