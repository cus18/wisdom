angular.module('controllers',[]).controller('livingIndexCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global',
        'ElderUtil','GetUserInfo','GetCommunityBannerList','GetLivingOfficeList',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,
                  ElderUtil,GetUserInfo,GetCommunityBannerList,GetLivingOfficeList) {


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

            GetUserInfo.save(function(data){
                ElderUtil.checkResponseData(data,'livingIndex');
                GetCommunityBannerList.save(function(data){
                    ElderUtil.checkResponseData(data,'livingIndex');
                    $scope.param.bannerList = data.responseData;
                });
            })

            GetLivingOfficeList.save({},function(data){
                if(data.result == Global.SUCCESS){
                    $scope.officeList = data.responseData;
                }
                else
                {
                    console.log(data.errorInfo);
                }
            })

            $scope.reserveService = function(beadHouseId){
                $state.go('beadHouseDetail',{beadHouseId:beadHouseId});
            }


        }])