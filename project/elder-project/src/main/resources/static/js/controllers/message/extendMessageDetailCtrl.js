angular.module('controllers',[]).controller('extendMessageDetailCtrl',
    ['$scope','$interval','$rootScope','$stateParams','GetExtendMessageDetail','UpdateExtendMessageStatus',
        function ($scope,$interval,$rootScope,$stateParams,GetExtendMessageDetail,UpdateExtendMessageStatus) {

            $rootScope.pageTitle = '消息详情';

            $scope.$on('$ionicView.enter', function(){

                //获取消息详情
                GetExtendMessageDetail.save({extendMessageId:$stateParams.extendMessageId},function(data){
                    $scope.extendMessageDetail = data.responseData;
                })

                //更改消息状态
                UpdateExtendMessageStatus.save({extendMessageId:$stateParams.extendMessageId},function(){

                })

            });

}])