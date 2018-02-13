define(['appElder','jquery'], function (app,$) {
    app
        .directive('elderHead', ['$rootScope','$state',
            function ($rootScope,$state) {
                return {
                    restrict: 'EAC',
                    replace: true,
                    template: '<ion-header-bar align-title=\"center\" class=\"bar-energized\">' +
                            '<div class=\"buttons\"><button class=\"button\" ng-click=\"returnRootNative()\">' +
                            '&lt;返回</button></div><h1 class=\"title\">{{elderName}}</h1><div class=\"buttons\">' +
                            '<button class=\"button\" ng-click=\"enterService()\">服务&gt;</button></div></ion-header-bar>',
                    link: function(scope,ele,attrs) {

                        scope.return = function(){
                            history.go(-1);
                        }

                        scope.enterService = function(){
                            window.location.href = "elder#/healthServiceList";
                        }


                    }
                }
        }])
        .directive('pageLoading', [function(){
            return {
                restrict: 'EAC',
                replace: true,
                template: '<span class="spinner-loader" ' +
                'style="position:absolute;top:60%;right:45%;z-index:999" ng-if="loadingStatus">Loading&#8230;</span>',
                link: function(scope,ele,attrs) {}
            }
        }])
        .directive('gobackMenu', [function(){
            return {
                restrict: 'EAC',
                replace: true,
                template: '<div class="goback_btn">'
                +'<img src="images/laoyou_icon01.png" alt="" ng-click="changeMenu()">'
                +'<ul class="list" ng-if="tabMenu">'
                +'<li class="item" style="border-top:none;"><a ui-sref="livingServiceList({type:\'short\'})">居家服务</a></li>'
                +'<li class="item"><a ui-sref="communityIndex">社区活动</a></li>'
                +'<li class="item"><a ui-sref="detectionDiagnose({firstMenu:\'detection\',secondMenu:\'bloodSugarCurve\'})">健康数据</a></li>'
                +'<li class="item"><a ui-sref="myselfCenter">个人中心</a></li>'
                +'</ul>'
                +'</div>',
                link: function(scope,ele,attrs) {
                    scope.tabMenu = false;
                    scope.changeMenu = function(){
                        scope.tabMenu = !scope.tabMenu;
                    }
                }
            }
        }])
})
