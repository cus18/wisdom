angular.module('controllers',[]).controller('communityIndexCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','GetCommunityBannerList',
        'ElderUtil','GetActivityListByFirstPage','GetOnlineCourseList',
        function ($scope,$interval,$rootScope,$stateParams,$state,GetCommunityBannerList,
                  ElderUtil,GetActivityListByFirstPage,GetOnlineCourseList) {

            $scope.param = {
                bannerList : '',
                activityList : ''
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

            GetCommunityBannerList.save(function(data){
                ElderUtil.checkResponseData(data,'communityIndex');
                $scope.param.bannerList = data.responseData;
            });

            GetActivityListByFirstPage.save(function(data){
                ElderUtil.checkResponseData(data,'communityIndex');
                $scope.param.activityList = data.responseData;
            })

            GetOnlineCourseList.save({pageNo:1,pageSize:5},function(data){
                ElderUtil.checkResponseData(data,'communityIndex');
                $scope.param.onlineCourseList = data.responseData;
                console.log($scope.param.onlineCourseList);
            })

        }])