package gui.listener;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
 
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
 
import gui.panel.BackupPanel;
import gui.panel.ConfigPanel;
import gui.panel.MainPanel;
import service.ConfigService;
import util.MysqlUtil;
 
public class BackupListener implements ActionListener {
 
    @Override
    public void actionPerformed(ActionEvent e) {
        BackupPanel p  =BackupPanel.instance;
        String mysqlPath= new ConfigService().get(ConfigService.mysqlPath);
        if(0==mysqlPath.length()){
            JOptionPane.showMessageDialog(p, "Please configure Mysql installation path first!");
            MainPanel.instance.workingPanel.show(ConfigPanel.instance);
            ConfigPanel.instance.tfMysqlPath.grabFocus();
            return;
        }
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new File("hutubill.sql"));
        fc.setFileFilter(new FileFilter() {
             
            @Override
            public String getDescription() {
                return ".sql";
            }
             
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".sql");
            }
        });
 
         int returnVal =  fc.showSaveDialog(p);
         File file = fc.getSelectedFile();
         System.out.println(file);
         if (returnVal == JFileChooser.APPROVE_OPTION) {
             //如果保存的文件名没有以.sql结尾，自动加上.sql
             System.out.println(file);
             if(!file.getName().toLowerCase().endsWith(".sql"))
                 file = new File(file.getParent(),file.getName()+".sql");
             System.out.println(file);
              
            try {
                MysqlUtil.backup(mysqlPath, file.getAbsolutePath());
                JOptionPane.showMessageDialog(p, "Backup successfully, backup file locates:\r\n"+file.getAbsolutePath());
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(p, "Backup Failure\r\n, ERROR:\r\n"+e1.getMessage());   
            }
              
         }      
    }
 
}