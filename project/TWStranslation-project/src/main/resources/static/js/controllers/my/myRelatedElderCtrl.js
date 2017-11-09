angular.module('controllers',[]).controller('myRelatedElderCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetRelativeElderInfo',
        function ($scope,$rootScope,$stateParams,$state,GetRelativeElderInfo) {

            GetRelativeElderInfo.save({},function(data){
                $scope.relativeElderList = data.responseData;
            })

            $scope.goHealthService = function(elderInfo){

                if($rootScope.rootElderId!=undefined)
                {
                    $scope.elderId = $rootScope.rootElderId;
                    $scope.elderName = $rootScope.rootElderName;
                    $scope.elderImg = $rootScope.rootElderImg;
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
                        $scope.elderImg = $rootScope.rootElderImg;
                    }
                    else
                    {
                        $scope.elderId = "0000";
                    }
                }

                $state.go("healthServiceList");
            }

        }])