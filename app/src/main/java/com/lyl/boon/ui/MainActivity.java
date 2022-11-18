package com.lyl.boon.ui;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.lyl.boon.R;
import com.lyl.boon.ui.base.BaseActivity;
import com.lyl.boon.ui.joke.JokeFragment;
import com.lyl.boon.ui.learn.LearnFragment;
import com.lyl.boon.ui.superboon.SuperBoonFragment;
import com.lyl.boon.ui.wanandroid.WanAndroidFragment;
import com.lyl.boon.ui.young.YoungFragment;
import com.lyl.boon.utils.NetStatusUtil;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends BaseActivity {

    private LearnFragment learnFragment;
    private WanAndroidFragment wanandroidFragment;
    private YoungFragment youngFragment;
    private JokeFragment jokeFragment;
    private SuperBoonFragment superFragment;

    private Fragment oldFragment;

    @Bind(R.id.bottomBar)
    BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initActionbar();
        setAppAbout();
        setFavoriteIcon();
        initBottom();
        initFragmentContent();
    }

    /**
     * 设置中间内容页
     */
    private void initFragmentContent() {
        learnFragment = new LearnFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, learnFragment).commit();
        oldFragment = learnFragment;
    }

    /**
     * 设置底部按钮
     */
    private void initBottom() {
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                switch (tabId) {

                    case R.id.menu_learn: //学习
                        setActTitle(R.string.menu_learn_msg);
                        toFragment(learnFragment);
                        break;

                    case R.id.menu_wanandroid: //玩Android
                        goFragment(0);
                        break;

                    case R.id.menu_joke: //开心
                        checkNet(1);
                        break;

                    case R.id.menu_young: //美女
                        checkNet(2);
                        break;

                    case R.id.menu_super: //超级福利
                        checkNet(3);
                        break;

                    default:
                        setActTitle(R.string.menu_learn_msg);
                        toFragment(learnFragment);
                        break;
                }
            }
        });
    }

    /**
     * 检查网络，并跳转
     */
    private void checkNet(final int position) {
        if (NetStatusUtil.isWifi(MainActivity.this)) {
            goFragment(position);
        } else {
            final MaterialDialog dialog = new MaterialDialog(this);
            dialog.setTitle("提示");
            dialog.setMessage("您当前不是WIFI状态，访问会消耗大量的流量，您确定要访问吗？");
            dialog.setPositiveButton("没事儿拼了", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goFragment(position);
                    dialog.dismiss();
                }
            });
            dialog.setNegativeButton("还是不看了", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    showToast("(*^__^*) 没事去读书学习吧");
                    mBottomBar.selectTabWithId(R.id.menu_wanandroid);
                }
            });

            dialog.show();
        }
    }

    private void goFragment(int position) {

        switch (position) {

            case 0://玩Android
                setActTitle(R.string.menu_wanandroid_msg);
                if (wanandroidFragment == null) {
                    wanandroidFragment = new WanAndroidFragment();
                }
                toFragment(wanandroidFragment);
                break;

            case 1://开心
                setActTitle(R.string.menu_joke_msg);
                if (jokeFragment == null) {
                    jokeFragment = new JokeFragment();
                }
                toFragment(jokeFragment);
                break;

            case 2: //美女
                setActTitle(R.string.menu_young_msg);
                if (youngFragment == null) {
                    youngFragment = new YoungFragment();
                }
                toFragment(youngFragment);
                break;

            case 3: //超级福利
                setActTitle(R.string.menu_super_msg);
                if (superFragment == null) {
                    superFragment = new SuperBoonFragment();
                }
                toFragment(superFragment);
                break;

        }
    }

    /**
     * 切换Fragment
     *
     * @param to 下一个Fragment页面
     */
    private void toFragment(Fragment to) {
        if (to == oldFragment) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        if (!to.isAdded()) {    // 先判断是否被add过
            transaction.hide(oldFragment).add(R.id.fragment_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(oldFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
        oldFragment = to;
    }

    /**
     * 设置导航栏的标题
     */
    private void setActTitle(int res) {
        if (mActionTitle != null) {
            mActionTitle.setText(getString(res));
        }
    }


    //***************************
    // 双击返回退出
    //***************************

    private long time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time <= 2000) {
                finish();
            } else {
                time = System.currentTimeMillis();
                Toast.makeText(getApplicationContext(), R.string.exit_app, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
