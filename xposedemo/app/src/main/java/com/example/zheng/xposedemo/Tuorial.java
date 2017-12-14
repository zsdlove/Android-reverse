/**
 * Created by zheng on 2017/12/2.
 */
package com.example.zheng.xposedemo;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
public class Tuorial implements IXposedHookLoadPackage {
    @Override
    //public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
    //XposedBridge.log("Loaded app: " + lpparam.packageName);
    // XposedBridge.log("你好xpose");
    // }
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if(lpparam.packageName.contains("com.tencent.mm")){
            XposedHelpers.findAndHookMethod(Application.class,"attach", Context.class,new XC_MethodHook(){
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ClassLoader loader=((Context)param.args[0]).getClassLoader();
                    Class<?> clazz = loader.loadClass("com.tencent.mm.ui.LauncherUI");//获取类
                    if(clazz!=null)
                    {
                        XposedHelpers.findAndHookMethod("com.tencent.mm.ui.LauncherUI",lpparam.classLoader,"onCreateOptionsMenu",Menu.class,new XC_MethodHook(){//获取方法
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                Log.i("zsdlove","find method....................");
                                final Context applicationContext=null;
                                Menu menu=(Menu) param.args[0];
                                menu.add(0,3,0,"zsdlove");
                                menu.add(0,4,0,"liudehua");
                                menu.add(0,5,0,"郑爽");
                                menu.add(0,6,0,"fuck");
                                menu.add(0,7,0,"666");
                                menu.add(0,8,0,"http://www.baidu.com");
                                for(int i=0;i<menu.size();i++){
                                    menu.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                        @Override
                                        public boolean onMenuItemClick(MenuItem item) {
                                            XposedBridge.log("你点击了");
                                            Toast.makeText(applicationContext,"点击了",Toast.LENGTH_SHORT).show();
                                            return false;
                                        }
                                    });
                                }




                            }
                        });
                    }
                }
            });

        }
    }
}