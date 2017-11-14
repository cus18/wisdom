var healthService = 'health/healthService/';
var healthArchive = 'health/healthArchive/';
var healthDetect = 'health/healthDetect/';
var healthMedication = 'health/medication/';
var healthDiet = 'health/diet/';
var core = 'core/';
var communityCourse = 'community/course/';
var communityActivity = 'community/activity/';
var communityIndex = 'community/index/';

define(['appElder'], function (app) {
    app
        .factory('GetOnGoingHealthServicePackageList',['$resource',function ($resource){
            return $resource(healthService + 'packageList');
        }])
        .factory('GetOnGoingHealthServicePackage',['$resource',function ($resource){
            return $resource(healthService + 'package');
        }])
        .factory('GetHealthServicePackageTemplateList',['$resource',function ($resource){
            return $resource(healthService + 'templateList');
        }])
        .factory('GetHealthServicePackageTemplateDetail',['$resource',function ($resource){
            return $resource(healthService + 'template');
        }])
        .factory('CreateHealthServicePackage',['$resource',function ($resource){
            return $resource(healthService + 'package/create');
        }])
        .factory('GetHealthArchiveBasicInfo',['$resource',function ($resource){
            return $resource(healthArchive + 'basicInfo');
        }])
        .factory('GetHealthArchivePhysicalExamination',['$resource',function ($resource){
            return $resource(healthArchive + 'physicalExamination');
        }])
        .factory('GetHealthArchivePhysicalExaminationList',['$resource',function ($resource){
            return $resource(healthArchive + 'physicalExaminationList');
        }])
        .factory('GetHealthArchivePhysicalExaminationTemplateList',['$resource',function ($resource){
            return $resource(healthArchive + 'physicalExaminationTemplateList');
        }])
        .factory('GetHealthArchiveHealthAssessmentList',['$resource',function ($resource){
            return $resource(healthArchive + 'healthAssessmentList');
        }])
        .factory('GetHealthArchiveHealthAssessmentResult',['$resource',function ($resource){
            return $resource(healthArchive + 'healthAssessment');
        }])
        .factory('GetHealthArchiveHealthAssessmentTemplateList',['$resource',function ($resource){
            return $resource(healthArchive + 'templateList');
        }])
        .factory('GetElderContactInfo',['$resource',function ($resource){
            return $resource(healthService + 'elderContactInfo');
        }])
        .factory('GetDetectionHealthData',['$resource',function ($resource){
            return $resource(healthDetect + 'detection');
        }])
        .factory('CreateDetection',['$resource',function ($resource){
            return $resource(healthDetect + 'createDetection');
        }])
        .factory('ControlTarget',['$resource',function ($resource){
            return $resource(healthDetect + 'controlTarget');
        }])
        .factory('GetControlTarget',['$resource',function ($resource){
            return $resource(healthDetect + 'getControlTarget');
        }])
        .factory('TestReportList',['$resource',function ($resource){
            return $resource(healthDetect + 'testReport')
        }])
        .factory('DiagnoseReportList',['$resource',function ($resource){
            return $resource(healthDetect + 'treatment')
        }])
        .factory('GetUserInfo',['$resource',function ($resource){
            return $resource(core + 'getUserInfo');
        }])
        .factory('UserLogin',['$resource',function ($resource){
            return $resource(core + 'login');
        }])
        .factory('UserLoginOut',['$resource',function ($resource){
            return $resource(core + 'loginout');
        }])
        .factory('FeedBack',['$resource',function ($resource){
            return $resource(core + 'feedback')
        }])
        .factory('GetGroupChatData',['$resource',function ($resource){
            return $resource(core + 'groupChatData')
        }])
        .factory('GetMedicationPlan',['$resource',function ($resource){
            return $resource(healthMedication + 'getMedicationPlan')
        }])
        .factory('InsertMedicationPlan',['$resource',function ($resource){
            return $resource(healthMedication + 'insertMedicationPlan')
        }])
        .factory('UpdateMedicationPlan',['$resource',function ($resource){
            return $resource(healthMedication + 'updateMedicationPlan')
        }])
        .factory('DeleteMedicationPlan',['$resource',function ($resource){
            return $resource(healthMedication + 'deleteMedicationPlan')
        }])
        .factory('GetMedicationPlanByID',['$resource',function ($resource){
            return $resource(healthMedication + 'getMedicationPlanByID')
        }])
        .factory('GetMedicationTimingPlanByID',['$resource',function ($resource){
            return $resource(healthMedication + 'getMedicationTimingPlanByID')
        }])
        .factory('UpdateMedicationPlanStatus',['$resource',function ($resource){
            return $resource(healthMedication + 'updateMedicationPlanStatus')
        }])
        .factory('GetMedicationPlanTimingByElderUserID',['$resource',function ($resource){
            return $resource(healthMedication + 'getMedicationPlanTimingByElderUserID')
        }])
        .factory('GetDietPlanByDate',['$resource',function ($resource){
            return $resource(healthDiet + 'getDietPlanByDate')
        }])

        //获取已参与的活动群列表信息
        .factory('GetAttendedActivityGroupMessage',['$resource',function ($resource){
            return $resource(core + 'attendedActivityGroupMessage')
        }])
        //获取老友提醒信息
        .factory('GetNotificationMessage',['$resource',function ($resource){
            return $resource(core + 'notificationMessage')
        }])
        //更新老友提醒信息中某条消息的已读状态
        .factory('UpdateNotificationMessageReadStatus',['$resource',function ($resource){
            return $resource(core + 'notificationMessage/update')
        }])
        //获取老友提醒信息
        .factory('GetExtendMessage',['$resource',function ($resource){
            return $resource(core + 'extendMessage')
        }])
        //获取老友提醒信息详情
        .factory('GetExtendMessageDetail',['$resource',function ($resource){
            return $resource(core + 'extendMessage/detail')
        }])
        //获取所有的活动列表
        .factory('GetActivityList',['$resource',function ($resource){
            return $resource(communityActivity + 'activityList')
        }])
        //获取某个活动的详情信息
        .factory('GetActivityDetail',['$resource',function ($resource){
            return $resource(communityActivity + 'activityDetail')
        }])
        //获取用户参加某个活动的状态
        .factory('GetActivityAttendStatus',['$resource',function ($resource){
            return $resource(communityActivity + 'activityAttendStatus')
        }])
        //获取某个活动的评论信息
        .factory('GetActivityDiscuss',['$resource',function ($resource){
            return $resource(communityActivity + 'activityDiscuss')
        }])
        //用户真对某个活动的发表评论
        .factory('CreateActivityDiscuss',['$resource',function ($resource){
            return $resource(communityActivity + 'activityDiscuss/create')
        }])
        //报名参加某个活动
        .factory('JoinActivity',['$resource',function ($resource){
            return $resource(communityActivity + 'joinActivity')
        }])
        //获取推荐的活动
        .factory('GetSuggestActivity',['$resource',function ($resource){
            return $resource(communityActivity + 'suggestActivity')
        }])
        //获取用户的参加的活动信息列表
        .factory('GetMyActivityList',['$resource',function ($resource){
            return $resource(communityActivity + 'myActivityList')
        }])
        //获取近期直播的列表
        .factory('GetRecentLiveBroadCast',['$resource',function ($resource){
            return $resource(communityCourse + 'recentLiveBroadCast')
        }])
        //获取历史直播的列表
        .factory('GetHistoryLiveBroadCast',['$resource',function ($resource){
            return $resource(communityCourse + 'historyLiveBroadCast')
        }])
        //获取直播的详细内容
        .factory('GetLiveBroadCastDetail',['$resource',function ($resource){
            return $resource(communityCourse + 'liveBroadCastDetail')
        }])
        //报名参加某个直播课程
        .factory('RegisterLiveBroadCast',['$resource',function ($resource){
            return $resource(communityCourse + 'liveBroadCast/register')
        }])
        //获取在线课堂的列表
        .factory('GetOnlineCourseList',['$resource',function ($resource){
            return $resource(communityCourse + 'onlineCourseList')
        }])
        //获取在线课堂的详细内容
        .factory('GetOnlineCourseDetail',['$resource',function ($resource){
            return $resource(communityCourse + 'onlineCourseDetail')
        }])
        //获取在线课堂的某个课程的所有评论
        .factory('GetOnlineCourseDiscuss',['$resource',function ($resource){
            return $resource(communityCourse + 'onlineCourseDiscuss')
        }])
        //获取在线课堂的某个课程进行评论
        .factory('CreateOnlineCourseDiscuss',['$resource',function ($resource){
            return $resource(communityCourse + 'onlineCourseDiscuss/create')
        }])
        //获取用户的参加在线课堂列表
        .factory('GetMyCourseList',['$resource',function ($resource){
            return $resource(communityCourse + 'myCourseList')
        }])
        .factory('GetMyOnlineCourseList',['$resource',function ($resource){
            return $resource(communityCourse + 'getMyOnlineCourse')
        }])

        //获取用户的社区首页的banner图
        .factory('GetActivityListByFirstPage',['$resource',function ($resource){
            return $resource(communityIndex + 'activityListByFirstPage')
        }])
        //获取用户的社区首页的活动列表信息
        .factory('GetCommunityBannerList',['$resource',function ($resource){
            return $resource(communityIndex + 'bannerList')
        }])

        //获取自己的亲友群中所有的亲友信息列表
        .factory('GetRelativeElderInfo',['$resource',function ($resource){
            return $resource(core + 'relativeElderInfo')
        }])


})
