# StatMaster: A Native Android Application Built Using Kotlin

Name: Lee Thornton

## Overview.
This application was built as a final year project for a Higher Diploma in Computer Science. 

The main aim of this project was to allow users to record statistical data from GAA games
and calculate statistics in real time of this information

## Technologies.
Kotlin was used as the primary coding language
Firebase realtime database was used for data storage
Rooms was used for local persistence

## UI Design.

![][splashscreen]

>> When the app is launched a splash screen with the app logo is displayed.


![][login]

>> If the user already has an account they can login via this screen. They can also click the sign up button to sign up a new account of needed.

![][gameList]

>>Once the user is logged in they can view the list of all games currently created. There is a toolbar that allows the user to search for games by name
& add games. They can also filter for games if they have been won, drawn or lost. From the bottom navigation widget a user can go to the team list view,
user settings view and logout from the application. 

![][game]

>>If the user clicks on a game in the gameList they are taken to the game view. Where the title, home team goals, points & opposition score are displayed.
The user can click a checkbox to select the outcome of the game if it's been won, drawn or lost.

![][game2]

>>A dropdown menu containing all the applications teams is displayed in a dropdown in the game view. When a user selects a team the
teams players are then loaded into the game.

![][teamList]
>>If the user clicks on the teams button in the bottom nav bar they will be taken to this view. Here a list of all the teams the user has added is displayed.
Users can add teams, click on a team to edit and search for teams by name. Users can also use the bottom nav to cycle to the game list view, user settings
or logout from the application.

![][team]
>>If the user clicks on a team in the teamList they are taken to the team view. Where the name and list of players are displayed.
The user can search for players by name in this view and also add players to the team.

![][player]
>>The player view is accessible from the game view once a team has been loaded and the team view. Here a user can edit the player
name, number and change the player image. The plus and minus buttons here are used to record the game data and statistics are calculated in
realtime.

![][settings]

>>If the user clicks the settings button from the bottom nav they will be taken to this screen. This allows the user to edit their password & email.

[gameList]: ./app/src/main/res/drawable/gameList.jpg
[game]: ./app/src/main/res/drawable/game.jpg
[teamList]: ./app/src/main/res/drawable/teamList.jpg
[team]: ./app/src/main/res/drawable/team.jpg
[player]: ./app/src/main/res/drawable/player.jpg
[login]: ./app/src/main/res/drawable/login.jpg
[game]: ./app/src/main/res/drawable/game.jpg
[game2]: ./app/src/main/res/drawable/game2.jpg
[settings]: ./app/src/main/res/drawable/settings.jpg
[splashscreen]: ./app/src/main/res/drawable/statlogo.png




