import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TreeView {

    private JFrame frame;
    // private JPanel panel;
    private JButton deleteTreeButton,createButton, deleteButton, insertButton, searchButton, persistButton, loadButton;
    private TextArea keyTextField;
    private TreeNode rootNode;

    public TreeView() {
        frame = new JFrame("Tree GUI");
        createButton = new JButton("Crear arbol random");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rootNode != null) {
                    JOptionPane.showMessageDialog(frame, "Ya existe un arbol");
                } else {
                    rootNode = EspecificFunctionsTrees.createRandomTree(2, null);
                    keyTextField.setText(EspecificFunctionsTrees.getTreeString(rootNode));
                    JOptionPane.showMessageDialog(frame, "Arbol creado");

                }
            }
        });

        deleteTreeButton = new JButton("Eliminar el arbol");
        deleteTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rootNode != null) {
                    rootNode=null;
                    keyTextField.setText("");
                    JOptionPane.showMessageDialog(frame, "Arbol eliminado");
                }else{
                    JOptionPane.showMessageDialog(frame, "No existe un arbol");
                }
            }
        });

        deleteButton = new JButton("Eliminar nodo");

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rootNode == null) {
                    JOptionPane.showMessageDialog(frame, "No creaste ningun arbol todavia");
                } else {
                    try{
                        int keyToDelete = Integer.parseInt(JOptionPane.showInputDialog("Ingresa key a eliminar"));
                        rootNode = EspecificFunctionsTrees.deleteNode(keyToDelete, rootNode);
                        keyTextField.setText(EspecificFunctionsTrees.getTreeString(rootNode));
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(frame,"No se pudo leer el numero");
                    }
                }
            }
        });

        insertButton = new JButton("Insertar nodo");
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rootNode == null) {
                    JOptionPane.showMessageDialog(frame, "No creaste ningun arbol todavia");
                } else {
                    try{

                        int newNodeKey = Integer.parseInt(JOptionPane.showInputDialog("Ingresa un numero para insertar"));
                        int dadKey = Integer.parseInt(JOptionPane.showInputDialog("Ingresa la key del padre"));
                        EspecificFunctionsTrees.insertNode(newNodeKey, dadKey, rootNode);
                        keyTextField.setText(EspecificFunctionsTrees.getTreeString(rootNode));
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(frame,"No se pudo leer el numero");
                    }
                }
            }
        });

        searchButton = new JButton("Buscar nodo");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rootNode == null) {
                    JOptionPane.showMessageDialog(frame, "No creaste ningun arbol todavia");
                } else {
                    try{
                        int numero = Integer
                                .parseInt(JOptionPane.showInputDialog("\n" + "Ingresa un numero para buscar por favor"));
                        TreeNode nodeSearched = EspecificFunctionsTrees.findNodeByKey(rootNode, numero);
                        if (nodeSearched != null) {
                            JOptionPane.showMessageDialog(null, "Existe");
                        } else {
                            JOptionPane.showMessageDialog(null, "No Existe");
                        }
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(frame,"No se pudo leer el numero");
                    }

                }

            }
        });

        persistButton = new JButton("Persistir arbol");
        persistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rootNode == null) {
                    JOptionPane.showMessageDialog(frame, "No creaste ningun arbol todavia");
                } else {
                    EspecificFunctionsTrees.persistTree(frame, rootNode);
                }

            }
        });

        loadButton = new JButton("cargar arbol de archivo");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootNode = EspecificFunctionsTrees.getTreeFromXML();
                keyTextField.setText(EspecificFunctionsTrees.getTreeString(rootNode));
            }
        });

        keyTextField = new TextArea("Arbol");
        keyTextField.setPreferredSize(new Dimension(150, 25));

        keyTextField.setEditable(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        Box box = Box.createVerticalBox();
        box.add(createButton);
        box.add(Box.createVerticalStrut(10));
        box.add(deleteTreeButton);
        box.add(Box.createVerticalStrut(10));
        box.add(searchButton);
        box.add(Box.createVerticalStrut(10));
        box.add(insertButton);
        box.add(Box.createVerticalStrut(10));
        box.add(deleteButton);
        box.add(Box.createVerticalStrut(10));
        box.add(loadButton);
        box.add(Box.createVerticalStrut(10));
        box.add(persistButton);
        box.add(Box.createVerticalStrut(10));
        box.add(keyTextField);
        buttonPanel.add(box);

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(buttonPanel);

        // Calcular tamaño medio según la pantalla del usuario
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int frameWidth = screenWidth / 2;
        int frameHeight = screenHeight / 2;

        frame.setTitle("Arboles");
        frame.setLocationRelativeTo(null);
        frame.setBounds(frameWidth / 2, frameHeight / 2, frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public void setCreateButton(JButton createButton) {
        this.createButton = createButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public JButton getInsertButton() {
        return insertButton;
    }

    public void setInsertButton(JButton insertButton) {
        this.insertButton = insertButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(JButton searchButton) {
        this.searchButton = searchButton;
    }

    public JButton getPersistButton() {
        return persistButton;
    }

    public void setPersistButton(JButton persistButton) {
        this.persistButton = persistButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public void setLoadButton(JButton loadButton) {
        this.loadButton = loadButton;
    }

    public TextArea getKeyTextField() {
        return keyTextField;
    }

    public void setKeyTextField(TextArea keyTextField) {
        this.keyTextField = keyTextField;
    }

    public TreeNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(TreeNode rootNode) {
        this.rootNode = rootNode;
    }
}
