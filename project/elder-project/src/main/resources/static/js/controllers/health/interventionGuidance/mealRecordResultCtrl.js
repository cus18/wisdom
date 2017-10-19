/**
 * Created by 郑强丽 on 2017/6/5.
 */
angular.module('controllers',[]).controller('mealRecordResultCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetDietPlanByDate',
        function ($scope,$rootScope,$stateParams,$state,GetDietPlanByDate) {

            $scope.loadingStatus = true;

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

            $scope.date = $stateParams.date;
            $scope.time = $stateParams.time;

            GetDietPlanByDate.save({elderUserID:$scope.elderId,startDate:$scope.date,endDate:$scope.date},function(data){

                $scope.loadingStatus = false;

                angular.forEach(data.responseData, function (value, index, array) {

                    if(value.createTime == $scope.time){
                        $scope.mealRecordResultData = value;
                    }

                });
            });


        }])

