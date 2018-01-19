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

public class Warehouse {
    private final String[] mapAsStringArray;
    private final String name;

    public Warehouse(String name, String[] mapAsStringArray) {
        this.mapAsStringArray = mapAsStringArray;
        this.name = name;
    }

    public String[] getMapAsStringArray() {
        return mapAsStringArray;
    }

    public String getName() {
        return name;
    }
    
    public int getRowCount() {
        return mapAsStringArray.length;
    }
    
    /**
     * Number of columns in smallest rectangle that fits in the
     * suggested map definition.
     * @return size of smallest string in the array
     */
    public int getColumnCount() {
        int l = mapAsStringArray[0].length();
        for (int i = 0; i < mapAsStringArray.length; i++) {
            int l_ = mapAsStringArray[i].length();
            if (l > l_)
                l = l_;
        }
        return l;
    }
}
