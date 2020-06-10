# PermissionX

PermissionX是一个用于简化Android运行时权限用法的开源库。

添加如下配置将PermissionX引入到你的项目当中：

```groovy
dependencies {
    ...
    implementation 'com.permissionx.tianjiaodev:permissionx:1.0.0'
}
```

然后就可以使用如下语法结构来申请运行时权限了：

```kotlin

val permissions: MutableMap<String, Boolean> = HashMap()
permissions[Manifest.permission.CAMERA] = true
permissions[Manifest.permission.CALL_PHONE] = true
permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] = false

PermissionX.request(
    this,
    permissions
) { allGranted, deniedList ->
    if (allGranted) {
        Toast.makeText(MainJavaActivity.this, "全部授权", Toast.LENGTH_SHORT).show();
    } else {
        Toast.makeText(MainJavaActivity.this, "未授权：" + deniedList.toString(), Toast.LENGTH_SHORT).show();
    }
}

```

```java

Map<String, Boolean> permissions = new HashMap<>();
permissions.put(Manifest.permission.CAMERA, true);
permissions.put(Manifest.permission.CALL_PHONE, true);
permissions.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, false);

PermissionX.init(MainJavaActivity.this, permissions, new IInvisibleListener() {
    @Override
    public void onGrant(boolean allGranted, List<String> deniedList) {
        if (allGranted) {
            Toast.makeText(MainJavaActivity.this, "全部授权", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainJavaActivity.this, "未授权：" + deniedList.toString(), Toast.LENGTH_SHORT).show();
        }
    }
});

```