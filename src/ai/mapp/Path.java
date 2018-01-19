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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

class Path {
    
    interface PathListener {
        void onTargetReached();
    }

    /**
     * source index = 0
     * destination is at last location
     */
    private final ArrayList<GridElement> pathElements = new ArrayList<>();
    
    private final PathIntersectionCollection intersections
            = new PathIntersectionCollection();
    
    private final PathListener pathListener;
    
    private final Bot bot;
    
    private PathIntersection nextIntersections[] = null,
            recentIntersections[] = null;
    
    private int iterator = 0;

    Path(Bot bot) {
        this.bot = bot;
        this.pathListener = bot;
    }
    
    private boolean isMotionAllowed() {
        if (iterator < 0)
            return false;
        
        GridElement nextPathElement = nextPathElement();
        
        Bot occupyingBot = nextPathElement.getOccupancy();
        if (occupyingBot != null)
            occupyingBot.push();
        
        if (intersections.isEmpty())
            return nextPathElement.getOccupancy() == null;
        
        if (nextIntersections == null || iterator == nextIntersections[0].startIndex)
            nextIntersections = intersections.peek();
        
        // check if bot is in movable condition
        boolean res = nextPathElement.getOccupancy() == null;
        // if not, it shall wait for next call to inspect intersection
        if (iterator == nextIntersections[0].startIndex - 1 && res) {
            for(PathIntersection nextIntersection: nextIntersections) 
                for (int i = nextIntersection.startIndex; i <= nextIntersection.endIndex; i++) {
                    Bot occupying = pathElements.get(i).getOccupancy();
                    if (occupying == nextIntersection.otherBot) {
                        int index = nextIntersection.startIndex,
                                direction = occupying.getDirectionAt(pathElements.get(index));
                        if (direction != Direction.NONE)
                            if(getDirectionAt(index) != direction)
                                return false;
                    }
                }
            recentIntersections = intersections.remove();
        }
        
        return res;
    }
    
    Location getNextLocation() {
        if (!isMotionAllowed())
            return null;
            
        pathElements.get(iterator).setOccupancy(null);
        GridElement element = nextPathElement();
        element.setOccupancy(bot);
        iterator++;
        Location p = element.getLocation();
        if(iterator == getSize() - 1) { // iterator is never equal to pathElements.size()
            pathListener.onTargetReached();
            iterator = -1;
        }
        return p;
    }
    
    private GridElement nextPathElement() {
        return pathElements.get(iterator + 1);
    }
    
    int getCurrentDirection() {
        if (iterator < getSize() - 1)
            return Direction.getDirection(pathElements.get(iterator).getLocation(), 
                    pathElements.get(iterator + 1).getLocation());
        
        return Direction.NONE;
    }
    
    int getDirectionAt(GridElement element) {
        if (recentIntersections == null)
            return Direction.NONE;
        
        for (int i = 0; i < getSize(); i++) 
            if (pathElements.get(i).equals(element))
                return getDirectionAt(i);
        
        return Direction.NONE;
    }
    
    int getDirectionAt(int index) {
        if (index < getSize() - 1)
            return Direction.getDirection(pathElements.get(index).getLocation(), 
                    pathElements.get(index + 1).getLocation());
        
        return Direction.NONE;
    }
	
    // A-star-specific addition
    void addElement(GridElement previousPathElement) {
        pathElements.add(0, previousPathElement);
    }
    
    int getSize() {
        return pathElements.size();
    }
    
    GridElement[] getPathElements() {
        return pathElements.toArray(new GridElement[0]);
    }
    
    void addPathIntersection(PathIntersection intersection) {
        intersections.add(intersection);
    }

    @Override
    public String toString() {
        String s = "";
        for(GridElement element: pathElements)
            s = s.concat(element.getLocation() + " -> ");
        return s;
    }
    
    void printIntersections() {
        System.out.println(intersections);
    }
    
    static class PathIntersection {
        
        private final int startIndex, endIndex;
        private final Bot otherBot;

        PathIntersection(int startIndex, int endIndex, Bot otherBot) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.otherBot = otherBot;
        }

        @Override
        public String toString() {
            return "I(" + startIndex + ", " + endIndex + ", " + otherBot + ")";
        }
    }
    
    private class PathIntersectionCollection {
        
        // sorted according to their startIndex
        private PathIntersection[] intersections = new PathIntersection[0];
        
        private int count = 0;
        
        void add(PathIntersection intersection) {
            /* 
            Initially Bot shall not be inside any intersection, i.e., 
            intersection.startIndex != 0
            */
            if (intersection.startIndex < 1)
                intersection = new PathIntersection(1, intersection.endIndex, intersection.otherBot);
            
            int index = Arrays.binarySearch(intersections, intersection, new Comparator<PathIntersection>() {
                @Override
                public int compare(PathIntersection o1, PathIntersection o2) {
                    if (o1.startIndex < o2.startIndex) return -1;
                    else if (o1.startIndex > o2.startIndex) return 1;
                    else if (o1.endIndex < o2.endIndex) return 1;
                    else if (o1.endIndex > o2.endIndex) return -1;
                    return 0;
                }
            });
            
            if (index < 0)
                index = -(index + 1); // insertion point
            
            int l = intersections.length;
            intersections = Arrays.copyOf(intersections, l + 1);
            System.arraycopy(intersections, index, intersections, index + 1, l - index);
            intersections[index] = intersection;
        }
        
        boolean isEmpty() {
            return intersections.length == 0;
        }
        
        PathIntersection[] remove() {
            if (intersections.length == 0)
                return null;
            
            if (count == 0)
                countEarliestIntersections();
            
            final PathIntersection[] removed = Arrays.copyOf(intersections, count);
            
            if (intersections.length > count)
                System.arraycopy(intersections, count, intersections, 0, 
                        intersections.length - count);
            
            intersections = Arrays.copyOf(intersections, intersections.length - count);
            
            count = 0;
            
            return removed;
        }
        
        PathIntersection[] peek() {
            if (intersections.length == 0)
                return null;
            
            if (count == 0)
                countEarliestIntersections();
            
            return Arrays.copyOf(intersections, count);
        }
        
        private void countEarliestIntersections() {
            count = 1;
            for (int i = count; i < intersections.length; i++) {
                if (intersections[0].startIndex != intersections[i].startIndex)
                    break;
                count++;
            }
        }
        
        PathIntersection[] toArray() {
            return Arrays.copyOf(intersections, intersections.length);
        }

        @Override
        public String toString() {
        String s = "";
        for(PathIntersection pi: intersections)
            s = s.concat(pi.toString() + " " + pathElements.get(pi.startIndex) + ", " + pathElements.get(pi.endIndex) + "\n");
        return s;
        }
    }
}
