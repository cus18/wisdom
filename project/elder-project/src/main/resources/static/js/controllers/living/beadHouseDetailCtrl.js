angular.module('controllers',[]).controller('beadHouseDetailCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global',
        'ElderUtil','GetUserInfo','GetCommunityBannerList','GetLivingOfficeList','GetlivingServiceList',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,
                  ElderUtil,GetUserInfo,GetCommunityBannerList,GetLivingOfficeList,GetlivingServiceList) {

            $scope.param = {
                type : 'short'
            }

            GetUserInfo.save(function(data){
                ElderUtil.checkResponseData(data,'beadHouseDetail/'+$stateParams.beadHouseId);
            })


            GetLivingOfficeList.save({id:$stateParams.beadHouseId},function(data){
                if(data.result == Global.SUCCESS){
                    $scope.officeInfo = data.responseData[0];
                }
                else
                {
                    console.log(data.errorInfo);
                }
            })


            GetlivingServiceList.save({
                sys_office_id:$stateParams.beadHouseId,
                type:'short',
                lastNo:'0',
                nextNo:'50'
            },function(data){
                if(data.result == Global.SUCCESS){
                    $scope.shortResponse = data.responseData;
                }
                else
                {
                    console.log(data.errorInfo);
                }

            })

            GetlivingServiceList.save({
                sys_office_id:$stateParams.beadHouseId,
                type:'long',
                lastNo:'0',
                nextNo:'50'
            },function(data){
                if(data.result == Global.SUCCESS){
                    $scope.longResponse = data.responseData;
                }
                else
                {
                    console.log(data.errorInfo);
                }
            })



            $scope.chooseLivingService = function(type){
                if(type=='short')
                {
                    $scope.param.type = 'short';
                }
                else if(type=='long')
                {
                    $scope.param.type = 'long';
                }
            }


            $scope.livingServiceDetail = function(livingServiceId){
                $state.go("livingServiceDetail",{livingServiceId:livingServiceId});
            }


        }])