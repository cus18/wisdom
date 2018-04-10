/**
 * Created by 郑强丽 on 2018/4/8.
 */
angular.module('controllers',[]).controller('indexCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','openidUtil','GetWeChatUserLocation',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,openidUtil,GetWeChatUserLocation) {



            $rootScope.pageTitle = '智慧养老';
            openidUtil.checkResponseData();
            // $rootScope.openid = 'ss';

            GetWeChatUserLocation.get({openid:$rootScope.openid},function(data){
                if(data.result == Global.SUCCESS){
                    $scope.location = data.responseData;
                }else{
                    $scope.location = '定位失败'
                }
            })







        }])