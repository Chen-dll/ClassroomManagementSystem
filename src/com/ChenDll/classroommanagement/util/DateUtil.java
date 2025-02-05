package com.ChenDll.classroommanagement.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

public class DateUtil {

    // 判断预约是否过期
    public static boolean isExpired(LocalDateTime endTime) {
        return LocalDateTime.now().isAfter(endTime);
    }

    // 获取当前时间的字符串表示，格式为：yyyy-MM-dd HH:mm:ss
    public static String getCurrentTimeString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    // 将字符串转换为 LocalDateTime，格式为：yyyy-MM-dd HH:mm:ss
    public static LocalDateTime parseStringToLocalDateTime(String dateTimeStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("时间格式不正确，应该是 yyyy-MM-dd HH:mm:ss", e);
        }
    }

    // 将 LocalDateTime 转换为字符串，格式为：yyyy-MM-dd HH:mm:ss
    public static String formatLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    // 判断当前时间是否在给定的时间区间内
    public static boolean isInRange(LocalDateTime startTime, LocalDateTime endTime) {
        return !LocalDateTime.now().isBefore(startTime) && !LocalDateTime.now().isAfter(endTime);
    }

    // 计算两个 LocalDateTime 之间的时间差（单位：秒）
    public static long getDifferenceInSeconds(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime).getSeconds();
    }

    // 判断当前时间是否在给定的时间之前
    public static boolean isBeforeNow(LocalDateTime dateTime) {
        return LocalDateTime.now().isBefore(dateTime);
    }

    // 判断当前时间是否在给定的时间之后
    public static boolean isAfterNow(LocalDateTime dateTime) {
        return LocalDateTime.now().isAfter(dateTime);
    }
}
