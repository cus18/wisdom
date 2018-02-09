angular.module('controllers',[]).controller('subscribeServiceSuccessCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global',
        'ElderUtil','GetUserInfo','GetlivingServiceList',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,
                  ElderUtil,GetUserInfo,GetlivingServiceList) {


            $rootScope.pageTitle = '预约成功';

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

            GetUserInfo.save(function(data){
                ElderUtil.checkResponseData(data,'subscribeServiceSuccess/'+$stateParams.livingServiceId);
            })

            GetlivingServiceList.save({
                id:$stateParams.livingServiceId,
                type:'',
                lastNo:'0',
                nextNo:'10'
            },function(data){
                if(data.result == Global.SUCCESS){
                    $scope.response = data.responseData[0];
                }
                else
                {
                    console.log(data.errorInfo);
                }

            })

            $scope.enterSubscribeServiceList = function()
            {
                $state.go("myService",{type:'inReview'});
            }

            $scope.enterLivingIndex = function()
            {
                $state.go("livingIndex");
            }



        }])