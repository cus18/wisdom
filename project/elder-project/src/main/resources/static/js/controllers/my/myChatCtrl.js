angular.module('controllers',[]).controller('myChatCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','$ionicScrollDelegate','openidUtil',
        'GetLaoyouUserByOpenId','Global','GetUserGroupChatInfo','GetWechatUserInfo','$ionicPopup','$timeout',
        function ($scope,$interval,$rootScope,$stateParams,$state,$ionicScrollDelegate,openidUtil,
                  GetLaoyouUserByOpenId,Global,GetUserGroupChatInfo,GetWechatUserInfo,$ionicPopup,$timeout) {


            $scope.groupType = $stateParams.groupType;

            openidUtil.checkResponseData();
            // $rootScope.openid = 'o1KHB1Sq5Okyu737zWGTQEHqmeJA';

            $scope.param = {
                messageList : [],
                chatMessage: '',
                elderMessage:{},
                chatStyle: {
                    "padding-top":"0px",
                    "height":"60px"
                },
                scrollStyle:{
                    "height":"93%"
                }
            }

            var addPhoto = false;

            $scope.addPhoto = function(){
                if(!addPhoto)
                {
                    $scope.param.chatStyle = {
                        "position": "relative",
                        "margin-top": "-40%",
                        "border-bottom":"1px solid #ccc",
                        "padding-top":"0px",
                        "height":"60px"
                    }
                    $scope.param.scrollStyle = {
                        "height":"70%"
                    }
                    $scope.addPhotoShow = true;
                }
                else
                {
                    $scope.param.chatStyle = {
                        "padding-top":"0px",
                        "height":"60px"
                    }
                    $scope.param.scrollStyle = {
                        "height":"93%"
                    }
                    $scope.addPhotoShow = false;
                }
                addPhoto = !addPhoto;
            }

            GetWechatUserInfo.get({openid:$rootScope.openid},function(data){
                $scope.elderName = data.nickname;
                $scope.elderImg = data.headimgurl;

            })

            //判断是否已绑定
            GetLaoyouUserByOpenId.get({openid:$rootScope.openid},function(data){
                if(data.result == Global.SUCCESS){
                    if(data.responseData != null){
                        //已绑定
                        $scope.easemobId = data.responseData.elderUserDTO.easemobID;
                        $scope.easemobPassword = data.responseData.elderUserDTO.easemobPassword;
                        $scope.elderId = data.responseData.elderUserDTO.id;

                        //获取群聊信息
                        GetUserGroupChatInfo.get({elderId:$scope.elderId,easemobId:$scope.easemobId},function(data){
                            if(data.result == Global.SUCCESS) {
                                if ($scope.groupType == 'activity') {
                                    $rootScope.pageTitle = '活动群';
                                    $scope.groupId = $stateParams.id;
                                    // if(data.responseData.activityEasemobGroupInfoList){
                                    //     console.log(4444)
                                    //     angular.forEach(data.responseData.activityEasemobGroupInfoList,function(data){
                                    //         if(data.id == $scope.id){
                                    //             $scope.groupId = data.groupId;
                                    //         }
                                    //     })
                                    // }else{
                                    //     console.log(33333)
                                    //     var alertPopup = $ionicPopup.show({
                                    //         title:'该活动没有活动群，请联系机构方创建'
                                    //     });
                                    //     $timeout(function() {
                                    //         alertPopup.close();
                                    //     }, 2000);
                                    // }
                                }
                                else if ($scope.groupType == 'healthData') {
                                    $rootScope.pageTitle = '健康管理群';
                                    if (data.responseData.easemobGroup.easemobGroupID) {
                                        $scope.groupId = data.responseData.easemobGroup.easemobGroupID;
                                    }else {
                                        var alertPopup = $ionicPopup.show({
                                            title:'该医护群不存在，请联系机构查看'
                                        });
                                        $timeout(function() {
                                            alertPopup.close();
                                            $state.go('bindPhone');
                                        }, 2000);
                                    }
                                }
                            }
                        })

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
                            success: function (data) {

                            },
                            error: function (data) {
                                console.log("error", data);
                            }

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
                                console.log('text')
                                // if(message[0].from != $scope.elderName){
                                    $scope.param.messageList.push(message);
                                // }
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
                                console.log('pic')
                                $scope.param.messageList.push(message);
                                console.log($scope.param.messageList);
                                $scope.$apply();
                                $ionicScrollDelegate.$getByHandle('chat-content').scrollBottom();
                            }, //收到图片消息
                            onCmdMessage: function ( message ) {},     //收到命令消息
                            onAudioMessage: function ( message ) {
                                console.log(message);
                            },   //收到音频消息
                            onLocationMessage: function ( message ) {},//收到位置消息
                            onFileMessage: function ( message ) {
                                console.log('file')
                            },    //收到文件消息
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
                                groupId: $scope.groupId,
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
                                to: $scope.groupId,                     // 接收消息对象(群组id)
                                roomType: false,
                                chatType: 'chatRoom',
                                ext :{"user_nice_name":$scope.elderName,"user_type":null,"user_img":$scope.elderImg},
                                success: function () {
                                    console.log('send room text success');
                                    $scope.param.elderMessage.ext = option.ext;
                                    $scope.param.elderMessage.data = option.msg;
                                    $scope.param.elderMessage.from = $scope.elderName;
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


                        // 发送图片消息
                        $scope.sendPrivateImg = function () {
                            var id = conn.getUniqueId();                   // 生成本地消息id
                            var msg = new WebIM.message('img', id);        // 创建图片消息
                            var input = document.getElementById('image');  // 选择图片的input
                            var file = WebIM.utils.getFileUrl(input);      // 将图片转化为二进制文件
                            var allowType = {
                                'jpg': true,
                                'gif': true,
                                'png': true,
                                'bmp': true
                            };
                            if (file.filetype.toLowerCase() in allowType) {
                                var option = {
                                    apiUrl: WebIM.config.apiURL,
                                    file: file,
                                    to: $scope.groupId,                       // 接收消息对象
                                    roomType: false,
                                    chatType: 'chatRoom',
                                    ext :{"user_nice_name":$scope.elderName,"user_type":null,"user_img":$scope.elderImg},
                                    onFileUploadError: function () {      // 消息上传失败
                                        console.log('onFileUploadError');
                                    },
                                    onFileUploadComplete: function () {   // 消息上传成功
                                        console.log('onFileUploadComplete');
                                    },
                                    success: function () {                // 消息发送成功
                                        console.log('Success');
                                        $scope.param.elderMessage.ext = option.ext;
                                        $scope.param.elderMessage.data = option.file;
                                        $scope.param.elderMessage.from = $scope.elderName;
                                        $scope.param.messageList.push(angular.copy($scope.param.elderMessage));
                                        $scope.param.chatMessage = '';
                                        $scope.param.elderMessage = {};
                                        $scope.$apply();
                                        console.log($scope.param.messageList);
                                        $ionicScrollDelegate.$getByHandle('chat-content').scrollBottom();
                                    },
                                    flashUpload: WebIM.flashUpload
                                };
                                msg.set(option);
                                conn.send(msg.body);
                            }
                        };

                    }else{
                        //未绑定
                        var alertPopup = $ionicPopup.show({
                            title:'您未绑定手机号，请至[我的]-[绑定手机号]进行手机号绑定，页面将在3秒后自动跳转'
                        });
                        $timeout(function() {
                            alertPopup.close();
                            $state.go('bindPhone');
                        }, 3000);
                    }
                }

            })




}])