angular.module('controllers',[]).controller('healthIndexCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','GetRelativeElderInfo',
        'GetOnGoingHealthServicePackageList','ElderUtil','GetOnlineCourseList',
        function ($scope,$interval,$rootScope,$stateParams,$state,GetRelativeElderInfo,
                  GetOnGoingHealthServicePackageList,ElderUtil,GetOnlineCourseList) {

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
            
            GetRelativeElderInfo.save({},function(data){

                ElderUtil.checkResponseData(data,'healthIndex');

                $scope.relativeElderList = data.responseData;

                $scope.elderId = $scope.relativeElderList[0].elderID;
                $scope.chooseRelativeElder($scope.elderId);

            })


            $scope.chooseRelativeElder = function(elderId){
                angular.forEach($scope.relativeElderList,function(value,index,array){
                    if(value.elderID==elderId)
                    {
                        $scope.elderId = value.elderID;
                        $scope.elderName = value.elderName;
                        $scope.age = value.age;
                        if(value.gender == '1'){
                            $scope.gender = '男';
                        }
                        else if(value.gender == '2')
                        {
                            $scope.gender = '女';
                        }
                        else{
                            $scope.gender = value.gender;
                        }

                        //根据elderId获取用户的健康产品列表信息
                        GetOnGoingHealthServicePackageList.save({pageNo:"1", pageSize:"3", orderType:"1",orderBy:"0",
                            requestData:{elderId:$scope.elderId}}, function (data) {
                            ElderUtil.checkResponseData(data,'healthIndex');
                            $scope.healthServicePackageList = data.responseData;
                        })

                        //将用户信息放入$rootScope中
                        $rootScope.rootElderId = angular.copy($scope.elderId);
                        $rootScope.rootElderName = angular.copy($scope.elderName);
                    }
                })
            }

            //获取健康类的课程列表
            GetOnlineCourseList.save({pageNo:1, pageSize:5, orderType:1, requestData:"health"},function(data){
                $scope.onlineCourseList = data.responseData;
            })

}])