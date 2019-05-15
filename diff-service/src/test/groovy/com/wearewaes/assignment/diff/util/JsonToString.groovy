package com.wearewaes.assignment.diff.util

import com.google.gson.Gson

class JsonToString {
    static convert(Object payload) {
        Gson gson = new Gson()
        return gson.toJson(payload, payload.class)
    }
}
