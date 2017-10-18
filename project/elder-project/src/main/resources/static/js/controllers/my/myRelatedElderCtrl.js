angular.module('controllers',[]).controller('myRelatedElderCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetRelativeElderInfo',
        function ($scope,$rootScope,$stateParams,$state,GetRelativeElderInfo) {

            // connectWebViewJavascriptBridge(function() {
            //     window.WebViewJavascriptBridge.callHandler(
            //         'getElderInfo','',function(responseData) {
            //             var dataValue = JSON.parse(responseData);
            //             $scope.elderId = dataValue.elderId;
            //             $scope.elderName = dataValue.elderName;

                            GetRelativeElderInfo.save({},function(data){
                                $scope.relativeElderList = data.responseData;
                            })
            //         })
            // })


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
                    $scope.elderId = $rootScope.rootElderId;
                    $scope.elderName = $rootScope.rootElderName;
                }

                $state.go("healthServiceList");

            }

        }])