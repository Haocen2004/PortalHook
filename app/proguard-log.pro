##########################################################################################################
# 作者：Sollyu
# 日期：2020-11-02
# 内容：发布版本移除日志，kotlin编译时带的而外信息，增强反调试难度
# 使用：proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro', 'proguard-log.pro'
# 修改以适配应用 2022.01.16 Hao_cen
##########################################################################################################

##########################################################################################################
# 删除安卓日志
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** w(...);
    public static *** e(...);
}
