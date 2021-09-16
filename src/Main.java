import javax.swing.*;

public class Main {
    public static void main(String[] args)
    {
        JFrame frame = new TextEditorGui("Editor de Texto");
        frame.setBounds(0, 0, 700, 700);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
