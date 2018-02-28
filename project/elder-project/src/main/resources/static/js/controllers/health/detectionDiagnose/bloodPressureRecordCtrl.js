/**
 * Created by 郑强丽 on 2017/5/20.
 */
angular.module('controllers',[]).controller('bloodPressureRecordCtrl',
    ['$scope','$timeout','$rootScope','$stateParams','$filter','$state','ElderUtil','openidUtil',
        function ($scope,$timeout,$rootScope,$stateParams,$filter,$state,ElderUtil,openidUtil) {

            $rootScope.pageTitle = '血压/心率数据';

            // $rootScope.openid = 'oRnVIxOypU0LiuavDpTl_xe10i7Y';
            openidUtil.checkResponseData();


            $scope.bloodPressure = {};

            /*只读*/
            if($stateParams.readOnly=='true')
            {
                $scope.bloodPressure.readOnly = true;
            }
            else if($stateParams.readOnly=='false')
            {
                $scope.bloodPressure.readOnly = false;
                $rootScope.measureBloodPressureRemark = '';
            }

            /*
             * 页面初始值
             * */
            if($rootScope.measureBloodPressure==null||angular.equals({}, $rootScope.measureBloodPressure)){
                $rootScope.measureBloodPressure = {};
                $scope.bloodPressure.diastolic = $stateParams.diastolic;
                $scope.bloodPressure.systolic = $stateParams.systolic;
                $scope.bloodPressure.heartRate = $stateParams.heartRate;
                $scope.bloodPressure.emptyCont = $stateParams.emptyCont;
                $scope.bloodPressure.remark = $rootScope.measureBloodPressureRemark;
                var time = $stateParams.measureTime.split(' ');
                $scope.bloodPressure.measureDate = time[0];
                $scope.bloodPressure.measureTime = time[1];
            }else{
                if($rootScope.measureBloodPressure.measureDate){
                    $scope.bloodPressure.measureTime = $rootScope.measureBloodPressure.measureTime;
                    $scope.bloodPressure.measureDate = $rootScope.measureBloodPressure.measureDate;
                }
                $scope.bloodPressure.diastolic = $rootScope.measureBloodPressure.diastolic;
                $scope.bloodPressure.systolic = $rootScope.measureBloodPressure.systolic;
                $scope.bloodPressure.heartRate = $rootScope.measureBloodPressure.heartRate;
                $scope.bloodPressure.emptyCont = $rootScope.measureBloodPressure.emptyCont;
            }

            /*默认时间显示*/
            $scope.bloodPressure.nowDate = $filter('date')(new Date().getTime(),'yyyy-MM-dd');
            $scope.bloodPressure.nowTime = $filter('date')(new Date().getTime(),'HH:mm');


}])
