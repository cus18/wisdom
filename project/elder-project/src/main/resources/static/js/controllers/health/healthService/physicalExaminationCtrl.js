angular.module('controllers',[]).controller('physicalExaminationCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetHealthArchivePhysicalExamination',
        function ($scope,$rootScope,$stateParams,$state, GetHealthArchivePhysicalExamination) {

            $scope.enterGroupTalk = function(){

                //进入健康群聊圈

                //window.WebViewJavascriptBridge.callHandler('enterGroupTalk','',function(responseData){});
            }

            $scope.elderId = $rootScope.elderId;
            $scope.elderName = $rootScope.elderName;

            $scope.physicalExaminationResult = false;

            $scope.loadingStatus = true;

            GetHealthArchivePhysicalExamination.get({physicalExaminationId:$stateParams.physicalExaminationId},
                function(data){

                $scope.loadingStatus = false;

                $scope.physicalExaminationResult = true;
                $scope.physicalExaminationResultData = data.responseData;

            });

        }])
