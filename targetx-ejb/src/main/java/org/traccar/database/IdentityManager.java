/*
 * Copyright 2015 Anton Tananaev (anton@traccar.org)
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

import java.util.List;

import org.traccar.model.Device;
import org.traccar.model.Position;

public interface IdentityManager {

    Device getDeviceById(long id);

    Device getDeviceByUniqueId(String uniqueId) throws Exception;

    Position getLastPosition(long deviceId);

    boolean isLatestPosition(Position position);

    long addUnknownDevice(String uniqueId);
    
    void addDevice(Device device);
    
    public void savePosition(Position position);
    
    public void savePositions(List<Position> position);
}
