package os.bracelets.children.app.edit;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import aio.health2world.http.HttpResult;
import aio.health2world.utils.ToastUtil;
import aio.health2world.view.LoadingDialog;
import os.bracelets.children.AppConfig;
import os.bracelets.children.R;
import os.bracelets.children.bean.FamilyMember;
import os.bracelets.children.common.BaseActivity;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;
import os.bracelets.children.utils.TitleBarUtil;
import os.bracelets.children.view.TitleBar;

/**
 * 设备绑定
 */
public class DeviceBindActivity extends BaseActivity {

    private TitleBar titleBar;

    private EditText edDeviceNo;
    private TextView tvName;
    private Button btnBind;
    private FamilyMember member;

    private LoadingDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_bind;
    }

    @Override
    protected void initView() {
        titleBar = findView(R.id.titleBar);
        TitleBarUtil.setAttr(this, "", "绑定设备", titleBar);

        edDeviceNo = findView(R.id.edDeviceNo);
        tvName = findView(R.id.tvName);
        btnBind = findView(R.id.btnBind);
    }

    @Override
    protected void initData() {
        member = (FamilyMember) getIntent().getSerializableExtra("member");
        tvName.setText(member.getNickName());
        dialog = new LoadingDialog(this);
    }

    @Override
    protected void initListener() {
        btnBind.setOnClickListener(this);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnBind) {
            String deviceNo = edDeviceNo.getText().toString().trim();
            if (TextUtils.isEmpty(deviceNo)) {
                ToastUtil.showShort("请输入设备编号");
                return;
            }

            ApiRequest.bindDevice(String.valueOf(member.getAccountId()), deviceNo,
                    new HttpSubscriber() {
                        @Override
                        public void onStart() {
                            super.onStart();
                            dialog.show();
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            dialog.dismiss();
                        }

                        @Override
                        public void onNext(HttpResult result) {
                            super.onNext(result);
                            dialog.dismiss();
                            if (result.code.equals(AppConfig.SUCCESS)) {
                                ToastUtil.showShort("操作成功");
                                finish();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog = null;
    }
}
