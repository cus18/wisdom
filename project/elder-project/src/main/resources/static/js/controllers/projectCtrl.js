/**
 * Created by 郑强丽 on 2018/4/9.
 */
angular.module('controllers',[]).controller('projectCtrl',
    ['$scope','$interval','$rootScope','$stateParams','$state','Global','openidUtil',
        function ($scope,$interval,$rootScope,$stateParams,$state,Global,openidUtil) {


            $rootScope.pageTitle = '巡访探视方案';

            //基础面积图
            $('#container1').highcharts({
                chart: {
                    type: 'area'
                },
                title: {
                    text: '年度医疗费用基础分析'
                },
                colors: ['#ff8e01', '#FE4600'],
                xAxis: {
                    allowDecimals: false,
                    labels: {
                        formatter: function () {
                            return this.value; // clean, unformatted number for year
                        }
                    }
                },
                yAxis: {
                    title: {
                        text: null
                    },
                    labels: {
                        formatter: function () {
                            return this.value / 1000 + 'k';
                        }
                    }
                },
                tooltip: {
                    pointFormat: '{series.name} 医疗费用 <b>{point.y:,.0f}元</b>'
                },
                credits: {		//去除右下角highcharts标志
                    enabled: false
                },
                exporting: {	//去除右上角导出按钮
                    enabled: false
                },
                plotOptions: {
                    area: {
                        pointStart: 60,
                        marker: {
                            enabled: false,
                            symbol: 'circle',
                            radius: 2,
                            states: {
                                hover: {
                                    enabled: true
                                }
                            }
                        }
                    }
                },
                series: [{
                    name: '男',
                    data: [2642,2493,2682,3083, 3275, 3649, 3057, 4618, 6444, 9822, 15468, 20434, 24126,27387, 29459, 31056, 31982, 32040, 31233, 29224, 27342, 26662,26956, 27912, 28999, 28965, 27826, 25579, 25722, 24826, 24605,38951]
                }, {
                    name: '女',
                    data: [2023,2155,2381,2957, 3015, 3156, 3652, 4417, 5962, 7385, 12689, 19563, 20425,24156, 23546, 27684, 28971, 18657, 28674, 28635, 24586, 21245,21578, 24568, 23248, 29587, 25684, 26151, 27546, 29542, 36148,39524]
                }]
            });

            //收入消费金字塔图
            var categories = ['60-70', '70-80', '80-90', '90-100','100 + '];
            $('#container2').highcharts({
                chart: {
                    type: 'bar'
                },
                title: {
                    text: '收入消费金字塔图'
                },
                colors: ['#ff8f00', '#23beae'],
                xAxis: [{
                    categories: categories,
                    reversed: false,
                    labels: {
                        step: 1
                    }
                }, { // mirror axis on right side
                    opposite: true,
                    reversed: false,
                    categories: categories,
                    linkedTo: 0,
                    labels: {
                        step: 1
                    }
                }],
                yAxis: {
                    title: {
                        text: null
                    },
                    labels: {
                        formatter: function () {
                            return (Math.abs(this.value) / 1000) + 'K';
                        }
                    },
                    min: -60000,
                    max: 60000
                },
                credits: {		//去除右下角highcharts标志
                    enabled: false
                },
                exporting: {	//去除右上角导出按钮
                    enabled: false
                },
                plotOptions: {
                    series: {
                        stacking: 'normal'
                    }
                },
                tooltip: {
                    formatter: function () {
                        return '<b>' + this.series.name + ',' + this.point.category + '年龄内</b><br/>' +
                            Highcharts.numberFormat(Math.abs(this.point.y), 0);
                    }
                },
                series: [{
                    name: '收入',
                    data: [-76412, -68594, -72143, -79583, -84729]
                }, {
                    name: '消费',
                    data: [21561, 35448, 51671, 52108, 55638]
                }]
            });

            //街道老人失能失智情况统计
            $('#container3').highcharts({
                chart: {
                    type: 'variwide'
                },
                title: {
                    text: '街道老人失能失智情况统计'
                },
                xAxis: {
                    type: 'category',
                    title: {
                        text: '* 柱子宽度与街道老人的总量成正比'
                    }
                },
                yAxis: {
                    title: {
                        text: null
                    }
                },
                legend: {
                    enabled: false
                },
                credits: { //去除右下角highcharts标志
                    enabled: false
                },
                exporting: { //去除右上角导出按钮
                    enabled: false
                },
                colors: ['#AA10C8', '#FA0CA2','#d0021b', '#ff402d','#ff8f00', '#ffbf09',
                    '#f8e71c', '#8ad934','#23beae', '#136efe','#002cfe', '#2e01c2'],
                series: [{
                    name: '失能失智老人',
                    data: [
                        ['西罗园街道', 556, 24711],
                        ['大红门街道', 622, 20733],
                        ['丰台街道', 600, 26681],
                        ['马家堡街道', 290, 14485],
                        ['新村街道', 520, 24746],
                        ['东高地街道', 299, 15719],
                        ['卢沟桥街道', 634, 35234],
                        ['云岗街道', 271, 13597],
                        ['方庄街道', 372, 19607],
                        ['长辛店街道', 413, 23664],
                        ['右安门街道', 227, 11389],
                        ['东铁匠营', 318, 19907]
                    ],
                    dataLabels: {
                        enabled: true,
                        format: '{point.y:.0f}'
                    },
                    tooltip: {
                        pointFormat: '失能失智老人： <b>{point.y}人</b><br>' +
                        '街道老人总数: <b>{point.z} 人</b><br>'
                    },
                    colorByPoint: true
                }]
            });

            //慢性病热力图
            $('#container4').highcharts({
                chart: {
                    type: 'heatmap',
                    marginTop: 40,
                    marginBottom: 80
                },
                title: {
                    text: '慢性病热力图'
                },
                xAxis: {
                    categories: ['0','60-64', '65-69', '70-74', '75-79', '80-84', '85-89', '90-94', '95-100', '105+']
                },
                yAxis: {
                    categories: ['0', '0k-3k', '3k-6k', '6k-9k', '9k-12k', '12k-15k'],
                    title: null
                },
                colorAxis: {
                    min: 0,
                    minColor: '#f8e71c',
                    maxColor: '#d0021b'
                },
                colors: ['#ccc'],
                credits: { //去除右下角highcharts标志
                    enabled: false
                },
                exporting: { //去除右上角导出按钮
                    enabled: false
                },
                legend: {
                    align: 'right',
                    layout: 'vertical',
                    margin: 0,
                    verticalAlign: 'top',
                    y: 25,
                    symbolHeight: 280
                },
                tooltip: {
                    formatter: function() {
                        return '<b>' + this.series.xAxis.categories[this.point.x] + '</b> 年龄 <br><b>' +
                            this.point.value + '</b> 人患慢性病 <br><b>';
                    }
                },
                series: [{
                    name: '慢性病热力图',
                    borderWidth: 1,
                    data: [
                        [1,1,3624],
                        [1,2,23450],
                        [1,3,2844],
                        [1,4,1033],
                        [1,5,368],
                        [2,1,7581],
                        [2,2,53110],
                        [2,3,6015],
                        [2,4,2353],
                        [2,5,505],
                        [3,1,4385],
                        [3,2,33083],
                        [3,3,4336],
                        [3,4,1563],
                        [3,5,410],
                        [4,1,3920],
                        [4,2,30185],
                        [4,3,4117],
                        [4,4,1636],
                        [4,5,451],
                        [5,1,3819],
                        [5,2,27459],
                        [5,3,3498],
                        [5,4,1575],
                        [5,5,421],
                        [6,1,1748],
                        [6,2,12635],
                        [6,3,1538],
                        [6,4,857],
                        [6,5,199],
                        [7,1,590],
                        [7,2,4333],
                        [7,3,552],
                        [7,4,254],
                        [7,5,56],
                        [8,1,161],
                        [8,2,1026],
                        [8,3,113],
                        [8,4,71],
                        [8,5,11],
                        [9,1,41],
                        [9,2,368],
                        [9,3,39],
                        [9,4,20],
                        [9,5,1]
                    ],
                    dataLabels: {
                        enabled: true,
                        color: '#000000'
                    }
                }]
            });

            //慢性病类型和数量
            $('#container5').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: '慢性病类型和数量'
                },
                tooltip: {
                    headerFormat: '{series.name}<br>',
                    pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
                },
                colors: ['#AA10C8', '#FA0CA2','#d0021b', '#ff402d','#ff8f00', '#ffbf09',
                    '#f8e71c', '#8ad934','#23beae'],
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: false
                        },
                        showInLegend: true
                    }
                },
                credits: {		//去除右下角highcharts标志
                    enabled: false
                },
                exporting: {	//去除右上角导出按钮
                    enabled: false
                },
                series: [{
                    type: 'pie',
                    name: '慢性病类型占比',
                    data: [
                        ['高血压',24.0],
                        ['糖尿病',19.8],
                        ['脑动脉硬化',8.5],
                        ['脑卒中（中风）',8.2],
                        ['恶性肿瘤',1.7],
                        ['抑郁症',8.5],
                        ['精神疾病',6.2],
                        {
                            name: '冠心病',
                            y: 21.0,
                            sliced: true,
                            selected: true
                        },
                        ['认知障碍／痴呆／阿兹海默症',2.1]
                    ]
                }]
            });

            //居家上门服务需求分析
            var data = [4519,4738,4671,4888, 4566, 11260, 10980, 10277, 8812, 7439, 6922, 6580, 5987,4812, 4571, 5115, 5071, 5244, 5001, 5291, 5388, 5002,4757, 4143, 3525, 2950, 2356, 2213, 1650, 1349, 1117,853,659,487,392,275,214,152,114,90,56];
            $('#container6').highcharts({
                title: {
                    text: '居家上门服务需求分析'
                },
                xAxis: [{
                    title: {
                        text: '年龄'
                    },
                    categories: [60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100]
                }, {
                    title: {
                        text: null
                    },
                    opposite: true
                }],
                yAxis: [{
                    title: {
                        text: null
                    }
                }, {
                    title: {
                        text: null
                    },
                    opposite: true
                }],
                credits: { //去除右下角highcharts标志
                    enabled: false
                },
                colors: ['#ff8e00', '#002cfe'],
                exporting: { //去除右上角导出按钮
                    enabled: false
                },
                series: [{
                    name: 'Bell curve',
                    type: 'bellcurve',
                    xAxis: 1,
                    yAxis: 1,
                    baseSeries: 1,
                    zIndex: -1
                }, {
                    name: 'Data',
                    type: 'scatter',
                    data: data,
                    marker: {
                        radius: 1.5
                    }
                }]
            });
        }])