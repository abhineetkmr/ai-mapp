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
import java.util.ArrayList;

/**
 *
 * @author Ankit and Abhineet
 */
public class Grid {
    
    final private GridElement[][] gridElements; // gridElements[Y][X]

    /**
     *
     * @param height total number of rows
     * @param width total number of columns
     */
    Grid(int height, int width) {
        gridElements = new GridElement[height][width];
    }

    GridElement getElement(Location location) {
        return getElement(location.getX(), location.getY());
    }

    GridElement getElement(int x, int y) {
        return gridElements[y][x];
    }
    
    /**
     * Adds grid-element at assigned location. It will replace
     * the pre-existing grid-element at that location.
     * @param ge grid-element to be added
     */
    void addElement(GridElement ge) {
        Location location = ge.getLocation();
        gridElements[location.getY()][location.getX()] = ge;
    }
    
    GridElement[] getTraversableNeighbours(Location location, Bot bot) {
        return getTraversableNeighbours(getElement(location), bot);
    }
    
    GridElement[] getTraversableNeighbours(GridElement ge, Bot bot) {
        ArrayList<GridElement> neighbours = new ArrayList<>();
        
        final Location location = ge.getLocation();
        
        addTraversableNeighbour(neighbours, bot, location.above());
        addTraversableNeighbour(neighbours, bot, location.below());
        addTraversableNeighbour(neighbours, bot, location.toRight());
        addTraversableNeighbour(neighbours, bot, location.toLeft());
        
        return neighbours.toArray(new GridElement[0]);
    }
    
    void addTraversableNeighbour(final ArrayList<GridElement> neighbours, 
            final Bot bot, final Location location) {
        
        if(locationExists(location)) {
            GridElement element = getElement(location);
            if(!element.isObstacle(bot)) {
                neighbours.add(element);
            }
        }
    }
    
    boolean locationExists(Location location) {
        if(location.getX() < 0)
            return false;
        
        if(location.getY() < 0)
            return false;
        
        if(location.getX() >= gridElements[0].length) // width
            return false;
        
        return location.getY() < gridElements.length; // height
    }
}
