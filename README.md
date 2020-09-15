# AALsystem
Simple implementation of Automated Attendance Logging System.

This Project uses Facial Recognition to automate the task of attendance Logging.

The Project is developed using following Libraries/Programming Languages/Tools:
  - Android Studio (for Android app development)
  - Python (for Server side language)
  - Java (used for Android app)
  - Flask (python web framework)
  - OpenCV (python library for Digital Image Processing)
  - SQLite (for Database management)

The Project has following three modules:
  - Admin app (Android app for Administration use only)
  - Attendance app (to be used by Professors to start attendance and by students for attendance record)
  - Server Module (runs on server for processing incoming requests)

How to use:
  - For initialisation you need run createdb.py file to create the database
  - start the Server by running routes.py file on Server system.
  - For Admin:
    - install the Admin app
    - provide name of student
    - System will take the pictures of student and will return the login id & password for student login in attendance app.
    - train the System to recognize newly enrolled by pressing "Train Model" button.
  - For Professors:
    - install Attendance App
    - login using credentials provided by admin.
    - tap on the "Start Attendance" button to start the attendance process.
    - On logging success list of students Present/ Absent will be displayed.
  - For Students:
    - install Attendance app
    - login using credentials provided by admin during registration to get attendance record.
