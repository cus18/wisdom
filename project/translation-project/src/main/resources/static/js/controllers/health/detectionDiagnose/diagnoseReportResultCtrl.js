angular.module('controllers',[]).controller('diagnoseReportResultCtrl',
    ['$scope','$rootScope','$stateParams','$state','$filter','DiagnoseReportList','$sce',
        '$timeout','$interval',
        function ($scope,$rootScope,$stateParams,$state,$filter,DiagnoseReportList,
                  $sce,$timeout,$interval) {

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

            $scope.diagnoseReportResult = false;


            DiagnoseReportList.get({elderId:$scope.elderId,
                    startDate:$filter('date')(new Date($stateParams.recordDate).getTime(),'yyyy-MM-dd'),
                    endDate:$filter('date')(new Date($stateParams.recordDate).getTime(),'yyyy-MM-dd')},
                function(data){
                    $scope.loadingStatus = false;
                    angular.forEach(data.responseData,function(value,index,array){
                        if($stateParams.recordDate == value.recordDate)
                        {
                            $scope.diagnoseReportResultData = value;
                            $scope.diagnoseReportResultData.audioUrl = $sce.trustAsResourceUrl($scope.diagnoseReportResultData.audioUrl);
                        }
                    })
                    $scope.diagnoseReportResult = true;
            });


            /*音频播放*/

            $timeout(function(){
                var aud = document.getElementById('diagnoseAudio');
                var audDuration = aud.duration;
                if(aud.duration){
                    $scope.duration = $filter('date')((new Date(-8*60*60*1000+aud.duration*1000)).getTime(),'HH:mm:ss');
                }
                var timer;
                $scope.audioCtrl = function(){
                    if(aud.paused){
                        timer = $interval(function(){
                            if(audDuration > 1){
                                audDuration--;
                                $scope.duration = $filter('date')((new Date(-8*60*60*1000+audDuration*1000)).getTime(),'HH:mm:ss');
                            }else{
                                $interval.cancel(timer);
                                $scope.duration = $filter('date')((new Date(-8*60*60*1000+aud.duration*1000)).getTime(),'HH:mm:ss');
                                audDuration = aud.duration;
                                $scope.paused = false;
                            }
                        },1000)
                        aud.play();
                        $scope.paused = true;
                    }else{
                        $interval.cancel(timer);
                        aud.pause();
                        $scope.paused = false;
                    }
                }
            },1000)

        }])
