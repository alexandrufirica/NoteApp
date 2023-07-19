package org.portofolio;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NoteWindow extends JFrame implements  ActionListener, WindowListener, MouseListener {

    JTextArea textArea = new JTextArea();
    File file;
    JPopupMenu popupmenu;
    JScrollPane scrollPaneText;

    public NoteWindow(){

        Font fnt = new Font("Arial", Font.PLAIN,20);
        Container container = getContentPane();
        JMenuBar menuBar = new JMenuBar();

        JMenu jmfile = new JMenu("FIle");
        JMenu jmedit = new JMenu("Edit");
        JMenu jmhelp = new JMenu("Help");

        container.setLayout(new BorderLayout());
        scrollPaneText = new JScrollPane(textArea);
        scrollPaneText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneText.setVisible(true);

        textArea.setFont(fnt);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        container.add(scrollPaneText);

        createMenuItem(jmfile, "New");
        createMenuItem(jmfile, "Open");
        createMenuItem(jmfile, "Save");
        jmfile.addSeparator();
        createMenuItem(jmfile, "Exit");

        createMenuItem(jmedit, "Cut");
        createMenuItem(jmedit, "Copy");
        createMenuItem(jmedit, "Paste");

        createMenuItem(jmhelp, "About NotePad");

        popupmenu = new JPopupMenu("Edit");
        JMenuItem cut = new JMenuItem("Cut");
        cut.addActionListener(this);
        JMenuItem copy = new JMenuItem("Copy");
        copy.addActionListener(this);
        JMenuItem paste = new JMenuItem("Paste");
        paste.addActionListener(this);
        popupmenu.add(cut);
        popupmenu.add(copy);
        popupmenu.add(paste);
        textArea.add(popupmenu);
        textArea.addMouseListener(this);


        menuBar.add(jmfile);
        menuBar.add(jmedit);
        menuBar.add(jmhelp);

        setJMenuBar(menuBar);

        setIconImage(Toolkit.getDefaultToolkit().getImage("NoteAppIcon.png"));
        addWindowListener(this);
        setSize(500,500);
        setTitle("Untitled.txt - NotePad");
        setVisible(true);

    }

    public void createMenuItem( JMenu jm, String txt){
        JMenuItem jmi = new JMenuItem(txt);
        jmi.addActionListener(this);
        jm.add(jmi);
    }

    public void createMenuItem( JPopupMenu jm, String txt){
        JMenuItem jmi = new JMenuItem(txt);
        jmi.addMouseListener(this);
        jm.add(jmi);
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (e.getActionCommand().equals("New")) {
            this.setTitle("Utitled.txt - NotePad");
            textArea.setText("");
            file = null;
        } else if (e.getActionCommand().equals("Open")) {
            int ret = fileChooser.showDialog(null, "Open");
            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    File selectedFile = fileChooser.getSelectedFile();
                    OpenFile(selectedFile.getAbsolutePath());
                    this.setTitle(selectedFile.getName() + " - NotePad");
                    file = selectedFile;
                } catch (IOException ets) {

                }
            }
        } else if (e.getActionCommand().equals("Save")) {
            if (file != null) {
                fileChooser.setCurrentDirectory(file);
                fileChooser.setSelectedFile(file);
            } else {
                fileChooser.setSelectedFile(new File("Untitled.txt"));
            }

            int ret = fileChooser.showSaveDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    File selectedFile = fileChooser.getSelectedFile();
                    SaveFile(selectedFile.getAbsolutePath());
                    this.setTitle(selectedFile.getName() + " - NotePad");
                    file = selectedFile;
                } catch (Exception ete) {

                }
            }
        } else if (e.getActionCommand().equals("Exit")) {
            Exiting();
        } else if (e.getActionCommand().equals("Copy")) {
            textArea.copy();
        } else if (e.getActionCommand().equals("Paste")) {
            textArea.paste();
        } else if (e.getActionCommand().equals("Cut")) {
            textArea.cut();
        } else if (e.getActionCommand().equals("About NotePad")) {
            JOptionPane.showMessageDialog(this, "Created by: Alexandru Firica", "NotePad", JOptionPane.INFORMATION_MESSAGE);
        }else if (e.getActionCommand().equals(MouseEvent.MOUSE_CLICKED)){


        }


    }

    public void OpenFile (String fname) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
        String load;
        textArea.setText("");
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        while((load =reader.readLine()) != null){
            textArea.setText(textArea.getText() + load + "\r\n");

        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        reader.close();
    }

    public void SaveFile(String fname) throws IOException{
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataOutputStream output = new DataOutputStream(new FileOutputStream(fname));
        output.writeBytes(textArea.getText());
        output.close();
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void windowDeactivated(WindowEvent e){}

    public void windowActivated(WindowEvent e){}

    public void windowDeiconified(WindowEvent e){}

    public void windowIconified(WindowEvent e){}

    public void windowClosed(WindowEvent e){}

    public  void windowClosing(WindowEvent e){
        Exiting();
    }
    public void windowOpened(WindowEvent e){}

    public void Exiting(){
        System.exit(0);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

            popupmenu.show(textArea, e.getX(), e.getY());

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}