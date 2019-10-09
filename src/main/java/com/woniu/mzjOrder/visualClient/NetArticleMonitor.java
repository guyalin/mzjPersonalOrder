package com.woniu.mzjOrder.visualClient;

import com.woniu.mzjOrder.dao.NetInformationDao;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.util.SpringUtil;
import layout.TableLayout;
import org.springframework.beans.factory.annotation.Value;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@org.springframework.stereotype.Component
public class NetArticleMonitor {

    private static NetInformationDao netInformationDao;
    @Value("${local.net-file.root-path}")
    private static String path;
    /*public static void main(String[] args){
        initFrame();
    }*/

    /**
     * 初始化界面
     */
    public static void initFrame() {
        netInformationDao = (NetInformationDao) SpringUtil.getBean("netInformationDao");
        double size[][] = {{TableLayout.FILL},{0.2,0.4,0.2,0.4}};
        // 创建 JFrame 实例
        JFrame frame = new JFrame("网站定向监控");
        // Setting the width and height of frame
        frame.setSize(600,500);
        frame.setLocationRelativeTo(null);             // 把窗口位置设置到屏幕中心
        /**
         * 下边的这句话，如果这么写的话，窗口关闭，springboot项目就会关掉，使用
         * dispose则不会
         */
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.dispose(); //如果写这句可实现窗口关闭，springboot项目仍运行

        // 2. 创建中间容器（面板容器）
        JPanel panel = new JPanel(new FlowLayout());                // 创建面板容器，使用网格布局
        JPanel subPanel1 = new JPanel(new FlowLayout());            //第一层
        JPanel subPanel2 = new JPanel(new FlowLayout());            //第二层
        JPanel subPanel3 = new JPanel(new FlowLayout());            //第三层
        JPanel subPanel4 = new JPanel(new FlowLayout());            //第四层

        // 3. 创建基本组件（按钮），并添加到各自的面板容器中
        //panel1
        JLabel jl_netList = new JLabel("监听网站数:");
        JTextField jtf_listNum = new JTextField(3);
        jtf_listNum.setText("");
        jtf_listNum.setEditable(false);
        JButton jb_showList = new JButton("显示已连接列表");
        jb_showList.addActionListener(e -> {
            if (jb_showList.getText().equals("显示已连接列表")){
                subPanel2.setVisible(true);
                jb_showList.setText("关闭已连接列表");
            }else{
                subPanel2.setVisible(false);
                jb_showList.setText("显示已连接列表");
            }
        });
        //panel2
        JScrollPane jsp = new JScrollPane();
        JTextArea jta_script = new JTextArea(6,40);
        jta_script.setEditable(false);
        jsp.setViewportView(jta_script);
        showVisiableCon(jta_script,jtf_listNum);
        //panel3
        JLabel pre_label = new JLabel("预选天数:");
        JTextField days_text = new JTextField(4);
        days_text.setText("30");
        JLabel desc_label = new JLabel("排序方式:");
        JComboBox jcbox = new JComboBox();
        jcbox.addItem("时间 网站");
        jcbox.addItem("网站 时间");
        JLabel query_label = new JLabel("内容筛选:");
        JTextField query_text = new JTextField(15);
        JButton jb_query = new JButton("查询");
        //panel4


        subPanel1.add(jl_netList);
        subPanel1.add(jtf_listNum);
        subPanel1.add(jb_showList);
        subPanel2.add(jsp);
        subPanel2.setVisible(false);
        subPanel2.setAlignmentX(Component.TOP_ALIGNMENT);

        subPanel3.add(pre_label);
        subPanel3.add(days_text);
        subPanel3.add(desc_label);

        subPanel3.add(jcbox);
        subPanel3.add(query_label);
        subPanel3.add(query_text);
        subPanel3.add(jb_query);

        panel.setLayout(new TableLayout(size));
        //panel.setLayout(new TableLayout(size));
        panel.add(subPanel1,"0,0");
        panel.add(subPanel2,"0,1");
        panel.add(subPanel3,"0,2");
        panel.add(subPanel4,"0,3");
        //panel.setLayout(new GridLayout(4,1,1,1));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 4. 把 面板容器 作为窗口的内容面板 设置到 窗口
        frame.setContentPane(panel);
        // 设置界面可见
        frame.setVisible(true);
    }

    private static void showVisiableCon(JTextArea query_text, JTextField jtf_listNum){
        StringBuilder builder = new StringBuilder();
        query_text.setText(builder.toString());
        List<UrlMonitorEntity> monitorEntities = netInformationDao.queryNetUrlEntity();
        for (UrlMonitorEntity entity : monitorEntities) {
            builder.append("网站名称：");
            builder.append(entity.getName());
            builder.append(" URL：");
            builder.append(entity.getConnectUrl());
            builder.append("\n");
        }
        jtf_listNum.setText(monitorEntities.size()+"");
        query_text.setText(builder.toString());
    }

}
