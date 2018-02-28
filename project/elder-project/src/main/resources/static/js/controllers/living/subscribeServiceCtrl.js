angular.module('controllers',[]).controller('subscribeServiceCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','$location','GetOpenID','GetUserInfo','CommitOrder','openidUtil','$ionicPopup','$timeout','GetlivingServiceList',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,$location,GetOpenID,GetUserInfo,CommitOrder,openidUtil,$ionicPopup,$timeout,GetlivingServiceList) {


            $rootScope.pageTitle = '预约服务';

            openidUtil.checkResponseData();
            // $rootScope.openid = 'oRnVIxOypU0LiuavDpTl_xe10i7Y';

            GetlivingServiceList.save({
                id:$stateParams.livingServiceId,
                type:'',
                lastNo:'0',
                nextNo:'10'
            },function(data){
                if(data.result == Global.SUCCESS){
                    $scope.response = data.responseData[0];
                    $scope.info = data.responseData[0].information;
                }
                else
                {
                    console.log(data.errorInfo);
                }

            })

            $scope.canClick = false;
            $scope.subscribeInfo = {
                livingservice_id:$stateParams.livingServiceId,
                openid:$rootScope.openid
            };

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

            /*自理能力*/
            $scope.careList = [
                { text: "自理", value: "1" },
                { text: "部分失能", value: "2" },
                { text: "失能一级", value: "3" },
                { text: "失能二级", value: "4" },
                { text: "完全失能", value: "5" }
            ];


            GetUserInfo.save(function(data){

            })

            $scope.subscribeConfirm = function(){
                $scope.submit = function(){
                    $scope.canClick = true;
                    CommitOrder.save($scope.subscribeInfo,function(data){
                        if(data.result == Global.SUCCESS){
                            $state.go('subscribeServiceSuccess',{livingServiceId:$stateParams.livingServiceId})
                        }
                        else
                        {
                            console.log(data.errorInfo);
                        }
                    })
                }
                if($scope.info.indexOf('1')>=0 && $scope.info.indexOf('2')>=0 && $scope.info.indexOf('3')>0 && $scope.info.indexOf('4')>=0)
                {
                    if($scope.subscribeInfo.care && $scope.subscribeInfo.name && $scope.subscribeInfo.phone && $scope.subscribeInfo.address && $scope.subscribeInfo.remarks)
                    {
                        $scope.submit();
                    }
                    else
                    {
                        var alertPopup = $ionicPopup.show({
                            title:'商家要求填写“自理能力”、“联系信息”、“服务地点”、“备注”'
                        });
                        $timeout(function() {
                            alertPopup.close();
                        }, 2000);
                    }
                }
                else if($scope.info.indexOf('1')>=0 && $scope.info.indexOf('2')>=0 && $scope.info.indexOf('3')>=0)
                {
                    if($scope.subscribeInfo.care && $scope.subscribeInfo.name && $scope.subscribeInfo.phone && $scope.subscribeInfo.address)
                    {
                        $scope.submit();
                    }
                    else
                    {
                        var alertPopup = $ionicPopup.show({
                            title:'商家要求填写“自理能力”、“联系信息”、“服务地点”'
                        });
                        $timeout(function() {
                            alertPopup.close();
                        }, 2000);
                    }
                }
                else if($scope.info.indexOf('1')>=0 && $scope.info.indexOf('2')>=0)
                {
                    if($scope.subscribeInfo.care && $scope.subscribeInfo.name && $scope.subscribeInfo.phone)
                    {
                        $scope.submit();
                    }
                    else
                    {
                        var alertPopup = $ionicPopup.show({
                            title:'商家要求填写“自理能力”、“联系信息”'
                        });
                        $timeout(function() {
                            alertPopup.close();
                        }, 2000);
                    }
                }
                else if($scope.info.indexOf('2')>=0)
                {
                    if($scope.subscribeInfo.name && $scope.subscribeInfo.phone)
                    {
                        $scope.submit();
                    }
                    else
                    {
                        var alertPopup = $ionicPopup.show({
                            title:'商家要求填写“联系信息”'
                        });
                        $timeout(function() {
                            alertPopup.close();
                        }, 2000);
                    }
                }



            }

        }])