angular.module('controllers',[]).controller('myServiceCtrl',
    ['$scope','$rootScope','$stateParams','$state','ElderUtil',
        function ($scope,$rootScope,$stateParams,$state,ElderUtil) {

            $scope.param = {
                tabValue : $stateParams.type,
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

            function tabChange(){
                if($scope.param.tabValue=='inReview')
                {

                }
                if($scope.param.tabValue=='inService')
                {

                }
                if($scope.param.tabValue=='finished')
                {

                }
                if($scope.param.tabValue=='failed')
                {

                }
            }
            tabChange();

            $scope.chooseTab = function(tabValue)
            {
                $scope.param.tabValue = tabValue;
                tabChange();
            }


        }])