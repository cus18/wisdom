angular.module('controllers',[]).controller('healthAssessmentTemplateListCtrl',
    ['$scope','$rootScope','$stateParams','$state','GetHealthArchiveHealthAssessmentTemplateList',
        function ($scope,$rootScope,$stateParams,$state,GetHealthArchiveHealthAssessmentTemplateList) {

            $scope.loadingStatus = true;

            $scope.elderId = $rootScope.elderId;
            $scope.elderName = $rootScope.elderName;

            GetHealthArchiveHealthAssessmentTemplateList.save({pageNo:"1", pageSize:"5",
                orderType:"1",orderBy:"0"},function(data){
                $scope.loadingStatus = false;
                $scope.healthAssessmentTemplateList = data.responseData;
            })

        }])
