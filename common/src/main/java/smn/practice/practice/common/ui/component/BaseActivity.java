package smn.practice.practice.common.ui.component;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public abstract class BaseActivity extends AppCompatActivity {

    public abstract View getRootView();
    public abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        // 启用边到边显示，让内容延伸到系统栏区域
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        // 处理系统栏的插入边距
        setupWindowInsets();
    }


    /**
     * 设置窗口插入边距处理
     * 确保内容不被系统UI遮挡
     */
    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(getRootView(), //findViewById(R.id.container),
                (view, windowInsets) -> {
                    // 获取系统栏的插入边距
                    androidx.core.graphics.Insets systemBars = windowInsets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    // 应用边距，避免内容被系统UI遮挡
                    view.setPadding(
                            systemBars.left,    // 左侧安全区域
                            systemBars.top,     // 顶部状态栏
                            systemBars.right,   // 右侧安全区域
                            systemBars.bottom   // 底部导航栏/手势区域
                    );

                    return windowInsets;
                });
    }

}