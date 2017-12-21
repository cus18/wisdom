angular.module('controllers',[]).controller('livingServiceListCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global',
        'ElderUtil','GetUserInfo','GetlivingServiceList',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,
                  ElderUtil,GetUserInfo,GetlivingServiceList) {

            $scope.param = {
                type:$stateParams.type,
                sort:''
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
                        nextNo:'50',
                        flag:$scope.param.sort
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
                        nextNo:'50',
                        flag:$scope.param.sort
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

            //筛选
            $scope.sortList = [
                {'id':'info1','value':'','name':'全部'},
                {'id':'info2','value':'1','name':'出行'},
                {'id':'info3','value':'2','name':'购物'},
                {'id':'info4','value':'3','name':'饮食'},
                {'id':'info5','value':'4','name':'环境卫生'},
                {'id':'info6','value':'5','name':'就医'},
                {'id':'info7','value':'6','name':'健康管理'},
                {'id':'info8','value':'7','name':'体能训练'},
                {'id':'info9','value':'8','name':'咨询'},
                {'id':'info10','value':'9','name':'个人清洁'}
            ];

            $scope.sortShow = function(){
                $('.filtrateArea').animate({'top':'0px'},'1000')
            }

            $scope.sortHide = function(){
                $('.filtrateArea').animate({'top':'100%'},'1000')
            }

            $scope.sortService = function(){
                serviceType();
                $scope.sortHide();
            }

        }])