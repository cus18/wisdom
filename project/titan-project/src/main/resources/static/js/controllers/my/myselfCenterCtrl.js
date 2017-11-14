angular.module('controllers',[]).controller('myselfCenterCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetRelativeElderInfo','GetActivityList','GetMyOnlineCourseList','ElderUtil',
        function ($scope,$rootScope,$stateParams,$state,GetRelativeElderInfo,GetActivityList,GetMyOnlineCourseList,ElderUtil) {

            $scope.param = {
                tabValue : 'message',
                messageImg : "http://yhllaoyouactivity.oss-cn-beijing.aliyuncs.com/head/%E6%88%91%E7%9A%84%E6%B6%88%E6%81%AFicon%E6%A9%99@2x.png",
                activeImg : "http://yhllaoyouactivity.oss-cn-beijing.aliyuncs.com/head/%E6%88%91%E7%9A%84%E6%B4%BB%E5%8A%A8icon%E7%81%B0@2x.png",
                courseImg : "http://yhllaoyouactivity.oss-cn-beijing.aliyuncs.com/head/%E6%88%91%E7%9A%84%E8%AF%BE%E7%A8%8Bicon%E7%81%B0@2x.png",
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

            $scope.chooseTab = function(tabValue)
            {
                $scope.param.tabValue = tabValue;
                if($scope.param.tabValue=='message')
                {
                    $scope.param.messageImg = "http://yhllaoyouactivity.oss-cn-beijing.aliyuncs.com/head/%E6%88%91%E7%9A%84%E6%B6%88%E6%81%AFicon%E6%A9%99@2x.png";
                    $scope.param.activeImg = "http://yhllaoyouactivity.oss-cn-beijing.aliyuncs.com/head/%E6%88%91%E7%9A%84%E6%B4%BB%E5%8A%A8icon%E7%81%B0@2x.png";
                    $scope.param.courseImg = "http://yhllaoyouactivity.oss-cn-beijing.aliyuncs.com/head/%E6%88%91%E7%9A%84%E8%AF%BE%E7%A8%8Bicon%E7%81%B0@2x.png";
                }
                if($scope.param.tabValue=='active')
                {
                    $scope.param.messageImg = "http://yhllaoyouactivity.oss-cn-beijing.aliyuncs.com/head/%E6%88%91%E7%9A%84%E6%B6%88%E6%81%AFicon%E7%81%B0@2x.png";
                    $scope.param.activeImg = "http://yhllaoyouactivity.oss-cn-beijing.aliyuncs.com/head/%E6%88%91%E7%9A%84%E6%B4%BB%E5%8A%A8icon%E6%A9%99@2x.png";
                    $scope.param.courseImg = "http://yhllaoyouactivity.oss-cn-beijing.aliyuncs.com/head/%E6%88%91%E7%9A%84%E8%AF%BE%E7%A8%8Bicon%E7%81%B0@2x.png";

                    GetActivityList.save({pageNo:5,requestData:'0'},function(data){
                        ElderUtil.checkResponseData(data,'myselfCenter');
                        $scope.param.recentPublishActive = data.responseData;
                    })
                    GetActivityList.save({pageNo:5,requestData:'1'},function(data){
                        ElderUtil.checkResponseData(data,'myselfCenter');
                        $scope.param.recentAttendActive = data.responseData;
                    })
                }
                if($scope.param.tabValue=='course')
                {
                    $scope.param.messageImg = "http://yhllaoyouactivity.oss-cn-beijing.aliyuncs.com/head/%E6%88%91%E7%9A%84%E6%B6%88%E6%81%AFicon%E7%81%B0@2x.png";
                    $scope.param.activeImg = "http://yhllaoyouactivity.oss-cn-beijing.aliyuncs.com/head/%E6%88%91%E7%9A%84%E6%B4%BB%E5%8A%A8icon%E7%81%B0@2x.png";
                    $scope.param.courseImg = "http://yhllaoyouactivity.oss-cn-beijing.aliyuncs.com/head/%E6%88%91%E7%9A%84%E8%AF%BE%E7%A8%8Bicon%E6%A9%99@2x.png";

                    GetMyOnlineCourseList.save({"pageNo":1,"pageSize":1,"requestData":"page,learn"},function(data){
                        ElderUtil.checkResponseData(data,'myselfCenter');
                        $scope.param.myCourseList = data.responseData;
                        console.log($scope.param.myCourseList);
                    })
                }
            }


        }])