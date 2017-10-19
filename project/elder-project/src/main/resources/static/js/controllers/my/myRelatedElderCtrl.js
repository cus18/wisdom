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
                }
                else
                {
                    //将用户信息放入$rootScope中
                    $rootScope.rootElderId = window.localStorage.getItem("elderId");
                    $rootScope.rootElderName = window.localStorage.getItem("elderName");
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

                $state.go("healthServiceList");
            }

        }])