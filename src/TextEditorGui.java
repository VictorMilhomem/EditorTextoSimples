import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextEditorGui extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private JTextArea textArea;
    private JScrollPane ScrollPane;
    private JComboBox fontBox;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu fontSizeMenu;
    private JRadioButtonMenuItem colorButton;
    private JMenuItem[] sizeItems;
    private JMenuItem openItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;

    public TextEditorGui(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        //------- Menu Bar -----------//
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        //------- End Menu Bar -------//


        //------- Text Area ----------//
        ScrollPane.setPreferredSize(new Dimension(this.getWidth()-50, this.getHeight()-50));
        textArea.setForeground(Color.WHITE);
        textArea.setCaretColor(Color.WHITE);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        //------- End Text Area -----//

        //------- Font Size ---------//

        final int MAXFONTSIZE = 62;
        final int MINFONTSIZE = 6;

        fontSizeMenu = new JMenu("Font Size");
        sizeItems = new JMenuItem[15];
        int j = 0;
        for(int i = MINFONTSIZE; i <= MAXFONTSIZE; i += 4)
        {
            sizeItems[j] = new JMenuItem(String.valueOf(i));
            sizeItems[j].addActionListener(this);
            fontSizeMenu.add(sizeItems[j]);
            j++;
        }
        menuBar.add(fontSizeMenu);

        //------- End Font Size -----//

        //------- Color Button ------//
        colorButton = new JRadioButtonMenuItem("Color");
        colorButton.addActionListener(this);
        menuBar.add(colorButton);

        //------- End Color Button --//

        //------- Font Combo Box ------//

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String font : fonts) {
            fontBox.addItem(font);
        }
        fontBox.setSelectedItem(fonts[0]);
        fontBox.addActionListener(this);


        //------- End Font Combo Box -//

        this.setJMenuBar(menuBar);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (colorButton.equals(e.getSource())) {
            JColorChooser colorChooser = new JColorChooser();
            Color color = colorChooser.showDialog(null, "Choose the font color", Color.BLACK);

            textArea.setForeground(color);
        }

        if (fontBox.equals(e.getSource())){
            textArea.setFont(new Font((String)fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));
        }

        if (openItem.equals(e.getSource())){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
            fileChooser.setFileFilter(filter);

            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;

                try {
                    fileIn = new Scanner(file);
                    if (file.isFile()){
                        while(fileIn.hasNextLine()){
                            String line = fileIn.nextLine()+"\n";
                            textArea.append(line);
                        }
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                finally {
                    fileIn.close();
                }
            }
        }

        if (saveItem.equals(e.getSource())){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int response = fileChooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION){
                File file;
                PrintWriter fileOut = null;

                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    fileOut = new PrintWriter(file);
                    fileOut.println(textArea.getText());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                finally {
                    fileOut.close();
                }

            }
        }

        if (exitItem.equals(e.getSource())){
            System.exit(0);
        }

        for(JMenuItem item : sizeItems)
        {
            if (item.equals(e.getSource())){
                textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, Integer.parseInt(item.getText())));
            }
        }
    }

}
