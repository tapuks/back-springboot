package com.example.app.response;

import java.util.ArrayList;
import java.util.HashMap;

public class ResponseRest {
    private ArrayList<HashMap<String, String>> metadata = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getMetadata() {
        return metadata;
    }

    public void setMetadata(String Type, String code, String date) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Type", Type);
        map.put("code", code);
        map.put("date", date);
        this.metadata.add(map);
    }

}
