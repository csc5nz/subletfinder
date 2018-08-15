### SubletFinder

SubletFinder is an Android app which allows users to browse sublets that other students have posted (into a Firebase Database) or post their own sublets. In addition, it  can send emails to potential sublet owners, and it shows an objectively bare view of the buildings that are classified as "housing" at UVa for the user to look at.   

The app is able to do the following:  
- Allows students to create a profile using their email account
- Allows students to login with their email/password
- Dynamically reads from the UVa buildings API and displays the buildings that are classified as "housing"
- Allows user to send emails to sublet owner
- Allows users to view a list of sublets available to them via a Firebase Database
- Allows users to list their sublet, if applicable
- Allows users to pick a image off of their library and associate it with their sublet

The app contains the following features:
- Build and consume your own web service using a third-party platform -  Firebase was used as a database back-end for the system.
- Using the camera to take pictures or choose a picture from the library to associate with a sublet
- Consume a pre-built web service - The app parses the data provided by the UVa buildings API
- Open shared activity / features - The app is able to open a email client to allow student to email sublet owner
- Use share preferences to save and load user settings
