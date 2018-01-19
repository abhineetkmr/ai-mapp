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

import static ai.mapp.GridElement.EFFICIENT_ONE_STEP_COST;
import static ai.mapp.GridElement.INEFFICIENT_ONE_STEP_COST;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author Ankit and Abhineet
 */
class APlusSpeedyPathFinder {
    
    final private Bot bot;
    
    final private Grid grid;

    APlusSpeedyPathFinder(Bot bot, Grid grid) {
        this.bot = bot;
        this.grid = grid;
    }
    
    void findPath() {
        PriorityQueue<GridElement> openSet = new PriorityQueue<>(new Comparator<GridElement>() {
                @Override
                public int compare(GridElement o1, GridElement o2) {
                    if (o1.getTotalScore(bot) > o2.getTotalScore(bot))
                        return 1;
                    
                    if (o1.getTotalScore(bot) < o2.getTotalScore(bot))
                        return -1;
                    
                    return 0;
                }
        });
         
        ArrayList<GridElement> closedSet = new ArrayList<>();
        
        GridElement element = grid.getElement(bot.getCurrentLocation()); //start point
        element.setCostInfo(bot, new GridElement.CostInfo(0, null));
        openSet.add(element);
        
        while (!openSet.isEmpty()) {
            element = openSet.poll();
            if (element.equals(bot.getTarget())) {
                constructAndSetPath();
                break;
            }
            closedSet.add(element);
            expand(element, closedSet, openSet);
        }
    }
    
    private void expand(GridElement currentElement, ArrayList<GridElement> closedSet, 
            PriorityQueue<GridElement> openSet) {
        
        int currentElementDirection = currentElement.getDirectionFor(bot),
                currentElementCost = currentElement.getCostFor(bot);
        
        for (GridElement neighbour : grid.getTraversableNeighbours(currentElement, bot)) {
            if(closedSet.contains(neighbour))
                continue;
            
            int cost, neighbourDirection;
            
            neighbourDirection = Direction.getDirection(currentElement.getLocation(), 
                    neighbour.getLocation());
            
            if (currentElementDirection == neighbourDirection)
                cost = currentElementCost + EFFICIENT_ONE_STEP_COST;
            else
                cost = currentElementCost + INEFFICIENT_ONE_STEP_COST;
            
            if(openSet.contains(neighbour)) {
                if (neighbour.getCostFor(bot) > cost) {
                    neighbour.setCostInfo(
                            bot, 
                            new GridElement.CostInfo(
                                    cost, 
                                    new GridElement.CostInfo.LinkInfo(
                                            currentElement, 
                                            neighbourDirection
                                    )
                            )
                    );
                    openSet.add(neighbour);
                }
            }
            else {
                neighbour.setCostInfo(
                        bot, 
                        new GridElement.CostInfo(
                                cost, 
                                new GridElement.CostInfo.LinkInfo(
                                        currentElement, 
                                        neighbourDirection
                                )
                        )
                );
                openSet.add(neighbour);
            }
        }
    }
    
    private void constructAndSetPath() {
        Path path = new Path(bot);
        GridElement element = bot.getTarget();
        while(element != null) {
            path.addElement(element);
            element = element.getParentFor(bot);
        }
        bot.setPath(path);
    }
}
