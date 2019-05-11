package com.wearewaes.assignment.cache.integration.util

import com.google.gson.Gson

class JsonContentSerializer {
    static String serialize(Object object) {
        Gson gson = new Gson()
        return gson.toJson(object, object.class)
    }
}
