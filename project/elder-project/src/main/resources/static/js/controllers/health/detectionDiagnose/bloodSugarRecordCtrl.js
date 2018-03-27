/**
 * Created by 郑强丽 on 2017/5/20.
 */
angular.module('controllers',[]).controller('bloodSugarRecordCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','$filter','ElderUtil',
        'openidUtil','GetLaoyouUserByOpenId','Global',
        function ($scope,$interval,$rootScope,$stateParams,$state,$filter,ElderUtil,
                  openidUtil,GetLaoyouUserByOpenId,Global) {

            $rootScope.pageTitle = '血糖数据';

            // $rootScope.openid = 'oRnVIxOypU0LiuavDpTl_xe10i7Y';
            openidUtil.checkResponseData();

            $scope.bloodSugar = {};

            /*只读*/
            if ($stateParams.readOnly == 'true') {
                $scope.bloodSugar.readOnly = true;
                $rootScope.measureBloodSugarRemark = $stateParams.remark;
            }
            else if ($stateParams.readOnly == 'false') {
                $scope.bloodSugar.readOnly = false;
                $rootScope.measureBloodSugarRemark = '';
            }

            /*
             * 页面初始值
             * */
            if ($rootScope.measureBloodSugar == null || angular.equals({}, $rootScope.measureBloodSugar)) {
                $rootScope.measureBloodSugar = {};
                $scope.bloodSugar.measureNum = $stateParams.bloodSugarNum;
                $scope.bloodSugar.recorded = $stateParams.recorded;
                $scope.bloodSugar.remark = $rootScope.measureBloodSugarRemark;
                $scope.bloodSugar.measureDate = $stateParams.timeDate.substring(0,10).toString();
                $scope.bloodSugar.measureTime = $stateParams.timeDate.substring(11,16).toString();
                switch ($stateParams.timeType) {
                    case 'dawn':
                        $scope.bloodSugar.measureType = '凌晨';
                        break;
                    case 'beforeBreakFast':
                        $scope.bloodSugar.measureType = '早餐前/空腹';
                        break;
                    case 'afterBreakFast':
                        $scope.bloodSugar.measureType = '早餐后';
                        break;
                    case 'beforeLunch':
                        $scope.bloodSugar.measureType = '午餐前';
                        break;
                    case 'afterLunch':
                        $scope.bloodSugar.measureType = '午餐后';
                        break;
                    case 'beforeDinner':
                        $scope.bloodSugar.measureType = '晚餐前';
                        break;
                    case 'afterDinner':
                        $scope.bloodSugar.measureType = '晚餐后';
                        break;
                    case 'beforeSleep':
                        $scope.bloodSugar.measureType = '睡前';
                        break;
                }
            } else {
                if ($rootScope.measureBloodSugar.measureDate) {
                    $scope.bloodSugar.measureTime = $rootScope.measureBloodSugar.measureTime;
                    $scope.bloodSugar.measureDate = $rootScope.measureBloodSugar.measureDate;
                }
                $scope.bloodSugar.measureNum = $rootScope.measureBloodSugar.measureNum;
                $scope.bloodSugar.measureType = $rootScope.measureBloodSugar.measureType;
                $scope.bloodSugar.recorded = $rootScope.measureBloodSugar.recorded;
            }

            /*默认时间显示*/
            $scope.bloodSugar.nowDate = $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
            $scope.bloodSugar.nowTime = $filter('date')(new Date().getTime(), 'HH:mm');


        }]);
