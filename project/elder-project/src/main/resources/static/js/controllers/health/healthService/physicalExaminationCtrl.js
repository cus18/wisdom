angular.module('controllers',[]).controller('physicalExaminationCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetHealthArchivePhysicalExamination',
        function ($scope,$rootScope,$stateParams,$state, GetHealthArchivePhysicalExamination) {

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

            $scope.physicalExaminationResult = false;

            $scope.loadingStatus = true;

            GetHealthArchivePhysicalExamination.get({physicalExaminationId:$stateParams.physicalExaminationId},
                function(data){

                $scope.loadingStatus = false;

                $scope.physicalExaminationResult = true;
                $scope.physicalExaminationResultData = data.responseData;

            });

        }])
