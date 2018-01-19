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

import ai.mapp.GridElement.Location;

/**
 *
 * @author Ankit and Abhineet
 */
class Direction {
    
    final private static int HORIZONTAL_DIRECTION = 0b10,
            VERTICAL_DIRECTION = 0b100;
    
    final public static int LEFT = HORIZONTAL_DIRECTION,
            RIGHT = HORIZONTAL_DIRECTION | 1,
            ABOVE = VERTICAL_DIRECTION,
            BELOW = VERTICAL_DIRECTION | 1,
            NONE = 0;
    
    /**
     * Gives direction of adjacent location from given location.
     * 
     * @param location location
     * @param adjacentLocation  location adjacent to location
     * @return direction of location from adjacentLocation
     */
    static int getDirection(Location location, Location adjacentLocation) {
        
        int delX = adjacentLocation.getX() - location.getX(),
                delY = adjacentLocation.getY() - location.getY(); 
        
        if (delX > 0)
            return RIGHT;
        else if(delX < 0)
            return LEFT;
        else {
            if (delY > 0)
                return BELOW;
            else if(delY < 0)
                return ABOVE;
            else
                return NONE;
        }
    }
    
    static boolean isHorizontal(int direction) {
        return (direction & HORIZONTAL_DIRECTION) > 0;
    }
    
    static boolean isVertical(int direction) {
        return (direction & VERTICAL_DIRECTION) > 0;
    }
    
    static boolean areParallel(int direction1, int direction2) {
        return (direction1 & direction2) > 1;
    }
    
    static boolean areOpposite(int direction1, int direction2) {
        return (direction1 ^ direction2) == 1;
    }
    
    static int[] getHorizintalDirections() {
        return new int[] { LEFT, RIGHT };
    }
    
    static int[] getVerticalDirections() {
        return new int[] { ABOVE, BELOW };
    }
    
    static int getOppositeDirection(int direction) {
        return direction ^ 1;
    }
}
