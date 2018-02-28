angular.module('controllers',[]).controller('bindPhoneCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','SendIdentifying',
        'BindLaoyouUser','openidUtil','GetLaoyouUserByOpenId','DeleteLaoyouUserByOpenId',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,SendIdentifying,
                  BindLaoyouUser,openidUtil,GetLaoyouUserByOpenId,DeleteLaoyouUserByOpenId) {


            // $rootScope.openid = 'oRnVIxOypU0LiuavDpTl_xe10i7Y';
            openidUtil.checkResponseData();

            $rootScope.pageTitle = '华录老友';
            $scope.phone = {
                codeText: '获取验证码',
                canClick:false
            };

            //判断是否绑定
            GetLaoyouUserByOpenId.get({openid:$rootScope.openid},function(data){
                if(data.result == Global.SUCCESS){
                    if(data.responseData == null){
                        $scope.state = 'didNotBind';
                    }else{
                        $scope.state = 'yetBind';
                    }
                }

            })

            //输入
            $scope.inputChange = function(){
                $scope.errorShow = false;
            }

            //绑定手机号
            $scope.bindPhone = function(){
                if($scope.phone.code && $scope.phone.number){
                    BindLaoyouUser.get({phone:$scope.phone.number,num:$scope.phone.code,openid:$rootScope.openid},function(data){
                        if(data.result == Global.SUCCESS){
                            if(data.responseData != null){
                                $state.go('bindPhoneSuccess');
                            }
                            else{
                                $scope.errorShow = true;
                                $scope.errorInfo = data.errorInfo;
                            }
                        }
                    })
                }
            }

            //重新绑定
            $scope.updateBind = function(){
                if($scope.phone.code && $scope.phone.number){
                    BindLaoyouUser.get({phone:$scope.phone.number,num:$scope.phone.code,openid:$rootScope.openid},function(data){
                        if(data.result == Global.SUCCESS){
                            if(data.responseData != null){
                                $state.go('bindPhoneSuccess');
                            }
                            else{
                                $scope.errorShow = true;
                                $scope.errorInfo = data.errorInfo;
                            }
                        }
                    })
                }
            }

            /*解除绑定*/
            $scope.unbindPhone = function(){
                DeleteLaoyouUserByOpenId.get({openid:$rootScope.openid},function(data){
                    if(data.result == Global.SUCCESS){
                        $state.go('unbindPhoneSuccess');
                    }
                })
            }

            /*更改手机号*/
            $scope.updatePhone = function(){
                $scope.state = 'yetBind_update';
            }


            /*
             *获取验证码
             */
            $scope.phone.getCode = function(){
                if($scope.phone.number){
                    SendIdentifying.save({phone:$scope.phone.number},function(data){

                    })
                    var second = 60;
                    var timer = $interval(function(){
                        if(second <=0){
                            $interval.cancel(timer);
                            second = 60;
                            $scope.phone.codeText = '重发验证码';
                            $scope.phone.canClick=false;
                        }else{
                            second--;
                            $scope.phone.codeText = second + '秒后重发';
                            $scope.phone.canClick=true;
                        }
                    },1000);
                }
            };


        }])