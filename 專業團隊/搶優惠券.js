//ST01--接受搶票資訊
//ST02--確認雜湊值之符合
//ST03--(主資料庫方)發行是否完畢
//ST04--(主資料庫方)依「優惠券資訊序號」查詢發行量
//ST05--(主資料庫方)查詢會員是否存在
//ST06--(redis方)查詢目前已插入量
//ST07--已插入量是否大於等於發行量
//ST08--以優惠資訊序號為key序列從後插入會員序號
//ST09--回傳是否大於發行量

//ST00--原始網頁伺服器版塊
/*var http = require('http'); 
var server = http.createServer(function (req, res) {   
    if (req.url == '/data1') { //check the URL of the current request
        json_analyze('Nosuccess',res,"用戶序號","優惠券資訊序號","優惠券序號","優惠券第幾位");
    }else if (req.url == '/data2') { //check the URL of the current request
        json_analyze('Successful',res,"用戶序號","優惠券資訊序號","優惠券序號","優惠券第幾位");
    }else if (req.url == '/data3') { //check the URL of the current request
        json_analyze('Nohave',res,"用戶序號","優惠券資訊序號","優惠券序號","優惠券第幾位");
    }
});
server.listen(5000);
console.log('Node.js web server at port 5000 is running..')*/

//ST00--預設JSON格式
/*{
    "type": "successful",
    "data": {
        "user_id": "用戶序號",
        "coupon_info_id": "優惠券資訊序號",
        "coupon_id": "優惠券序號",
        "coupon_number": "優惠券第幾位"
    }
} */
//res.write(JSON.stringify({ message: "Hello World"}));



//----------------------主程式開始--------------------
const http = require('http'); 
const url = require('url');



//JSON回傳設定
function json_analyze(type,res,user_id,coupon_info_id,coupon_id,coupon_number){
    res.writeHead(200, { 'Content-Type': 'application/json' });//回傳設為JSON物件
    var vu_data = '{"user_id": "'+user_id+'","coupon_info_id": "'+coupon_info_id+'","coupon_id": "'+coupon_id+'","coupon_number": "'+coupon_number+'"}';
    switch(type){
        case 'Nosuccess': //發生錯誤時觸發
            var vu_one = '{"type": "Nosuccess","data":'+vu_data+'}';
            res.write(JSON.stringify(JSON.parse(vu_one)));
            break;
        case 'Successful': //完成搶票時觸發
            var vu_one = '{"type": "Successful","data":'+vu_data+'}';
            res.write(JSON.stringify(JSON.parse(vu_one)));
            break;
        case 'Nohave': //已發完時時觸發
            var vu_one = '{"type": "Nohave","data":'+vu_data+'}';
            res.write(JSON.stringify(JSON.parse(vu_one)));
            break;
    }
    //client.quit();
    res.end();
}

const server = http.createServer(function (req, res) {
    if (req.url !== '/favicon.ico') {  // 因接收時會一併取得 undefined 的 facicon.ico，使用簡單的邏輯排除
        //ST00-pre 資料庫初始化
        const firebase = require('firebase/app');
        require('firebase/database');
        const redis = require('redis');//連線redis
        var client = redis.createClient(6379, '127.0.0.1')
        client.on('error', function (err) {
        console.log('Error ' + err);
        });
        const opt = { 
            databaseURL: `https://project-1000196430473354434-default-rtdb.firebaseio.com/`
        };
        if (!firebase.apps.length) {
            var app = firebase.initializeApp(opt); // firebase 初始化
        }else{
            var app = firebase.app();
        }
        //const app = firebase.initializeApp(opt); // firebase 初始化
        const database = app.database();

        //ST01--接受搶票資訊   
        var params = url.parse(req.url, true).query; //取得網址每個參數
        var user_id = params.user_id;    //一般用戶序號
        var coupon_info_id = params.coupon_info_id;    //優惠券資訊序號
        var password_hash = params.password_hash;    //雜湊值
        res.writeHead(200, {"Content-Type": "text/html; charset=utf-8"});

        //ST01-final-防止攻擊
        //user_id=user_id.replace(/[\ |\~|\`|\!|\@|\#|\$|\%|\^|\&|\*|\;|\:|\”|\’|\,|\<|\.|\>|\/|\?]/g,"");
        //coupon_info_id = coupon_info_id.replace(/[\ |\~|\`|\!|\@|\#|\$|\%|\^|\&|\*|\;|\:|\”|\’|\,|\<|\.|\>|\/|\?]/g,"");
        
        //ST02--確認雜湊值之符合(省略)
        var coupon_issued = -1; //優惠券發行量
        var coupon_finifsh = -1; //優惠券已發行完畢
        coupon_info_id = 'deafult';
        user_id="deafult";
        

        //ST03--(主資料庫方)發行是否完畢
        //ST04--(主資料庫方)依「優惠券資訊序號」查詢發行量
        database.ref('/coupon_info/'+coupon_info_id).once('value', e => {
            /*console.log(e.val()); //{ num: '9319', title: 'The Turing Machine' }
            res.write(JSON.stringify(e.val()), "utf-8");
            res.end();*/
            coupon_finifsh = e.val().issue_finish;
            coupon_issued = e.val().num;
        }).then(() => {
            //firebase.app().delete(); // 讀取完成後刪除 firebase 宣告
            console.log(coupon_finifsh);
            //判斷是否發行完畢 與 優惠券發行量
            if(coupon_finifsh==-1){ //--發生錯誤
                json_analyze('Nosuccess',res,user_id,coupon_info_id,"-1","-1");
            }else if(coupon_finifsh==1){ //--發生完畢
                json_analyze('Nohave',res,user_id,coupon_info_id,"-1","-1");
            }else if(coupon_finifsh==0){ //--續行判斷發行量 
                //redis部分
                client.llen(coupon_info_id,function(err, coupon_expost) { //value為已出量
                    if (err){
                        //console.log("優惠券已出量-->發生錯誤");
                        //client.quit();
                        json_analyze('Nosuccess',res,user_id,coupon_info_id,"-1","-1");//錯誤
                        throw err;
                    } 
                    console.log("優惠券已出量-->"+coupon_expost);
                    if(coupon_expost>coupon_issued || coupon_expost==coupon_issued){
                        //console.log("優惠券已達到巔峰或超越!!");
                        //client.quit();
                        json_analyze('Nohave',res,user_id,coupon_info_id,"-1","-1");//未得
                    }else{//優惠券未超越顛峰，先行查詢是否重複，再插入一般會員序號
                        client.lrange(coupon_info_id,0,-1,function(err_lrange,coupon_lrange_value){
                            if(err_lrange){
                                console.log("優惠券總清單-->發生錯誤");
                                //client.quit();
                                json_analyze('Nosuccess',res,user_id,coupon_info_id,"-1","-1");//錯誤
                                throw err;
                            }
                            console.log("優惠券總清單-->"+coupon_lrange_value);
                            //判斷是否重複
                            if(coupon_lrange_value.includes(user_id)){
                                console.log("一般會員序號重複-->"+user_id);
                                //client.quit();
                                json_analyze('Nohave',res,user_id,coupon_info_id,"-1","-1");//未得
                            }else{
                                client.rpush(coupon_info_id,user_id,function(err_serial,coupon_serial){
                                    if(err_serial){
                                        console.log("優惠券序號-->發生錯誤");
                                        client.quit();
                                        json_analyze('Nosuccess',res,user_id,coupon_info_id,"-1","-1");//錯誤
                                        throw err;
                                    }
                                    console.log("優惠券序號-->"+coupon_serial);
                                    if(coupon_serial>coupon_issued){
                                        console.log("優惠券序號-->大於發行量");
                                        json_analyze('Nohave',res,user_id,coupon_info_id,"優惠券序號-->大於發行量",oupon_serial);//未得
                                    }else{
                                        console.log("優惠券序號-->得到");
                                        //插入firebase資料庫
                                        /*coupon_info_id: "deafult",
                                            gettime: "2004-04-25 02:03:04",
                                            user_id: "deafult" */
                                        //優先建立物件(JSON轉物件)
                                        var Today=new Date();
                                        var json_create_coupon = JSON.parse('{"coupon_info_id":"'+coupon_info_id+'","gettime": "'+Today.getFullYear()+'-'+ (Today.getMonth()+1) +'-'+ Today.getDate() +' '+Today.getHours()+':'+Today.getMinutes()+':'+Today.getSeconds()　+'","user_id": "'+user_id+'"}');
                                        var cre_coupon_firebase = database.ref("/coupon").push().set(json_create_coupon).then(() => {
                                            //res.end(`push data to ok`);
                                            //firebase.app().delete(); // 寫入完成後刪除 firebase 宣告
                                            json_analyze('Successful',res,user_id,coupon_info_id,Promise.resolve(cre_coupon_firebase),coupon_serial);
                                        });
                                        
                                    }
                                    //client.quit();
                                });
                                //client.quit();
                            }
                        });

                        
                    }
                });                
            }
        });
        //res.end();
    }

});
 
server.listen(5000);
console.log('Node.js web server at port 5000 is running..')