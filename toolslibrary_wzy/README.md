## Project的build.gradle:


```
// Top-level build file where you can add configuration options common to all sub-projects/modules.



buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.1.2'
        
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
        //上传到Bintray需要gradle-bintray-plugin的支持
        classpath 'com.github.dcendents:android-maven-gradle-plugin:1.3'
        classpath 'com.jfrog.bintray.gradle:gradle-bintray-plugin:1.4'
    }
}


allprojects {
    repositories {
        jcenter()
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

ext {

    mavenGroup = 'pub.devrel'
    mavenArtifactId = 'easypermissions'
    mavenVersion = '0.2.1'

    bintrayOrg = 'easygoogle'

    **support_library_version** = '24.2.1'

    buildTools = '24.0.3'
    compileSdk = 24
    targetSdk = 24
    minSdk = 17
}
```




## 2016-10-31

com/wzylibrary/tools/ImageLoader:
    AsyncImageLoaderFileUtils：可设置 FOLDER_NAME（下载的文件夹名）



## 2016-11-21

添加：[com/wzylibrary/view/twinklingrefresh](https://github.com/lcodecorex/TwinklingRefreshLayout)

TwinklingRefreshLayout延伸了Google的SwipeRefreshLayout的思想,
不在列表控件上动刀,而是使用一个ViewGroup来包含列表控件,以保持其较低的耦合性和较高的通用性。


> ###### 2017-1-9
> 作者更新至1.0.5版本，修复了一些问题


## 2016-12-9

添加：com/wzylibrary/utils/retrofit 包（retrofit的封装）

使用方法：


```
     项目的Application中需设置Constant_wzylibrary.BaseUrl = "http://192.168.1.1:888/"
     
     项目的Application需继承BaseApplication_wzylibrary（以便方便的封装OnMyRetrofitCallBackImp中的一些toast）

     需要请求数据的地方：
     call_toMLogin = RetrofitHelp.createAPI(ApiService.class).toMLogin(new RequestBean("","",jo.toString()));
     call_toMLogin.enqueue(new MyRetrofitCallback(new OnMyRetrofitCallBackImp(){
         @Override
         public void onState_1(ResponseBean responseBean) {
         }
         
         可选方法...
     }));
```




## 2016-12-16

添加  com.wzylibrary.view.[RandomTextView](https://github.com/AndroidMsky/RandomTextView)  滚动显示的数字

使用方法见github





## 2016-12-23

添加  com/wzylibrary/view/[tipsview](https://github.com/Qiaoidea/QQTipsView)

方便使用的仿qq未读消息的小圆点；仿微信底部渐变的导航栏并且可以动态添加fragment,导航图标，底部导航栏也可用小圆点；条目仿qq可滑动选择删除更多

使用方法：直接在要使用的地方调用TipsView.create(activity) .attach(view , TipsView.DragListener);实现小圆点拖拽


添加了包 com.tablayout 用于实现底部导航栏（带滑动渐变，小圆点，动态添加fragment）

修改了Bottom_Tablayout类中 onPageScrolled，onPageScrollStateChanged方法，配合onclick方法，
用于修复直接点击时按钮透明度变化不全的问题






