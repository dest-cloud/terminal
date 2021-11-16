package com.bgconsole.desktop;

import com.bgconsole.desktop.location.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppData {

    public static final AppData instance = new AppData();

    private Map<String, LocationData> locationList;

    private AppData() {
        locationList = new HashMap<>();
    }

    public LocationData get(String id) {
        return locationList.get(id);
    }

    public void setLocationList(List<Location> locations) {
        locationList.clear();
        locations.forEach(location -> {
            locationList.put(location.getId(), new LocationData(location));
        });
    }

}
