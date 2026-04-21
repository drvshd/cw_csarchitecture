/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cw_csarchitecture.resources;

import cw_csarchitecture.dataHolder.DataStore;
import cw_csarchitecture.models.Sensor;
import cw_csarchitecture.models.SensorReading;
import cw_csarchitecture.errorExceptions.SensorUnavailableException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 *
 * @author dervishdenaj
 */
public class SensorReadingResource {

    private String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadings() {
        List<SensorReading> history = DataStore.sensorReadings.get(sensorId);
        if (history == null) {
            return Response.ok(new ArrayList<>()).build();
        }
        return Response.ok(history).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading) {
        Sensor sensor = DataStore.sensors.get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Sensor not found\"}")
                    .type(MediaType.APPLICATION_JSON).build();
        }
        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            throw new SensorUnavailableException("This sensor is undergoing maintenance. Try again another time.");
        }

        DataStore.sensorReadings
                .computeIfAbsent(sensorId, k -> new ArrayList<>())
                .add(reading);

        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}
