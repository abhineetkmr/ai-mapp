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

import java.util.HashMap;

abstract class GridElement {
    
    public static final int GRID_ELEMENT_SIZE = 20, // in pixels (grid-element is square)
            EFFICIENT_ONE_STEP_COST = 1,
            INEFFICIENT_ONE_STEP_COST = 2;
    
    private final TYPE type;
    
    private final Location location;
    
    private final HashMap<GridElementTarget, Integer> targetHeuriticCostMap = new HashMap<>();
    
    private final HashMap<Bot, CostInfo> botCostInfoMap = new HashMap<>();
    
    private Bot occupyingBot = null;

    GridElement(TYPE type, int x, int y) {
        this.type = type;
        location = new Location(x, y);
    }
    
    void setOccupancy(Bot bot) {
        occupyingBot = bot;
    }
    
    Bot getOccupancy() {
        return occupyingBot;
    }
    
    int getMotionDirection() {
        if (occupyingBot == null)
            return Direction.NONE;
        
        return occupyingBot.getCurrentDirection();
    }
    
    void setCostInfo(Bot b, CostInfo costInfo) {
        botCostInfoMap.put(b, costInfo);
    }
    
    int getCostFor(Bot b) {
        if(!botCostInfoMap.containsKey(b))
            return Integer.MAX_VALUE;
        
        return botCostInfoMap.get(b).cost;
    }
    
    int getDirectionFor(Bot b) {
        if(!botCostInfoMap.containsKey(b))
            return Direction.NONE;
        
        CostInfo.LinkInfo li = botCostInfoMap.get(b).linkInfo;
        return li == null ? Direction.NONE : li.direction;
    }
    
    GridElement getParentFor(Bot b) {
        if(!botCostInfoMap.containsKey(b))
            return null;
        
        CostInfo.LinkInfo li = botCostInfoMap.get(b).linkInfo;
        return li == null ? null : li.parentGridElement;
    }
    
    int getHeuristicCostFor(Bot b) {
        return getHeuristicCostFor(b.getTarget());
    }
    
    int getHeuristicCostFor(GridElementTarget target) {
        if (targetHeuriticCostMap.containsKey(target))
            return targetHeuriticCostMap.get(target);
        
        Location targetLocation = target.getLocation();
        int h = Math.abs(targetLocation.x - location.x) 
                + Math.abs(targetLocation.y - location.y);
        
        targetHeuriticCostMap.put(target, h);
                
        return h;
    }
    
    int getTotalScore(Bot b) {
        return getCostFor(b) + getHeuristicCostFor(b);
    }
        
    public boolean isObstacle(Bot b)
    {
        switch(type) {
            case TARGET:
                return !this.equals(b.getTarget());
        }
        return isObstacleForAll();
    }
        
    public boolean isObstacleForAll() {
        return !type.equals(TYPE.BLANK);
    }
	
    public boolean isOcuupied( ) {
        return occupyingBot != null;
    }
    
    public TYPE getType() {
        return type;
    }
    
    Location getLocation() {
        return location;
    }
        
    @Override 
    public String toString()
    {
        return type +  ": "  + location;
    }
    
    static class Location {
        
        final private int x, y;

        Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }
    
        int getPixelX(){
            return x * GRID_ELEMENT_SIZE;
        }

        int getPixelY(){
            return y * GRID_ELEMENT_SIZE;
        }
        
        Location inDirection(int direction) {
            switch(direction) {
                case Direction.ABOVE:
                    return above();
                    
                case Direction.BELOW:
                    return below();
                    
                case Direction.LEFT:
                    return toLeft();
                    
                case Direction.RIGHT:
                    return toRight();
                    
                default:
                    return this;
            }
        }
        
        Location toLeft() {
            return new Location(x - 1, y);
        }
        
        Location toRight() {
            return new Location(x + 1, y);
        }
        
        Location above() {
            return new Location(x, y - 1);
        }
        
        Location below() {
            return new Location(x, y + 1);
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
    
    /**
     * <code>CostInfo</code> conveys the cost of reaching the 
     * <code>GridElement</code> via parent <code>GridElement</code>.
     * <br>
     * It holds both information: cost, and parent <code>GridElement</code>
     */
    static class CostInfo {
        
        static class LinkInfo {
            
            final private GridElement parentGridElement;
        
            final private int direction;

            public LinkInfo(GridElement parentGridElement, int direction) {
                this.parentGridElement = parentGridElement;
                this.direction = direction;
            }
        }
        
        final private int cost;
        
        final private LinkInfo linkInfo;

        public CostInfo(int cost, LinkInfo linkInfo) {
            this.cost = cost;
            this.linkInfo = linkInfo;
        }
    }
    
    static class OccupancyInfo {
        final private long stepTime;
        final private int stepDuration;

        public OccupancyInfo(long stepTime, int stepDuration) {
            this.stepTime = stepTime;
            this.stepDuration = stepDuration;
        }
    }
    
    static enum TYPE { 
        WALL,
        TARGET,
        BLANK
    };
}
