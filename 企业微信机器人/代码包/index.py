# coding:utf-8
import re
from flask import Flask, current_app, redirect, url_for,request
import WXBizMsgCrypt3 
import sql

# 创建Flask的应用程序
# __name__表示当前的模块名字
#           模块名，flask以这个模块所在的目录为总目录，默认这个目录中的static为静态目录，templates为模板目录
app = Flask(__name__)

# 通过method限定访问方式
@app.route("/", methods=["GET","POST"])
def function():
    sToken = ""
    sEncodingAESKey = ""
    sCorpID = ""
    
    #接收信息区
    msg_signature=request.args.get("msg_signature")
    timestamp=request.args.get("timestamp")
    nonce=request.args.get("nonce")
    
    #实例化一个加解码对象
    sb=WXBizMsgCrypt3.WXBizMsgCrypt(sToken,sEncodingAESKey,sCorpID)
    
    #解码区
        #验证URL有效性
    if(request.method=="GET"):
        echostr=request.args.get("echostr")
        ret,sEchoStr=sb.VerifyURL(msg_signature,timestamp,nonce,echostr)
        print("接收到的值是：",sEchoStr)
        if(ret!=0):
            print("故障码：",ret)
            return("故障码",ret)
        return (sEchoStr)
    
        #回调函数
    if(request.method=="POST"):  
        #接收数据  
        body_data=request.data
        ret,sMsg=sb.DecryptMsg(body_data, msg_signature, timestamp, nonce)
        if( ret!=0 ):
            print("故障码：",ret)
            return("故障码",ret)
        print("接收到的用户发送的全数据是：",sMsg.decode("utf-8"))
        #<Content><![CDATA[ABCDEFGHIJKLMN]]>
        rx_msg=re.findall("<Content><!\[CDATA\[(.*?)\]",sMsg.decode("utf-8"))
        # print("用户发送的数据是：",rx_msg[0])
        
        if rx_msg==[]:
    #         body_data = '''<xml>
    # <MsgType><![CDATA[text]]></MsgType>
    # <Content><![CDATA['''+"暂时解析不了别的消息哦"+''']]></Content>
    # </xml>'''
    #         ret,sMsg=sb.EncryptMsg(body_data, nonce,timestamp)
    #         if( ret!=0 ):
    #             print("故障码：",ret)
    #             return("故障码",ret)
            return "不接收用户隐私信息"
        
        
        #返回数据
            #防注入
        hack=[';', '"', ')' , '(' , "and" , "insert" , "update","updata"]
        for i in hack:
            if i in str(rx_msg[0]):
                print("注入检验命中")
                body_data = '''<xml>
    <MsgType><![CDATA[text]]></MsgType>
    <Content><![CDATA['''+"小伙子思想很危险啊"+''']]></Content>
    </xml>'''
                ret,sMsg=sb.EncryptMsg(body_data, nonce,timestamp)
                if( ret!=0 ):
                    print("故障码：",ret)
                    return("故障码",ret)
                return sMsg

        
        db_s=sql.sql_real()
        db_result=db_s.sql_com(rx_msg[0])
        body_data = '''<xml>
        <MsgType><![CDATA[text]]></MsgType>
        <Content><![CDATA['''+db_result+''']]></Content>
        </xml>'''
            
        ret,sMsg=sb.EncryptMsg(body_data, nonce,timestamp)
        if( ret!=0 ):
            print("故障码：",ret)
            return("故障码",ret)
        db_s.close()
        return sMsg
    
    

if __name__ == '__main__':
    # 通过url_map可以查看整个flask中的路由信息
    print (app.url_map)
    # 启动flask程序
    # app.run(debug=True)
    app.run(host="0.0.0.0", port=8888,debug=False)

