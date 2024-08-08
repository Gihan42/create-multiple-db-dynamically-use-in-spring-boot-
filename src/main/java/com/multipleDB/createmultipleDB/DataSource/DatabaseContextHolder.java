package com.multipleDB.createmultipleDB.DataSource;

public class DatabaseContextHolder {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setCurrentCompany(String company) {
        CONTEXT.set(company);
    }

    public static String getCurrentCompany() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
