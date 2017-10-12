/**
 * 建立angular.module
 */

define(['angular'], function (angular) {
    var app = angular.module('elderApp',['ngResource','ui.router','ngSanitize','ionic',
        'oc.lazyLoad','highcharts-ng','infinite-scroll','elderGlobal','ionic-datepicker'])
        .config(['$httpProvider',function($httpProvider) {

            if($httpProvider.defaults.headers.common.logintoken==null)
            {
                $httpProvider.defaults.headers.common = {'logintoken':window.localStorage.getItem("loginToken")};
            }
        }]);
    return app;
});