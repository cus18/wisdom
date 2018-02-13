angular.module('controllers',[]).controller('extendMessageDetailCtrl',
    ['$scope','$interval','$rootScope','$stateParams','GetExtendMessageDetail',
        function ($scope,$interval,$rootScope,$stateParams,GetExtendMessageDetail) {

    $rootScope.pageTitle = '消息详情';
    $scope.$on('$ionicView.enter', function(){
        GetExtendMessageDetail.save({extendMessageId:$stateParams.extendMessageId},
            function(data){
            $scope.extendMessageDetail = data.responseData;
        })
    });

}])