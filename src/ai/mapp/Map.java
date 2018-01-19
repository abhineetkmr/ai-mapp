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

import ai.mapp.GridElement.Location;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;

class Map implements BotsHolder, GridHolder, Bot.BotListener {
    
    private final int ROWS_COUNT, // number of rows
            COLUMNS_COUNT; // number of columns
    final int HEIGHT, WIDTH; // of mapImage
    
    int unusedBots;
    
    private Image mapImage; // re-usable

    private Grid grid;
    
    final private ArrayList<Bot> bots = new ArrayList<>(),
            idleBots = new ArrayList<>(),
            nonIdleBots = new ArrayList<>();
    
    final private ArrayList<Location> initialBotsLocation = new ArrayList<>();
    
    private ArrayList<GridElementTarget> targets = new ArrayList<>();
    
    private final PathPlanner pathPlanner = new PathPlanner(this);

    private boolean halt = false;
    
    /**
     * Creates a preset warehouse map from definition
     * in the form of <code>String</code> array.
     * @param warehouse definition for which the map is to be obtained
     */
    Map(Warehouse warehouse) {
        final String[] mapDefinition = warehouse.getMapAsStringArray();
        
        ROWS_COUNT = mapDefinition.length;
        COLUMNS_COUNT = mapDefinition[0].length();
        HEIGHT = ROWS_COUNT * GridElement.GRID_ELEMENT_SIZE;
        WIDTH = COLUMNS_COUNT * GridElement.GRID_ELEMENT_SIZE;
        
        grid = new Grid(ROWS_COUNT, COLUMNS_COUNT);
        
        for (int i = 0; i < ROWS_COUNT; i++) {
            for (int j = 0; j < COLUMNS_COUNT; j++) {
                
                switch (mapDefinition[i].charAt(j)) {
                    case ' ':
                        addBlankElement(j, i);
                        break;
                    case 'X':
                        addWallElement(j, i);
                        break;
                    case 'O':
                        addTarget(j, i);
                        break;
                    case 'B':
                        addBot(j, i);
                        break;
                    default:
                        addBlankElement(j, i);
                        break;
                }
            }
        }
    }

    /**
     * Creates empty or blank map of specified size.
     * Empty map has all nulls and blank map has all blank grid-elements.
     * It is used for creating customized map, especially random map.
     * @param rows number of rows
     * @param columns number of columns
     * @param empty true means empty map, false means blank map
     */
    Map(int rows, int columns, boolean empty) {
        ROWS_COUNT = rows;
        COLUMNS_COUNT = columns;
        HEIGHT = ROWS_COUNT * GridElement.GRID_ELEMENT_SIZE;
        WIDTH = COLUMNS_COUNT * GridElement.GRID_ELEMENT_SIZE;
        
        grid = new Grid(ROWS_COUNT, COLUMNS_COUNT);
        
        if(!empty)
            for (int i = 0; i < ROWS_COUNT; i++) {
                for (int j = 0; j < COLUMNS_COUNT; j++) {
                    addBlankElement(j, i);
                }
            }
    }
    
    final void addBlankElement(int x, int y) {
        grid.addElement(new GridElementBlank(x, y));
    }
    
    final void addWallElement(int x, int y) {
        grid.addElement(new GridElementWall(x, y));
    }
    
    final void addBot(int x, int y) {
        GridElement element = new GridElementBlank(x, y);
        grid.addElement(element);
        Bot b;
        Location location = element.getLocation();
        initialBotsLocation.add(location);
        bots.add(
                b = new Bot(
                        location, 
                        bots.size() + 1,
                        this,
                        this
                )
        );
        idleBots.add(b);
        element.setOccupancy(b);
    }
    
    final void addTarget(int x, int y) {
        GridElementTarget target = new GridElementTarget(x, y);
        grid.addElement(target);
        targets.add(target);
    }
    
    final GridElement getElementAt(int x, int y) {
        return grid.getElement(x, y);
    }
    
    final void assignTargets() {
        ArrayList<GridElementTarget> unassignedTargets = new ArrayList<>(targets);
        
        ArrayList<Bot> unassignedBots = new ArrayList<>(bots);
        
        class BotTargetPair implements Comparable<BotTargetPair>{
            final Bot bot;
            final GridElementTarget target;
            final int distance;

            public BotTargetPair(Bot bot, GridElementTarget target) {
                this.bot = bot;
                this.target = target;
                distance = getActualDistance(bot.getCurrentLocation(), target.getLocation());
            }

            @Override
            public int compareTo(BotTargetPair o) {
                if(distance < o.distance)
                    return -1;
                else if(distance > o.distance)
                    return 1;
                return 0;
            }
        }
        
        ArrayList<BotTargetPair> botTargetPairs = new ArrayList<>();
        
        for (Bot b: unassignedBots) {
            for (GridElementTarget target: unassignedTargets)
                botTargetPairs.add(new BotTargetPair(b, target));
        }
        
        botTargetPairs.sort(null);
        
        while(!unassignedBots.isEmpty()) {
            if(unassignedTargets.isEmpty() || botTargetPairs.isEmpty())
                break;
            
            BotTargetPair pair = botTargetPairs.get(0);
            
            if (unassignedBots.contains(pair.bot) && unassignedTargets.contains(pair.target)) {
                pair.bot.setTarget(pair.target);
                pair.target.setColorAndId(pair.bot.getColor(), pair.bot.getID());
                unassignedBots.remove(pair.bot);
                unassignedTargets.remove(pair.target);
            }
            
            botTargetPairs.remove(0);
        }
    }
    
    void reset() {
        halt = false;
        
        for (Bot b: bots) {
            b.setPath(null);
            grid.getElement(b.getCurrentLocation()).setOccupancy(null);
        }
        
        for (int i = 0; i < bots.size(); i++) {
            Bot b = bots.get(i);
            Location location = initialBotsLocation.get(i);
            grid.getElement(location).setOccupancy(b);
            b.setCurrentLocation(location);
            b.setTarget(b.getTarget());
        }
    }
    
    private int getActualDistance(Location l1, Location l2) {
        int delX = l1.getX() - l2.getX(),
                delY = l1.getY() - l2.getY();
        return (int) (Math.sqrt(delY * delY + delX * delX) * 10);
    }

    @Override
    public Bot[] getAllBots() {
        return bots.toArray(new Bot[0]);
    }

    @Override
    public Bot[] getIdleBots() {
        return idleBots.toArray(new Bot[0]);
    }

    @Override
    public Bot[] getNonIdleBots() {
        return nonIdleBots.toArray(new Bot[0]);
    }

    @Override
    public void setBotAsIdle(Bot b) {
        if (nonIdleBots.remove(b))
            idleBots.add(b);
    }

    @Override
    public void setBotAsNonIdle(Bot b) {
        if (idleBots.remove(b))
            nonIdleBots.add(b);
    }

    @Override
    public Grid getGrid() {
        return grid;
    }
	
    void draw(Container c, Graphics graphics) {
        if (mapImage == null) {
            mapImage = c.createImage(WIDTH, HEIGHT);
            Graphics gMap = mapImage.getGraphics();

            gMap.setColor(Color.WHITE);
            gMap.fillRect(0, 0, WIDTH, HEIGHT);

            drawElements(gMap);
            gMap.dispose();
            c.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        }
        
        Insets insets = c.getInsets();
                
        Graphics gMap = mapImage.getGraphics();
        //trail
        /*for (Bot b: bots)
            b.draw(new Insets(0, 0, 0, 0), gMap);*/
        
        for (GridElementTarget target: targets)
            target.draw(gMap);
        gMap.dispose();
        graphics.drawImage(mapImage, insets.left, insets.top, c);
        
        for (Bot b: bots)
            b.draw(insets, graphics);
    }
	
    private void drawElements(Graphics g) {
        for (int y = 0; y < ROWS_COUNT; y++) {
            for (int x = 0; x < COLUMNS_COUNT; x++) {
                GridElement element = grid.getElement(x, y);
                switch (element.getType()) {
                    
                    case TARGET:
                        ((GridElementTarget)element).draw(g);
                        break;
                
                    case WALL:
                        ((GridElementWall)element).draw(g);
                        break;
                
                    case BLANK:
                        ((GridElementBlank)element).draw(g);
                        break;
                                
                }
            }
        }   
    }

    boolean update() {
        if (halt)
            return !halt;
        
        halt = true;
        for (Bot b: bots)
            halt = !b.move() && halt;
        
        return !halt;
    }

    @Override
    public void onBotIdle(Bot b) {
        setBotAsIdle(b);
    }

    @Override
    public void onBotTargetSet(Bot b) {
        pathPlanner.planPathForBot(b, new PathPlanner.PathPlanListener() {
            @Override
            public void onPathFound(Bot b) {
                setBotAsNonIdle(b);
            }

            @Override
            public void onPathNotFound(Bot b) { }
        });
    }
}

