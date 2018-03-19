package main.t01;

import main.t01.model.DirectoryChooser;
import main.t01.model.TxtChanger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Arrays;

/**
 * Created by Ekaterina Semenova on 18.03.2018.
 */
public class TextViewer extends JFrame {
    private JButton openButton = new JButton("Open");
    private JFileChooser mainFileChooser = new JFileChooser();
    private JTextField directory;
    private JTextArea textArea;
    private JList<String> stringJList;
    private DefaultListModel<String> defaultListModel;
    private JButton addButton = new JButton("add");
    private JTextField textField;
    private final String TITLE = "Text viewer";
    private JButton deleteButton = new JButton("delete");
    private JButton createButton = new JButton("create");
    private JTextField createNewFileField;
    private DirectoryChooser[] directoryChooser = new DirectoryChooser[1];
    final TxtChanger[] txtChanger = new TxtChanger[1];


    public TextViewer() {
        setTitle(TITLE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initInterface();
        initInteraction();

        setVisible(true);
    }

    private void initInterface() {

        JPanel panelList = new JPanel(new BorderLayout());
        setLayout(new BorderLayout());
        stringJList = new JList<>();
        stringJList.setFixedCellWidth(200);
        panelList.add(new JScrollPane(stringJList), BorderLayout.CENTER);
        JPanel panelCreateFile = new JPanel(new BorderLayout());
        panelCreateFile.add(createButton, BorderLayout.SOUTH);
        createNewFileField = new JTextField("write new file name");
        panelCreateFile.add(createNewFileField, BorderLayout.CENTER);
        panelList.add(panelCreateFile, BorderLayout.SOUTH);
        add(panelList, BorderLayout.WEST);
        defaultListModel = new DefaultListModel<>();
        stringJList.setModel(defaultListModel);
        defaultListModel.addElement(null);


        ListCellRenderer<Object> renderer = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                String path = (String) value;

                if (path == null) {
                    label.setText("Choose a folder");
                    return label;
                }

                File file = new File(path);
                String name = file.getName();
                long bytes = file.length();
                double bytesDouble = (double) bytes;

                String size = "bytes";
                if (bytes > 1000) {
                    bytesDouble /= 1024;
                    size = "kilobytes";
                    if (bytes > 2000) {
                        bytesDouble /= 1024;
                        size = "megabytes";
                    }
                    bytesDouble = Math.round(bytesDouble * 100) / 100;
                }

                label.setText(
                        "<html>" + name +
                                "<span>"
                                + "</span>" +
                                "<br> размер: " + bytesDouble + " " + size +
                                "</html>");

                return label;
            }
        };
        stringJList.setCellRenderer(renderer);

        textArea = new JTextArea();
        textArea.setSize(new Dimension(200, 400));
        textArea.setEditable(false);
        textArea.setBorder(new BasicBorders.FieldBorder(Color.GRAY, Color.BLACK, Color.BLUE, Color.WHITE));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel panelForOpenDirectory = new JPanel(new BorderLayout());
        panelForOpenDirectory.add(openButton, BorderLayout.EAST);
        directory = new JTextField();
        directory.setEditable(false);
        panelForOpenDirectory.add(directory, BorderLayout.CENTER);
        add(panelForOpenDirectory, BorderLayout.NORTH);

        JPanel panelAddText = new JPanel(new BorderLayout());
        textField = new JTextField();
        textField.setSize(500, 100);
        panelAddText.add(textField, BorderLayout.CENTER);
        JPanel panelButton = new JPanel(new BorderLayout());
        panelButton.add(addButton, BorderLayout.WEST);
        panelButton.add(deleteButton, BorderLayout.EAST);
        panelAddText.add(panelButton, BorderLayout.EAST);
        add(panelAddText, BorderLayout.SOUTH);

    }

    private void initInteraction() {
        mainFileChooser.addChoosableFileFilter(
                new FileNameExtensionFilter("Текстовые файлы", "txt")
        );

        mainFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        openButton.addActionListener(e -> {
            if (mainFileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
                return;

            directoryChooser[0] = new DirectoryChooser(mainFileChooser.getSelectedFile().getPath());

            directory.setText(directoryChooser[0].getDirectoryPath());

            File[] files = directoryChooser[0].getFilesArray(".txt");
            defaultListModel.clear();
            Arrays.stream(files).forEach(file -> defaultListModel.addElement(file.getAbsolutePath()));
            if (files.length < 1) {
                defaultListModel.addElement("NO *.txt FILES");
            }

        });
        stringJList.addListSelectionListener(event -> {
            String name = stringJList.getSelectedValue();
            if (name == null) {
                setTitle(TITLE);
                return;
            }

            txtChanger[0] = new TxtChanger(name);
            textArea.setText(txtChanger[0].getText());

            setTitle(TITLE + " - " + name);

        });

        ActionListener actionForTextField = event -> {
            String text = textField.getText();
            if (txtChanger[0] != null) {
                txtChanger[0].addLine(text);
                textArea.setText(txtChanger[0].getText());
            }
            textField.setText("");
        };

        addButton.addActionListener(actionForTextField);
        textField.addActionListener(actionForTextField);
        createNewFileField.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createNewFileField.setText("");
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
        });
        createButton.addActionListener(event -> {

            if (!directoryChooser[0].createNewFile(createNewFileField.getText())) {
                createNewFileField.setText("Failed while creating");
            } else {
                createNewFileField.setText("");
            }

            defaultListModel.clear();
            Arrays.stream(directoryChooser[0].getFilesArray("txt"))
                    .forEach(file -> defaultListModel.addElement(file.getAbsolutePath()));
        });

        deleteButton.addActionListener(event -> {
            System.out.println(txtChanger[0].getFile().getAbsolutePath());
            File newFile = txtChanger[0].getFile();

            if (newFile.delete()) {
                defaultListModel.clear();
                Arrays.stream(directoryChooser[0].getFilesArray("txt"))
                        .forEach(file -> defaultListModel.addElement(file.getAbsolutePath()));
                textArea.setText("");
            } else {
                System.out.println("fuck");
            }
        });
    }


    public static void main(String[] args) {
        new TextViewer();
    }
}
