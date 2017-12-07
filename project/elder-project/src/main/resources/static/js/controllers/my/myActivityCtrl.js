angular.module('controllers',[]).controller('myActivityCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetRelativeElderInfo','GetActivityList','ElderUtil',
        function ($scope,$rootScope,$stateParams,$state,GetRelativeElderInfo,GetActivityList,ElderUtil) {

            $scope.param = {
                recentPublishActive:[],
                recentAttendActive:[],
                myCourseList:[]
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
                $rootScope.rootElderImg = window.localStorage.getItem("elderImg");
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



            GetActivityList.save({pageNo:5,requestData:'0'},function(data){
                ElderUtil.checkResponseData(data,'myActivity');
                $scope.param.recentPublishActive = data.responseData;
            })
            GetActivityList.save({pageNo:5,requestData:'1'},function(data){
                ElderUtil.checkResponseData(data,'myActivity');
                $scope.param.recentAttendActive = data.responseData;
            })



        }])