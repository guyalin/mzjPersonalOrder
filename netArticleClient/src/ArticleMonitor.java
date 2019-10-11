import javax.swing.*;

public class ArticleMonitor {
    private JPanel NetMonitor;
    private JLabel labelIp;
    private JTextField textIp;
    private JPanel subPanel1;
    private JLabel labelPort;
    private JTextField textPort;
    private JButton btnCon;
    private JPanel subPanel2;
    private JLabel conStats;
    private JPanel subPanel3;
    private JTextArea monitored;
    private JLabel watchNet;
    private JTextField text_days;
    private JPanel subPanel4;
    private JLabel label_days;
    private JLabel label_desc;
    private JComboBox comboBox1;
    private JLabel label_filter;
    private JTextField textField1;
    private JButton btn_query;
    private JTextPane textPanel;
    private JTextField textField2;
    private JButton btn_download;
    private JLabel label_path;
    private JTextField text_fileName;
    private JLabel label_fileName;
    private JLabel down_stats;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ArticleMonitor");


        frame.setContentPane(new ArticleMonitor().NetMonitor);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
