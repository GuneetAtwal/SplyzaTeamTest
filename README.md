# SplyzaTeams - Android Recruit Test

- Project is created on Android Studio Chipmunk 2021.2.1 Patch 2
- Kotlin version - 1.7.0
- Current minimal supported sdk version - 21 (Android 5.0)
- MVVM Architecture
- Hilt for Dependency Injection
- Kotlin Co-routines for all the asynchronous tasks
- ViewModel, LiveData, etc. from Jetpack components
- Automated UI testing using Espresso


## This project is based on MVVM + Repository Architecture

- *Data Layer* : This layer will contain all data repositories, models and network logic
- *View Model* : This will contain all the business logic only.
- *View* : This will contain all the UI logic (All android context related things will be done here)<br/>
  This will contain Activities, Fragments, drawables, layouts, styles, strings etc.
- *DI* : All dependency injection related logic will go here.


## Naming conventions to be followed

### For Activities:
Kotlin files : {usage}Activity For Example : `HomeActivity` <br />
Layout Files : activity_{usage} For Example: `activity_home` <br />

### For Fragments:
Kotlin files : {usage}Fragment For Example : `HomeFragment` <br />
Layout Files : fragment_{usage} For Example: `fragment_home` <br />

### For RecyclerView Items
Layout Files: item_{usage} For Example: `item_home` <br />

### For Common Layout files
For layout files to be included in other layouts <br />
Layout Files: include_{usage} For Example: `include_home_header` <br />

### For Variables
Camel Notations will be used. <br />
For Example `isUserLoggedIn`  <br />

### For Layout IDs

**Camel Notations will be used**<br/>
**Reason**: Since we are using ViewBinding, it is easier to search for that id being used in the project.

File Names : <br />
TextViews : tv{unique identifier(filename)}{usage} For Example `tvCurrentMembers` <br />
`EditTexts : et{unique identifier(filename)}{usage} For Example `etLoginPassword` <br />
RelativeLayoout : rl{unique identifier(filename)}{usage} For Example `rlPermLevels` <br />

Note:Make sure each view has unique id in whole project. <br />


### For Constants
Upper case with underscore will be followed.
For example : `MIN_PASSWORD_LENGTH`

