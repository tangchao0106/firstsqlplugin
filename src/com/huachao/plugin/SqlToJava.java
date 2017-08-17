package com.huachao.plugin;

import com.google.gson.Gson;
import com.huachao.plugin.dialog.SelectDialog;
import com.huachao.plugin.dialog.SelectTextCallBack;
import com.huachao.plugin.net.HttpRequest;
import com.huachao.plugin.net.TranslateBean;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
public class SqlToJava extends AnAction {

    private Editor mEditor;
    private Project mProject;
    @Override
    public void actionPerformed(AnActionEvent e) {
        mEditor = e.getData(PlatformDataKeys.EDITOR);
        mProject = e.getData(PlatformDataKeys.PROJECT);
        String selectText = mEditor.getSelectionModel().getSelectedText();
        sqltojava(selectText);
    }
    /**
     * 方法描述：   把格式化的sql转成sb,转换后去掉SCYW.
     * 创建人：tangchao
     * QQ:419704299@qq.com
     * 创建时间：2017年8月17日 下午4:50:18
     * 返回值: void
     */
    private void sqltojava(String  selectText) {
        String string = selectText;
        string = string.toUpperCase();
        string=string.replace("SCYW.", "");
        String sz[] = string.split("\n");
        List list = new ArrayList<>();
        String ss = "";
        // 去掉空行
        for (int i = 0; i < sz.length; i++) {
            if ("".equals(sz[i])) {
            } else {
                list.add(sz[i]);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            ss = ss + "\n" + "sb.append(\"" + list.get(i) + " \");";
        }
        String string2 = "StringBuilder sb = new StringBuilder();";

        String datetable ="\n" +  "try {" + "\n" + "DataTable dt = dam.remoteExecuteQuery(\"pms\", sb.toString(), 200);" + "\n"

                + "if (dt != null) {" + "\n" + "int length = dt.getRows().size();" + "\n"
                + "	for (int i = 0; i < length; i++) {" + "\n"

                + "\n" + "}" + "\n" + "\n" + "} else {" + "\n" + "\n" + "}" + "\n" + "} catch (Exception e) {" + "\n"
                + "\n" + "}";
        changeSelectText(string2 + ss+datetable);
    }

    /**
     * 方法描述：   显示在页面上
     * 创建人：tangchao
     * QQ:419704299@qq.com
     * 创建时间：2017年8月17日 下午4:50:18
     * 返回值: void
     */
    private void changeSelectText(String text) {
        Document document = mEditor.getDocument();
        SelectionModel selectionModel = mEditor.getSelectionModel();

        final int start = selectionModel.getSelectionStart();
        final int end = selectionModel.getSelectionEnd();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                document.replaceString(start, end, text);
            }
        };
        WriteCommandAction.runWriteCommandAction(mProject, runnable);
        selectionModel.removeSelection();
    }

}
