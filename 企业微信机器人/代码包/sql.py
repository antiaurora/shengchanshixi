import re
import pymysql

class sql_real:
    
    def __init__(self):
        # 打开数据库连接
        #(host="localhost", user="root", password="xxx", database="xxxx")
        self.db = pymysql.connect(host="",user="",password="",database="",autocommit=True)
        # 使用 cursor() 方法创建一个游标对象 cursor
        self.cursor = self.db.cursor()
    
    def sql_com(self,command):
        # 使用 execute()  方法执行 SQL 查询 
        # sql_com="select * from qa where `question` > '"+str(command)+"'; "
        try:
            sql_com='''SELECT * FROM qa WHERE instr(\'''' + command + '''\',question) ;'''

            # print(sql_com)
            self.cursor.execute(sql_com)
            
            # 使用 fetchone() 方法获取单条数据.
            data = self.cursor.fetchone()
            print ("回复是" ,data[2],"命中次数",str(int(data[3])+1))
            
            #数据库count++
            sql_com='''update qa set count=\''''+str(int(data[3])+1)+'''\' WHERE  `answer`=\''''+data[2]+'''\' ;'''

            # print(sql_com)
            self.cursor.execute(sql_com)
            
        except:
            return("没有查到信息哦")
        return(data[2]+"\n\n有"+str(int(data[3])+1)+"人提了同样的问题\n ٩(๑•ㅂ•)۶ ")
    
    def close(self):
        # 关闭数据库连接
        self.db.close()
        
if __name__=="__main__":
    st=sql_real()
    st.sql_com("保送生")
    st.close()