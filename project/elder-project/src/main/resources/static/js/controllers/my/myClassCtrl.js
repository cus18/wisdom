angular.module('controllers',[]).controller('myClassCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetMyOnlineCourseList','ElderUtil',
        function ($scope,$rootScope,$stateParams,$state,GetMyOnlineCourseList,ElderUtil) {

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


            GetMyOnlineCourseList.save({"pageNo":1,"pageSize":1,"requestData":"page,learn"},function(data){
                ElderUtil.checkResponseData(data,'myselfClass');
                $scope.param.myCourseList = data.responseData;
                console.log($scope.param.myCourseList);
            })




        }])