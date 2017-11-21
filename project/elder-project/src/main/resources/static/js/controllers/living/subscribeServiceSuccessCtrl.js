angular.module('controllers',[]).controller('subscribeServiceSuccessCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state',
        'ElderUtil','GetUserInfo',
        function ($scope,$interval,$rootScope,$stateParams,$state,
                  ElderUtil,GetUserInfo) {

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

            $scope.enterSubscribeServiceList = function()
            {
                $state.go("subscribeServiceList");
            }

            $scope.enterLivingIndex = function()
            {
                $state.go("livingIndex");
            }


        }])