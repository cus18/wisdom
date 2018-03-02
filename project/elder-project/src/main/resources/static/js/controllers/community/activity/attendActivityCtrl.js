angular.module('controllers',[]).controller('attendActivityCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','JoinActivityEasemobGroup',
        'GetActivityDetail','JoinActivity','Global','openidUtil','$ionicPopup','$timeout',
        function ($scope,$interval,$rootScope,$stateParams,$state,JoinActivityEasemobGroup,
                  GetActivityDetail,JoinActivity,Global,openidUtil,$ionicPopup,$timeout) {


            var activityId = $stateParams.activityId;
            $scope.canClick = false;

            // $rootScope.openid = 'o1KHB1Sq5Okyu737zWGTQEHqmeJA';
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

            //输入
            $scope.inputFocus = function(){
                $scope.blurInp = false;
            }
            $scope.inputBlur = function(){
                $scope.blurInp = true;
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
                    var alertPopup = $ionicPopup.show({
                        title:'请填写姓名和电话信息'
                    });
                    $timeout(function() {
                        alertPopup.close();
                    }, 2000);
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
                    if(data.result == Global.SUCCESS){
                        $state.go('myChat',{'groupType':'activity','id':data.responseData})
                    }else{
                        alert(data.errorInfo)
                    }
                })
            }
        }])