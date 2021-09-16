import javax.swing.*;

public class Main {
    public static void main(String[] args)
    {
        JFrame frame = new TextEditorGui("Notepad");
        frame.setBounds(0, 0, 700, 700);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
