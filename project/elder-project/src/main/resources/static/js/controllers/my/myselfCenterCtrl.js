angular.module('controllers',[]).controller('myselfCenterCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetLivingServiceOrderStatus','openidUtil','Global',
        function ($scope,$rootScope,$stateParams,$state,GetLivingServiceOrderStatus,openidUtil,Global) {

            $rootScope.pageTitle = '个人中心';

            openidUtil.checkResponseData();
            // $rootScope.openid = 'o1KHB1Sq5Okyu737zWGTQEHqmeJA';

            GetLivingServiceOrderStatus.get({openID:$rootScope.openid,status:''},function(data){
                if(data.result == Global.SUCCESS){
                    $scope.inReviewSize = data.responseData.length;
                }
            })

            GetLivingServiceOrderStatus.get({openID:$rootScope.openid,status:'1'},function(data){
                if(data.result == Global.SUCCESS){
                    $scope.inServiceSize = data.responseData.length;
                }
            })

            GetLivingServiceOrderStatus.get({openID:$rootScope.openid,status:'3'},function(data){
                if(data.result == Global.SUCCESS){
                    $scope.finishedSize = data.responseData.length;
                }
            })

            GetLivingServiceOrderStatus.get({openID:$rootScope.openid,status:'4'},function(data){
                if(data.result == Global.SUCCESS){
                    $scope.failedSize = data.responseData.length;
                }
            })


        }])