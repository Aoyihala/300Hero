package com.example.evenalone.a300hero.event;

import com.example.evenalone.a300hero.bean.UpdateBean;

public class UpdateVersionEvent {
    private UpdateBean updateBean;

    public UpdateVersionEvent(UpdateBean updateBean) {
        this.updateBean = updateBean;
    }

    public void setUpdateBean(UpdateBean updateBean) {
        this.updateBean = updateBean;
    }

    public UpdateBean getUpdateBean() {
        return updateBean;
    }
}
