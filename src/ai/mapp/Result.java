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
public class Result {
    private int initialTimeCost,
            successPercentage,
            runtimeCost,
            unreachableBots;
    
    private float averagePathLength,
            averagePathCost,
            idealAveragePathCost;

    public float getIdealAveragePathCost() {
        return idealAveragePathCost;
    }

    public Result setIdealAveragePathCost(float idealAveragePathCost) {
        this.idealAveragePathCost = idealAveragePathCost;
        return this;
    }

    public int getInitialTimeCost() {
        return initialTimeCost;
    }

    public Result setInitialTimeCost(int initialTimeCost) {
        this.initialTimeCost = initialTimeCost;
        return this;
    }

    public int getSuccessPercentage() {
        return successPercentage;
    }

    public Result setSuccessPercentage(int successPercentage) {
        this.successPercentage = successPercentage;
        return this;
    }

    public int getRuntimeCost() {
        return runtimeCost;
    }

    public Result setRuntimeCost(int runtimeCost) {
        this.runtimeCost = runtimeCost;
        return this;
    }

    public float getAveragePathLength() {
        return averagePathLength;
    }

    public Result setAveragePathLength(float averagePathLength) {
        this.averagePathLength = averagePathLength;
        return this;
    }

    public float getAveragePathCost() {
        return averagePathCost;
    }

    public Result setAveragePathCost(float averagePathCost) {
        this.averagePathCost = averagePathCost;
        return this;
    }

    public int getUnreachableBots() {
        return unreachableBots;
    }

    public Result setUnreachableBots(int unreachableBots) {
        this.unreachableBots = unreachableBots;
        return this;
    }

    @Override
    public String toString() {
        return "Initial Time Cost = " + initialTimeCost + " ms"
                + "\nRuntime Cost = " + runtimeCost + " ns"
                + "\nUnreachable Bots = " + unreachableBots
                + "\nSuccess Percentage = " + successPercentage + "%"
                + "\nAverage Path Length = " + averagePathLength
                + "\nIdeal Average Path Cost = " + idealAveragePathCost
                + "\nAverage Path Cost = " + averagePathCost;
    }
}
