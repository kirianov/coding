package com.kirianov.coding.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    private static final String TAG = "igor.kirianov";
    private static final int LINE_LENGTH = 1000;

    public synchronized static void log() {
        String method = Thread.currentThread().getStackTrace()[3].getClassName() + "." +
                Thread.currentThread().getStackTrace()[3].getMethodName() + ":" +
                Thread.currentThread().getStackTrace()[3].getLineNumber();
        for (String line : split(method, "", LINE_LENGTH, false)) {
            Log.d(TAG, line);
        }
    }

    public synchronized static void log(Object message) {
        String method = Thread.currentThread().getStackTrace()[3].getClassName() + "." +
                Thread.currentThread().getStackTrace()[3].getMethodName() + ":" +
                Thread.currentThread().getStackTrace()[3].getLineNumber();
        for (String line : split(method, "" + message, LINE_LENGTH, true)) {
            Log.d(TAG, line);
        }
    }

    public synchronized static String getCurrentStackTrace() {
        StringBuilder out = new StringBuilder();
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            out.append("[").append(element.getClassName()).append(".").append(element.getMethodName()).append(":").append(element.getLineNumber()).append("], ");
        }
        return out.toString();
    }

    public static List<String> split(String method, String message, int size) {
        return split(method, message, size, false);
    }

    private static List<String> split(String method, String message, int size, boolean spaces) {
        if (spaces) {
            method += "  ";
        }
        int methodLength = method != null ? method.length() : 0;

        List<String> result = new ArrayList<>();

        String prefix = "... ";
        int prefixLength = prefix.length();

        int position = 0;
        int positionNext;
        do {
            positionNext = position == 0 ?
                    Math.min(message.length(), position + size - methodLength) :
                    Math.min(message.length(), position + size - methodLength - prefixLength);
            result.add(position == 0 ?
                    method + message.substring(position, positionNext) :
                    method + prefix + message.substring(position, positionNext));
            position = positionNext;
        } while (position < message.length());

        return result;
    }
}
