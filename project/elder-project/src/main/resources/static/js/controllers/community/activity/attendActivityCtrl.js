angular.module('controllers',[]).controller('attendActivityCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state',
        'GetActivityDetail','JoinActivity','Global','openidUtil',
        function ($scope,$interval,$rootScope,$stateParams,$state,
                  GetActivityDetail,JoinActivity,Global,openidUtil) {


            var activityId = $stateParams.activityId;

            $rootScope.openid = 'oRnVIxOypU0LiuavDpTl_xe10i7Y';
            openidUtil.checkResponseData();

            $scope.activity = {};
            $scope.param = {
                attended : false
            };
            if($scope.param.attended){
                $rootScope.pageTitle = '报名成功';
            }else{
                $rootScope.pageTitle = '活动报名';
            }

            $scope.confirmAttend = function(){
                if($scope.activity.name && $scope.activity.phone){
                    JoinActivity.get({openId:$rootScope.openid,activityId:activityId},function(data){
                        if(data.result = Global.SUCCESS)
                        {
                            $scope.param.attended = true;
                            $scope.activityGroupId = data.responseData
                        }
                    })
                }else{
                    alert('请填写姓名和电话信息')
                }
            };

            $scope.$on('$ionicView.enter', function(){

                GetActivityDetail.get({activityId:activityId}, function(data){
                    $scope.detailActivityInfo = data.responseData;
                })

            });

            // $scope.enterActivityGroupTalk = function(){
            //     connectWebViewJavascriptBridge(function() {
            //         window.WebViewJavascriptBridge.callHandler('attendActivityGroupTalk',
            //             $scope.activityGroupId+";"+$scope.detailActivityInfo.activityName,function(responseData) {})
            //     })
            // }
        }])