angular.module('controllers',[]).controller('testReportResultCtrl',
    ['$scope','$rootScope','$stateParams','$state','TestReportList',
        function ($scope,$rootScope,$stateParams,$state,TestReportList) {

            $scope.loadingStatus = true;

            $scope.enterGroupTalk = function(){

                //进入健康群聊圈

                //window.WebViewJavascriptBridge.callHandler('enterGroupTalk','',function(responseData){});
            }

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

            $scope.testReportResult = false;

            TestReportList.get({elderId:$scope.elderId,
                    startDate:$stateParams.testDate,
                    endDate:$stateParams.testDate},
                function(data){
                    $scope.loadingStatus = false;

                    angular.forEach(data.responseData,function(value,index,array){
                        if(value.testTime == $stateParams.testTime){
                            $scope.testReportResultData = value;
                        }
                    })
                    $scope.testReportResult = true;
                });


        }])
