angular.module('controllers',[]).controller('myChatCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','$ionicScrollDelegate',
        function ($scope,$interval,$rootScope,$stateParams,$state,$ionicScrollDelegate) {

            $rootScope.pageTitle = '健康管理群';
            $scope.param = {
                messageList : [],
                chatMessage: '',
                elderMessage:{},
                chatStyle: {
                    "position": "relative",
                    "margin-top": "0"
                }
            }

            var addPhoto = false;

            if($rootScope.rootElderId!=undefined)
            {
                $scope.elderId = $rootScope.rootElderId;
                $scope.elderName = $rootScope.rootElderName;
                $scope.elderImg = $rootScope.rootElderImg;
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
                    $scope.elderImg = $rootScope.rootElderImg;
                }
                else
                {
                    $scope.elderId = "0000";
                }
            }
            $scope.elderId = '165a351da0b442d8917924c6aac68b5d';
            $scope.elderName = '讲对抗';
            $scope.elderImg = 'http://yhlofficial.oss-cn-beijing.aliyuncs.com/image/%E6%B3%95%E5%9B%BD%E5%A4%9A%E6%85%B0.png';

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
                user: $rootScope.easemobId,
                pwd: $rootScope.easemobPassword,
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
                },
                onClosed: function ( message ) {},         //连接关闭回调
                onTextMessage: function ( message ) {
                    $scope.param.messageList.push(message);
                    console.log($scope.param.messageList);
                    $scope.$apply();
                    $ionicScrollDelegate.$getByHandle('chat-content').scrollBottom();
                },    //收到文本消息
                onEmojiMessage: function ( message )
                {
                    $scope.param.messageList.push(message);
                    console.log($scope.param.messageList);
                },   //收到表情消息
                onPictureMessage: function ( message ) {
                    $scope.param.messageList.push(message);
                    console.log($scope.param.messageList);
                }, //收到图片消息
                onCmdMessage: function ( message ) {},     //收到命令消息
                onAudioMessage: function ( message ) {
                    console.log(message);
                },   //收到音频消息
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
                            console.log(objectURL);
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

            $scope.groupList = function() {
                var pageNum = 1, pageSize = 1000;
                var options = {
                    pageNum: pageNum,
                    pageSize: pageSize,
                    groupId: $rootScope.groupId,
                    success: function (resp) {
                        console.log("Group Info Response: ", resp)
                    },
                    error: function(e){}
                };
                conn.listGroupMember(options);
            }

            $scope.sendMessage = function(){

                // 群组发送文本消息
                var id = conn.getUniqueId();            // 生成本地消息id
                var msg = new WebIM.message('txt', id); // 创建文本消息
                var option = {
                    msg: $scope.param.chatMessage,             // 消息内容
                    to: $rootScope.groupId,                     // 接收消息对象(群组id)
                    roomType: false,
                    chatType: 'chatRoom',
                    ext :{"user_nice_name":$scope.elderName,"user_type":null,"user_img":$scope.elderImg},
                    success: function () {
                        console.log('send room text success');
                        $scope.param.elderMessage.ext = option.ext;
                        $scope.param.elderMessage.data = option.msg;
                        $scope.param.elderMessage.from = 'elder';
                        $scope.param.messageList.push(angular.copy($scope.param.elderMessage));
                        $scope.param.chatMessage = '';
                        $scope.param.elderMessage = {};
                        $scope.$apply();
                        console.log($scope.param.messageList);
                        $ionicScrollDelegate.$getByHandle('chat-content').scrollBottom();
                    },
                    fail: function () {
                        console.log('failed');
                    }
                };
                msg.set(option);
                msg.setGroup('groupchat');
                conn.send(msg.body);
            }

            $scope.addPhoto = function(){

                if(!addPhoto)
                {
                    $scope.param.chatStyle = {
                        "position": "relative",
                        "margin-top": "-25%"
                    }
                }
                else
                {
                    $scope.param.chatStyle = {
                        "position": "relative",
                        "margin-top": "0"
                    }
                }
                addPhoto = !addPhoto;

            }

}])