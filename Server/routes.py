from flask import Flask, render_template, jsonify,request
import json
from record import recordData
from training import traindata
from recogniz import recog
import sqlite3
app = Flask(__name__)      
 
@app.route('/')
def gg():
    return render_template('index.html')
@app.route('/record',methods=['POST','GET'])
def recordImg():
  req=request.args.get('name')
  Id=recordData(req)
  return jsonify(ID=Id['id'],passw=Id['pass'])

@app.route('/train')
def train():
  return jsonify(status=traindata())

@app.route('/recognize',methods=['POST','GET'])
def takeAtt():
  req=request.args.get('faculty')
  conn = sqlite3.connect('database.db')
  sql="select * from attreq where id="+str(req)+" and datereq=(select date('now'));"
  print(sql)
  data=conn.execute(sql)
  for row in data:
      return jsonify(status="already marked")
  conn.close()
  attendance=recog(req)
  return jsonify(status="marked",attstatus=attendance)

@app.route('/getdata',methods=['POST','GET'])
def getdata():
    req=request.args.get('id')
    print(req)
    conn = sqlite3.connect('database.db')
    sql="select f.name,attdate from attendlog s,faculty f where s.id="+str(req)+" and f.id=s.facultyid;"
    data=conn.execute(sql)
    datadict={}
    s=set()
    for row in data:
        if str(row[1]) not in datadict:
            datadict[str(row[1])]=[row[0]]
        else:
            datadict[str(row[1])].extend([row[0]])
        s.add(str(row[1]))
    ss=list(s)
    datadict["Dates"]=ss
    conn.close()
    return jsonify(absent=datadict)
@app.route('/logins',methods=['POST','GET'])
def logins():
    reqid=request.args.get('id')
    reqpass=request.args.get('pass')
    conn = sqlite3.connect('database.db')
    sql="select * from students where id="+str(reqid)+" and pass='"+str(reqpass)+"';"
    data=conn.execute(sql)
    for row in data:
        conn.close()
        return jsonify(status=True)
    return jsonify(status=False)
@app.route('/loginf',methods=['POST','GET'])
def loginf():
    reqid=request.args.get('id')
    reqpass=request.args.get('pass')
    conn = sqlite3.connect('database.db')
    sql="select * from faculty where id="+str(reqid)+" and pass='"+str(reqpass)+"';"
    data=conn.execute(sql)
    for row in data:
        conn.close()
        return jsonify(status=True)
    return jsonify(status=False)
if __name__ == '__main__':
  app.run(debug=True,host='0.0.0.0',port=4444)
