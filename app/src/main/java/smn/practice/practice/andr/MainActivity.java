package smn.practice.practice.andr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowCompat;

import android.os.Bundle;

import smn.practice.practice.andr.ui.main.MainFragment;

/**
 * 主Activity - 支持边到边显示和安全区域处理
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 启用边到边显示，让内容延伸到系统栏区域
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        
        setContentView(R.layout.activity_main);
        
        // 处理系统栏的插入边距
        setupWindowInsets();
        
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
    
    /**
     * 设置窗口插入边距处理
     * 确保内容不被系统UI遮挡
     */
    private void setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container), 
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