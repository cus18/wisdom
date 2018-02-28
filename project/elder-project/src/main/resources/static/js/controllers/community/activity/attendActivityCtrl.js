angular.module('controllers',[]).controller('attendActivityCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','JoinActivityEasemobGroup',
        'GetActivityDetail','JoinActivity','Global','openidUtil',
        function ($scope,$interval,$rootScope,$stateParams,$state,JoinActivityEasemobGroup,
                  GetActivityDetail,JoinActivity,Global,openidUtil) {


            var activityId = $stateParams.activityId;
            $scope.canClick = false;

            // $rootScope.openid = 'oRnVIxOypU0LiuavDpTl_xe10i7Y';
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
                    $scope.canClick = true;
                    JoinActivity.get({openId:$rootScope.openid,activityId:activityId},function(data){
                        if(data.result = Global.SUCCESS)
                        {
                            $scope.param.attended = true;
                            $scope.activityGroupId = data.responseData;
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

            //进入活动群聊
            $scope.enterActivityGroupTalk = function(){
                JoinActivityEasemobGroup.get({activityId:activityId,openId:$rootScope.openid},function(data){

                })
                // $state.go('myChat',{'groupId':})
            }
        }])