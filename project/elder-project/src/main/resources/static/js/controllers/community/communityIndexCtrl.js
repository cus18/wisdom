angular.module('controllers',[]).controller('communityIndexCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','GetCommunityBannerList',
        'ElderUtil','GetActivityListByFirstPage','GetOnlineCourseList','GetRelativeElderInfo',
        function ($scope,$interval,$rootScope,$stateParams,$state,GetCommunityBannerList,
                  ElderUtil,GetActivityListByFirstPage,GetOnlineCourseList,GetRelativeElderInfo) {

            $rootScope.pageTitle = '老友智学';

            $scope.param = {
                bannerList : '',
                activityList : ''
            }

            if($rootScope.rootElderId!=undefined)
            {
                $scope.elderId = $rootScope.rootElderId;
                $scope.elderName = $rootScope.rootElderName;
            }
            else
            {
                //将用户信息放入$rootScope中
                $rootScope.rootElderId = window.localStorage.getItem("elderId");
                $rootScope.rootElderName = window.localStorage.getItem("elderName");
                $rootScope.rootElderImg = window.localStorage.getItem("elderImg");
                if($rootScope.rootElderId!=undefined)
                {
                    $scope.elderId = $rootScope.rootElderId;
                    $scope.elderName = $rootScope.rootElderName;
                }
                else
                {
                    $scope.elderId = "0000";
                }
            }

            // GetCommunityBannerList.save(function(data){
            //     ElderUtil.checkResponseData(data,'communityIndex');
            //     $scope.param.bannerList = data.responseData;
            // });

            GetActivityListByFirstPage.save(function(data){
                ElderUtil.checkResponseData(data,'communityIndex');
                angular.forEach(data.responseData,function(data){
                    if(data.activityStatus == 'end'){
                        data.activityStatus = '已结束';
                    }else if(data.activityStatus == 'waiting'){
                        data.activityStatus = '未开始';
                    }else if(data.activityStatus == 'ongoing') {
                        data.activityStatus = '进行中';
                    }
                })
                $scope.param.activityList = data.responseData;
            })

            GetOnlineCourseList.save({pageNo:1,pageSize:5},function(data){
                ElderUtil.checkResponseData(data,'communityIndex');
                $scope.param.onlineCourseList = data.responseData;
            })

            //获取用户健康群的环信ID号
            GetRelativeElderInfo.save({},function(data){

                ElderUtil.checkResponseData(data,'communityIndex');

                $scope.relativeElderList = data.responseData;

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
                    user: $scope.relativeElderList[0].easemobID,
                    pwd: $scope.relativeElderList[0].easemobPassword,
                    appKey: '1156170425115453#laoyoupractitioner'
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
                                        $rootScope.groupId = value.groupid;
                                        $rootScope.groupName = value.groupname;
                                        $rootScope.easemobId = $scope.relativeElderList[0].easemobID;
                                        $rootScope.easemobPassword = $scope.relativeElderList[0].easemobPassword;
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
            })

        }])