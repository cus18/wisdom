angular.module('controllers',[]).controller('myselfInfoCtrl',
    ['$scope','$rootScope','$stateParams','$state','ElderUtil','GetUserInfo','UserLoginOut','Global',
        function ($scope,$rootScope,$stateParams,$state,ElderUtil,GetUserInfo,UserLoginOut,Global) {

            $scope.param = {
                myselfInf0:{}
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
                ElderUtil.checkResponseData(data,'myselfCenter');
                $scope.param.myselfInfo = data.responseData;
            })

            $scope.loginOut = function(){
                UserLoginOut.save(function(data){

                    if(data.result == Global.LOGIN_OUT)
                    {
                        window.location.href = "login";
                    }

                })
            }


        }])