package com.ChenDll.classroommanagement.util;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static int getInt(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine());
    }

//    public static String retryInput(String message, int maxAttempts, InputValidator validator) {
//        for (int i = 0; i < maxAttempts; i++) {
//            String input = InputUtil.getString(message);
//            if (validator.validate(input)) {
//                return input;
//            }
//            System.out.println("输入无效！您还有 " + (maxAttempts - i - 1) + " 次尝试。");
//        }
//        throw new RuntimeException("超过最大尝试次数！");
//    }
//
//    @FunctionalInterface
//    public interface InputValidator {
//        boolean validate(String input);
//    }
}