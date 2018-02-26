angular.module('controllers',[]).controller('feedbackCtrl',
    ['$scope','$rootScope','$stateParams','$state','Global','FeedBack','openidUtil','$timeout','$ionicPopup',
        function ($scope,$rootScope,$stateParams,$state,Global,FeedBack,openidUtil,$timeout,$ionicPopup) {

            $rootScope.pageTitle = '意见反馈';

            openidUtil.checkResponseData();
            $rootScope.openid = 'o1KHB1Sq5Okyu737zWGTQEHqmeJA';

            $scope.submit = function(){
                if($scope.content){
                    FeedBack.get({text:$scope.content,openid:$rootScope.openid},function(data){
                        if(data.result == Global.SUCCESS){
                            var alertPopup = $ionicPopup.show({
                                title:'提交成功，感谢您的反馈！'
                            });
                            $timeout(function() {
                                alertPopup.close();
                                $state.go('myselfCenter')
                                $scope.content = '';
                            }, 2000);
                        }
                    })
                }
            }

        }])