angular.module('controllers',[]).controller('myClassCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetMyOnlineCourseList',
        function ($scope,$rootScope,$stateParams,$state,GetMyOnlineCourseList) {

            $rootScope.pageTitle = '我的课程';

            $scope.param = {
                myCourseList:[]
            }


            GetMyOnlineCourseList.save({"pageNo":1,"pageSize":1,"requestData":"page,learn"},function(data){

                $scope.param.myCourseList = data.responseData;
                console.log($scope.param.myCourseList);
            })




        }])