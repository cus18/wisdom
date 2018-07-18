/**
 * Created by 郑强丽 on 2018/7/18.
 */
angular.module('controllers',['ui.calendar','ui.bootstrap']).controller('healthDemoCtrl',
    ['$scope','$rootScope','$stateParams','$state','$filter',
        function ($scope,$rootScope,$stateParams,$state,$filter) {

            $rootScope.pageTitle = '健康数据';
            $scope.menu = 'xueya';
            var xueya = Highcharts.chart('xueya', {
                chart: {
                    type: 'line'
                },
                title: {
                    text: '血压/心率'
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    categories: ['07-23', '07-24', '07-25', '07-26', '07-27', '07-28', '07-29', '07-30', '07-31']
                },
                yAxis: {
                    title: {
                        text: ''
                    }
                },
                plotOptions: {
                    line: {
                        dataLabels: {
                            // 开启数据标签
                            enabled: true
                        },
                        // 关闭鼠标跟踪，对应的提示框、点击事件会失效
                        enableMouseTracking: false
                    }
                },
                credits: {		//去除右下角highcharts标志
                    enabled: false
                },
                exporting: {	//去除右上角导出按钮
                    enabled: false
                },
                series: [{
                    name: '收缩压/高压',
                    data: [135, 123, 138, 106, 132, 94, 99, 121, 132]
                }, {
                    name: '舒张压/低压',
                    data: [67, 89, 83, 82, 63, 69, 74, 90, 69]
                }, {
                    name: '心率',
                    data: [70, 75, 79,87, 80, 86, 94, 90, 99]
                }]
            });
            $scope.changTab = function(menu){

                if(menu == 'xueya'){
                    $scope.menu = 'xueya';
                    var xueya = Highcharts.chart('xueya', {
                        chart: {
                            type: 'line'
                        },
                        title: {
                            text: '血压/心率'
                        },
                        subtitle: {
                            text: ''
                        },
                        xAxis: {
                            categories: ['07-23', '07-24', '07-25', '07-26', '07-27', '07-28', '07-29', '07-30', '07-31']
                        },
                        yAxis: {
                            title: {
                                text: ''
                            }
                        },
                        plotOptions: {
                            line: {
                                dataLabels: {
                                    // 开启数据标签
                                    enabled: true
                                },
                                // 关闭鼠标跟踪，对应的提示框、点击事件会失效
                                enableMouseTracking: false
                            }
                        },
                        credits: {		//去除右下角highcharts标志
                            enabled: false
                        },
                        exporting: {	//去除右上角导出按钮
                            enabled: false
                        },
                        series: [{
                            name: '收缩压/高压',
                            data: [135, 123, 138, 106, 132, 94, 99, 121, 132]
                        }, {
                            name: '舒张压/低压',
                            data: [67, 89, 83, 82, 63, 69, 74, 90, 69]
                        }, {
                            name: '心率',
                            data: [70, 75, 79,87, 80, 86, 94, 90, 99]
                        }]
                    });
                }else if(menu == 'xuetang'){
                    $scope.menu = 'xuetang';
                    var lingchenxuetang = new Highcharts.Chart('lingchenxuetang', {
                        chart: {
                            zoomType: 'x'
                        },
                        title: {
                            text: '凌晨血糖值'
                        },
                        xAxis: {
                            type: 'datetime',
                            dateTimeLabelFormats: {
                                millisecond: '%H:%M:%S.%L',
                                second: '%H:%M:%S',
                                minute: '%H:%M',
                                hour: '%H:%M',
                                day: '%m-%d',
                                week: '%m-%d',
                                month: '%Y-%m',
                                year: '%Y'
                            }
                        },
                        tooltip: {
                            dateTimeLabelFormats: {
                                millisecond: '%H:%M:%S.%L',
                                second: '%H:%M:%S',
                                minute: '%H:%M',
                                hour: '%H:%M',
                                day: '%Y-%m-%d',
                                week: '%m-%d',
                                month: '%Y-%m',
                                year: '%Y'
                            }
                        },
                        yAxis: {
                            title: {
                                text: ''
                            }
                        },
                        legend: {
                            floating:true,
                            enabled:false
                        },
                        plotOptions: {
                            area: {
                                fillColor: {
                                    linearGradient: {
                                        x1: 0,
                                        y1: 0,
                                        x2: 0,
                                        y2: 1
                                    },
                                    stops: [
                                        [0, Highcharts.getOptions().colors[0]],
                                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                                    ]
                                },
                                marker: {
                                    radius: 2
                                },
                                lineWidth: 1,
                                states: {
                                    hover: {
                                        lineWidth: 1
                                    }
                                },
                                threshold: null
                            },
                            line:{
                                dataLabels:{
                                    enabled:true
                                }
                            }
                        },
                        credits: {		//去除右下角highcharts标志
                            enabled: false
                        },
                        exporting: {	//去除右上角导出按钮
                            enabled: false
                        },
                        series: [{
                            type: 'area',
                            name: '血糖',
                            data: [
                                [1532275200000, 5.9],
                                [1532361600000, 4],
                                [1532448000000,5.4],
                                [1532534400000,7],
                                [1532620800000,6.3],
                                [1532707200000,6.8],
                                [1532793600000,5.2],
                                [1532880000000,6],
                                [1532966400000,5]
                            ]
                        }]
                    });
                    var kongfuxuetang = new Highcharts.Chart('kongfuxuetang', {
                        chart: {
                            zoomType: 'x'
                        },
                        title: {
                            text: '空腹血糖值'
                        },
                        xAxis: {
                            type: 'datetime',
                            dateTimeLabelFormats: {
                                millisecond: '%H:%M:%S.%L',
                                second: '%H:%M:%S',
                                minute: '%H:%M',
                                hour: '%H:%M',
                                day: '%m-%d',
                                week: '%m-%d',
                                month: '%Y-%m',
                                year: '%Y'
                            }
                        },
                        tooltip: {
                            dateTimeLabelFormats: {
                                millisecond: '%H:%M:%S.%L',
                                second: '%H:%M:%S',
                                minute: '%H:%M',
                                hour: '%H:%M',
                                day: '%Y-%m-%d',
                                week: '%m-%d',
                                month: '%Y-%m',
                                year: '%Y'
                            }
                        },
                        yAxis: {
                            title: {
                                text: ''
                            }
                        },
                        legend: {
                            floating:true,
                            enabled:false
                        },
                        plotOptions: {
                            area: {
                                fillColor: {
                                    linearGradient: {
                                        x1: 0,
                                        y1: 0,
                                        x2: 0,
                                        y2: 1
                                    },
                                    stops: [
                                        [0, Highcharts.getOptions().colors[0]],
                                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                                    ]
                                },
                                marker: {
                                    radius: 2
                                },
                                lineWidth: 1,
                                states: {
                                    hover: {
                                        lineWidth: 1
                                    }
                                },
                                threshold: null
                            },
                            line:{
                                dataLabels:{
                                    enabled:true
                                }
                            }
                        },
                        credits: {		//去除右下角highcharts标志
                            enabled: false
                        },
                        exporting: {	//去除右上角导出按钮
                            enabled: false
                        },
                        series: [{
                            type: 'area',
                            name: '血糖',
                            data: [
                                [1532275200000, 4.3],
                                [1532361600000, 4],
                                [1532448000000,3.4],
                                [1532534400000,3.9],
                                [1532620800000,2.3],
                                [1532707200000,2.8],
                                [1532793600000,2.2],
                                [1532880000000,3],
                                [1532966400000,3.8]
                            ]
                        }]
                    });
                    var zaocanhou = new Highcharts.Chart('zaocanhou', {
                        chart: {
                            zoomType: 'x'
                        },
                        title: {
                            text: '早餐后血糖值'
                        },
                        xAxis: {
                            type: 'datetime',
                            dateTimeLabelFormats: {
                                millisecond: '%H:%M:%S.%L',
                                second: '%H:%M:%S',
                                minute: '%H:%M',
                                hour: '%H:%M',
                                day: '%m-%d',
                                week: '%m-%d',
                                month: '%Y-%m',
                                year: '%Y'
                            }
                        },
                        tooltip: {
                            dateTimeLabelFormats: {
                                millisecond: '%H:%M:%S.%L',
                                second: '%H:%M:%S',
                                minute: '%H:%M',
                                hour: '%H:%M',
                                day: '%Y-%m-%d',
                                week: '%m-%d',
                                month: '%Y-%m',
                                year: '%Y'
                            }
                        },
                        yAxis: {
                            title: {
                                text: ''
                            }
                        },
                        legend: {
                            floating:true,
                            enabled:false
                        },
                        plotOptions: {
                            area: {
                                fillColor: {
                                    linearGradient: {
                                        x1: 0,
                                        y1: 0,
                                        x2: 0,
                                        y2: 1
                                    },
                                    stops: [
                                        [0, Highcharts.getOptions().colors[0]],
                                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                                    ]
                                },
                                marker: {
                                    radius: 2
                                },
                                lineWidth: 1,
                                states: {
                                    hover: {
                                        lineWidth: 1
                                    }
                                },
                                threshold: null
                            },
                            line:{
                                dataLabels:{
                                    enabled:true
                                }
                            }
                        },
                        credits: {		//去除右下角highcharts标志
                            enabled: false
                        },
                        exporting: {	//去除右上角导出按钮
                            enabled: false
                        },
                        series: [{
                            type: 'area',
                            name: '血糖',
                            data: [
                                [1532275200000, 8.9],
                                [1532361600000, 8.4],
                                [1532448000000,7.4],
                                [1532534400000,7.7],
                                [1532620800000,7.3],
                                [1532707200000,7.8],
                                [1532793600000,7.2],
                                [1532880000000,7.6],
                                [1532966400000,7.5]
                            ]
                        }]
                    });
                    var wucanqian = new Highcharts.Chart('wucanqian', {
                        chart: {
                            zoomType: 'x'
                        },
                        title: {
                            text: '午餐前血糖值'
                        },
                        xAxis: {
                            type: 'datetime',
                            dateTimeLabelFormats: {
                                millisecond: '%H:%M:%S.%L',
                                second: '%H:%M:%S',
                                minute: '%H:%M',
                                hour: '%H:%M',
                                day: '%m-%d',
                                week: '%m-%d',
                                month: '%Y-%m',
                                year: '%Y'
                            }
                        },
                        tooltip: {
                            dateTimeLabelFormats: {
                                millisecond: '%H:%M:%S.%L',
                                second: '%H:%M:%S',
                                minute: '%H:%M',
                                hour: '%H:%M',
                                day: '%Y-%m-%d',
                                week: '%m-%d',
                                month: '%Y-%m',
                                year: '%Y'
                            }
                        },
                        yAxis: {
                            title: {
                                text: ''
                            }
                        },
                        legend: {
                            floating:true,
                            enabled:false
                        },
                        plotOptions: {
                            area: {
                                fillColor: {
                                    linearGradient: {
                                        x1: 0,
                                        y1: 0,
                                        x2: 0,
                                        y2: 1
                                    },
                                    stops: [
                                        [0, Highcharts.getOptions().colors[0]],
                                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                                    ]
                                },
                                marker: {
                                    radius: 2
                                },
                                lineWidth: 1,
                                states: {
                                    hover: {
                                        lineWidth: 1
                                    }
                                },
                                threshold: null
                            },
                            line:{
                                dataLabels:{
                                    enabled:true
                                }
                            }
                        },
                        credits: {		//去除右下角highcharts标志
                            enabled: false
                        },
                        exporting: {	//去除右上角导出按钮
                            enabled: false
                        },
                        series: [{
                            type: 'area',
                            name: '血糖',
                            data: [
                                [1532275200000, 6.9],
                                [1532361600000, 7.4],
                                [1532448000000,7.4],
                                [1532534400000,6.7],
                                [1532620800000,6.3],
                                [1532707200000,5.8],
                                [1532793600000,5.2],
                                [1532880000000,6.6],
                                [1532966400000,6.5]
                            ]
                        }]
                    });
                    var wucanhou = new Highcharts.Chart('wucanhou', {
                        chart: {
                            zoomType: 'x'
                        },
                        title: {
                            text: '午餐后血糖值'
                        },
                        xAxis: {
                            type: 'datetime',
                            dateTimeLabelFormats: {
                                millisecond: '%H:%M:%S.%L',
                                second: '%H:%M:%S',
                                minute: '%H:%M',
                                hour: '%H:%M',
                                day: '%m-%d',
                                week: '%m-%d',
                                month: '%Y-%m',
                                year: '%Y'
                            }
                        },
                        tooltip: {
                            dateTimeLabelFormats: {
                                millisecond: '%H:%M:%S.%L',
                                second: '%H:%M:%S',
                                minute: '%H:%M',
                                hour: '%H:%M',
                                day: '%Y-%m-%d',
                                week: '%m-%d',
                                month: '%Y-%m',
                                year: '%Y'
                            }
                        },
                        yAxis: {
                            title: {
                                text: ''
                            }
                        },
                        legend: {
                            floating:true,
                            enabled:false
                        },
                        plotOptions: {
                            area: {
                                fillColor: {
                                    linearGradient: {
                                        x1: 0,
                                        y1: 0,
                                        x2: 0,
                                        y2: 1
                                    },
                                    stops: [
                                        [0, Highcharts.getOptions().colors[0]],
                                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                                    ]
                                },
                                marker: {
                                    radius: 2
                                },
                                lineWidth: 1,
                                states: {
                                    hover: {
                                        lineWidth: 1
                                    }
                                },
                                threshold: null
                            },
                            line:{
                                dataLabels:{
                                    enabled:true
                                }
                            }
                        },
                        credits: {		//去除右下角highcharts标志
                            enabled: false
                        },
                        exporting: {	//去除右上角导出按钮
                            enabled: false
                        },
                        series: [{
                            type: 'area',
                            name: '血糖',
                            data: [
                                [1532275200000, 6.7],
                                [1532361600000, 7.3],
                                [1532448000000,7.4],
                                [1532534400000,7.7],
                                [1532620800000,7.3],
                                [1532707200000,7.8],
                                [1532793600000,8.2],
                                [1532880000000,8.6],
                                [1532966400000,8.5]
                            ]
                        }]
                    });
                    var wancanqian = new Highcharts.Chart('wancanqian', {
                        chart: {
                            zoomType: 'x'
                        },
                        title: {
                            text: '晚餐前血糖值'
                        },
                        xAxis: {
                            type: 'datetime',
                            dateTimeLabelFormats: {
                                millisecond: '%H:%M:%S.%L',
                                second: '%H:%M:%S',
                                minute: '%H:%M',
                                hour: '%H:%M',
                                day: '%m-%d',
                                week: '%m-%d',
                                month: '%Y-%m',
                                year: '%Y'
                            }
                        },
                        tooltip: {
                            dateTimeLabelFormats: {
                                millisecond: '%H:%M:%S.%L',
                                second: '%H:%M:%S',
                                minute: '%H:%M',
                                hour: '%H:%M',
                                day: '%Y-%m-%d',
                                week: '%m-%d',
                                month: '%Y-%m',
                                year: '%Y'
                            }
                        },
                        yAxis: {
                            title: {
                                text: ''
                            }
                        },
                        legend: {
                            floating:true,
                            enabled:false
                        },
                        plotOptions: {
                            area: {
                                fillColor: {
                                    linearGradient: {
                                        x1: 0,
                                        y1: 0,
                                        x2: 0,
                                        y2: 1
                                    },
                                    stops: [
                                        [0, Highcharts.getOptions().colors[0]],
                                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                                    ]
                                },
                                marker: {
                                    radius: 2
                                },
                                lineWidth: 1,
                                states: {
                                    hover: {
                                        lineWidth: 1
                                    }
                                },
                                threshold: null
                            },
                            line:{
                                dataLabels:{
                                    enabled:true
                                }
                            }
                        },
                        credits: {		//去除右下角highcharts标志
                            enabled: false
                        },
                        exporting: {	//去除右上角导出按钮
                            enabled: false
                        },
                        series: [{
                            type: 'area',
                            name: '血糖',
                            data: [
                                [1532275200000, 4.6],
                                [1532361600000, 4],
                                [1532448000000,4.4],
                                [1532534400000,4.7],
                                [1532620800000,6.3],
                                [1532707200000,6.8],
                                [1532793600000,5.2],
                                [1532880000000,4.6],
                                [1532966400000,4.5]
                            ]
                        }]
                    });

                }else if(menu == 'tizhong'){
                    $scope.menu = 'tizhong';
                    var tizhong = Highcharts.chart('tizhong', {
                        chart: {
                            type: 'line'
                        },
                        title: {
                            text: '体重/kg'
                        },
                        subtitle: {
                            text: ''
                        },
                        xAxis: {
                            categories: ['07-23', '07-24', '07-25', '07-26', '07-27', '07-28', '07-29', '07-30', '07-31']
                        },
                        yAxis: {
                            title: {
                                text: ''
                            }
                        },
                        plotOptions: {
                            line: {
                                dataLabels: {
                                    // 开启数据标签
                                    enabled: true
                                },
                                // 关闭鼠标跟踪，对应的提示框、点击事件会失效
                                enableMouseTracking: false
                            }
                        },
                        credits: {		//去除右下角highcharts标志
                            enabled: false
                        },
                        exporting: {	//去除右上角导出按钮
                            enabled: false
                        },
                        series: [{
                            name: '体重',
                            data: [57, 57.8, 60.3, 60.1, 61, 61.3, 60.5, 61.8, 60]
                        }]
                    });
                }else if(menu == 'tiwen'){
                    $scope.menu = 'tiwen';
                    var tiwen = Highcharts.chart('tiwen', {
                        chart: {
                            type: 'line'
                        },
                        title: {
                            text: '体温'
                        },
                        subtitle: {
                            text: ''
                        },
                        xAxis: {
                            categories: ['07-23', '07-24', '07-25', '07-26', '07-27', '07-28', '07-29', '07-30', '07-31']
                        },
                        yAxis: {
                            title: {
                                text: ''
                            }
                        },
                        plotOptions: {
                            line: {
                                dataLabels: {
                                    // 开启数据标签
                                    enabled: true
                                },
                                // 关闭鼠标跟踪，对应的提示框、点击事件会失效
                                enableMouseTracking: false
                            }
                        },
                        credits: {		//去除右下角highcharts标志
                            enabled: false
                        },
                        exporting: {	//去除右上角导出按钮
                            enabled: false
                        },
                        series: [{
                            name: '体温/度',
                            data: [37.6, 37.8, 36.8, 36.5, 36.9, 36.3, 37, 36.4,37.3]
                        }]
                    });
                }
            }




        }])
