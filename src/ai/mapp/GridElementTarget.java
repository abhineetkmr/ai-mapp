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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Ankit and Abhineet
 */
class GridElementTarget extends GridElement {
    
    private final Color ORANGE2 = new Color(255, 100, 0);
    
    private Color color;
    
    private int botID;
    
    GridElementTarget(int x, int y) {
        super(TYPE.TARGET, x, y);
        color = Color.WHITE;
    }

    void setColorAndId(Color color, int botId) {
        this.color = color;
        botID = botId;
    }
    
    void draw(Graphics g) {
        int x = getLocation().getPixelX(), y = getLocation().getPixelY();
        
        if (getOccupancy() == null)
            g.setColor(Color.ORANGE);
        else
            g.setColor(ORANGE2);
        
        g.fillRect(x, y, GRID_ELEMENT_SIZE, GRID_ELEMENT_SIZE);
        g.setColor(color);
        g.fillOval(x + 2, y + 2, GRID_ELEMENT_SIZE - 4, GRID_ELEMENT_SIZE - 4);
        if(botID > 0) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Serif", Font.PLAIN, 11));
            g.drawString("" + botID, x + 5, y + GridElement.GRID_ELEMENT_SIZE - 5);
        }
        g.setColor(Color.RED);
        g.drawLine(x, y, x + GRID_ELEMENT_SIZE, y + GRID_ELEMENT_SIZE);
        g.drawLine(x + GRID_ELEMENT_SIZE, y, x, y + GRID_ELEMENT_SIZE);
    }
}
