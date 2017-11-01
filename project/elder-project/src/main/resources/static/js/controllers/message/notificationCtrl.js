angular.module('controllers',[]).controller('notificationCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','GetNotificationMessage',
        function ($scope,$interval,$rootScope,$stateParams,$state,GetNotificationMessage) {

            $scope.param = {
                page:{
                    pageNo:1,
                    pageSize:10,
                    orderType:1
                }
            }

            $scope.$on('$ionicView.enter', function(){

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

                GetNotificationMessage.save($scope.param.page,
                    function(data){
                    $scope.notificationMessageList = data.responseData;
                })
            });

            $scope.goDetail = function (message) {
                if(message.notificationMessageType=="0")
                {
                    //去直播详情板块
                    connectWebViewJavascriptBridge(function() {
                        window.WebViewJavascriptBridge.callHandler('getLiveBroadCast',
                            message.notificationMessageContentId,function(responseData) {})
                    })
                }else if(message.notificationMessageType=="1")
                {
                    //去活动详情页
                    $state.go("activityDetail",{activityId:message.notificationMessageContentId});
                }
            }

            $scope.doRefresh = function(){
                $scope.param.page.pageSize = $scope.param.page.pageSize+10;
                GetNotificationMessage.save($scope.param.page,
                    function(data){
                    $scope.notificationMessageList = data.responseData;
                    $scope.$broadcast('scroll.refreshComplete');
                })
            }

        }])