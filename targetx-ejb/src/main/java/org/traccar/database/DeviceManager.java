/*
 * Copyright 2016 - 2017 Anton Tananaev (anton@traccar.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.traccar.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.traccar.model.Command;
import org.traccar.model.CommandType;
import org.traccar.model.Device;
import org.traccar.model.Position;

import pojos.DriverInfo;
import beans.CacheDriversSingleton;
import beans.DatabaseBean;
import beans.EntityConverterBean;

public class DeviceManager implements IdentityManager {

    public static final long DEFAULT_REFRESH_DELAY = 300;

    private final Map<Long, Position> positions = new ConcurrentHashMap<>();

	@EJB
	private CacheDriversSingleton cacheDriversBean;
	@EJB
	private DatabaseBean databaseBean;
	@EJB
	private EntityConverterBean entityConverterBean;
	
    public DeviceManager() {
    	Context ctxgt;
		try {
			ctxgt = new InitialContext();
			cacheDriversBean = (CacheDriversSingleton) ctxgt.lookup("java:global/targetx-ear-1.0/targetx-web-1.0/CacheDriversSingleton");
			databaseBean = (DatabaseBean) ctxgt.lookup("java:global/targetx-ear-1.0/targetx-web-1.0/DatabaseBean");
			entityConverterBean = (EntityConverterBean) ctxgt.lookup("java:global/targetx-ear-1.0/targetx-web-1.0/EntityConverterBean");
		} catch (NamingException e) {
			e.printStackTrace();
		}
    }

    @Override
    public Device getDeviceById(long id) {
    	List<DriverInfo> devices = cacheDriversBean.getDriversCache(2L);
    	Device deviceByID = new Device();
    	for(DriverInfo device:devices){
    		if(device.getDevice().getId() == id){
    			deviceByID = device.getDevice();	
    		}
    	}
        return deviceByID;
    }

    public DriverInfo getDdriverInfoByDeviceImei(long id) {
    	List<DriverInfo> devices = cacheDriversBean.getDriversCache(2L);
    	DriverInfo driver = new DriverInfo();
    	for(DriverInfo device:devices){
    		if(device.getDevice().getId() == id){
    			driver = device;	
    		}
    	}
        return driver;
    }
    
    @Override
    public Device getDeviceByUniqueId(String uniqueId) {
    	return getDeviceById(Long.valueOf(uniqueId));
    }

    public long addUnknownDevice(String uniqueId) {
    	System.out.println("addUnknownDevice Automatically registered dev "+uniqueId);
        Device device = new Device();
/*      device.setName(uniqueId);
        device.setUniqueId(uniqueId);
        cacheDrivers.getDevicesByUniqueId().put(uniqueId, device);
        cacheDrivers.getDevicesById().put(Long.valueOf(uniqueId),device);*/
        System.out.println("Automatically registered dev "+device.getId());
        return device.getId();
    }
    
    public void addDevice(Device device){
		try {
			System.out.println("adddevice start device.id "+device.getId());
			
//			Map<Long, Device> deviceMap = cacheDrivers.getDevicesById();
//			System.out.println("adddevice 2 ");
//			Map<String, Device> deviceUniqMap = cacheDrivers.getDevicesByUniqueId();
//			System.out.println("adddevice 3 "+device.getId());
//			deviceMap.put(device.getId(), device);
//			System.out.println("adddevice 4 "+device.getId());
//			deviceUniqMap.put(device.getUniqueId(), device);
//			System.out.println("adddevice 5 "+device.getId());
//			cacheDrivers.setDevicesById(deviceMap);
//			System.out.println("adddevice 6 "+device.getId());
//			cacheDrivers.setDevicesByUniqueId(deviceUniqMap);
			
			System.out.println("adddevice end id "+device.getId());
			
		} catch (Exception e) {
			System.out.println("Exception adddevice2" + e.getMessage());
		}
	}

    public boolean isLatestPosition(Position position) {
        Position lastPosition = getLastPosition(position.getDeviceId());
        return lastPosition == null || position.getFixTime().compareTo(lastPosition.getFixTime()) >= 0;
    }
    
    @Override
    public Position getLastPosition(long deviceId) {
        return positions.get(deviceId);
    }

    public void sendCommand(Command command) throws Exception {

    }

    public Collection<CommandType> getCommandTypes(long deviceId, boolean textChannel) {
        List<CommandType> result = new ArrayList<>();
//        Position lastPosition = Context.getDeviceManager().getLastPosition(deviceId);
//        if (lastPosition != null) {
//            BaseProtocol protocol = Context.getServerManager().getProtocol(lastPosition.getProtocol());
//            Collection<String> commands;
//            commands = textChannel ? protocol.getSupportedTextCommands() : protocol.getSupportedDataCommands();
//            for (String commandKey : commands) {
//                result.add(new CommandType(commandKey));
//            }
//        } else {
//            result.add(new CommandType(Command.TYPE_CUSTOM));
//        }
        return result;
    }
    
    public void savePosition(Position position){
    	System.out.println("Started savePosition");
    	databaseBean.savePosition(position);
    }

	@Override
	public void savePositions(List<Position> position) {
		// TODO Auto-generated method stub
		
	}
    
}
