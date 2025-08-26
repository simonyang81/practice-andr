package smn.practice.practice.andr;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import smn.practice.practice.andr.logic.MainActivityLogic;
import smn.practice.practice.common.ui.component.BaseActivity;

/**
 * 主Activity
 */
public class MainActivity extends BaseActivity implements MainActivityLogic.ActivityProvider {

    private MainActivityLogic activityLogic;

    @Override
    public View getRootView() {
        return findViewById(R.id.container);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activityLogic = new MainActivityLogic(this, savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        this.activityLogic.onSaveInstanceState(outState);
    }
}