/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cw_csarchitecture.dataHolder;

import cw_csarchitecture.models.Room;
import cw_csarchitecture.models.Sensor;
import cw_csarchitecture.models.SensorReading;
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
    
}
