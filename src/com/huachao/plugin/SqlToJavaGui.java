package com.huachao.plugin;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * 类名称：SqlToJava
 * 类描述：   将PL/SQL美化后的sql生成JAVA语句
 * 创建人：tangchao
 * QQ:419704299@qq.com
 * 创建时间：2017年8月9日 下午4:39:16
 */
public class SqlToJavaGui extends JFrame {
    private JButton btn0 = new JButton("将PL/SQL美化后的sql生成JAVA语句(  去掉SCYW.)");
    private JButton btn1 = new JButton("执行远程查询方法remoteExecuteQuery");
    private JTextArea input = new JTextArea(20, 121);
    private JTextArea show = new JTextArea("Result:\n", 20, 121);

    private String result = "";

    private ArrayList<Integer> signpos = new ArrayList<Integer>();
    String selectText;
    private Editor mEditor;
    private Project mProject;
    public SqlToJavaGui(String title, String selectText, Editor mEditor, Project mProject) {
        this();
        this.selectText = selectText;
        this.mEditor = mEditor;
        this.mProject = mProject;
        setTitle(title);
        input.setText(selectText);
    }

    private SqlToJavaGui() {
        setLayout(new FlowLayout(FlowLayout.LEADING));
        setSize(1000, 1000);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(btn0);
        add(btn1);
        add(input);
//        add(show);

        btn0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zhuanhuanfangfa();
            }
        });
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yuanchengchaxunfangfa();
            }
        });

    }



    //远程查询方法
    private void yuanchengchaxunfangfa() {
        String string = input.getText();
        string = string.toUpperCase();
        string = string.replace("SCYW.", "");
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

        String datetable = "\n" + "try {" + "\n" + "DataTable dt = dam.remoteExecuteQuery(\"pms\", sb.toString(), 200);" + "\n"

                + "if (dt != null) {" + "\n" + "int length = dt.getRows().size();" + "\n"
                + "	for (int i = 0; i < length; i++) {" + "\n"

                + "\n" + "}" + "\n" + "\n" + "} else {" + "\n" + "\n" + "}" + "\n" + "} catch (Exception e) {" + "\n"
                + "\n" + "}";

//        show.setText(string2 + ss + datetable);

        changeSelectText(string2 + ss + datetable);
    }

    /**
     * 方法描述：   把格式化的sql转成sb,转换后去掉SCYW.
     * 创建人：tangchao
     * QQ:419704299@qq.com
     * 创建时间：2017年8月9日 下午4:50:18
     * 返回值: void
     */
    protected void zhuanhuanfangfa() {
        String string = input.getText();
        string = string.toUpperCase();
        string = string.replace("SCYW.", "");
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
//        show.setText(string2 + ss);

        changeSelectText(string2 + ss);
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
        this.setVisible(false);
    }
}
