package org.portofolio;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NoteWindow extends JFrame implements  ActionListener, WindowListener, MouseListener {

    JTextArea textArea;
    File file;
    JPopupMenu popupmenu;
    JScrollPane scrollPaneText;

    public NoteWindow(){
        //Set font
        Font fnt = new Font("Arial", Font.PLAIN,20);

        //Create and setup text area
        textArea = new JTextArea();
        textArea.setFont(fnt);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        //Create scrolabale text area
        scrollPaneText = new JScrollPane(textArea);
        scrollPaneText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneText.setVisible(true);

        //Create menuBar and tabs
        JMenuBar menuBar = new JMenuBar();

        JMenu jmfile = new JMenu("File");
        JMenu jmedit = new JMenu("Edit");
        JMenu jmhelp = new JMenu("Help");

        //Create File menu buttons
        createMenuItem(jmfile, "New");
        createMenuItem(jmfile, "Open");
        createMenuItem(jmfile, "Save");
        jmfile.addSeparator();
        createMenuItem(jmfile, "Exit");

        //Create Edit menu buttons
        createMenuItem(jmedit, "Cut");
        createMenuItem(jmedit, "Copy");
        createMenuItem(jmedit, "Paste");

        //Create Help menu buttons
        createMenuItem(jmhelp, "About Note App");

        //Add menu bar tabs
        menuBar.add(jmfile);
        menuBar.add(jmedit);
        menuBar.add(jmhelp);
        setJMenuBar(menuBar);

        //PopUp menu : create, add ,setup muouse listener
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

        //Add to text area PopUp menu and Mouse Listeners
        textArea.add(popupmenu);
        textArea.addMouseListener(this);

        container.add(scrollPaneText);

        //Frame properties
        setIconImage(Toolkit.getDefaultToolkit().getImage("NoteAppIcon.png"));
        addWindowListener(this);
        setSize(500,500);
        setTitle("Untitled.txt - Note App");
        setVisible(true);

    }

    //Method which create a new menu item
    public void createMenuItem( JMenu jm, String txt){
        JMenuItem jmi = new JMenuItem(txt);
        jmi.addActionListener(this);
        jm.add(jmi);
    }


    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (e.getActionCommand().equals("New")) {
            this.setTitle("Utitled.txt - Note App");
            textArea.setText("");
            file = null;
        } else if (e.getActionCommand().equals("Open")) {
            int ret = fileChooser.showDialog(null, "Open");
            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    File selectedFile = fileChooser.getSelectedFile();
                    OpenFile(selectedFile.getAbsolutePath());
                    this.setTitle(selectedFile.getName() + " - Note App");
                    file = selectedFile;
                } catch (IOException ex) {
                    ex.printStackTrace();
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
                    this.setTitle(selectedFile.getName() + " - Notea App");
                    file = selectedFile;
                } catch (Exception ex) {
                    ex.printStackTrace();
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
        } else if (e.getActionCommand().equals("About Note App")) {
            JOptionPane.showMessageDialog(this, "Created by: Alexandru Firica", "Note App", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    //Method used to open a file
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

    //Method used to save a file
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
        if( e.getButton() == MouseEvent.BUTTON3) {
            popupmenu.show(textArea, e.getX(), e.getY());
        }
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