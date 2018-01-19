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
public class RandomMapGenerator {
    
    public static int MAX_TARGET_COUNT = 30;
    
    /**
     * Creates a random grid-map of size <code>rowsCount</code> * <code>
     * columnsCOunt</code>, having <code>targetCount</code> number of targets/
     * bots and probability of having an obstacle at a grid-elements equal to 
     * <code>obstacleProbability</code>.
     * <br><br> <b>Note:</b> number of targets = number of bots
     * @param rowsCount number of rows in grid
     * @param columnsCount number of columns in grid
     * @param targetCount number of targets/bots
     * @param obstacleProbability probability of finding an obstacle at a given
     * grid element
     * @return random grid-map
     */
    static Map createRandomMap(int rowsCount, int columnsCount, int targetCount, float obstacleProbability) {
        
        if (obstacleProbability > 1 || obstacleProbability < 0)
            throw new IllegalArgumentException("Value of obstacleProbability should be in the range - 0.0 to 1.0");
        
	Map map = new Map(rowsCount, columnsCount, true);
        
        /* Add obstacles/wall-elements using chess-board tracing.
        Chess-board tracing of a 2D array, here, means thinking of 2D array as
        chess-board and then tracing the "white" elements first, then "black" 
        elements, or vice-versa. */
        
        int obstacleCount = 0, estimatedObstacleCount = 
                (int)(rowsCount * columnsCount * obstacleProbability);
        
        outer: for (int i = 0; i < rowsCount; i++) {
            for(int j = i % 2; j < columnsCount; j+=2) {
                if(obstacleCount == estimatedObstacleCount)
                    break outer;
                if(Math.random() < obstacleProbability) {
                    map.addWallElement(j, i);
                    obstacleCount++;
                }
            }
        }
        
        
        outer2: for (int i = 0; i < rowsCount; i++) {
            for(int j = (i + 1) % 2; j < columnsCount; j+=2) {
                if(obstacleCount == estimatedObstacleCount)
                    break outer2;
                if(Math.random() < obstacleProbability) {
                    map.addWallElement(j, i);
                    obstacleCount++;
                }
            }
        }
        
        // Add targets
        
        for(int i = 0; i < targetCount; i++) {
            int x, y;
            do {
                x = (int)(Math.random() * columnsCount);
                y = (int)(Math.random() * rowsCount);
            } while (map.getElementAt(x, y) != null);
            map.addTarget(x, y);
        }
        
        // Add bots
        
        for(int i = 0; i < targetCount; i++) {
            int x, y;
            do {
                x = (int)(Math.random() * columnsCount);
                y = (int)(Math.random() * rowsCount);
            } while (map.getElementAt(x, y) != null);
            map.addBot(x, y);
        }
        
        // Add blanks
        
        for (int i = 0; i < rowsCount; i++)
            for(int j = 0; j < columnsCount; j++) {
                if(map.getElementAt(j, i) == null)
                    map.addBlankElement(j, i);
            }
            
        return map;
    }
    
    /**
     * Returns maximum number of targets/bots that can be accommodated in the 
     * grid of size <code>rowsCount</code> * <code>columnsCount</code>.
     * <br><br>This is supposed to be used as a limit on the target count while using
     * <code>RandomMapGenerator.createRandomMap()</code>. 
     * <br><br> <b>Note:</b> number of targets = number of bots
     * @param rowsCount number of rows in grid
     * @param columnsCount number of columns in grid
     * @param obstacleProbability probability of having an obstacle at any
     * grid-element
     * @return maximum number of targets/bots that can be accommodated in the 
     * grid
     */
    public static int maxTargetCount(int rowsCount, int columnsCount, float obstacleProbability) {
        int elementCount = rowsCount * columnsCount,
                obstacleCount = (int)(elementCount * obstacleProbability);
        int maxTC = (elementCount - obstacleCount) / 2;
        return (maxTC < MAX_TARGET_COUNT) ? maxTC : MAX_TARGET_COUNT;
    }
}
