package com.ChenDll.classroommanagement.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    // 获取字符串输入
    public static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // 获取整数输入，增加容错机制
    public static int getInt(String message) {
        int num;
        while (true) {
            try {
                System.out.print(message);
                num = scanner.nextInt();
                scanner.nextLine(); // 清除换行符
                return num;
            } catch (InputMismatchException e) {
                System.out.println("输入错误，请输入有效的整数！");
                scanner.nextLine(); // 清除错误输入
            }
        }
    }

    // 获取正整数
    public static int getPositiveInt(String message) {
        int num;
        while (true) {
            try {
                System.out.print(message);
                num = scanner.nextInt();
                scanner.nextLine(); // 清除换行符
                if (num <= 0) {
                    System.out.println("请输入一个正整数！");
                    continue;
                }
                return num;
            } catch (InputMismatchException e) {
                System.out.println("输入错误，请输入有效的正整数！");
                scanner.nextLine(); // 清除错误输入
            }
        }
    }

    // 获取用户确认输入，限制为 "Y" 或 "N"
    public static boolean getYesNo(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt + " (Y/N): ");
            input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            } else {
                System.out.println("请输入 'Y' 或 'N'！");
            }
        }
    }

    // 获取用户输入的日期时间（格式：yyyy-MM-dd HH:mm）
    public static LocalDateTime getDateTime(String prompt) {
        LocalDateTime dateTime = null;
        while (dateTime == null) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                dateTime = LocalDateTime.parse(input, formatter);  // 解析输入的日期时间
            } catch (Exception e) {
                System.out.println("日期时间格式错误，请使用正确格式（yyyy-MM-dd HH:mm）！");
            }
        }
        return dateTime;
    }
}
