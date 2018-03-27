angular.module('controllers',[]).controller('unbindPhoneSuccessCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global) {


            $rootScope.pageTitle = '华录老友';
            $scope.goIndex = function(){
                $state.go('communityIndex')
            }




        }])