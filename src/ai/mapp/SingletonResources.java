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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 * Contains all singleton (static) resources that are supposed to be initialized
 * during application loading.
 * @author Abhineet Kumar
 */
public class SingletonResources {
    
    final static int ICON_SIZE = 14;
    
    final static javax.swing.ImageIcon PLAY_ICON = createImageIcon("/icon/play.png"), 
            PAUSE_ICON = createImageIcon("/icon/pause.png"), 
            RESET_ICON = createImageIcon("/icon/reset.png"), 
            NEXT_FRAME_ICON = createImageIcon("/icon/next.png");
    
    private static ImageIcon createImageIcon(String str) {
        try {
            return new ImageIcon(javax.imageio.ImageIO.read(Simulator.class.getResource(str)).getScaledInstance(ICON_SIZE, ICON_SIZE, java.awt.Image.SCALE_SMOOTH));
        } catch (IOException ex) {
            Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * To be called during application loading to initialize class members.
     */
    public static void doNothing() {}
}
