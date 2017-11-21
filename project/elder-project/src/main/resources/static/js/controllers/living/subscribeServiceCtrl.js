angular.module('controllers',[]).controller('subscribeServiceCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state',
        'ElderUtil','GetUserInfo','GetCommunityBannerList',
        function ($scope,$interval,$rootScope,$stateParams,$state,
                  ElderUtil,GetUserInfo,GetCommunityBannerList) {

            $scope.param = {
                type : 'short'
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

            GetUserInfo.save(function(data){
                ElderUtil.checkResponseData(data,'subscribeService/'+$stateParams.livingServiceId);
            })

            $scope.subscribeConfirm = function(){
                $state.go('subscribeServiceSuccess',{livingServiceId:$stateParams.livingServiceId})
            }

        }])