package com.google.gson.internal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PreJava9DateFormatProvider {
    public static DateFormat getUSDateFormat(int style) {
        return new SimpleDateFormat(getDateFormatPattern(style), Locale.US);
    }

    public static DateFormat getUSDateTimeFormat(int dateStyle, int timeStyle) {
        StringBuilder sb = new StringBuilder();
        sb.append(getDatePartOfDateTimePattern(dateStyle));
        sb.append(" ");
        sb.append(getTimePartOfDateTimePattern(timeStyle));
        return new SimpleDateFormat(sb.toString(), Locale.US);
    }

    private static String getDateFormatPattern(int style) {
        if (style == 0) {
            return "EEEE, MMMM d, y";
        }
        if (style == 1) {
            return "MMMM d, y";
        }
        if (style == 2) {
            return "MMM d, y";
        }
        if (style == 3) {
            return "M/d/yy";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown DateFormat style: ");
        sb.append(style);
        throw new IllegalArgumentException(sb.toString());
    }

    private static String getDatePartOfDateTimePattern(int dateStyle) {
        if (dateStyle == 0) {
            return "EEEE, MMMM d, yyyy";
        }
        if (dateStyle == 1) {
            return "MMMM d, yyyy";
        }
        if (dateStyle == 2) {
            return "MMM d, yyyy";
        }
        if (dateStyle == 3) {
            return "M/d/yy";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown DateFormat style: ");
        sb.append(dateStyle);
        throw new IllegalArgumentException(sb.toString());
    }

    private static String getTimePartOfDateTimePattern(int timeStyle) {
        if (timeStyle == 0 || timeStyle == 1) {
            return "h:mm:ss a z";
        }
        if (timeStyle == 2) {
            return "h:mm:ss a";
        }
        if (timeStyle == 3) {
            return "h:mm a";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown DateFormat style: ");
        sb.append(timeStyle);
        throw new IllegalArgumentException(sb.toString());
    }
}
