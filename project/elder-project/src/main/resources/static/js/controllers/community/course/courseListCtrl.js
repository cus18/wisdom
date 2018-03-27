angular.module('controllers',[]).controller('courseListCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetRecentLiveBroadCast',
        'GetHistoryLiveBroadCast','GetOnlineCourseList','ElderUtil',
        function ($scope,$rootScope,$stateParams,$state,GetRecentLiveBroadCast,
                  GetHistoryLiveBroadCast,GetOnlineCourseList,ElderUtil) {

            $rootScope.pageTitle = '在线课堂';

            $scope.param = {
                page:{
                    pageNo:1,
                    pageSize:10,
                    orderType:1,
                    requestData:""
                }
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


            GetOnlineCourseList.save($scope.param.page,function(data){
                ElderUtil.checkResponseData(data);
                $scope.onlineCourseList = data.responseData;
            })


            $scope.doRefresh = function(){
                $scope.param.page.pageSize = $scope.param.page.pageSize+10;
                    GetOnlineCourseList.save($scope.param.page,function(data){
                        ElderUtil.checkResponseData(data);
                        $scope.onlineCourseList = data.responseData;
                        $scope.$broadcast('scroll.refreshComplete');
                    })

            }

        }])