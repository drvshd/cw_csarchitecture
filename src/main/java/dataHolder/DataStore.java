/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataHolder;

import com.mycompany.cw_csarchitecture.models.Room;
import com.mycompany.cw_csarchitecture.models.Sensor;
import com.mycompany.cw_csarchitecture.models.SensorReading;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author dervishdenaj
 */
public class DataStore {
    private static DataStore instance;
    
    public static Map<String, Room> rooms = new ConcurrentHashMap<>();
    public static Map<String, Sensor> sensors = new ConcurrentHashMap<>();
    public static Map<String, List<SensorReading>> sensorReadings = new ConcurrentHashMap<>();

    public DataStore() {
    }
    
    public static DataStore getInstance() { 
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }
    
    
    
}
