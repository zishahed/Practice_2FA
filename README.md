# Let's integrate the project according to your need:

1. Create a Maven based JavaFX project in IntelliJ.  
2. Replace all the files and folders of it with the ones in this repository.  
3. Go to [Firebase Console](https://console.firebase.google.com/) and create a Firebase Project for yourself.  
4. You will see a **Settings** button at the top left corner. Go to **Project Settings** and from **General** copy the **Web API Key**.  
5. Paste the key into your source code. Navigate to `FirebaseAuthClient.java` and replace the value of the `API_KEY` string with your key.  
6. Now go to your **Project Overview**, select **Authentication**, and in the **Sign-in method** tab enable **Email/Password**.  
7. Then again, go to your **Project Overview** and select **Firestore Database**.  
8. Click **Create database**, choose the appropriate security rules (start in test mode if you want to develop quickly, but secure your rules before production), and select a Cloud Firestore location near you.  
9. Download your Firebase service account JSON file from **Project Settings > Service accounts** and save it in the path `src/main/resources/serviceAccountKey.json`.  
10. Make sure your JavaFX application calls the Firebase initialization method (`FireBaseInit.FirebaseInit()`) early in the startup process to properly initialize Firebase SDK.  
11. Finally, navigate to `EmailService.java`, you will see two variables named `String from = ""` & `String password = "";`.  
12. You need to fill the `from` String with your personal Gmail (make sure 2FA is enabled for the Gmail account you are using), e.g., `String from = "zishahed25@gmail.com"`.  
13. To fill the password, go to [Google App Passwords](https://myaccount.google.com/apppasswords).  
14. Now enter an app name, e.g., "practice", and after creating it, it will give you a password. Copy it and paste it into the `password` String.  
15. You are good to go now!

# Note
  1. This project uses Firebase Authentication REST API to securely sign in users via email/password.
  2. OTPs are generated and stored in Firestore and sent via SMTP (Gmail SMTP is used by default).
  3. Make sure to update your SMTP credentials in EmailService.java to send emails correctly.
  4. For production, never store plaintext passwords in Firestore. This project stores passwords only for demonstration purposes.
  5. Use Firebase Authentication for secure password management.
  6. Customize Firebase Authentication email templates in Firebase Console to match your app branding.



















Run the application. You should be able to register new users, login, and receive OTP verification emails (if configured).

