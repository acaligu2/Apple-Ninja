# CS441_Project3

Youtube Game Demo --> https://youtu.be/v-39PiZgtD8

This is a clone of Fruit Ninja made using Android Studio. The objective is to slice as many Apple logos as you can. The user can only miss three before the game is over. If the user swipes on an Android, the game is over, similar to the way bombs are used in the real Fruit Ninja.

To allow the user to swipe on objects, I created a small Rectange object that is invisible. The code tracks the location of the user's finger and updates the location of the invisible rectangle. There are 6 randomly assigned Apple logos to swipe, each having a unique score amount. They are randomly chosen before being spawned on the screen. The speed at which the Apples and Androids fall will increase as the time of the game gets longer. There are a few sound effects for swiping a fruit, hitting the bomb, and missing a fruit.


