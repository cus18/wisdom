angular.module('controllers',[]).controller('activityDetailCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','GetActivityDetail','Global','GetLaoyouUserByOpenId',
        'GetActivityAttendStatus','GetActivityDiscuss','CreateActivityDiscuss','openidUtil','JoinActivityEasemobGroup',
        '$ionicPopup','$timeout',
        function ($scope,$interval,$rootScope,$stateParams,$state,GetActivityDetail,Global,GetLaoyouUserByOpenId,
                  GetActivityAttendStatus,GetActivityDiscuss,CreateActivityDiscuss,openidUtil,JoinActivityEasemobGroup,
                  $ionicPopup,$timeout) {


            $rootScope.pageTitle = '活动详情';
            var activityId = $stateParams.activityId;

            // $rootScope.openid = 'o1KHB1Sq5Okyu737zWGTQEHqmeJA';
            openidUtil.checkResponseData();

            $scope.param = {
                activityAttendStatus : "0",
                operation : "introduce",
                page : {pageNo:1, pageSize:5, orderType:1},
                activityId : activityId,
                attendDiscuss: false,
                discussContent: "",
                discussInfinite:true
            }

            $scope.$on('$ionicView.enter', function(){
                GetActivityDetail.get({activityId:activityId}, function(data){
                    $scope.detailActivityInfo = data.responseData;
                    GetActivityAttendStatus.get({activityId:activityId,openId:$rootScope.openid},function(data){
                        $scope.param.activityAttendStatus = data.responseData;
                    })
                })
            });

            $scope.discuss = function(){
                $scope.param.operation = "discuss";
                $scope.param.page.requestData = activityId;
                GetActivityDiscuss.save($scope.param.page,function(data){
                    $scope.activityDiscussList = data.responseData;
                })
            }

            $scope.introduce = function(){
                $scope.param.operation = "introduce";
            }



            $scope.attendActivityGroupTalk = function(){
                //判断是否已绑定
                GetLaoyouUserByOpenId.get({openid:$rootScope.openid},function(data) {
                    if (data.result == Global.SUCCESS) {
                        if (data.responseData != null) {
                            //已绑定
                            $scope.easemobId = data.responseData.elderUserDTO.easemobID;
                            //进入活动群聊圈
                            JoinActivityEasemobGroup.get({activityId:activityId,easemobId:$scope.easemobId},function(data){
                                if(data.result == Global.SUCCESS){
                                    if(data.responseData != 'unEasemobGroup'){
                                        //有活动群
                                        $state.go('myChat',{'groupType':'activity','id':data.responseData})
                                    }else{
                                        //没有活动群
                                        var alertPopup = $ionicPopup.show({
                                            title:'该活动未创建群聊'
                                        });
                                        $timeout(function() {
                                            alertPopup.close();
                                        }, 2000);
                                    }
                                }else{
                                    alert(data.errorInfo)
                                }
                            })
                        }else{
                            //未绑定
                            var alertPopup = $ionicPopup.show({
                                title:'您未绑定手机号，请至[我的]-[绑定手机号]进行手机号绑定'
                            });
                            $timeout(function() {
                                alertPopup.close();
                            }, 2000);
                        }
                    }
                })
            }

            $scope.doRefresh = function(){
                $scope.param.page.pageSize = 10;
                $scope.param.discussInfinite = true;
                GetActivityDiscuss.save($scope.param.page,function(data){
                    $scope.activityDiscussList = data.responseData;
                    $scope.$broadcast('scroll.refreshComplete');
                })
            }

            $scope.loadMoreDiscuss = function(){
                $scope.param.page.pageSize = $scope.param.page.pageSize + 5;
                GetActivityDiscuss.save($scope.param.page,function(data){
                    $scope.activityDiscussList = data.responseData;
                    if(data.responseData.length < $scope.param.page.pageSize){
                        $scope.param.discussInfinite = false;
                    }
                })
            }

            $scope.attendDiscuss = function(){
                $scope.param.attendDiscuss = true;
                $('.discussContainer textarea').focus();
            }

            $scope.cancelDiscuss = function(){
                $scope.param.discussContent="";
                $scope.param.attendDiscuss = false;
            }

            $scope.submitDiscuss = function(){

                if($scope.param.discussContent=="")
                {
                    alert("请输入评论内容再提交");
                }
                else
                {
                    CreateActivityDiscuss.save({
                            discussContent:$scope.param.discussContent,
                            activityId:activityId,
                            openId:$rootScope.openid},function(data){
                                $scope.param.discussContent="";
                                GetActivityDiscuss.save($scope.param.page,function(data){
                                    $scope.activityDiscussList = data.responseData;
                                })
                    })
                    $scope.param.attendDiscuss = false;
                }
            }

            $scope.return = function(){

                // connectWebViewJavascriptBridge(function() {
                //     window.WebViewJavascriptBridge.callHandler('returnNative','',function(responseData) {});});
            }

        }])