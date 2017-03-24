Logger:
https://github.com/orhanobut/logger
compile 'com.orhanobut:logger:1.15'




easypermissions：

compile 'pub.devrel:easypermissions:0.2.1' //https://github.com/googlesamples/easypermissions
package com.google_easy_permission;

注1：权限存在特殊情况！！！坑
    比如mx5中，android2.2，api22：
    在EasyPermissions.hasPermissions方法中有
       // Always return true for SDK < M, let the system deal with the permissions
       if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
           Log.w(TAG, "hasPermissions: API version < M, returning true by default");
           return true;
       }
    所以默认权限为安装时赋予的权限，比如为true，可是在实际中，魅族自身携带权限判定，在调用拨打电话时还会有提示是否允许拨打电话，
    此时哪怕是拒绝的，但ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED仍然为true，
    所以拒绝后就不会打电话，也不会弹出提示了

注2：此包使用了android.support.annotation.RequiresApi类，在support的24.2.1中才有这个类，
    原本使用的'com.android.support:appcompat-v7:23.4.0'中没有此类，
    多个版本的support包又导致CollapsingToolbarLayout报错（不知道为啥），
    所以升级了app中：compileSdkVersion 24，compile 'com.android.support:appcompat-v7:24.2.1'

    //easypermissions使用方法例子
    public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

        private static final String TAG = "BasicActivity";
        private static final int PERMISSON_CODE = 123;
        private static final int PERMISSION_onActivityResult = 125;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    locationAndContactsTask();

                }
            });
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            // EasyPermissions handles the request result.
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        }

        @AfterPermissionGranted(PERMISSON_CODE)
        public void locationAndContactsTask() {
            String[] perms = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE };
            if (EasyPermissions.hasPermissions(this, perms)) {
                // Have permissions, do the thing!
    //            Toast.makeText(this, "TODO: Location and Contacts things", Toast.LENGTH_LONG).show();
                callPhone();
            } else {
                // Ask for both permissions
                //第一次的时候是直接选择同意或拒绝，第二次是参数二文字的弹出提示框
                EasyPermissions.requestPermissions(this, "此应用需要获取电话、定位权限，拒绝将无法正常使用",
                        PERMISSON_CODE, perms);
            }
        }

        /**
         * 权限请求，选择同意或拒绝后（如果有多个权限则在全部选择完后）回调
         * @param requestCode PERMISSON_CODE
         * @param perms 选择同意的权限
         */
        @Override
        public void onPermissionsGranted(int requestCode, List<String> perms) {
            Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
            Toast.makeText(this, "onPermissionsGranted:" + requestCode + ":" + perms.toString(), Toast.LENGTH_LONG).show();
            
            
            //如果申请的权限全部允许了，则去做要做的事
            if (perms.size() == needPermissions.length) {
                wantToDoSomething();
            }
        }

        /**
         * 权限请求，选择同意或拒绝后（如果有多个权限则在全部选择完后）回调
         * @param requestCode PERMISSON_CODE
         * @param perms 选择拒绝的权限
         */
        @Override
        public void onPermissionsDenied(int requestCode, List<String> perms) {
            Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
            Toast.makeText(this, "onPermissionsDenied:" + requestCode + ":" + perms.toString(), Toast.LENGTH_LONG).show();
            
             SnackbarUtil.noDismissAlertSnackbar(this, "必要权限被禁止", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tryTodoWithPermission();
                        }
                    });


            //如果权限被拒绝，并且勾选了不在提示，则弹出窗口提示去设置里手动打开
            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                new AppSettingsDialog.Builder(this, "提示：")
                        .setTitle("缺乏必要权限，请在设置里打开")
                        .setPositiveButton("设置")
                        .setNegativeButton("取消", null /* click listener */)
                        .setRequestCode(PERMISSION_onActivityResult)  //从设置里回来后
                        .build()
                        .show();
            }
        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PERMISSION_onActivityResult) {
                // Do something after user returned from app settings screen, like showing a Toast.
                Toast.makeText(this, "onActivityResult", Toast.LENGTH_SHORT).show();
                locationAndContactsTask();
            }
        }


        public void callPhone() {
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + "123456");
            intent.setData(data);
            startActivity(intent);
        }
    }






leakcanary

https://github.com/square/leakcanary