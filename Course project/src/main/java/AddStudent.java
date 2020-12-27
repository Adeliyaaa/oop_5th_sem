import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddStudent extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField lastname;
    private JTextField surname;
    private JTextField name;
    private JLabel errorLabel;


    public AddStudent(addedListener listener) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        errorLabel.setForeground(Color.red);
        errorLabel.setVisible(false);
        setBounds(340, 110, 300, 200);

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name.getText().equals("") || surname.getText().equals("")){
                    errorLabel.setVisible(true);
                }
                else{
                    Student temp = new Student(name.getText(), surname.getText(), lastname.getText());
                    listener.onAdded(temp);
                    errorLabel.setVisible(false);
                    dispose();
                }

            }
        });
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
