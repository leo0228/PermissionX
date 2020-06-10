package com.permissionx.tianjiaodev.java;

import java.util.List;

public interface IInvisibleListener {
    void onGrant(boolean allGranted, List<String> deniedList);
}
