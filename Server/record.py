import cv2
import sqlite3
from test import getrandpass
from flask import jsonify
def recordData(id):
    conn = sqlite3.connect('database.db')
    passw = getrandpass()
    sql="insert into students(name,pass) values('"+id+"','"+passw+"')"
    print(sql)
    conn.execute(sql)
    conn.commit()
    sql="""select max(id) from students"""
    print(sql)
    data = conn.execute(sql)
    for row in data:
        data = str(row[0])
    cam = cv2.VideoCapture(0)

    #Create a window to display image
    WindowName="Main View"
    view_window = cv2.namedWindow(WindowName,cv2.WINDOW_NORMAL)

    detector=cv2.CascadeClassifier('haarcascade_frontalface_default.xml')

    Id=int(data)
    sampleNum=0
    while(True):
        ret, img = cam.read()
        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        faces = detector.detectMultiScale(gray, 1.3, 5)
        for (x,y,w,h) in faces:
            cv2.rectangle(img,(x,y),(x+w,y+h),(255,0,0),2)
            
            #incrementing sample number 
            sampleNum=sampleNum+1
            #saving the captured face in the dataset folder
            cv2.imwrite("dataSet/User."+str(Id) +'.'+ str(sampleNum) + ".jpg", gray[y:y+h,x:x+w])

            cv2.imshow(view_window,img)
        #wait for 100 miliseconds 
        if cv2.waitKey(100) & 0xFF == ord('q'):
            break
        # break if the sample number is morethan 20
        elif sampleNum>50:
            break
    cam.release()
    cv2.destroyAllWindows()
    
    conn.close()
    return {'id':data,'pass':passw}