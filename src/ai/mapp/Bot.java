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

/* 
 * @author Ankit and Abhineet
 */
package ai.mapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

class Bot implements Path.PathListener {
    
    interface BotListener {
        void onBotIdle(Bot b);
        void onBotTargetSet(Bot b);
    }
    
    private GridElementTarget target;

    private GridElement.Location currentLocation;
	
    private Path path;
    
    final private int ID;
    
    final private Color color;
    
    final private BotListener botListener;
    
    final private Grid grid;
    
    private int moves = 0;

    Bot(GridElement.Location currentLocation, int id, BotListener listener, 
            GridHolder gridHolder) {
        ID = id;
        botListener = listener;
        grid = gridHolder.getGrid();
        
        int r = (ID & 4) >> 2, 
                g = (ID & 2) >> 1, 
                b = ID & 1,
                k = (ID & 8) >> 3;
        color = new Color(100 * r + k * 80, 140 * g + k * 80, 100 * b + k * 80);
        setCurrentLocation(currentLocation);
    }
    
    int getID() {
        return ID;
    }

    Color getColor() {
        return color;
    }

    GridElement.Location getCurrentLocation() {
        return currentLocation;
    }

    final void setCurrentLocation(GridElement.Location location) {
        this.currentLocation = location;
    }

    GridElementTarget getTarget() {
        return target;
    }
    
    void setTarget(GridElementTarget target) {
        this.target=target;
        botListener.onBotTargetSet(this);
    }
        
    void setPath(Path path) {
        this.path = path;
    }
    
    Path getPath() {
        return path;
    }
    
    int getDirectionAt(GridElement element) {
        if (path == null)
            return Direction.NONE;
        
        return path.getDirectionAt(element);
    }
    
    int getCurrentDirection() {
        if (path == null)
            return Direction.NONE;
        
        return path.getCurrentDirection();
    }
	
    void draw(Insets insets, Graphics graphics) {
        graphics.setColor(color);
        int x = insets.left + currentLocation.getPixelX(),
                y = insets.top + currentLocation.getPixelY();
        graphics.fillOval(
                x + 2, 
                y + 2, 
                GridElement.GRID_ELEMENT_SIZE - 4, 
                GridElement.GRID_ELEMENT_SIZE - 4
        );
        graphics.setFont(new Font("Serif", 0, 11));
        graphics.setColor(Color.WHITE);
        graphics.drawString("" + ID, x + 5, y + GridElement.GRID_ELEMENT_SIZE - 5);
    }
    
    boolean move() {
        if (path != null) {
            moves++;
            GridElement.Location p = path.getNextLocation();
            if (p != null) {
                currentLocation = p;
                return true;
            }
        }
        return false;
    }

    int getMoves() {
        return moves;
    }
    
    void push() {
        if (path == null) {
            for(GridElement ge: grid.getTraversableNeighbours(currentLocation, this))
                if (!ge.isOcuupied()) {
                    grid.getElement(currentLocation).setOccupancy(null);
                    currentLocation = ge.getLocation();
                    ge.setOccupancy(this);
                }
        }
    }
    
    void printPath() {
        System.out.println(path);
    }

    @Override
    public String toString() {
        return "Bot - " + ID;
    }

    @Override
    public void onTargetReached() {
        path = null;
        botListener.onBotIdle(this);
    }
}


