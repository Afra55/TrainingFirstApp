package com.magus.trainingfirstapp.design_mode.capter_one.class_5;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by yangshuai in the 17:22 of 2015.11.17 .
 */
public class CloseUtils {

    public static void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
