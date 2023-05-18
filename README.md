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
<img width="369" alt="image" src="https://github.com/MuazHassan837/SplitWise/assets/113602921/b9613101-0f30-43fc-8841-d25ff62c304a">

> Login
<img width="373" alt="image" src="https://github.com/MuazHassan837/SplitWise/assets/113602921/5729f55a-e050-49b4-b871-18c601bce85a">

> Register
<img width="373" alt="image" src="https://github.com/MuazHassan837/SplitWise/assets/113602921/4ef8de5f-f4d8-4d5b-8487-4699c656c375">

> Lend & Owned Amount
<img width="374" alt="image" src="https://github.com/MuazHassan837/SplitWise/assets/113602921/6c1bab74-6016-4b4e-9419-84700cf68089">
<img width="375" alt="image" src="https://github.com/MuazHassan837/SplitWise/assets/113602921/0199806e-a3d2-4878-bf4d-c17e84d67d03">

> Groups
<img width="381" alt="image" src="https://github.com/MuazHassan837/SplitWise/assets/113602921/6fdc4b95-ed64-49d2-a7b2-bede1759f59c">

> Add Expense
<img width="379" alt="image" src="https://github.com/MuazHassan837/SplitWise/assets/113602921/34e6c6dd-ee56-4a8b-9162-22b3aa140157">

> Sigout
<img width="216" alt="image" src="https://github.com/MuazHassan837/SplitWise/assets/113602921/aec200c8-b45a-40cd-89eb-01c58d921e38">



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





