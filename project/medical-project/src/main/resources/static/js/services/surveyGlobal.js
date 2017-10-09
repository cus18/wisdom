angular.module('surveyGlobal',[])
    .constant("Survey",
        {
            town : [
                {
                    name:'右安门街道',
                    village:['翠林一里社区','翠林二里社区','翠林三里社区','玉林里社区','玉林西里社区','西铁营社区','东滨河路社区',
                        '东庄社区','玉林东里一区社区','玉林东里二区社区','玉林东里三区社区','永乐社区','开阳里第一社区','开阳里第二社区',
                        '开阳里第三社区','开阳里第四社区']
                },
                {
                    name:'太平桥街道',
                    village:['莲花池社区','太平桥西里社区','太平桥中里社区','太平桥东里社区','太平桥南里社区','东管头社区','三路居社区','天伦北里社区','菜户营社区','万泉寺社区']
                },
                {
                    name:'西罗园街道',
                    village:['西罗园第一社区','西罗园第二社区','西罗园第三社区','西罗园第四社区','洋桥北里社区','洋桥西里社区','海户西里北社区','角门东里一社区','花椒树社区'
                    ,'马家堡东里社区','鑫福里社区','洋桥村社区','洋桥东里社区','海户西里南社区','角门东里二社区','四路通社区']
                },
                {
                    name:'大红门街道',
                    village:['西罗园南里社区','西罗园南里果园社区','西罗园南里华远社区','海户屯社区','木樨园南里社区','东罗园社区','南顶村社区','南顶路社区',
                    '康泽园社区','时村社区','石榴园北里第一社区','石榴园北里第二社区','石榴庄南里社区','石榴庄东街社区','大红门东街社区','久敬庄社区',
                    '苗圃东里社区','苗圃西里社区','西马场南里社区','西马场北里社区','西马小区社区','建欣苑社区']
                },
                {
                    name:'南苑街道',
                    village:['三营门社区','东新华社区','西新华社区','西长街社区','红房子社区','西宏苑社区','诚苑社区','槐房社区','机场社区']
                },
                {
                    name:'东高地街道',
                    village:['东高地社区','三角地第一社区','三角地第二社区','西洼地社区','六营门社区','万源东里社区','万源西里社区','梅源社区','东营房社区','万源南里社区']
                },
                {
                    name:'东铁匠营街道',
                    village:['蒲黄榆第一社区','蒲黄榆第二社区','蒲黄榆第三社区','蒲安里第一社区','蒲安里第二社区','刘家窑第一社区','刘家窑第二社区','刘家窑第三社区','木樨园第一社区','木樨园第二社区','光彩路第一社区','光彩路第二社区','横七条路第一社区','横七条路第二社区','横七条路第三社区','宋庄路第一社区','宋庄路第二社区','成仪路社区','成寿寺社区','南方庄社区','分钟寺社区']
                },
                {
                    name:'卢沟桥街道',
                    village:['丰台路口社区','望园社区','六里桥南里社区','六里桥北里社区','八一厂社区','六里桥社区','莲怡园社区','莲香园社区','岳各庄社区','金家村第一社区','金家村第二社区','青塔东里社区','青塔西里社区','蔚园社区','秀园社区','芳园社区','春园社区','小瓦窑西里社区','蒋家坟社区','小屯社区','大瓦窑社区','五里店第一社区','五里店第二社区','丰西路社区','油泵厂社区','大井社区','长安新城社区','京铁家园社区','民岳小区社区']
                },
                {
                    name:'丰台街道',
                    village:['新兴家园社区','东安街头条19号院社区','63号院社区','北大地16号院社区','北大地西区社区','东安街头条社区','东安街社区','北大街北里社区','东大街西里社区','北大街社区','东幸福街社区','永善社区','正阳北里社区','东大街东里社区','东大街社区','前泥洼社区','南开西里社区','向阳社区','建国街社区','新华街北社区','新华街南社区','程庄路16号院社区']
                },
                {
                    name:'新村街道',
                    village:['万柳园社区','万柳西园社区','育芳园社区','草桥社区','银地社区','育仁里社区','明春苑社区','桥梁厂第一社区','桥梁厂第二社区','造甲南里社区','造甲村社区','韩庄子第一社区','韩庄子第二社区','科学城第二社区','怡海花园社区','科学城第一社区','富丰园社区','看丹社区','富锦嘉园社区','丰西社区','电力机社区；中关村科技园区丰台园']
                },
                {
                    name:'长辛店街道',
                    village:['南墙缝社区','合成公社区','东山坡社区','北关社区','西峰寺社区','朱家坟南区社区','朱家坟西山坡社区','朱家坟北区社区','张家坟社区','赵辛店社区','北岗洼社区','崔村二里社区','装甲兵工程学院社区','建设里社区','东南街社区','陈庄社区','光明里社区','杜家坎社区','玉皇庄社区','二七车辆厂社区','张郭庄社区','槐树岭社区','芦井社区']
                },
                {
                    name:'云岗街道',
                    village:['南区第一社区','南区第二社区','云西路社区','田城社区','北区社区','北里社区','翠园社区','镇岗南里社区','大灰厂社区']
                },
                {
                    name:'方庄地区',
                    village:['芳古园一区第一社区','芳古园一区第二社区','芳古园二区社区','芳城园一区社区','芳城园二区社区','芳城园三区社区','芳群园一区社区','芳群园二区社区','芳群园三区社区','芳群园四区社区','芳星园一区社区','芳星园二区社区','芳星园三区社区','芳城东里社区','紫芳园社区']
                },
                {
                    name:'宛平城地区',
                    village:['宛平城城北社区','宛平城宛平社区','宛平城城南社区','宛平城晓月苑社区','宛平城老庄子社区','老庄子村','永合庄村','北天堂村']
                },
                {
                    name:'马家堡街道',
                    village:['嘉园一里社区','嘉园二里社区','嘉园三里社区','马家堡西里第一社区','马家堡西里第二社区','马家堡西里第三社区','双晨社区','角门东里西社区','晨宇社区','欣汇社区','富卓苑社区','玉安园社区','城南嘉园社区']
                },
                {
                    name:'和义街道',
                    village:['和义东里第一社区','和义东里第二社区','和义东里第三社区','南苑北里第一社区','南苑北里第二社区','和义西里第一社区','和义西里第二社区']
                },
                {
                    name:'长辛店镇',
                    village:['张郭庄村','东河沿村','辛庄村','大灰厂村','李家峪村','太子峪村','张家坟村','赵辛店村','长辛店村']
                },
                {
                    name:'王佐镇',
                    village:['西庄店中心村','沙锅村中心村','怪村中心村','魏各庄中心村','西王佐中心村','南宫中心村','庄户中心村','佃起中心村']
                },
                {
                    name:'卢沟桥乡',
                    village:['太平桥村','马连道村','菜户营村','三路居村','万泉寺村','东管头村','小井村','六里桥村','西局村','周庄子村','岳各庄村','靛厂村','郑常庄村','大井村','小屯村','小瓦窑村','张仪村','郭庄子村','卢沟桥村','果园村','精图村']
                },
                {
                    name:'花乡乡',
                    village:['草桥村','黄土岗村','新发地村','白盆窑村','郭公庄村','高立庄村','六圈村','看丹村','榆树庄村','羊坊村','保台村','樊家村','纪家庙村','造甲村','四合庄村']
                },
                {
                    name:'南苑乡',
                    village:['西铁营村','右安门村','花园村','马家堡村','东罗元村','果园村','时村','石榴庄村','大红门村','东铁营村','成寿寺村','分中寺村','南苑村','槐房村','新宫村']
                }
            ]
        })