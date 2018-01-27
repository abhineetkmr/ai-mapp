# ai-mapp
This project is on Multi-Agent Path Planning(MAPP) using Decoupled Coordination Technique and a variant of A-star algorithm.
It has a simulator that makes analysis of algorithm very easy.
The project has been published for the students or anyone who pursue Artificial Intelligence, and is licensed under <a href="http://www.apache.org/licenses/LICENSE-2.0">Apache License 2.0</a>.
<br><br>
This project has been made using NetBeans and JDK8.
<br><br>
This is just the basic version (see <a href="https://youtu.be/Tfu6WJT_ecE">demo</a>) which assigns the bots with targets in a grid with numerous obstacles, finds their paths (independent of each other; decoupling) using variant of A-star alogorithm which we call as A-Plus algorithm (only horizontal and vertical motions are allowed), finds path-intersections and manages their motion dynamically (decoupling).
<br><br>
It has a lot of scope for improvement like: bots-assignment, velocity management, increasing problem complexity etc. 
Simulator makes it easy to have a better vision of such improvements - <b>this is the second important goal of this project</b>. 
If anyone uses it, they can let me know and I'll help improve the simulator and also make the project more manageable.
<br><br>
It is advised that you try using simulator (you'll need <a href="https://java.com/en/download/">Java 8</a> to run the JAR file) along with reading the source code and find out what improvements are possible. 
Then add/improve algorithms. You can post your algorithm or improvements and let someone, including me, write the code for you. Then you can test your algorithm using the simulator.
