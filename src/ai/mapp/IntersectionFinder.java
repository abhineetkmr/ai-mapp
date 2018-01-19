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
class IntersectionFinder {
    static void findIntersections(Bot b1, Bot b2) {
        Path p1 = b1.getPath(),
                p2 = b2.getPath();
        
        if(p1 == null || p2 == null)
            return;
        
        final GridElement[] p1Elements = p1.getPathElements(),
                p2Elements = p2.getPathElements();
        
        for (int i = 0; i < p1Elements.length; i++) {
            p2Trace: for (int j = 0; j < p2Elements.length; j++) {
                if (p1Elements[i].equals(p2Elements[j])) {
                    if (p1Elements[i + 1].equals(p2Elements[j + 1])) {
                        int k;
                        for (k = 1 ; k < p1Elements.length - i && k < p2Elements.length - j; k++)
                            if (!p1Elements[i + k].equals(p2Elements[j + k])) {
                                p1.addPathIntersection(new Path.PathIntersection(i, i + k - 1, b2));
                                p2.addPathIntersection(new Path.PathIntersection(j, j + k - 1, b1));
                                i = i + k - 1;
                                break p2Trace;
                            }
                        // loop will never reach the end of any path
                    } else if (j > 0 && p1Elements[i + 1].equals(p2Elements[j - 1])) {
                        int k;
                        for (k = 1 ; k < p1Elements.length - i && k <= j; k++)
                            if (!p1Elements[i + k].equals(p2Elements[j - k])) {
                                p1.addPathIntersection(new Path.PathIntersection(i, i + k - 1, b2));
                                p2.addPathIntersection(new Path.PathIntersection(j - k + 1, j, b1));
                                i = i + k - 1;
                                break p2Trace;
                            }
                        // loop will never reach the end of any path
                        
                        if (k - j > 0) {
                                p1.addPathIntersection(new Path.PathIntersection(i, i + k - 1, b2));
                                p2.addPathIntersection(new Path.PathIntersection(j - k + 1, j, b1));
                                i = i + k - 1;
                            break;
                        }
                    } else {
                        p1.addPathIntersection(new Path.PathIntersection(i, i, b2));
                        p2.addPathIntersection(new Path.PathIntersection(j, j, b1));
                        break;
                    }
                }
            }
        }
    }
}
