angular.module('controllers',['ui.calendar','ui.bootstrap']).controller('detectionDiagnoseCtrl',
    ['$scope','$rootScope','$stateParams','$state','$filter','openidUtil','GetLaoyouUserByOpenId',
        'GetDetectionHealthData','ElderUtil','$timeout','Global','$window','GetUserGroupChatInfo',
        function ($scope,$rootScope,$stateParams,$state,$filter,openidUtil,GetLaoyouUserByOpenId,
                  GetDetectionHealthData,ElderUtil,$timeout,Global,$window,GetUserGroupChatInfo) {

            $rootScope.pageTitle = '健康数据';
            $scope.loadingStatus = true;

            // $rootScope.openid = 'oRnVIxOypU0LiuavDpTl_xe10i7Y';
            openidUtil.checkResponseData();

            $scope.goHealthChat = function(){
                $state.go('myChat',{'groupType':'healthData','id':'1'})
            }
            $scope.goMenu = function(firstMenuParam,secondMenuParam){
                $state.go("detectionDiagnose", {firstMenu: firstMenuParam, secondMenu: secondMenuParam});
            }
            $scope.goBloodSugarRecord = function(bloodSugarNum,timeType,timeDate,readOnly,remark){
                $state.go("bloodSugarRecord",{bloodSugarNum:bloodSugarNum,recorded:true,timeType:timeType,timeDate:timeDate,readOnly:readOnly,remark:remark});
                $rootScope.measureBloodSugarRemark = remark;
            }
            $scope.goBloodPressureRecord = function(measureTime,diastolic,systolic,heartRate,readOnly,remark){
                $state.go("bloodPressureRecord",{emptyCont:true,measureTime:measureTime,diastolic:diastolic,systolic:systolic,heartRate:heartRate,readOnly:readOnly,remark:remark});
                $rootScope.measureBloodPressureRemark = remark;
            }

            $scope.firstMenu = $stateParams.firstMenu;
            $scope.secondMenu = $stateParams.secondMenu;

            var initHealthData = function(num){
                var date = new Date();
                if($scope.secondMenu=="bloodSugarCurve"||$scope.secondMenu=="bloodSugarTable") {
                    for(var i=0; i<num; i++)
                    {
                        var bloodSugar = {};
                        var value = {
                            date : ElderUtil.getAddDate(date.getTime(),0-i),
                            bloodSugar : bloodSugar
                        }
                        $scope.healthDatas.push(value);
                    }
                }
                else if($scope.secondMenu=="bloodPressureCurve"||$scope.secondMenu=="bloodPressureTable")
                {
                    for(var i=0; i<num; i++)
                    {
                        var bloodPressure = {};
                        var value = {
                            date : ElderUtil.getAddDate(date.getTime(),0-i),
                            bloodPressure : bloodPressure
                        }
                        $scope.healthDatas.push(value);
                    }
                }
            };

            var arrangeHealthData = function(healthData, dataResponse){
                if($scope.secondMenu=="bloodSugarCurve"||$scope.secondMenu=="bloodSugarTable"){
                    if(dataResponse.mealType=='dawn')
                    {
                        healthData.bloodSugar.dawn = dataResponse.result;
                        healthData.bloodSugar.dawnTime = dataResponse.measureTime;
                    }
                    else if(dataResponse.mealType=='beforeBreakFast')
                    {
                        healthData.bloodSugar.beforeBreakFast = dataResponse.result;
                        healthData.bloodSugar.beforeBreakFastTime = dataResponse.measureTime;
                    }
                    else if(dataResponse.mealType=='afterBreakFast')
                    {
                        healthData.bloodSugar.afterBreakFast = dataResponse.result;
                        healthData.bloodSugar.afterBreakFastTime = dataResponse.measureTime;
                    }
                    else if(dataResponse.mealType=='beforeLunch')
                    {
                        healthData.bloodSugar.beforeLunch = dataResponse.result;
                        healthData.bloodSugar.beforeLunchTime = dataResponse.measureTime;
                    }
                    else if(dataResponse.mealType=='afterLunch')
                    {
                        healthData.bloodSugar.afterLunch = dataResponse.result;
                        healthData.bloodSugar.afterLunchTime = dataResponse.measureTime;
                    }
                    else if(dataResponse.mealType=='beforeDinner')
                    {
                        healthData.bloodSugar.beforeDinner = dataResponse.result;
                        healthData.bloodSugar.beforeDinnerTime = dataResponse.measureTime;
                    }
                    else if(dataResponse.mealType=='afterDinner')
                    {
                        healthData.bloodSugar.afterDinner = dataResponse.result;
                        healthData.bloodSugar.afterDinnerTime = dataResponse.measureTime;
                    }
                    else if(dataResponse.mealType=='beforeSleep')
                    {
                        healthData.bloodSugar.beforeSleep = dataResponse.result;
                        healthData.bloodSugar.beforeSleepTime = dataResponse.measureTime;
                    }
                }
            }

            var loadDetectionDiagnose = function(){
                if($scope.firstMenu=="detection")
                {
                    $scope.chooseHealthDataTime('week');
                }
            }

            $scope.chooseHealthDataTime = function(time){
                $scope.dataLoad = false;
                $scope.healthDatas = [];
                $scope.charts = [];

                if(time =='week'){
                    $scope.timeType = 'week';
                    initHealthData(7);
                }
                else if(time == 'month'){
                    $scope.timeType = 'month';
                    initHealthData(30);
                }
                else if(time == 'threeMonth'){
                    $scope.timeType = 'threeMonth';
                    initHealthData(90);
                }
                else if(time == 'halfYear'){
                    $scope.timeType = 'halfYear';
                    initHealthData(180);
                }

                if($scope.secondMenu=="bloodSugarCurve"||$scope.secondMenu=="bloodSugarTable")
                {
                    $scope.charts.bloodSugar = {};
                    $scope.charts.categories = [];
                    $scope.charts.bloodSugar.title = {text: ''};

                    $scope.loadingStatus = true;

                    GetDetectionHealthData.get({detectionType:'bg',
                        detectionDateType:$scope.timeType,elderId:$scope.elderId},function(data){

                        $scope.loadingStatus = false;

                        ElderUtil.checkResponseData(data);

                        if(data.responseData!=undefined){

                            angular.forEach($scope.healthDatas, function(healthData,index,array){
                                angular.forEach(data.responseData.detectionData, function(dataResponse,index,array){
                                    if(dataResponse.measureTime.substring(0,10)==healthData.date)
                                    {
                                        arrangeHealthData(healthData, dataResponse);
                                    }
                                });
                            });

                            if($scope.secondMenu=="bloodSugarCurve")
                            {
                                $scope.charts.bloodSugar.dawnSeries = [{type: 'area', name: '凌晨血糖', data: []}];
                                $scope.charts.bloodSugar.beforeBreakFastSeries = [{type: 'area', name: '空腹血糖', data: []}];
                                $scope.charts.bloodSugar.afterBreakFastSeries = [{type: 'area', name: '早餐后血糖', data: []}];
                                $scope.charts.bloodSugar.beforeLunchSeries = [{type: 'area', name: '午餐前血糖', data: []}];
                                $scope.charts.bloodSugar.afterLunchSeries = [{type: 'area', name: '午餐后血糖', data: []}];
                                $scope.charts.bloodSugar.beforeDinnerSeries = [{type: 'area', name: '晚餐前血糖', data: []}];
                                $scope.charts.bloodSugar.afterDinnerSeries = [{type: 'area', name: '晚餐后血糖', data: []}];
                                $scope.charts.bloodSugar.beforeSleepSeries = [{type: 'area', name: '睡前血糖', data: []}];

                                angular.forEach($scope.healthDatas,function(data,index,array) {
                                    if (data.bloodSugar.dawn != undefined) {
                                        $scope.charts.bloodSugar.dawnSeries[0].data.push([
                                            new Date($filter('date')((new Date(data.date)).getTime(), 'yyyy-MM-dd')).getTime(),
                                            parseFloat(data.bloodSugar.dawn)]);
                                    }
                                    else {
                                        $scope.charts.bloodSugar.dawnSeries[0].data.push([
                                            new Date($filter('date')((new Date(data.date)).getTime(), 'yyyy-MM-dd')).getTime(),
                                            null]);
                                    }
                                    if(data.bloodSugar.beforeBreakFast!=undefined)
                                    {
                                        $scope.charts.bloodSugar.beforeBreakFastSeries[0].data.push([
                                            new Date($filter('date')((new Date(data.date)).getTime(), 'yyyy-MM-dd')).getTime(),
                                            parseFloat(data.bloodSugar.beforeBreakFast)]);
                                    }
                                    else
                                    {
                                        $scope.charts.bloodSugar.beforeBreakFastSeries[0].data.push([
                                            new Date($filter('date')((new Date(data.date)).getTime(), 'yyyy-MM-dd')).getTime(),
                                            null]);
                                    }
                                    if(data.bloodSugar.afterBreakFast!=undefined)
                                    {
                                        $scope.charts.bloodSugar.afterBreakFastSeries[0].data.push([
                                            new Date($filter('date')((new Date(data.date)).getTime(), 'yyyy-MM-dd')).getTime(),
                                            parseFloat(data.bloodSugar.afterBreakFast)]);
                                    }
                                    else
                                    {
                                        $scope.charts.bloodSugar.afterBreakFastSeries[0].data.push([
                                            new Date($filter('date')((new Date(data.date)).getTime(), 'yyyy-MM-dd')).getTime(),
                                            null]);
                                    }
                                    if(data.bloodSugar.beforeLunch!=undefined)
                                    {
                                        $scope.charts.bloodSugar.beforeLunchSeries[0].data.push([
                                            new Date($filter('date')((new Date(data.date)).getTime(), 'yyyy-MM-dd')).getTime(),
                                            parseFloat(data.bloodSugar.beforeLunch)]);
                                    }
                                    else
                                    {
                                        $scope.charts.bloodSugar.beforeLunchSeries[0].data.push([
                                            new Date($filter('date')((new Date(data.date)).getTime(), 'yyyy-MM-dd')).getTime(),
                                            null]);
                                    }
                                    if(data.bloodSugar.afterLunch!=undefined)
                                    {
                                        $scope.charts.bloodSugar.afterLunchSeries[0].data.push([
                                            new Date($filter('date')((new Date(data.date)).getTime(), 'yyyy-MM-dd')).getTime(),
                                            parseFloat(data.bloodSugar.afterLunch)]);
                                    }
                                    else
                                    {
                                        $scope.charts.bloodSugar.afterLunchSeries[0].data.push([
                                            new Date($filter('date')((new Date(data.date)).getTime(), 'yyyy-MM-dd')).getTime(),
                                            null]);
                                    }
                                    if(data.bloodSugar.beforeDinner!=undefined)
                                    {
                                        $scope.charts.bloodSugar.beforeDinnerSeries[0].data.push([
                                            new Date($filter('date')((new Date(data.date)).getTime(), 'yyyy-MM-dd')).getTime(),
                                            parseFloat(data.bloodSugar.beforeDinner)]);
                                    }
                                    else
                                    {
                                        $scope.charts.bloodSugar.beforeDinnerSeries[0].data.push([
                                            new Date($filter('date')((new Date(data.date)).getTime(), 'yyyy-MM-dd')).getTime(),
                                            null]);
                                    }
                                    if(data.bloodSugar.afterDinner!=undefined)
                                    {
                                        $scope.charts.bloodSugar.afterDinnerSeries[0].data.push([
                                            new Date($filter('date')((new Date(data.date)).getTime(), 'yyyy-MM-dd')).getTime(),
                                            parseFloat(data.bloodSugar.afterDinner)]);
                                    }
                                    else
                                    {
                                        $scope.charts.bloodSugar.afterDinnerSeries[0].data.push([
                                            new Date($filter('date')((new Date(data.date)).getTime(), 'yyyy-MM-dd')).getTime(),
                                            null]);
                                    }
                                    if(data.bloodSugar.beforeSleep!=undefined)
                                    {
                                        $scope.charts.bloodSugar.beforeSleepSeries[0].data.push([
                                            new Date($filter('date')((new Date(data.date)).getTime(), 'yyyy-MM-dd')).getTime(),
                                            parseFloat(data.bloodSugar.beforeSleep)]);
                                    }
                                    else
                                    {
                                        $scope.charts.bloodSugar.beforeSleepSeries[0].data.push([
                                            new Date($filter('date')((new Date(data.date)).getTime(), 'yyyy-MM-dd')).getTime(),
                                            null]);
                                    }
                                })

                                $scope.charts.bloodSugar.options =  {
                                    chart: {
                                        zoomType: 'x',
                                        backgroundColor:'#f8f8f8'
                                    },
                                    xAxis: {
                                        type: 'datetime',
                                        dateTimeLabelFormats: {
                                            millisecond: '%H:%M:%S.%L',
                                            second: '%H:%M:%S',
                                            minute: '%H:%M',
                                            hour: '%H:%M',
                                            day: '%m-%d',
                                            week: '%m-%d',
                                            month: '%Y-%m',
                                            year: '%Y'
                                        },
                                        gridLineColor:'#262626',
                                        lineColor:'#262626'
                                    },
                                    tooltip: {
                                        dateTimeLabelFormats: {
                                            millisecond: '%H:%M:%S.%L',
                                            second: '%H:%M:%S',
                                            minute: '%H:%M',
                                            hour: '%H:%M',
                                            day: '%Y-%m-%d',
                                            week: '%m-%d',
                                            month: '%Y-%m',
                                            year: '%Y'
                                        }
                                    },
                                    yAxis: {
                                        title: {
                                            text: ''
                                        },
                                        gridLineColor:'#262626',
                                        lineColor:'#262626'
                                    },
                                    legend:{
                                        enabled: false
                                    },
                                    plotOptions: {
                                        area: {
                                            fillColor: {
                                                linearGradient: {
                                                    x1: 0,
                                                    y1: 0,
                                                    x2: 0,
                                                    y2: 1
                                                },
                                                stops: [
                                                    [0, Highcharts.getOptions().colors[0]],
                                                    [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                                                ]
                                            },
                                            marker: {
                                                radius: 2
                                            },
                                            lineWidth: 1,
                                            states: {
                                                hover: {
                                                    lineWidth: 1
                                                }
                                            },
                                            threshold: null
                                        }
                                    },
                                    credits: {		//去除右下角highcharts标志
                                        enabled: false
                                    },
                                    exporting: {	//去除右上角导出按钮
                                        enabled: false
                                    }
                                };
                            }

                            $scope.dataLoad = true;

                        }
                    })
                }
                else if($scope.secondMenu=="bloodPressureCurve"||$scope.secondMenu=="bloodPressureTable")
                {
                    $scope.charts.bloodPressure = {};
                    $scope.charts.bloodPressure.title = {text: ''};

                    $scope.loadingStatus = true;
                    GetDetectionHealthData.get({detectionType:'bp',
                        detectionDateType:$scope.timeType,elderId:$scope.elderId},function(data){
                        $scope.loadingStatus = false;

                        ElderUtil.checkResponseData(data);
                        if(data.responseData!=undefined) {

                            if($scope.secondMenu=="bloodPressureCurve")
                            {
                                $scope.charts.bloodPressure.pressureSeries = [{type: 'area', name: '舒张压', data: []},{type: 'area', name: '收缩压', data: []}];
                                $scope.charts.bloodPressure.heartRateSeries = [{type: 'area', name: '心率', data: []}];

                                angular.forEach(data.responseData.detectionData,function(value,index,array){
                                    // alert(value.measureTime.substring(0,16));
                                    // alert(new Date(value.measureTime.substring(0,16)).getTime());
                                    $scope.charts.bloodPressure.pressureSeries[0].data.push([
                                        value.measureTime,
                                        parseFloat(value.diastolic)]);
                                    $scope.charts.bloodPressure.pressureSeries[1].data.push([
                                        value.measureTime,
                                        parseFloat(value.systolic)]);
                                    $scope.charts.bloodPressure.heartRateSeries[0].data.push([
                                        value.measureTime,
                                        parseFloat(value.heartRate)]);
                                })
                            }
                            else if($scope.secondMenu=="bloodPressureTable")
                            {
                                $scope.healthDatas = data.responseData.detectionData;
                            }
                        }

                        $scope.charts.bloodPressure.options = {
                            chart: {
                                zoomType: 'x',
                                backgroundColor:'#f8f8f8'
                            },
                            xAxis: {
                                type: 'datetime',
                                dateTimeLabelFormats: {
                                    millisecond: '%H:%M:%S.%L',
                                    second: '%H:%M:%S',
                                    minute: '%H:%M',
                                    hour: '%H:%M',
                                    day: '%m-%d',
                                    week: '%m-%d',
                                    month: '%Y-%m',
                                    year: '%Y'
                                },
                                gridLineColor:'#262626',
                                lineColor:'#262626'
                            },
                            tooltip: {
                                dateTimeLabelFormats: {
                                    millisecond: '%H:%M:%S.%L',
                                    second: '%H:%M:%S',
                                    minute: '%H:%M',
                                    hour: '%H:%M',
                                    day: '%Y-%m-%d',
                                    week: '%m-%d',
                                    month: '%Y-%m',
                                    year: '%Y'
                                }
                            },
                            yAxis: {
                                title: {
                                    text: ''
                                },
                                gridLineColor:'#262626',
                                lineColor:'#262626'
                            },
                            plotOptions: {
                                area: {
                                    fillColor: {
                                        linearGradient: {
                                            x1: 0,
                                            y1: 0,
                                            x2: 0,
                                            y2: 1
                                        },
                                        stops: [
                                            [0, Highcharts.getOptions().colors[0]],
                                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                                        ]
                                    },
                                    marker: {
                                        radius: 2
                                    },
                                    lineWidth: 1,
                                    states: {
                                        hover: {
                                            lineWidth: 1
                                        }
                                    },
                                    threshold: null
                                }
                            },
                            credits: {		//去除右下角highcharts标志
                                enabled: false
                            },
                            exporting: {	//去除右上角导出按钮
                                enabled: false
                            }
                        };

                        $scope.dataLoad = true;
                    })

                }
            };

            //判断是否绑定
            GetLaoyouUserByOpenId.get({openid:$rootScope.openid},function(data){
                if(data.result == Global.SUCCESS){
                    if(data.responseData != null){
                        //已绑定
                        $scope.hasData = true;
                        $scope.elderId = data.responseData.elderUserDTO.id;
                        $scope.easemobId = data.responseData.elderUserDTO.easemobID;
                        $scope.easemobPassword = data.responseData.elderUserDTO.easemobPassword;
                        loadDetectionDiagnose();

                        //获取群聊信息
                        GetUserGroupChatInfo.get({elderId:$scope.elderId,easemobId:$scope.easemobId},function(data){
                            if(data.result == Global.SUCCESS){
                                if(data.responseData.easemobGroup == null){
                                    $scope.noGroup = true;
                                }else{
                                    $scope.noGroup = false;
                                    $scope.groupId = data.responseData.easemobGroup.easemobGroupID;

                                    var conn = new WebIM.connection({
                                        isMultiLoginSessions: WebIM.config.isMultiLoginSessions,
                                        https: typeof WebIM.config.https === 'boolean' ? WebIM.config.https : location.protocol === 'https:',
                                        url: WebIM.config.xmppURL,
                                        heartBeatWait: WebIM.config.heartBeatWait,
                                        autoReconnectNumMax: WebIM.config.autoReconnectNumMax,
                                        autoReconnectInterval: WebIM.config.autoReconnectInterval,
                                        apiUrl: WebIM.config.apiURL,
                                        isAutoLogin: true
                                    });

                                    var options = {
                                        apiUrl: WebIM.config.apiURL,
                                        user: $scope.easemobId,
                                        pwd: $scope.easemobPassword,
                                        appKey: WebIM.config.appkey,
                                    };
                                    conn.open(options);

                                    conn.listen({
                                        onOpened: function ( message ) {
                                            //连接成功回调
                                            // 如果isAutoLogin设置为false，那么必须手动设置上线，否则无法收消息
                                            // 手动上线指的是调用conn.setPresence(); 如果conn初始化时已将isAutoLogin设置为true
                                            // 则无需调用conn.setPresence();
                                            console.log("connection success");

                                            options = {
                                                success: function (resp) {

                                                    angular.forEach(resp.data,function(value,index,array){
                                                        if(value.groupname.indexOf('医护')!=-1)
                                                        {
                                                            $scope.groupId = value.groupid;
                                                            $scope.groupName = value.groupname;
                                                            // $scope.easemobId = $scope.relativeElderList[0].easemobID;
                                                            // $scope.easemobPassword = $scope.relativeElderList[0].easemobPassword;
                                                        }
                                                    })
                                                },
                                                error: function (e) {
                                                }
                                            }
                                            conn.getGroup(options);
                                        },
                                        onClosed: function ( message ) {},         //连接关闭回调
                                        onTextMessage: function ( message ) {},    //收到文本消息
                                        onEmojiMessage: function ( message ) {},   //收到表情消息
                                        onPictureMessage: function ( message ) {}, //收到图片消息
                                        onCmdMessage: function ( message ) {},     //收到命令消息
                                        onAudioMessage: function ( message ) {},   //收到音频消息
                                        onLocationMessage: function ( message ) {},//收到位置消息
                                        onFileMessage: function ( message ) {},    //收到文件消息
                                        onVideoMessage: function (message) {
                                            var node = document.getElementById('privateVideo');
                                            var option = {
                                                url: message.url,
                                                headers: {
                                                    'Accept': 'audio/mp4'
                                                },
                                                onFileDownloadComplete: function (response) {
                                                    var objectURL = WebIM.utils.parseDownloadResponse.call(conn, response);
                                                    node.src = objectURL;
                                                },
                                                onFileDownloadError: function () {
                                                    console.log('File down load error.')
                                                }
                                            };
                                            WebIM.utils.download.call(conn, option);
                                        },   //收到视频消息
                                        onPresence: function ( message ) {},       //处理“广播”或“发布-订阅”消息，如联系人订阅请求、处理群组、聊天室被踢解散等消息
                                        onRoster: function ( message ) {},         //处理好友申请
                                        onInviteMessage: function ( message ) {},  //处理群组邀请
                                        onOnline: function () {},                  //本机网络连接成功
                                        onOffline: function () {},                 //本机网络掉线
                                        onError: function ( message ) {},          //失败回调
                                        onBlacklistUpdate: function (list) {       //黑名单变动
                                            // 查询黑名单，将好友拉黑，将好友从黑名单移除都会回调这个函数，list则是黑名单现有的所有好友信息
                                            console.log(list);
                                        },
                                        onReceivedMessage: function(message){},    //收到消息送达服务器回执
                                        onDeliveredMessage: function(message){},   //收到消息送达客户端回执
                                        onReadMessage: function(message){},        //收到消息已读回执
                                        onCreateGroup: function(message){},        //创建群组成功回执（需调用createGroupNew）
                                        onMutedMessage: function(message){}        //如果用户在A群组被禁言，在A群发消息会走这个回调并且消息不会传递给群其它成员
                                    });
                                }
                            }
                        })
                    }else{
                        //未绑定
                        $scope.hasData = false;


                    }
                }

            })





        }])
