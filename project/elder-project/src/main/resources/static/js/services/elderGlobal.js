angular.module('elderGlobal',[])
    .constant("Global",
        {
            SUCCESS : '0x00001',
            FAILURE : '0x00002',
            ONLINE : '0x00003',
            OFFLINE : '0x00004',
            LOGIN_OUT: '0x00005',
            TOKEN_ERROR: '0x00006',
            PARAM_ERROR: '0x00007',
            LOGIN_SUCCESS_SECOND : '0x00008',
        })
    .factory('openidUtil',['Global','$rootScope','$location',
        function(Global,$rootScope,$location){
            return{
                checkResponseData:function(){
                    var absUrl = $location.absUrl().replace('#','@');
                    if(window.localStorage.getItem('openid')){
                        $rootScope.openid = window.localStorage.getItem('openid');
                    }
                    else
                    {
                        if($location.search().openid)
                        {
                            window.localStorage.setItem('openid',$location.search().openid);
                            $rootScope.openid = window.localStorage.getItem('openid');
                        }
                        else
                        {
                            window.location.href = "http://wechat.hlsenior.com/wechat/getOpenID?url=" + absUrl;
                        }
                    }
                }
            }
    }])
    .factory('ElderUtil', ['Global','$ionicPopup',
        function(Global,$ionicPopup) {
            return {
                checkResponseData: function(data,redirectParam) {
                    // if(data.result==Global.FAILURE)
                    // {
                    //     if(data.errorInfo==Global.TOKEN_ERROR){
                    //         如果登录token发生了问题，跳转到登录页面，且在登录成功后，跳转到之前页面去
                    //         window.location.href = "login?redirectParam="+redirectParam;
                    //     }
                    // }
                },
                getAddDate:function(date,days){
                    var d=new Date(date);
                    d.setDate(d.getDate()+days);
                    var month=d.getMonth()+1;
                    var day = d.getDate();
                    if(month<10){
                        month = "0"+month;
                    }
                    if(day<10){
                        day = "0"+day;
                    }
                    var val = d.getFullYear()+"-"+month+"-"+day;
                    return val;
                },
                identityCount:function(UUserCard,num) {
                    if(num==1){
                        //获取出生日期
                        var birthday = UUserCard.substring(6, 10) + "-" + UUserCard.substring(10, 12) + "-" + UUserCard.substring(12, 14);
                        return birthday;
                    }
                    if(num==2){
                        //获取性别
                        if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) {
                            //男
                            return "男";
                        } else {
                            //女
                            return "女";
                        }
                    }
                    if(num==3){
                        //获取年龄
                        var myDate = new Date();
                        var month = myDate.getMonth() + 1;
                        var day = myDate.getDate();
                        var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1;
                        if (UUserCard.substring(10, 12) < month || UUserCard.substring(10, 12) == month && UUserCard.substring(12, 14) <= day) {
                            age++;
                        }
                        return age;
                    }
                },
                IdentityCodeValid:function(code) {
                    var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
                    var tip = "";
                    var pass= true;

                    if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
                        tip = "身份证号格式错误";
                        pass = false;
                    }

                    else if(!city[code.substr(0,2)]){
                        tip = "地址编码错误";
                        pass = false;
                    }
                    else{
                        //18位身份证需要验证最后一位校验位
                        if(code.length == 18){
                            code = code.split('');
                            //∑(ai×Wi)(mod 11)
                            //加权因子
                            var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
                            //校验位
                            var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
                            var sum = 0;
                            var ai = 0;
                            var wi = 0;
                            for (var i = 0; i < 17; i++)
                            {
                                ai = code[i];
                                wi = factor[i];
                                sum += ai * wi;
                            }
                            var last = parity[sum % 11];
                            if(parity[sum % 11] != code[17]){
                                tip = "校验位错误";
                                pass =false;
                            }
                        }
                    }
                    if(!pass) alert(tip);
                    return pass;
                },
                CheckSurveyAnswerData:function(question,answer){

                    function checkIfAnswerTheQuestion(question,answer)
                    {
                        if(question.questionType=='multi'||question.questionType=='select')
                        {
                            var answerValue = answer.question_answer.split(";");
                            if(answerValue[0]=='undefined'||answerValue[0]=='')
                            {
                                return false;
                            }
                            else
                            {
                                return true;
                            }
                        }
                        else if(question.questionType=='input'||question.questionType=='single')
                        {
                            if(answer.question_answer==''||answer.question_answer==undefined)
                            {
                                return false;
                            }
                            else
                            {
                                return true;
                            }
                        }
                    }

                    var result = checkIfAnswerTheQuestion(question,answer);

                    if(question.mustAnswer)
                    {
                        if(!result)
                        {
                            alert("此题必答，请答题后进入下一题");
                            return false;
                        }

                    }
                    else
                    {
                        if(!result)
                        {
                            var a = confirm("此题未答，是否直接进入下题？");
                            return a;
                        }
                    }


                    if(question.questionStatus=='elderIdentityNum')
                    {
                        //进行身份证格式校验
                        if(!this.IdentityCodeValid(answer.question_answer))
                        {
                            return false;
                        }
                        else
                        {
                            return true;
                        }
                    }
                    if(question.questionStatus=='name')
                    {
                        if(answer.question_answer.length>=6)
                        {
                            alert("名字过长，请检查是否有错");
                            return false;
                        }
                        else
                        {
                            return true;
                        }
                    }
                    if(question.questionStatus=='phone')
                    {
                        var mobile = /^1[3|5|8]\d{9}$/;
                        var phone = /^0\d{2,3}-?\d{7,8}$/;
                        if(answer.question_answer==""||mobile.test(answer.question_answer)||phone.test(answer.question_answer))
                        {
                            return true;
                        }
                        else
                        {
                            alert("输入的电话号码格式不准确，请重新输入");
                            return false;
                        }
                    }
                    else
                    {
                        return true;
                    }

                }
            };
        }])
