####################################################
# Modified by Kshitiz                              #
# Original code: http://thecodacus.com/            #
# All right reserved to the respective owner       #
####################################################

# Import OpenCV2 for image processing
import cv2
import json
import sqlite3
# Import numpy for matrices calculations
import numpy as np
def recog(facultyid):
    conn = sqlite3.connect('database.db')
    #get list of students
    sql="select id,name from students;"
    data=conn.execute(sql)
    stud_list={}
    for row in data:
        stud_list[row[0]]=[row[1],False]
    print(stud_list)
    # Create Local Binary Patterns Histograms for face recognization
    recognizer = cv2.createLBPHFaceRecognizer()

    # Load the trained mode
    recognizer.load('trainer/trainer.yml')

    # Load prebuilt model for Frontal Face
    cascadePath = "haarcascade_frontalface_default.xml"

    # Create classifier from prebuilt model
    faceCascade = cv2.CascadeClassifier(cascadePath);

    # Set the font style
    font = cv2.FONT_HERSHEY_SIMPLEX

    # Initialize and start the video frame capture
    cam = cv2.VideoCapture(0)

    # Loop
    while True:
        # Read the video frame
        ret, im =cam.read()

        # Convert the captured frame into grayscale
        gray = cv2.cvtColor(im,cv2.COLOR_BGR2GRAY)

        # Get all face from the video frame
        faces = faceCascade.detectMultiScale(gray, 1.2,5)

        # For each face in faces
        for(x,y,w,h) in faces:

            # Create rectangle around the face
            cv2.rectangle(im, (x-20,y-20), (x+w+20,y+h+20), (0,255,0), 4)

            # Recognize the face belongs to which ID
            Id = recognizer.predict(gray[y:y+h,x:x+w])
            Id=Id[0]
            name="unknown"
            # Check the ID if exist 
            if Id in stud_list:
                name=stud_list[Id][0]
                stud_list[Id][1]=True
            # Put text describe who is in the picture
            cv2.rectangle(im, (x-22,y-90), (x+w+22, y-22), (0,255,0), -1)
            cv2.putText(im, str(name), (x,y-40), font, 2, (255,255,255), 3)

        # Display the video frame with the bounded rectangle
        cv2.imshow('im',im) 

        # If 'q' is pressed, close program
        if cv2.waitKey(10) & 0xFF == ord(' '):
            break

    # Stop the camera
    cam.release()

    # Close all windows
    cv2.destroyAllWindows()
    ll=[]
    for key,val in stud_list.items():
        info={"id":key,"name":val[0],"status":val[1]}
        ll.append(info)
        if val[1]==False:
            sql="insert into attendlog(id,facultyid,attdate) values("+str(key)+","+str(facultyid)+",(select date('now')));"
            conn.execute(sql)
            conn.commit()
    sql="insert into attreq(id,datereq) values("+str(facultyid)+",(select date('now')));"
    conn.execute(sql)
    conn.commit()
    conn.close()
    return ll
