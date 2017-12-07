angular.module('controllers',[]).controller('myServiceCtrl',
    ['$scope','$rootScope','$stateParams','$state','ElderUtil','Global','GetLivingServiceOrderStatus',
        'SendMessage','DelLivingServiceOrder','GetUserInfo',
        function ($scope,$rootScope,$stateParams,$state,ElderUtil,Global,GetLivingServiceOrderStatus,
        SendMessage,DelLivingServiceOrder,GetUserInfo) {

            $scope.param = {
                tabValue : $stateParams.type
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
                ElderUtil.checkResponseData(data,'myService/'+$stateParams.type);
            })

            function tabChange(){
                if($scope.param.tabValue=='inReview')
                {
                    GetLivingServiceOrderStatus.get({status:'1'},function(data){
                        console.log(data)
                        if(data.result == Global.SUCCESS){

                        }
                        else
                        {
                            console.log(data.errorInfo);
                        }
                    })

                    //催审核
                    $scope.sendMessage = function(id){
                        sendMessage.get({livingServiceOrderID:id},function(data){
                            console.log(data)
                            if(data.result == Global.SUCCESS){

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

                }
                if($scope.param.tabValue=='finished')
                {


                }
                if($scope.param.tabValue=='failed')
                {

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
                    console.log(data)
                    if(data.result == Global.SUCCESS){

                    }
                    else
                    {
                        console.log(data.errorInfo);
                    }
                })
            }


        }])