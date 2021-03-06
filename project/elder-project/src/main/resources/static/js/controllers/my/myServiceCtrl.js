angular.module('controllers',[]).controller('myServiceCtrl',
    ['$scope','$rootScope','$stateParams','$state','ElderUtil','Global','GetLivingServiceOrderStatus','$timeout',
        'SendMessage','DelLivingServiceOrder','GetUserInfo','$location','GetOpenID','$ionicPopup','openidUtil',
        function ($scope,$rootScope,$stateParams,$state,ElderUtil,Global,GetLivingServiceOrderStatus,$timeout,
        SendMessage,DelLivingServiceOrder,GetUserInfo,$location,GetOpenID,$ionicPopup,openidUtil) {



            $rootScope.pageTitle = '我的服务';

            openidUtil.checkResponseData();
            // $rootScope.openid = 'o1KHB1Sq5Okyu737zWGTQEHqmeJA';

            $scope.param = {
                tabValue : $stateParams.type
            }

            function tabChange(){
                if($scope.param.tabValue=='inReview')
                {
                    GetLivingServiceOrderStatus.get({openID:$rootScope.openid,status:''},function(data){
                        if(data.result == Global.SUCCESS){
                            $scope.inReviewResponse = data.responseData;
                        }
                        else
                        {
                            console.log(data.errorInfo);
                        }
                    })

                    //催审核
                    $scope.sendMessage = function(id){
                        SendMessage.get({livingServiceOrderID:id},function(data){
                            if(data.result == Global.SUCCESS){
                                var alertPopup = $ionicPopup.show({
                                    title: data.responseData
                                });
                                $timeout(function() {
                                    alertPopup.close();
                                }, 1500);
                            }
                            else
                            {
                                console.log(data.errorInfo);
                            }
                        })
                    }

                }
                if($scope.param.tabValue=='inService')
                {
                    GetLivingServiceOrderStatus.get({openID:$rootScope.openid,status:'1'},function(data){
                        if(data.result == Global.SUCCESS){
                            $scope.inServiceResponse = data.responseData;
                        }
                        else
                        {
                            console.log(data.errorInfo);
                        }
                    })
                }
                if($scope.param.tabValue=='finished')
                {
                    GetLivingServiceOrderStatus.get({openID:$rootScope.openid,status:'3'},function(data){
                        if(data.result == Global.SUCCESS){
                            $scope.finishedResponse = data.responseData;
                        }
                        else
                        {
                            console.log(data.errorInfo);
                        }
                    })

                }
                if($scope.param.tabValue=='failed')
                {
                    GetLivingServiceOrderStatus.get({openID:$rootScope.openid,status:'4'},function(data){
                        if(data.result == Global.SUCCESS){
                            $scope.failedResponse = data.responseData;
                        }
                        else
                        {
                            console.log(data.errorInfo);
                        }
                    })
                }
            }
            tabChange();

            $scope.chooseTab = function(tabValue)
            {
                $scope.param.tabValue = tabValue;
                tabChange(); 
            }

            //删除服务订单
            $scope.delOrder = function(id){
                DelLivingServiceOrder.get({livingServiceOrderID:id},function(data){
                    if(data.result == Global.SUCCESS){
                        var alertPopup = $ionicPopup.show({
                            title:'删除成功'
                        });
                        $timeout(function() {
                            alertPopup.close();
                            tabChange();
                        }, 1500);
                    }
                    else
                    {
                        console.log(data.errorInfo);
                    }
                })
            }


        }])