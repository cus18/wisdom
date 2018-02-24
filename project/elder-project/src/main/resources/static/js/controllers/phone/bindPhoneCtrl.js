angular.module('controllers',[]).controller('bindPhoneCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global) {

            $rootScope.pageTitle = '华录老友';
            $scope.state = 'didNotBind';
            $scope.phone = {
                codeText: '获取验证码',
                canClick:false
            };

            $scope.bindPhone = function(){
                $state.go('bindPhoneSuccess');
            }
            $scope.updateBind = function(){
                $state.go('bindPhoneSuccess');
            }
            $scope.unbindPhone = function(){
                $state.go('unbindPhoneSuccess');
            }
            $scope.updatePhone = function(){
                $state.go('bindPhoneSuccess');
            }


            /*
             *获取验证码
             */
            $scope.phone.getCode = function(){

                    var second = 3;
                    $scope.phone.codeText = second + '秒后重发';
                    var timer = $interval(function(){
                        if(second <=0){
                            $interval.cancel(timer);
                            second = 3;
                            $scope.phone.codeText = '重发验证码';
                            $scope.phone.canClick=false;
                        }else{
                            second--;
                            $scope.phone.codeText = second + '秒后重发';
                            $scope.phone.canClick=true;
                        }
                    },1000);

            };


        }])