angular.module('controllers',[]).controller('myActivityCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetRelativeElderInfo','GetActivityList','ElderUtil',
        function ($scope,$rootScope,$stateParams,$state,GetRelativeElderInfo,GetActivityList,ElderUtil) {

            $rootScope.pageTitle = '我的活动';
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
                ElderUtil.checkResponseData(data,'myActivity');
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