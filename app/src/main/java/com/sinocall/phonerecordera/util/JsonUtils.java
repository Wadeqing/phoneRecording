package com.sinocall.phonerecordera.util;

import com.google.gson.Gson;

import java.io.IOException;

/**
 *
 */
public class JsonUtils {
    public static Object Json2Gson( Class<?> clz,String jsonString) throws IOException {
        return new Gson().fromJson(jsonString, clz);
    }

}
