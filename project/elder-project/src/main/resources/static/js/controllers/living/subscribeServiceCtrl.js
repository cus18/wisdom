angular.module('controllers',[]).controller('subscribeServiceCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global',
        'ElderUtil','GetUserInfo','CommitOrder',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,
                  ElderUtil,GetUserInfo,CommitOrder) {

            $scope.subscribeInfo = {
                livingservice_id:$stateParams.livingServiceId
            };

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

            /*自理能力*/
            $scope.careList = [
                {'id':'info1','value':'1','name':'自理'},
                {'id':'info2','value':'2','name':'部分失能'},
                {'id':'info3','value':'3','name':'失能一级'},
                {'id':'info4','value':'4','name':'失能二级'},
                {'id':'info5','value':'5','name':'完全失能'}
            ];

            // $scope.repeatSelection = function($event,value){
            //     var checkbox = $event.target;
            //     var checked = checkbox.checked;
            //     if(checked){
            //         $scope.subscribeInfo.care.push(value);
            //     }else{
            //         var idx = $scope.subscribeInfo.care.indexOf(value);
            //         $scope.subscribeInfo.care.splice(idx,1);
            //     }
            // };

            GetUserInfo.save(function(data){
                ElderUtil.checkResponseData(data,'subscribeService/'+$stateParams.livingServiceId);
            })

            $scope.subscribeConfirm = function(valid){
                // $scope.subscribeInfo.care=$scope.subscribeInfo.care.join(';');
               if(valid){
                   CommitOrder.save($scope.subscribeInfo,function(data){
                       if(data.result == Global.SUCCESS){
                           $state.go('subscribeServiceSuccess',{livingServiceId:$stateParams.livingServiceId})
                       }
                       else
                       {
                           console.log(data.errorInfo);
                       }

                   })
               }
               else
               {
                   alert('请完善联系信息内容')
               }


            }

        }])