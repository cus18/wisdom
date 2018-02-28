angular.module('controllers',[]).controller('courseDetailCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','GetLiveBroadCastDetail','openidUtil',
        'GetOnlineCourseDetail','GetOnlineCourseDiscuss','$sce','CreateOnlineCourseDiscuss','$location','$anchorScroll',
        function ($scope,$interval,$rootScope,$stateParams,$state,GetLiveBroadCastDetail,openidUtil,
                  GetOnlineCourseDetail,GetOnlineCourseDiscuss,$sce,CreateOnlineCourseDiscuss,$location,$anchorScroll) {

            $rootScope.pageTitle = '课堂详情';

            // $rootScope.openid = 'oRnVIxOypU0LiuavDpTl_xe10i7Y';
            openidUtil.checkResponseData();

            $scope.param = {
                courseId:$stateParams.courseId,
                // startCourseFlag : false,
                operation : "introduce",
                discussPage:{
                    pageNo:1,
                    pageSize:2,
                    orderType:1
                },
                submitDiscussContent : "",
                attendDiscuss:false,
                discussInfinite:true

            }

            $scope.introduce = function(){
                $scope.param.operation = "introduce";

            }

            $scope.discuss = function(){
                $scope.param.operation = "discuss";
                $scope.param.discussPage.pageSize = 10;
                GetOnlineCourseDiscuss.save($scope.param.discussPage,function(data){
                    $scope.onlineCourseDiscussData  = data.responseData;
                })
            }

            GetOnlineCourseDetail.save({onlineCourseId:$scope.param.courseId},function(data){
                $scope.param.onlineCourseDetail = data.responseData;
                $scope.param.discussPage.requestData = $scope.param.onlineCourseDetail.onlineCourseId;
                $scope.param.playIndex = 0;
                $scope.param.playURL = angular.copy($sce.trustAsResourceUrl($scope.param.onlineCourseDetail.onLineCourseDataDTOList[0].onlineCourseURL));


            })

            $scope.doRefresh = function(){
                $scope.param.discussPage.pageSize = 10;
                $scope.param.discussInfinite = true;
                GetOnlineCourseDiscuss.save($scope.param.discussPage,function(data){
                    $scope.onlineCourseDiscussData  = data.responseData;
                    $scope.$broadcast('scroll.refreshComplete');
                })
            }

            $scope.loadMoreDiscuss = function(){
                $scope.param.discussPage.pageSize = $scope.param.discussPage.pageSize + 5;
                GetOnlineCourseDiscuss.save($scope.param.discussPage,function(data){
                    $scope.onlineCourseDiscussData  = data.responseData;
                    if(data.responseData.length < $scope.param.discussPage.pageSize){
                        $scope.param.discussInfinite = false;
                    }
                })
            }

            $scope.playCourse = function(newIndex){
                $scope.param.playIndex = newIndex;
                angular.forEach($scope.param.onlineCourseDetail.onLineCourseDataDTOList,
                    function(value,index,array){
                    if(newIndex==index)
                    {
                        $scope.param.playURL = angular.copy($sce.trustAsResourceUrl($scope.param.onlineCourseDetail.onLineCourseDataDTOList[index].onlineCourseURL));
                    }
                })

            }

            $scope.attendDiscuss = function(){
                $scope.param.attendDiscuss = true;
                $('.discussContainer textarea').focus();
            }

            $scope.cancelDiscuss = function(){
                $scope.param.discussContent="";
                $scope.param.attendDiscuss = false;
            }

            $scope.submitDiscuss = function(){
                if($scope.param.submitDiscussContent=="")
                {
                    alert("请输入评论内容再提交");
                }
                else {
                    CreateOnlineCourseDiscuss.save({
                        onlineCourseId: $scope.param.onlineCourseDetail.onlineCourseId,
                        onlineCourseDiscussContent: $scope.param.submitDiscussContent,
                        openId: $rootScope.openid
                    }, function (data) {
                        $scope.onlineCourseDiscussData.onlineCourseDiscussDTOList = data.responseData;
                        $scope.param.submitDiscussContent = "";
                        GetOnlineCourseDiscuss.save($scope.param.discussPage, function (data) {
                            $scope.onlineCourseDiscussData = data.responseData;
                        })
                    })
                    $scope.param.attendDiscuss = false;
                }
            }

            $scope.return = function(){
                connectWebViewJavascriptBridge(function() {
                    window.WebViewJavascriptBridge.callHandler('returnNative','',function(responseData) {});});
            }

        }])