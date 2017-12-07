angular.module('controllers',[]).controller('livingServiceListCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global',
        'ElderUtil','GetUserInfo','GetlivingServiceList',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,
                  ElderUtil,GetUserInfo,GetlivingServiceList) {

            $scope.param = {
                type:$stateParams.type
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
                ElderUtil.checkResponseData(data,'livingServiceList/'+$stateParams.type);
                $scope.param.type = $stateParams.type;
            });

            function serviceType(){
                if($scope.param.type == 'short'){
                    GetlivingServiceList.save({
                        id:'',
                        type:'short',
                        lastNo:'0',
                        nextNo:'10'
                    },function(data){
                        if(data.result == Global.SUCCESS){
                            $scope.shortResponse = data.responseData;
                        }
                        else
                        {
                            console.log(data.errorInfo);
                        }

                    })
                }
                else if($scope.param.type == 'long')
                {
                    GetlivingServiceList.save({
                        id:'',
                        type:'long',
                        lastNo:'0',
                        nextNo:'10'
                    },function(data){
                        if(data.result == Global.SUCCESS){
                            $scope.longResponse = data.responseData;
                        }
                        else
                        {
                            console.log(data.errorInfo);
                        }
                    })
                }
            }
            serviceType();

            $scope.chooseLivingService = function(type){
                if(type=='short')
                {
                    $scope.param.type = 'short';

                }
                else if(type=='long')
                {
                    $scope.param.type = 'long';
                }
                serviceType();
            }



            $scope.livingServiceDetail = function(id){
                $state.go("livingServiceDetail",{livingServiceId:id});
            }


        }])