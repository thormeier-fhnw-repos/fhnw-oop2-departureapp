# FHNW OOP2 Departure app

This is a more or less simple Java/JavaFX app for the module OOP2 at FHNW. LED and SplitFlap were provided by course lecturer. FontAwesomeFX, Mockito and ScenicView are provided by their respective developers.

This app serves a purely educational purpose. Also the code isn't the cleanest.

www.fhnw.ch

## Usage

Start the app by executung `ch.fhnw.oop2.departure_app.DepartureAppController.main` without any arguments.

### Editor

To edit a train ride, simply select it in the list and change its values in the text fields on the right.

### Score board

If you click on the train icon in the top blue navigation, a score board will appear. Via the `upload` button you can transfer the currently selected train ride at the next free position in the board. You can add more than the available places, they will simply move upwards when the topmost departed.

By clicking on the `pause` button, you announce a departure, this is indicated by yellow blinking of the LEDs. If you now press the `play` button, the LEDs will turn green and the train is about to depart. Clicking on the `play` button again will remove the departed train from the score board and move all other trains in the list upwards.