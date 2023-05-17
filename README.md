# Mobile App Development - Assignment 1.

__Name:__ Syed Muaz Hassan

## Overview.
+ Login.
+ Register.
+ Lend Amount.
+ Owned Amount.
+ Groups.
+ Add Expense.
+ Profile.
+ Sigout.

## Feature Design.
<img width="369" alt="image" src="https://github.com/MuazHassan837/portfolio/assets/113602921/cebe4255-20bf-47a0-92ab-84c93cb2bb6c">

> Login
<img width="373" alt="image" src="https://github.com/MuazHassan837/portfolio/assets/113602921/88565faa-daf9-4732-9c74-b449b33c2ec4">

> Register
<img width="373" alt="image" src="https://github.com/MuazHassan837/portfolio/assets/113602921/327137f4-509d-4049-8c1a-617601c76bf3">

> Lend & Owned Amount
<img width="374" alt="image" src="https://github.com/MuazHassan837/portfolio/assets/113602921/dc63fe3c-b7e9-43d4-90f4-6af6a7b05235">
<img width="375" alt="image" src="https://github.com/MuazHassan837/portfolio/assets/113602921/0278f637-f569-4291-908a-396f0f9ecfa7">

> Groups
<img width="381" alt="image" src="https://github.com/MuazHassan837/portfolio/assets/113602921/5d8d7e37-3bfc-4b61-99e6-9a5d0b260ace">

> Add Expense
<img width="379" alt="image" src="https://github.com/MuazHassan837/portfolio/assets/113602921/5df13a55-8bf9-42ee-8506-5425144095ac">

> Sigout
<img width="216" alt="image" src="https://github.com/MuazHassan837/portfolio/assets/113602921/28e183b0-53a1-41e4-80cd-ac5b98b08a97">



## Additional Information.

The app uses Realm to store local databases and Firebase for authentication and cloud database features. Splitwise enables users to control and monitor group spending, promoting transparency and simplifying expense management.

Splitwise uses Firebase Authentication to offer safe and dependable user authentication. Through the use of Firebase Authentication, users may safely register, and log in.

Splitwise uses Firebase Firestore Database as its cloud database. It offers a NoSQL JSON database that permits multi-device real-time synchronization. Group-related spending information, including specifics such as expense amounts, descriptions, dates, and the people involved, is stored in the Firestore Database.

Splitwise uses Realm to store local databases. Realm offers local storage as a complement to Firebase Firestore Database's cloud storage for enhanced offline functionality. Users can obtain spending data even when there is no active internet connection thanks to the local database. Data persistence on the smartphone is made possible by Realm's effective and simple API, guaranteeing a seamless user experience.

The amount, description, group, strategy for amount division and individuals involved in the expense can all be entered/computed by users when creating new expenses. Both the local Realm database and the Firebase Firestore Database in the cloud house this data.

Splitwise keeps track of every expense made by the group and displays the specifics in an extensive expense history. Users have access to both their own and other group members' costs.

Splitwise facilitates expense reimbursements by calculating and displaying the balances among group members. 
Employing industry-standard security methods like encryption and secure token-based authentication, Firebase Authentication enables secure user authentication.
To safeguard private information kept in the local database, Realm comes with built-in encryption mechanisms. 

The expenditure management tool Splitwise uses Realm for local storage and Firebase for cloud database capabilities. An effective and secure platform is provided by the software for monitoring group spending, maintaining transparency, and expediting expense refunds. Splitwise offers a smooth user experience with real-time synchronization, offline capability, and robust security measures by merging the capabilities of Firebase and Realm.





