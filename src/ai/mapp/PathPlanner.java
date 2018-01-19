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
 * @author Ankit and Abhineet
 */
class PathPlanner {
    
    private final BotsHolder botsHolder;
    private final GridHolder gridHolder;
    
    PathPlanner(Map m) {
        this(m, m);
    }

    PathPlanner(BotsHolder botsHolder, GridHolder gridHolder) {
        this.botsHolder = botsHolder;
        this.gridHolder = gridHolder;
    }
    
    void planPathForBot(Bot bot, PathPlanListener listener) {
        
        new APlusSpeedyPathFinder(bot, gridHolder.getGrid()).findPath();
        
        Path botPath = bot.getPath();
        if (botPath == null) {
            listener.onPathNotFound(bot);
            return;
        }
        
        listener.onPathFound(bot);
        
        for(Bot b: botsHolder.getNonIdleBots()) {
            // do not find intersections with itself
            if (bot.equals(b))
                continue;
            IntersectionFinder.findIntersections(bot, b);
        }
    }
    
    interface PathPlanListener {
        void onPathFound(Bot b);
        void onPathNotFound(Bot b);
    }
}
