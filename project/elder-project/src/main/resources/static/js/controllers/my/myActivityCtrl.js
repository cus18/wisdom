angular.module('controllers',[]).controller('myActivityCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetRelativeElderInfo','GetActivityList',
        function ($scope,$rootScope,$stateParams,$state,GetRelativeElderInfo,GetActivityList) {

            $rootScope.pageTitle = '我的活动';
            $scope.param = {
                recentPublishActive:[],
                recentAttendActive:[],
                myCourseList:[]
            }

            GetActivityList.save({pageNo:5,requestData:'0'},function(data){
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
            GetActivityList.save({pageNo:5,requestData:'1'},function(data){
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