angular.module('controllers',[]).controller('healthServicePackageTemplateListCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetHealthServicePackageTemplateList','ElderUtil',
        function ($scope,$rootScope,$stateParams,$state,GetHealthServicePackageTemplateList,ElderUtil) {

            $scope.elderId = $rootScope.elderId;
            $scope.elderName = $rootScope.elderName;

            $scope.loadingStatus = true;
            GetHealthServicePackageTemplateList.save({pageNo:"1", pageSize:"5",
                orderType:"1",orderBy:"0"},function(data){

                $scope.loadingStatus = false;

                ElderUtil.checkResponseData(data);
                $scope.healthServicePackageTemplateList = data.responseData;
            })

        }])
