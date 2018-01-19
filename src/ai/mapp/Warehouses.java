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
public class Warehouses {
    final public static Warehouse WAREHOUSE1 = new Warehouse("Warehouse 1",
            new String[] {
                "XXXXXXXXXXXXXXXXXXXXX",	// 1
                "XO X  X           O X",	// 2
                "X  X  X  XXXX  X  X X",	// 3
                "X  X  X  XO OX X  X X",	// 4
                "X  X  X  XO OX X  X X",	// 5
		"X        XO OX    X X",	// 6
		"X  XOOXOXXB X  X  X X",	// 7
		"X  O OXO X  X  X  X X",	// 8
		"XO X OXO X  XXXX  X X",	// 9
		"X  X OXO X XX  X  X X",	// 10
		"X    OOO X          X",	// 11
		"XBBBB    O          X",	// 12
		"X    OOO XBBBBBBBB  X",	// 13
		"X    OOO XBBBB   BB X",	// 14
		"X        XBBBBBBBBBBX",	// 15
		"XXXXXXXXXXXXXXXXXXXXX",	// 16
            }
    ), WAREHOUSE2 = new Warehouse("Warehouse 2",
            new String[] {
                "XXXXXXXXXXXXXXXXXXXXX",	// 1
		"X        O     O    X",	// 2
		"X  X  X  X OX  X  X X",	// 3
		"X  X  X  X  X  X  X X",	// 4
		"X  X  X  X  X  X  X X",	// 5
		"X  X  X  X  X  X  X X",	// 6
		"X                   X",	// 7
		"X                   X",	// 8
		"X                   X",	// 9
		"X                   X",	// 10
		"X                   X",	// 11
		"X                   X",	// 12
		"X                   X",	// 13
		"X                   X",	// 14
		"XBBB                X",	// 15
		"XXXXXXXXXXXXXXXXXXXXX",	// 16
            }
    ), WAREHOUSE3 = new Warehouse("Warehouse 3",
            new String[] {
                "XXXXXXXXXXXXXXXXXXXXX",	// 1
		"XO                  X",	// 2
		"XO X  X  X  X  X  X X",	// 3
		"XO X  X  X  X  X  X X",	// 4
		"XO X  X  X  X  X  X X",	// 5
		"XO X  X  X  X  X  X X",	// 6
		"XO X  X  X  X  X  X X",	// 7
		"XO X  X  X  X  X  X X",	// 8
		"XO X  X  X  X  X  X X",	// 9
		"XO X  X  X  X  X  X X",	// 10
		"X                   X",	// 11
		"X                   X",	// 12
		"X                   X",	// 13
		"XBB                 X",	// 14
		"XBBBBBBB            X",	// 15
		"XXXXXXXXXXXXXXXXXXXXX",	// 16
            }
    ), WAREHOUSE4 = new Warehouse("Warehouse 4",
            new String[] {
				"XXXXXXXXXXXXXXXXXXXXX",	// 1
				"XO                OOX",	// 2
				"X  X  X  X  X  X  X X",	// 3
				"X  X  X  XOOX  X  X X",	// 4
				"X  X  X  XOOX  X  X X",	// 5
				"X  X  X  X  X  X  X X",	// 6
				"X  X  X  X  X  X  X X",	// 7
				"X  X  X  X  X  X  X X",	// 8
				"XO X  X  X  X  X  X X",	// 9
				"X  X  X  X  X  X  X X",	// 10
				"X                   X",	// 11
				"X                   X",	// 12
				"X                   X",	// 13
				"X                   X",	// 14
				"XBBBBBBB            X",	// 15
				"XXXXXXXXXXXXXXXXXXXXX",	// 16
            }
    ), WAREHOUSE5 = new Warehouse("Warehouse 5",
            new String[] {
				"XXXXXXXXXXXXXXXXXXXXX",	// 1
				"XO                  X",	// 2
				"X  X  X  X  X  X  X X",	// 3
				"X  X  X  X  X  X  X X",	// 4
				"X  X  X  X  X  X  X X",	// 5
				"X  X  X  X  X  X  X X",	// 6
				"X  X  X  X  X  X  X X",	// 7
				"X  X  X  X  X  X  X X",	// 8
				"X  X  X  X  X  X  X X",	// 9
				"XOXXXOXOXXXXXXOXXOXXX",	// 10
				"X                   X",	// 11
				"X                   X",	// 12
				"X                   X",	// 13
				"X                   X",	// 14
				"XBBBBBB             X",	// 15
				"XXXXXXXXXXXXXXXXXXXXX",	// 16
            }
    ), WAREHOUSE6 = new Warehouse("Warehouse 6",
            new String[] {
				"XXXXXXXXXXXXXXXXXXXXX",	// 1
				"XO                  X",	// 2
				"X XX  X  X  X  X  X X",	// 3
				"X XX  X  X  X  X  X X",	// 4
				"X OX  X  X  X  X  X X",	// 5
				"X OX  X  X  X  X  X X",	// 6
				"X  X  X  X  X  X  X X",	// 7
				"X  X  X  X  X  X  X X",	// 8
				"X  X  X  X  X  X  X X",	// 9
				"XXXXXOXOXXXXXXOXXOXXX",	// 10
				"X                   X",	// 11
				"X                   X",	// 12
				"X                   X",	// 13
				"X                   X",	// 14
				"XBBBBBBB            X",	// 15
				"XXXXXXXXXXXXXXXXXXXXX",	// 16
            }
    ), WAREHOUSE7 = new Warehouse("Warehouse 7",
            new String[] {
				"XXXXXXXXXXXXXXXXXXXXX",	// 1
				"X     X  X          X",	// 2
				"X  X  X  X X  X  X  X",	// 3
				"X  X  X  X X  X  X  X",	// 4
				"X  X  X  X XOOXOOX  X",	// 5
				"X  XOOX  X X  X  X  X",	// 6
				"X  XOOX  X X  X  X  X",	// 7
				"X  XBBX  X X  X  X  X",	// 8
				"X  XBBX  X X  X  X  X",	// 9
				"X XX  XXXXXXBBXBBX  X",	// 10
				"X                   X",	// 11
				"X  X  XXXXXX        X",	// 12
				"X  X  X  X X        X",	// 13
				"X  X  X  X X        X",	// 14
				"X  X  X  X          X",	// 15
				"XXXXXXXXXXXXXXXXXXXXX",	// 16
            }
    ), WAREHOUSE8 = new Warehouse("Warehouse 8",
            new String[] {
				"XXXXXXXXXXXXXXXXXXXXX",	// 1
				"X                   X",	// 2
				"X  X  X  X X  X  X  X",	// 3
				"X  X  X  XXX  X  X  X",	// 4
				"X  X  X  OOO  X  X  X",	// 5
				"X  X  X  OOO  X  X  X",	// 6
				"X  X  X  XXX  X  X  X",	// 7
				"X  X  X  X X  X  X  X",	// 8
				"X  X  X  X X  X  X  X",	// 9
				"X  X  X  X X  X  X  X",	// 10
				"X                   X",	// 11
				"X                   X",	// 12
				"X                   X",	// 13
				"X                   X",	// 14
				"XBBBBBBB            X",	// 15
				"XXXXXXXXXXXXXXXXXXXXX",	// 16
            }
    ), WAREHOUSE9 = new Warehouse("Warehouse 9",
            new String[] {
				"XXXXXXXXXXXXXXXXXXXXX",	// 1
				"X                   X",	// 2
				"X  X  X  X X  X  X  X",	// 3
				"X  X  X  XXX  X  X  X",	// 4
				"X  X  X  OOO  X  X  X",	// 5
				"X  X  X       X  X  X",	// 6
				"X  X  X  OOOXXX  X  X",	// 7
				"X  X  X  XXX  X  X  X",	// 8
				"X  X  X  X X  X  X  X",	// 9
				"X  X  X  X X  X  X  X",	// 10
				"X                   X",	// 11
				"X                   X",	// 12
				"X                   X",	// 13
				"X                   X",	// 14
				"XBBBBB B            X",	// 15
				"XXXXXXXXXXXXXXXXXXXXX",	// 16
            }
    ), WAREHOUSE10 = new Warehouse("Warehouse 10",
            new String[] {
				"XXXXXXXXXXXXXXXXXXXXX",	// 1
				"X                   X",	// 2
				"X   XXXXXXXXXXXX    X",	// 3
				"X  XO  O   O   OX   X",	// 4
				"X X              X  X",	// 5
				"X XO            OX  X",	// 6
				"X X              X  X",	// 7
				"X  XO          OX   X",	// 8
				"X   X          X    X",	// 9
				"X                   X",	// 10
				"X                   X",	// 11
				"X                   X",	// 12
				"X                   X",	// 13
				"X                   X",	// 14
				"XBBBBBB             X",	// 15
				"XXXXXXXXXXXXXXXXXXXXX",	// 16
            }
    ), WAREHOUSE11 = new Warehouse("Warehouse 11",
            new String[] {
				"XXXXXXXXXXXXXXXXXXXXX",	// 1
				"XO               O  X",	// 2
				"X  X  X  X  X  X  X X",	// 3
				"X  X  X  XOOX  X  X X",	// 4
				"X  X  X  XOOX  X  X X",	// 5
				"X  X  X  X  X OX  X X",	// 6
				"X  X  X  X  X  X  X X",	// 7
				"X  X  X  X  X  X  X X",	// 8
				"XO X  X  X  X  X  X X",	// 9
				"X  X  X  X  X  X  X X",	// 10
				"X                   X",	// 11
				"X                   X",	// 12
				"X                   X",	// 13
				"X                   X",	// 14
				"X B B B B B B B B   X",	// 15
				"XXXXXXXXXXXXXXXXXXXXX",	// 16
            }
    );
}
