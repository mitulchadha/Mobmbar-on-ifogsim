import java.util.ArrayList;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;

import org.fog.application.AppEdge;
import org.fog.application.AppLoop; 
import org.fog.application.Application;
import org.fog.application.selectivity.FractionalSelectivity;
import org.fog.entities.Actuator; 
import org.fog.entities.FogBroker;
import org.fog.entities.Sensor; 
import org.fog.entities.Tuple; 
import org.fog.placement.ModulePlacementEdgewards;
//import org.fog.policy.MobilityAwarePolicy; 
//import org.fog.utils.DistanceFinder;
//import org.fog.utils.MobilityTraceGenerator;
import org.fog.utils.TimeKeeper;

public class MobilityAwareMBar extends ModulePlacementEdgewards {
	
	private Random random;
	
	
	private Map<Sensor, FogBroker> sensorToFogMap;
    private Map<FogBroker, List<Sensor>> fogToSensorsMap;
    private int sensorUpdateInterval;
	
	
    @Override
	public void startEntity() { 
		
    	// Initialize sensor to fog broker mapping
    	
		sensorToFogMap = new HashMap<>();
		fogToSensorsMap = new HashMap<>();
	}

	
    @Override
	public FogBroker getFogBrokerForSensor(Sensor sensor , FogBroker brokerlist) {
		
    	// Get the fog broker closest to the given sensor
    	
		double minDistance = Double.MAX_VALUE;
		FogBroker closestFog = null;
		for (FogBroker fog : brokerList) 
		{
			double distance = DistanceFinder.calculateDistance(fog.gethorizontal(),fog.getvertical(),sensor.gethorizontal(),sensor.getvertical());
			if (distance < minDistance) 
			{
				minDistance = distance;
				closestFog = fog;
			}
		}
		return closestFog;
	}

	@Override 
	public void updateSensorToFogMapping(Sensor sensor) 
	{  
		FogBroker oldFog = sensorToFogMap.get(sensor);
		if (oldFog != null) 
		{	
			// Remove sensor from old fog broker's list
			
			fogToSensorsMap.get(oldFog).remove(sensor); 
		}
		// Get new fog broker closest to sensor's latest location
		
		FogBroker newFog = getFogBrokerForSensor(sensor);  
		// Update sensor to fog mapping
		
		sensorToFogMap.put(sensor, newFog); 
		// Add sensor to new fog broker's list 
		
		if (!fogToSensorsMap.containsKey(newFog)) 
		{
			fogToSensorsMap.put(newFog, new ArrayList<>());
		}
		fogToSensorsMap.get(newFog).add(sensor);
	}
	
	 @Override
	    public void updateSensorPositions(Sensor sensor) 
	 {  // called periodically to update sensor positions
		 
		 
	 }
	        if (getTickCount() % sensorUpdateInterval == 0) {
	            for (Sensor sensor : bipartiteGraph.getSensors()) 
	            {
	            	
	                // Update sensor location randomly after some fixed amount of time
	            	
	                double x = sensor.horizontal + random.nextDouble() * 10 - 5;
	                double y = sensor.vertical+ random.nextDouble() * 10 - 5;
	                sensor.setLocation(new Location(x, y));
	                updateLocation(sensor, x , y);
	            }
	            
	            // Update bipartite graph mapping based on new sensor-fog node distances
	            
	            updateSensorToFogMapping(sensor);
	        }
	    }
	


	public void updateLocation(Sensor sensor, int x, int y)
	{
		sensor.horizontal=x;
		sensor.vertical=y;
	}

		// called in order to find the distance between a sensor and fog node
	 public class DistanceFinder {
		    public double calculateDistance(double x1, double y1, double x2, double y2) {
		        double xDiff = x2 - x1;
		        double yDiff = y2 - y1;
		        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
		    }
}