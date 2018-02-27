angular.module('controllers',[]).controller('myActivityCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetRelativeElderInfo','GetActivityList','openidUtil','$location',
        function ($scope,$rootScope,$stateParams,$state,GetRelativeElderInfo,GetActivityList,openidUtil,$location) {

            $rootScope.pageTitle = '我的活动';

            openidUtil.checkResponseData();
            $rootScope.openid = 'oRnVIxOypU0LiuavDpTl_xe10i7Y';

            $scope.param = {
                recentPublishActive:[],
                recentAttendActive:[],
                myCourseList:[]
            }

            GetActivityList.save({pageNo:5,requestData:'0',orderType:$rootScope.openid},function(data){
                angular.forEach(data.responseData,function(data){
                    if(data.activityStatus == 'end'){
                        data.activityStatus = '已结束';
                    }else if(data.activityStatus == 'waiting'){
                        data.activityStatus = '未开始';
                    }else if(data.activityStatus == 'ongoing') {
                        data.activityStatus = '进行中';
                    }
                })
                $scope.param.recentPublishActive = data.responseData;
            })
            GetActivityList.save({pageNo:5,requestData:'1',orderType:$rootScope.openid},function(data){
                angular.forEach(data.responseData,function(data){
                    if(data.activityStatus == 'end'){
                        data.activityStatus = '已结束';
                    }else if(data.activityStatus == 'waiting'){
                        data.activityStatus = '未开始';
                    }else if(data.activityStatus == 'ongoing') {
                        data.activityStatus = '进行中';
                    }
                })
                $scope.param.recentAttendActive = data.responseData;
            })



        }])