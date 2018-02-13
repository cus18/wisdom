angular.module('controllers',[]).controller('activityListCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','GetActivityList','ElderUtil',
        function ($scope,$interval,$rootScope,$stateParams,$state,GetActivityList,ElderUtil) {

            $rootScope.pageTitle = '更多活动';

            $scope.param = {
                page:{
                    pageNo:1,
                    orderType:1,
                    requestData:"0"
                }
            }

            if($rootScope.rootElderId!=undefined)
            {
                $scope.elderId = $rootScope.rootElderId;
                $scope.elderName = $rootScope.rootElderName;
                $scope.elderImg = $rootScope.rootElderImg;
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
                    $scope.elderImg = $rootScope.rootElderImg;
                }
                else
                {
                    $scope.elderId = "0000";
                }
            }

            $scope.$on('$ionicView.enter', function(){
                GetActivityList.save($scope.param.page,
                    function(data){
                    ElderUtil.checkResponseData(data);
                    angular.forEach(data.responseData,function(data){
                        if(data.activityStatus == 'end'){
                            data.activityStatus = '已结束';
                        }else if(data.activityStatus == 'waiting'){
                            data.activityStatus = '未开始';
                        }else if(data.activityStatus == 'ongoing') {
                            data.activityStatus = '进行中';
                        }
                    })
                    $scope.activityList = data.responseData;
                })
            });

            $scope.doRefresh = function(){
                $scope.param.page.pageNo = $scope.param.page.pageNo+1;
                GetActivityList.save($scope.param.page,
                    function(data){
                        ElderUtil.checkResponseData(data,'activityList');
                        angular.forEach(data.responseData,function(data){
                            if(data.activityStatus == 'end'){
                                data.activityStatus = '已结束';
                            }else if(data.activityStatus == 'waiting'){
                                data.activityStatus = '未开始';
                            }else if(data.activityStatus == 'ongoing') {
                                data.activityStatus = '进行中';
                            }
                        })
                        $scope.activityList = data.responseData;
                        $scope.$broadcast('scroll.refreshComplete');
                    })
            }

            $scope.chooseActivity = function(type){
                if(type=="comm")
                {
                    $scope.param.page.requestData = "0";
                }
                else if(type=="hot")
                {
                    $scope.param.page.requestData = "2";
                }
                GetActivityList.save($scope.param.page, function(data){
                    ElderUtil.checkResponseData(data,'activityList');
                    angular.forEach(data.responseData,function(data){
                        if(data.activityStatus == 'end'){
                            data.activityStatus = '已结束';
                        }else if(data.activityStatus == 'waiting'){
                            data.activityStatus = '未开始';
                        }else if(data.activityStatus == 'ongoing') {
                            data.activityStatus = '进行中';
                        }
                    })
                    $scope.activityList = data.responseData;
                })
            }

        }])