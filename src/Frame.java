import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Frame extends JFrame {
    private JPanel onePanel;
    private JTextArea leftArea;
    private JTextArea rightArea;
    private JButton uploadFile;
    private JButton clean;
    private JButton copyFile;
    private JButton saveFile;
    private JButton extract;
    private JLabel entryField;
    private JLabel result;
    private JComboBox select;
    private JButton exit;
    private JButton maxWindow;
    private JButton iconfied;
    private JPanel top;

    private int px;
    private int py;

    private final ReadFile readFile;


    public Frame() throws HeadlessException {

        readFile = new ReadFile();
        setUndecorated(true);
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(onePanel);


        uploadFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int ret = fileChooser.showDialog(null, "Open file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    leftArea.setText(readFile.readers(file.getAbsolutePath()));
                }
            }

        });
        extract.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (select.getSelectedIndex()) {
                    case 1:
                        rightArea.setText(readFile.extractEmail(leftArea.getText()));
                        break;
                    case 2:
                        rightArea.setText(readFile.extractPhone(leftArea.getText()));
                        break;
                    case 3:
                        rightArea.setText(readFile.extractURL(leftArea.getText()));
                }
            }
        });

        clean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftArea.setText(readFile.clean(new StringBuilder(leftArea.getText())));
            }
        });


        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Saving a file");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION)
                    JOptionPane.showMessageDialog(null,
                            "File saved in " + fileChooser.getSelectedFile());

                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()))) {

                    String[] split = new String[0];

                    if (select.getSelectedIndex() == 1) {
                        split = rightArea.getText().split(" ");
                    } else if (select.getSelectedIndex() == 2) {
                        split = rightArea.getText().split("(?<=\\d$)");
                    } else if (select.getSelectedIndex() == 3) {
                        split = rightArea.getText().split(" ");
                    }
                    for (String s : split) {
                        bufferedWriter.write(s);
                        bufferedWriter.newLine();
                        bufferedWriter.newLine();
                    }

                } catch (NullPointerException | IOException n) {
                    System.out.println("(Cancel file saving) " + n);
                }
            }
        });

        copyFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection stringSelection = new StringSelection(rightArea.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });
        top.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                px = e.getX();
                py = e.getY();
            }
        });

        top.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(x - px, y - py);
            }
        });
        maxWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getExtendedState() == NORMAL)
                    setExtendedState(MAXIMIZED_BOTH);
                else
                    setExtendedState(NORMAL);

            }
        });
        iconfied.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(ICONIFIED);
            }
        });

        maxWindow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                maxWindow.setBackground(new Color(151, 154, 156));

            }
        });
        maxWindow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                maxWindow.setBackground(new Color(121, 123, 125));
            }
        });
        iconfied.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                iconfied.setBackground(new Color(151, 154, 156));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                iconfied.setBackground(new Color(121, 123, 125));
            }
        });

    }

    public void windowResize() {
        ComponentResizer cr = new ComponentResizer();
        cr.setMinimumSize(new Dimension(100, 30));
        cr.setMaximumSize(new Dimension(1360, 768));
        cr.registerComponent(this);
        cr.setSnapSize(new Dimension(5, 5));
        pack();
        setSize(950, 560);
        setLocationRelativeTo(null);
    }
}
