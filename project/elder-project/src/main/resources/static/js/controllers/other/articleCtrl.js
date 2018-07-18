/**
 * Created by 郑强丽 on 2018/7/17.
 */
angular.module('controllers',[]).controller('articleCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state',
        function ($scope,$interval,$rootScope,$stateParams,$state) {



            $scope.id = $stateParams.id;
            if($scope.id == '1'){
                $rootScope.pageTitle = '好心情对老人有多重要';
            }
            else if($scope.id == '2'){
                $rootScope.pageTitle = '“将就”镶牙后患多';
            }
            else if($scope.id == '3'){
                $rootScope.pageTitle = '老人养生需要避开的四个误区';
            }
            else if($scope.id == '4'){
                $rootScope.pageTitle = '老年人腿脚无力怎么回事';
            }
            else if($scope.id == '5'){
                $rootScope.pageTitle = '手麻是大病的征兆';
            }
            else if($scope.id == '6'){
                $rootScope.pageTitle = '糖尿病人易陷入的几个误区';
            }
            else if($scope.id == '7'){
                $rootScope.pageTitle = '牙疼可能是心梗征兆';
            }
            else if($scope.id == '8'){
                $rootScope.pageTitle = '安度晚年离不开良好的心态';
            }
            else if($scope.id == '9'){
                $rootScope.pageTitle = '老年人冬季晨练六不宜';
            }
            else if($scope.id == '10'){
                $rootScope.pageTitle = '心衰的特殊表现应引起注意';
            }









        }])