package com.huachao.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 类名称：SqlToJava
 * 类描述：   将PL/SQL美化后的sql生成JAVA语句
 * 创建人：tangchao
 * QQ:419704299@qq.com
 * 创建时间：2017年8月17日 下午4:39:16
 */
public class SqlToJavaFormGui extends AnAction {

    private Editor mEditor;
    private Project mProject;
    @Override
    public void actionPerformed(AnActionEvent e) {
        mEditor = e.getData(PlatformDataKeys.EDITOR);
        mProject = e.getData(PlatformDataKeys.PROJECT);
        String selectText = mEditor.getSelectionModel().getSelectedText();
        SqlToJavaGui mf = new SqlToJavaGui("移动作业项目组专属工具",selectText, mEditor, mProject);
        mf.setVisible(true);
    }
}
