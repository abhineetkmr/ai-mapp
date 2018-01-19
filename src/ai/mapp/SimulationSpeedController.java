/*
 * Copyright 2018 Abhineet Kumar.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.mapp;

/**
 *
 * @author Abhineet Kumar
 */
public class SimulationSpeedController {
    
    final static int SPEED_MULTIPLIER_MAX = 30; // maximum speed-multiplier
    final static private int FRAME_INTERVAL_MAX = 2400; // minimum frame-interval in ms
        
    private int speedMultiplier, frameInterval;// frameInterval = FRAME_INTERVAL_MAX / speedMultiplier

    SimulationSpeedController(int speedMultiplier) {
        setSpeedMultiplier(speedMultiplier);
    }

    int getFrameInterval() {
        return frameInterval;
    }

    int getSpeedMultiplier() {
        return speedMultiplier;
    }
    
    void speedUp() {
        if (speedMultiplier < SPEED_MULTIPLIER_MAX)
            speedMultiplier ++;
        calculateFrameRate();
        
    }
    
    void slowDown() {
        if (speedMultiplier > 1)
            speedMultiplier --;
        calculateFrameRate();
    }

    final void setSpeedMultiplier(int speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
        calculateFrameRate();
    }
    
    private void calculateFrameRate() {
        frameInterval = FRAME_INTERVAL_MAX / speedMultiplier;
    }
}
