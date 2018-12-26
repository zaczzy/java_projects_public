
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: zhaozeyu
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an approprate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Collections: 8 linked lists of falling objects, 2d Arrays, TreeSets

  2. I/O: Reading the levels from a text file, reading the high scores from the text file to display 
  in a frame; and writing the high score to a text file.

  3. Dynamic Dispatch: Falling Object is superclass of Falling Bar, Falling Bomb. These two objects
  are very distinct and they have different behavior.

  4.Recursion: Recursion is crucial to keep the timer keep changing its pace, because this modify
  interval at runtime is unsupported by Java. I have no idea what else I can do to set the timer
  interval while the timer is running. So here it is, I thought it was bad because there is no way
  to stop it once it is kicked off. But I am just glad that there is a way, cuz i saw the ways online
  are just too advanced and complicated, some event scheduling stuff.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  Game: assembles the top-level JComponents into the JPanel into the JFrame, and run it.
  
  GameCourt: All the creation of falling object list, control of behavior, especially what to expect 
  when the player hits a button at a specific position arrangement of falling objects. The painting 
  of all things in the canvas, and the creation of creator timers as well as moving object timers.
  
  LevelGenerator: this static class is used mostly for all io work with three files, level controls 
  time difference, speed controls speed, and high scores log the high score. Deal with all kinds of 
  data sabotage with resetting the game data. You can delete all three files and they will be 
  recreated.
  


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  At once a huge bug bugged me for half an hour even when I actually fixed it. Because it damaged
  the txt data, making it infinitely large, it blocks access and therefore stopped the right code 
  from working.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  
  I need to the make this game multi-threaded. I must be very easy to do that but I don't yet know
  how. My computer is quad-core with 8 threads so I plan on using at least 6, if not all. 



========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  1. How to add image to a JPanel:
  http://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
  
  2. Playing .mp3 and .wav in Java?
  http://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java
  
  3. I would like to say I looked into thread scheduling to see if the timer can change inteval at
  runtime, that failed. I saw that I can use some other event scheduling class but that was too
  complicated for me to figure in hours. Therefore I used the recursion, although it is impossible to
  pause the recursion, it is worth it because other ways are just TOO complicated.
  

Thoughts:
But today, I come up with the idea of making a Music Master game, where players use A S D F H J K L keys to hit the bars that fall down the screen, when they collide with target line. All of this will be accompanied by cool music in the background. I attached a pic as a template, but it won't be exactly identical.
So the music part is easy, importing javax.sound.sampled.* should suffice .wav sound.
Here are the rudimentary thoughts on how to meet as many requirements as possible:

        1.  I/O: I need 8 lanes of bars falling at pre-determined time and speed, therefore each game level should have two files that each log down the time and speed of bars.
        2.  Collections: I think I can have a Tree Map from Strings to Integers: "Excellent" -> 9 times; "Good" -> 5 times "Missed" -> 2 times.
        
Also another essential  usage of Linked List is, I need 8 linked list of Falling Bars, as they are generated on screen they are enqued, and as they fall out of screen they are dequed.

        3. JUnit testing to test the time and speed of  the falling objects and Dynamic Dispatch for different types of objects.
        4. adding pther falling objects: falling rectangle, falling bombs etc, and apply different rules on them.
        
Falling Rectangles: need to detect starting key press, assume the player keeps pressing, detect the time when press released to calculate the accumulative score.

Falling Bars: the score is calculated by finding the distance between the target line and the lowest falling object at the time of the key press.

Falling Bombs: the reverse of Falling bars, if too close within rangle when key pressed, deduct points.


