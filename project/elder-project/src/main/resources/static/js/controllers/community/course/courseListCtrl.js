angular.module('controllers',[]).controller('courseListCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetRecentLiveBroadCast',
        'GetHistoryLiveBroadCast','GetOnlineCourseList','ElderUtil',
        function ($scope,$rootScope,$stateParams,$state,GetRecentLiveBroadCast,
                  GetHistoryLiveBroadCast,GetOnlineCourseList,ElderUtil) {

            $scope.param = {
                page:{
                    pageNo:1,
                    pageSize:10,
                    orderType:1,
                    requestData:""
                },
                type : $stateParams.type
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

            if($scope.param.type=="live")
            {
                console.log($scope.param.type)
            }
            else if($scope.param.type=="vod")
            {
                GetOnlineCourseList.save($scope.param.page,function(data){
                    ElderUtil.checkResponseData(data);
                    $scope.onlineCourseList = data.responseData;
                })
            }

            $scope.doRefresh = function(){
                $scope.param.page.pageSize = $scope.param.page.pageSize+10;
                if($scope.param.type=="live")
                {
                    console.log($scope.param.type);
                    $scope.$broadcast('scroll.refreshComplete');
                }
                else if($scope.param.type=="vod")
                {
                    GetOnlineCourseList.save($scope.param.page,function(data){
                        ElderUtil.checkResponseData(data);
                        $scope.onlineCourseList = data.responseData;
                        $scope.$broadcast('scroll.refreshComplete');
                    })
                }
            }

        }])