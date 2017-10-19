angular.module('controllers',[]).controller('healthServicePackageCtrl',
    ['$scope','$rootScope','$stateParams','$state','$sce',
        'GetHealthServicePackageTemplateDetail','CreateHealthServicePackage',
        'GetOnGoingHealthServicePackage','ElderUtil','Global','$timeout',
        function ($scope,$rootScope,$stateParams,$state,$sce,
                  GetHealthServicePackageTemplateDetail,CreateHealthServicePackage,
                  GetOnGoingHealthServicePackage,ElderUtil,Global,$timeout) {

            $scope.loadingStatus = true;

            $scope.operation = $stateParams.operation;
            $scope.servicePackageId = $stateParams.servicePackageId;

            $scope.enterGroupTalk = function(){

                //进入健康群聊圈

                //window.WebViewJavascriptBridge.callHandler('enterGroupTalk','',function(responseData){});
            }

            // connectWebViewJavascriptBridge(function(bridge) {
            //
            //     bridge.registerHandler("createDoctorSignatureDown", function(data, responseCallback) {
            //         $scope.healthServicePackageData.firstParty.doctorSignature = data;
            //         $timeout(function() {
            //             $scope.$apply();
            //         }, 6000);
            //         responseCallback(responseData);
            //     });
            //
            //     bridge.registerHandler("createNurseSignatureDown", function(data, responseCallback) {
            //         $scope.healthServicePackageData.firstParty.nurseSignature = data;
            //         $timeout(function() {
            //             $scope.$apply();
            //         }, 6000);
            //         responseCallback(responseData);
            //     });
            //
            //     bridge.registerHandler("createSecondPartySignatureDown", function(data, responseCallback) {
            //         $scope.healthServicePackageData.secondParty.secondPartySignature = data;
            //         $timeout(function() {
            //             $scope.$apply();
            //         }, 6000);
            //         responseCallback(responseData);
            //     });
            // });


            $scope.takeDoctorSignature = function(){
                // window.WebViewJavascriptBridge.callHandler(
                //     'createDoctorSignature',
                //     function(responseData) {
                //     }
                // );
            }

            $scope.takeNurseSignature = function(){
                // window.WebViewJavascriptBridge.callHandler(
                //     'createNurseSignature',
                //     function(responseData) {
                //     }
                // );
            }

            $scope.takeSecondPartySignature = function(){
                // window.WebViewJavascriptBridge.callHandler(
                //     'createSecondPartySignature',
                //     function(responseData) {
                //     }
                // );
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

            $scope.healthServicePackageData ={
                servicePackageId:"",
                servicePackageTemplateId:"",
                elderId:"",
                servicePackageTemplateName:"",
                status:"",
                updateDate:"",
                firstParty:{},
                secondParty:{}
            }

            if($scope.operation == "existHealthServicePackage")
            {
                GetOnGoingHealthServicePackage.get({healthPackageServiceId:$scope.servicePackageId, elderId:$scope.elderId},function(data){
                    $scope.loadingStatus = false;
                    ElderUtil.checkResponseData(data);
                    $scope.healthServicePackageData = data.responseData;
                    GetHealthServicePackageTemplateDetail.get({healthPackageServiceTemplateId:$scope.healthServicePackageData.servicePackageTemplateId},
                        function(data){
                            ElderUtil.checkResponseData(data);
                            $scope.healthPackageTemplateDetail = $sce.trustAsHtml('<section class="serviceCont">'
                                +data.responseData.healthServicePackageTemplateData+'</section>');
                        });
                });

            }
            else if($scope.operation == "newHealthServicePackage")
            {
                GetHealthServicePackageTemplateDetail.get({healthPackageServiceTemplateId:$scope.servicePackageId},function(data){
                    $scope.loadingStatus = false;
                    $scope.healthServicePackageData.firstParty.firstPartySignature = data.responseData.firstPartySignature;
                    $scope.healthServicePackageData.servicePackageTemplateId = $scope.servicePackageId;
                    $scope.healthServicePackageData.servicePackageTemplateName = data.responseData.healthServicePackageTemplateName;
                    $scope.healthServicePackageData.elderId = $scope.elderId;
                    $scope.healthPackageTemplateName = data.responseData.healthServicePackageTemplateName;
                    $scope.healthPackageTemplateDetail = $sce.trustAsHtml('<section class="serviceCont">'
                        +data.responseData.healthServicePackageTemplateData+'</section>');
                });
            }

            /*保存服务套餐*/
            var submitOnOff = true;        //防止多次提交
            $scope.commitHealthServicePackage = function(valid)
            {

                if($scope.healthServicePackageData.firstParty.firstPartySignature &&
                    $scope.healthServicePackageData.firstParty.doctorSignature &&
                    $scope.healthServicePackageData.firstParty.nurseSignature &&
                    $scope.healthServicePackageData.secondParty.secondPartySignature){
                    var signArea = true;
                }

                if(valid && signArea){
                    if(submitOnOff){
                        submitOnOff = false;
                        CreateHealthServicePackage.save($scope.healthServicePackageData, function(data){
                            if(data.result==Global.SUCCESS)
                            {
                                $state.go("healthService",{firstMenu: 'healthServicePackage', secondMenu: ''});
                            }
                        })
                    }

                }

            }

        }])
