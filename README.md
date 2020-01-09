# ThoughtWorks-trains-diff
Shows differences before/after feedback for ThoughtWorks trains code.

Feedback from reviewers given during interview with Tracy (to fix):
- Node class shouldn't store both the nodes it's connected to and the nodes connected to it; only one.
- Unit tests are too bunched together; should break them up more.

Feedback addressed:
- The only place where the "from" method calls for Nodes were required was in the function in ShortestPath that found the smallest cycle; rewrote to only use "to" method calls, and removed the Node connectionsFrom attribute & associated methods/method calls.
- Unit tests broken up into multiple files (by functionality being tested), and tests broken up by type.

Other fixes:
- Some functions were exposed despite being internal to the class / not being called outside of the class; have made those private.

Still to fix (maybe):
- Should make sure all code properly distinguishes between the Node itself (e.g. "source") and the Character that indexes the node (e.g. "sourceName"), and not confuse labels in that regard.

Questions / to discuss:
- Need to ask for a LOT of feedback regarding unit tests... how to write them, how to organize them, etc. given that the way they were done for this project were super post-hoc and likely in bad practice
- Should there be a graph constructor class, instead of just including a function to construct graphs in the main class (Trains.java)? Maybe... worth asking
- Is there an easier way to find the smallest cycle? The current implementation feels like it could be rather slow.
- Object calisthenics questions... I don't know how not to violate some of these https://williamdurand.fr/2013/06/03/object-calisthenics/
- Overloading the shortestPath and pathsFind methods in Trains.java ... is it ok? On the one hand it allows polymorphism but on the other it may be confusing re: readability