angular.module('controllers',[]).controller('healthServiceCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetOnGoingHealthServicePackageList',
        'GetHealthArchiveBasicInfo','GetHealthArchivePhysicalExaminationList',
        'GetHealthArchiveHealthAssessmentList', 'GetElderContactInfo',
        'ElderUtil','Global','$timeout',
        function ($scope,$rootScope,$stateParams,$state,GetOnGoingHealthServicePackageList,
                  GetHealthArchiveBasicInfo,GetHealthArchivePhysicalExaminationList,
                  GetHealthArchiveHealthAssessmentList,GetElderContactInfo,
                  ElderUtil,Global,$timeout) {

            $scope.loadingStatus = true;

            $scope.goMenu = function(firstMenuParam,secondMenuParam){
                $state.go("healthService", {firstMenu: firstMenuParam, secondMenu: secondMenuParam});
            }

            $scope.goPhysicalExamination = function(physicalExaminationId) {
                $state.go("physicalExamination",{physicalExaminationId:physicalExaminationId});
            }

            $scope.newPhysicalExamination = function () {

                //创建检测报告

                // connectWebViewJavascriptBridge(function() {
                //     window.WebViewJavascriptBridge.callHandler(
                //         'createPhysicalExamination','',function(responseData) {
                //         });
                // });
                //
                // connectWebViewJavascriptBridge(function(bridge) {
                //     bridge.registerHandler("createPhysicalExaminationDown", function(data, responseCallback) {
                //         if(data == Global.SUCCESS)
                //         {
                //             $timeout(function() {
                //                 GetHealthArchivePhysicalExaminationList.save({pageNo:"1", pageSize:"5",
                //                     orderType:"1",orderBy:"0",
                //                     requestData:{elderId:$scope.elderId}},function(data){
                //                     $scope.loadingStatus = false;
                //                     ElderUtil.checkResponseData(data);
                //                     $scope.healthArchivePhysicalExaminationList = data.responseData;
                //                 });
                //             }, 6000);
                //         }
                //         responseCallback(responseData);
                //     });
                // });
            }

            $scope.firstMenu = $stateParams.firstMenu;
            $scope.secondMenu = $stateParams.secondMenu;
            $rootScope.h5Page = true;

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

            if($scope.elderId==undefined)
            {
                $scope.elderId = "00000";
                $scope.elderName = "0000";
            }

            if($scope.firstMenu == "healthServicePackage"){
                GetOnGoingHealthServicePackageList.save({pageNo:"1", pageSize:"5",
                    orderType:"1",orderBy:"0",
                    requestData:{elderId:$scope.elderId}}, function (data) {
                    $scope.loadingStatus = false;
                    ElderUtil.checkResponseData(data);
                    $scope.healthServicePackageList = data.responseData;
                })
            }
            else if($scope.firstMenu == "healthArchives")
            {
                if($scope.secondMenu == 'basicInfo'){
                    GetHealthArchiveBasicInfo.get({elderId:$scope.elderId},function(data){
                        $scope.loadingStatus = false;
                        ElderUtil.checkResponseData(data,"healthService/healthArchives,basicInfo");
                        $scope.healthArchiveBasicInfo = data.responseData;
                    });
                }
                else if($scope.secondMenu == 'physicalExamination'){
                    GetHealthArchivePhysicalExaminationList.save({pageNo:"1", pageSize:"5",
                        orderType:"1",orderBy:"0",
                        requestData:{elderId:$scope.elderId}},function(data){
                        $scope.loadingStatus = false;
                        ElderUtil.checkResponseData(data,"healthService/healthArchives,physicalExamination");
                        $scope.healthArchivePhysicalExaminationList = data.responseData;
                    });
                }
            }
            else if($scope.firstMenu == "healthAssessment")
            {
                GetHealthArchiveHealthAssessmentList.save({pageNo:"1", pageSize:"10",
                    orderType:"1",orderBy:"0",
                    requestData:{elderId:$scope.elderId}},function(data){
                    $scope.loadingStatus = false;
                    ElderUtil.checkResponseData(data,"healthService/healthAssessment,");
                    $scope.healthArchiveAssessmentList = data.responseData;
                });
            }

            $scope.gotoHealthServicePackage = function(servicePackageId,operation){
                $state.go("healthServicePackage",{servicePackageId:servicePackageId,operation:operation});
            }
}])
