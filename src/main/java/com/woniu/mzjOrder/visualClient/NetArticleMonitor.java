package com.woniu.mzjOrder.visualClient;

import javax.swing.*;
import java.awt.*;

public class NetArticleMonitor {


    public static void main(String[] args){
        initFrame();
    }

    /**
     * 初始化界面
     */
    private static void initFrame() {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("Net article monitor");
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

        // 3. 创建基本组件（按钮），并添加到各自的面板容器中
        JLabel jl_netList = new JLabel("Listened List:");
        JTextField jtf_listNum = new JTextField(3);
        jtf_listNum.setText("2");
        JButton jb_showList = new JButton("显示已连接列表");

        subPanel1.add(jl_netList);
        subPanel1.add(jtf_listNum);
        subPanel1.add(jb_showList);


        panel.add(subPanel1);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 4. 把 面板容器 作为窗口的内容面板 设置到 窗口
        frame.setContentPane(panel);
        // 设置界面可见
        frame.setVisible(true);
    }

}
