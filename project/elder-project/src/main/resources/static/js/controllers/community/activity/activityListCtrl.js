angular.module('controllers',[]).controller('activityListCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','GetActivityList','ElderUtil',
        function ($scope,$interval,$rootScope,$stateParams,$state,GetActivityList,ElderUtil) {

            $scope.param = {
                page:{
                    pageNo:1,
                    orderType:1,
                    requestData:"0"
                }
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

            $scope.$on('$ionicView.enter', function(){
                GetActivityList.save($scope.param.page,
                    function(data){
                    ElderUtil.checkResponseData(data);
                    $scope.activityList = data.responseData;
                })
            });

            $scope.doRefresh = function(){
                $scope.param.page.pageNo = $scope.param.page.pageNo+1;
                GetActivityList.save($scope.param.page,
                    function(data){
                        ElderUtil.checkResponseData(data,'activityList');
                        $scope.activityList = data.responseData;
                        $scope.$broadcast('scroll.refreshComplete');
                    })
            }

            $scope.chooseActivity = function(type){
                if(type=="comm")
                {
                    $scope.param.page.requestData = "0";
                }
                else if(type=="hot")
                {
                    $scope.param.page.requestData = "2";
                }
                GetActivityList.save($scope.param.page, function(data){
                    ElderUtil.checkResponseData(data,'activityList');
                    $scope.activityList = data.responseData;
                })
            }

        }])