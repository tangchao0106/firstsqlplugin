package com.huachao.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;


public class FirstPlugin extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        Messages.showMessageDialog(project, "slq转java的插件!", "移动作业测试插件", Messages.getInformationIcon());
    }
}