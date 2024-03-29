package os.bracelets.children.app.news;

import java.util.List;

import os.bracelets.children.bean.HealthInfo;
import os.bracelets.children.common.BasePresenter;
import os.bracelets.children.common.BaseView;

/**
 * Created by lishiyou on 2019/2/21.
 */

public interface HealthInfoContract {

    interface View extends BaseView<Presenter> {
        void loadInfoSuccess(List<HealthInfo> infoList);

        void loadInfoError();

    }

    abstract class Presenter extends BasePresenter<View> {
        public Presenter(View mView) {
            super(mView);
        }

        abstract void informationList(int type, int pageNo,String releaseTime);
    }

}
