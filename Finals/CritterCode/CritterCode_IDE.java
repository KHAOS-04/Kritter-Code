package Finals.CritterCode;

/**
 * @author Kia
 */
import Finals.CritterCode.lang.Lexer;
import Finals.CritterCode.lang.Parser;
import Finals.CritterCode.lang.Interpreter;
import Finals.CritterCode.ast.ProgramNode;
import Finals.CritterCode.lang.Token;

import java.util.*;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.undo.UndoManager;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
public class CritterCode_IDE extends javax.swing.JFrame {

    public CritterCode_IDE() {
        initComponents();
        setSize(2080, 1440); 
        setTitle("CritterCode");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on screen
        setVisible(true);
        
        
        try{
            File fontStyle = new File("src/resources/fonts/Tiny5-Regular.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontStyle).deriveFont(36f);
            jLabelTitle.setFont(font);
            jLabel_Output.setFont(font);
            jLabel_Input.setFont(font);
            jButton_Run.setFont(font);
            jButton_Save.setFont(font);
            jButton_Open.setFont(font);
            jButton_Erase.setFont(font);
            jButton_Undo.setFont(font);
            jButton_Redo.setFont(font);
            jButton_Guide.setFont(font);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        defaultButtonFont = jButton_Undo.getFont();
        
        jTextArea_CodingArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });
    }
    
        public static void playMusic(String filePath) {
        try {
            File musicPath = new File(filePath);

            if (musicPath.exists()) {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(musicPath));
                clip.start();
                // Optional: For looping, use clip.loop(Clip.LOOP_CONTINUOUSLY);

                // Keep the program alive while music plays (if not in a long-running GUI)
                // JOptionPane.showMessageDialog(null, "Playing music..."); 
            } else {
                System.out.println("Can't find file: " + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    // =========================================
    //      CRITTER CODE - RUN FUNCTION
    // =========================================
    private void runCritterCode() {
        try {
            String code = jTextArea_CodingArea.getText();

            // LEXER
            Lexer lexer = new Lexer();
            List<Token> tokens = lexer.tokenize(code);

            // PARSER
            Parser parser = new Parser();
            ProgramNode program = parser.parse(tokens);

            // INTERPRETER
            Interpreter interpreter = new Interpreter();
            String output = interpreter.run(program);

            // DISPLAY OUTPUT
            jTextArea_OutputArea.setText(output);

        } catch (Exception e) {
            jTextArea_OutputArea.setText("Error:\n" + e.getMessage());
            jTextArea_TerminalArea.setText("There's been some\nmisunderstanding");
            e.printStackTrace();
        }

    }
    
    private Font defaultButtonFont;
    private boolean isRunning = false;
    private UndoManager undoManager = new UndoManager();

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_IDE_frame = new javax.swing.JPanel();
        jPanel_IDE_tab = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jButton_Exit = new javax.swing.JButton();
        jPanel_LowerNavBar = new javax.swing.JPanel();
        jButton_Run = new javax.swing.JButton();
        jButton_Save = new javax.swing.JButton();
        jButton_Open = new javax.swing.JButton();
        jButton_Erase = new javax.swing.JButton();
        jButton_Undo = new javax.swing.JButton();
        jButton_Redo = new javax.swing.JButton();
        jButton_Info = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea_CodingArea = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel_Output = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea_OutputArea = new javax.swing.JTextArea();
        jLabel_Screen = new javax.swing.JLabel();
        jLabel_Egg = new javax.swing.JLabel();
        jButton_Submit_Input = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea_Input = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel_Input = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea_TerminalArea = new javax.swing.JTextArea();
        jButton_Guide = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel_IDE_frame.setBackground(new java.awt.Color(200, 185, 224));
        jPanel_IDE_frame.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 55, 136), 8));
        jPanel_IDE_frame.setPreferredSize(new java.awt.Dimension(2080, 1440));
        jPanel_IDE_frame.setLayout(null);

        jPanel_IDE_tab.setBackground(new java.awt.Color(194, 161, 255));
        jPanel_IDE_tab.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 55, 136), 4));

        jLabelTitle.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(75, 55, 136));
        jLabelTitle.setText("KRITTER CODE IDE");

        jButton_Exit.setBackground(new java.awt.Color(204, 204, 255));
        jButton_Exit.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jButton_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Finals/Images/Exit.png"))); // NOI18N
        jButton_Exit.setBorderPainted(false);
        jButton_Exit.setContentAreaFilled(false);
        jButton_Exit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Exit.setFocusPainted(false);
        jButton_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_IDE_tabLayout = new javax.swing.GroupLayout(jPanel_IDE_tab);
        jPanel_IDE_tab.setLayout(jPanel_IDE_tabLayout);
        jPanel_IDE_tabLayout.setHorizontalGroup(
            jPanel_IDE_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_IDE_tabLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabelTitle)
                .addContainerGap(1827, Short.MAX_VALUE))
            .addGroup(jPanel_IDE_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_IDE_tabLayout.createSequentialGroup()
                    .addContainerGap(1986, Short.MAX_VALUE)
                    .addComponent(jButton_Exit)
                    .addGap(10, 10, 10)))
        );
        jPanel_IDE_tabLayout.setVerticalGroup(
            jPanel_IDE_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_IDE_tabLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(jPanel_IDE_tabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_IDE_tabLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jButton_Exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel_IDE_frame.add(jPanel_IDE_tab);
        jPanel_IDE_tab.setBounds(0, 0, 2080, 80);

        jPanel_LowerNavBar.setBackground(new java.awt.Color(200, 185, 224));
        jPanel_LowerNavBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 55, 136), 4));
        jPanel_LowerNavBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel_LowerNavBarMouseExited(evt);
            }
        });

        jButton_Run.setBackground(new java.awt.Color(200, 185, 224));
        jButton_Run.setFont(new java.awt.Font("Dialog", 0, 32)); // NOI18N
        jButton_Run.setForeground(new java.awt.Color(75, 55, 136));
        jButton_Run.setIcon(new javax.swing.ImageIcon("C:\\Users\\Kia\\Documents\\NetBeansProjects\\ICT 115\\src\\Finals\\Images\\ON.png")); // NOI18N
        jButton_Run.setText(" RUN");
        jButton_Run.setBorderPainted(false);
        jButton_Run.setContentAreaFilled(false);
        jButton_Run.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Run.setFocusPainted(false);
        jButton_Run.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton_Run.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_RunMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_RunMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_RunMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton_RunMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton_RunMouseReleased(evt);
            }
        });
        jButton_Run.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RunActionPerformed(evt);
            }
        });

        jButton_Save.setBackground(new java.awt.Color(200, 185, 224));
        jButton_Save.setFont(new java.awt.Font("Dialog", 0, 32)); // NOI18N
        jButton_Save.setForeground(new java.awt.Color(75, 55, 136));
        jButton_Save.setIcon(new javax.swing.ImageIcon("C:\\Users\\Kia\\Documents\\NetBeansProjects\\ICT 115\\src\\Finals\\Images\\Interface-Essential-Floppy-Disk--Streamline-Pixel.png")); // NOI18N
        jButton_Save.setText(" SAVE");
        jButton_Save.setBorderPainted(false);
        jButton_Save.setContentAreaFilled(false);
        jButton_Save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Save.setFocusPainted(false);
        jButton_Save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_SaveMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_SaveMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton_SaveMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton_SaveMouseReleased(evt);
            }
        });
        jButton_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SaveActionPerformed(evt);
            }
        });

        jButton_Open.setBackground(new java.awt.Color(200, 185, 224));
        jButton_Open.setFont(new java.awt.Font("Dialog", 0, 32)); // NOI18N
        jButton_Open.setForeground(new java.awt.Color(75, 55, 136));
        jButton_Open.setIcon(new javax.swing.ImageIcon("C:\\Users\\Kia\\Documents\\NetBeansProjects\\ICT 115\\src\\Finals\\Images\\NEW_FILE.png")); // NOI18N
        jButton_Open.setText(" OPEN");
        jButton_Open.setBorderPainted(false);
        jButton_Open.setContentAreaFilled(false);
        jButton_Open.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Open.setFocusPainted(false);
        jButton_Open.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_OpenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_OpenMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton_OpenMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton_OpenMouseReleased(evt);
            }
        });
        jButton_Open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_OpenActionPerformed(evt);
            }
        });

        jButton_Erase.setBackground(new java.awt.Color(200, 185, 224));
        jButton_Erase.setFont(new java.awt.Font("Dialog", 0, 32)); // NOI18N
        jButton_Erase.setForeground(new java.awt.Color(75, 55, 136));
        jButton_Erase.setIcon(new javax.swing.ImageIcon("C:\\Users\\Kia\\Documents\\NetBeansProjects\\ICT 115\\src\\Finals\\Images\\ERASE.png")); // NOI18N
        jButton_Erase.setText("ERASE");
        jButton_Erase.setBorderPainted(false);
        jButton_Erase.setContentAreaFilled(false);
        jButton_Erase.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Erase.setFocusPainted(false);
        jButton_Erase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_EraseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_EraseMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton_EraseMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton_EraseMouseReleased(evt);
            }
        });
        jButton_Erase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EraseActionPerformed(evt);
            }
        });

        jButton_Undo.setBackground(new java.awt.Color(200, 185, 224));
        jButton_Undo.setFont(new java.awt.Font("Dialog", 0, 32)); // NOI18N
        jButton_Undo.setForeground(new java.awt.Color(75, 55, 136));
        jButton_Undo.setIcon(new javax.swing.ImageIcon("C:\\Users\\Kia\\Documents\\NetBeansProjects\\ICT 115\\src\\Finals\\Images\\REDO.png")); // NOI18N
        jButton_Undo.setText(" UNDO");
        jButton_Undo.setBorderPainted(false);
        jButton_Undo.setContentAreaFilled(false);
        jButton_Undo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Undo.setFocusPainted(false);
        jButton_Undo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_UndoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_UndoMouseExited(evt);
            }
        });
        jButton_Undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_UndoActionPerformed(evt);
            }
        });

        jButton_Redo.setBackground(new java.awt.Color(200, 185, 224));
        jButton_Redo.setFont(new java.awt.Font("Dialog", 0, 32)); // NOI18N
        jButton_Redo.setForeground(new java.awt.Color(75, 55, 136));
        jButton_Redo.setIcon(new javax.swing.ImageIcon("C:\\Users\\Kia\\Documents\\NetBeansProjects\\ICT 115\\src\\Finals\\Images\\UNDO.png")); // NOI18N
        jButton_Redo.setText(" REDO");
        jButton_Redo.setBorderPainted(false);
        jButton_Redo.setContentAreaFilled(false);
        jButton_Redo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Redo.setFocusPainted(false);
        jButton_Redo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_RedoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_RedoMouseExited(evt);
            }
        });
        jButton_Redo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RedoActionPerformed(evt);
            }
        });

        jButton_Info.setBackground(new java.awt.Color(200, 185, 224));
        jButton_Info.setFont(new java.awt.Font("Dialog", 0, 32)); // NOI18N
        jButton_Info.setForeground(new java.awt.Color(75, 55, 136));
        jButton_Info.setIcon(new javax.swing.ImageIcon("C:\\Users\\Kia\\Documents\\NetBeansProjects\\ICT 115\\src\\Finals\\Images\\BULB.png")); // NOI18N
        jButton_Info.setBorderPainted(false);
        jButton_Info.setContentAreaFilled(false);
        jButton_Info.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Info.setFocusPainted(false);
        jButton_Info.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_InfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_LowerNavBarLayout = new javax.swing.GroupLayout(jPanel_LowerNavBar);
        jPanel_LowerNavBar.setLayout(jPanel_LowerNavBarLayout);
        jPanel_LowerNavBarLayout.setHorizontalGroup(
            jPanel_LowerNavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_LowerNavBarLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jButton_Run)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Save)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Open)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Erase)
                .addGap(250, 250, 250)
                .addComponent(jButton_Undo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Redo)
                .addGap(702, 702, 702)
                .addComponent(jButton_Info, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_LowerNavBarLayout.setVerticalGroup(
            jPanel_LowerNavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_LowerNavBarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_LowerNavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton_Info)
                    .addGroup(jPanel_LowerNavBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton_Save)
                        .addComponent(jButton_Open)
                        .addComponent(jButton_Erase)
                        .addComponent(jButton_Redo)
                        .addComponent(jButton_Undo)
                        .addComponent(jButton_Run)))
                .addGap(192, 192, 192))
        );

        jPanel_IDE_frame.add(jPanel_LowerNavBar);
        jPanel_LowerNavBar.setBounds(0, 1350, 1440, 90);

        jTextArea_CodingArea.setBackground(new java.awt.Color(238, 225, 255));
        jTextArea_CodingArea.setColumns(20);
        jTextArea_CodingArea.setFont(new java.awt.Font("Consolas", 0, 48)); // NOI18N
        jTextArea_CodingArea.setForeground(new java.awt.Color(73, 55, 110));
        jTextArea_CodingArea.setRows(5);
        jTextArea_CodingArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 55, 136), 4));
        jScrollPane1.setViewportView(jTextArea_CodingArea);

        jPanel_IDE_frame.add(jScrollPane1);
        jScrollPane1.setBounds(40, 110, 1400, 1040);

        jPanel1.setBackground(new java.awt.Color(147, 119, 198));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 55, 136), 4));

        jLabel_Output.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel_Output.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Output.setText("OUTPUT");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel_Output, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(392, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel_Output, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel_IDE_frame.add(jPanel1);
        jPanel1.setBounds(1470, 110, 580, 45);

        jTextArea_OutputArea.setEditable(false);
        jTextArea_OutputArea.setBackground(new java.awt.Color(238, 225, 255));
        jTextArea_OutputArea.setColumns(1);
        jTextArea_OutputArea.setFont(new java.awt.Font("Consolas", 0, 40)); // NOI18N
        jTextArea_OutputArea.setForeground(new java.awt.Color(73, 55, 110));
        jTextArea_OutputArea.setRows(1);
        jTextArea_OutputArea.setTabSize(5);
        jTextArea_OutputArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 55, 136), 4));
        jTextArea_OutputArea.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane3.setViewportView(jTextArea_OutputArea);

        jPanel_IDE_frame.add(jScrollPane3);
        jScrollPane3.setBounds(1470, 150, 580, 450);

        jLabel_Screen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Screen.setIcon(new javax.swing.ImageIcon("C:\\Users\\Kia\\Documents\\NetBeansProjects\\ICT 115\\src\\Finals\\Images\\SCREEN.png")); // NOI18N
        jLabel_Screen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel_IDE_frame.add(jLabel_Screen);
        jLabel_Screen.setBounds(1650, 1010, 220, 133);

        jLabel_Egg.setIcon(new javax.swing.ImageIcon("C:\\Users\\Kia\\Documents\\NetBeansProjects\\ICT 115\\src\\Finals\\Images\\Output.png")); // NOI18N
        jLabel_Egg.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel_IDE_frame.add(jLabel_Egg);
        jLabel_Egg.setBounds(1450, 750, 620, 600);

        jButton_Submit_Input.setBackground(new java.awt.Color(147, 119, 198));
        jButton_Submit_Input.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jButton_Submit_Input.setIcon(new javax.swing.ImageIcon("C:\\Users\\Kia\\Documents\\NetBeansProjects\\ICT 115\\src\\Finals\\Images\\TALKp.png")); // NOI18N
        jButton_Submit_Input.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(75, 55, 136), 4, true));
        jButton_Submit_Input.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Submit_Input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Submit_InputActionPerformed(evt);
            }
        });
        jPanel_IDE_frame.add(jButton_Submit_Input);
        jButton_Submit_Input.setBounds(1340, 1230, 80, 80);

        jTextArea_Input.setBackground(new java.awt.Color(238, 225, 255));
        jTextArea_Input.setColumns(1);
        jTextArea_Input.setFont(new java.awt.Font("Consolas", 0, 48)); // NOI18N
        jTextArea_Input.setForeground(new java.awt.Color(73, 55, 110));
        jTextArea_Input.setRows(1);
        jTextArea_Input.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 55, 136), 4));
        jTextArea_Input.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextArea_Input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea_InputKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(jTextArea_Input);

        jPanel_IDE_frame.add(jScrollPane4);
        jScrollPane4.setBounds(40, 1210, 1400, 120);

        jPanel2.setBackground(new java.awt.Color(147, 119, 198));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 55, 136), 4));

        jLabel_Input.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel_Input.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Input.setText("INPUT HERE:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel_Input, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(830, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel_Input, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel_IDE_frame.add(jPanel2);
        jPanel2.setBounds(40, 1170, 1400, 45);

        jTextArea_TerminalArea.setEditable(false);
        jTextArea_TerminalArea.setBackground(new java.awt.Color(238, 225, 255));
        jTextArea_TerminalArea.setColumns(1);
        jTextArea_TerminalArea.setFont(new java.awt.Font("Consolas", 0, 42)); // NOI18N
        jTextArea_TerminalArea.setForeground(new java.awt.Color(73, 55, 110));
        jTextArea_TerminalArea.setRows(1);
        jTextArea_TerminalArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 55, 136), 4));
        jTextArea_TerminalArea.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane2.setViewportView(jTextArea_TerminalArea);

        jPanel_IDE_frame.add(jScrollPane2);
        jScrollPane2.setBounds(1470, 610, 580, 90);

        jButton_Guide.setBackground(new java.awt.Color(200, 185, 224));
        jButton_Guide.setFont(new java.awt.Font("Dialog", 0, 32)); // NOI18N
        jButton_Guide.setForeground(new java.awt.Color(75, 55, 136));
        jButton_Guide.setIcon(new javax.swing.ImageIcon("C:\\Users\\Kia\\Documents\\NetBeansProjects\\ICT 115\\src\\Finals\\Images\\BULB.png")); // NOI18N
        jButton_Guide.setBorderPainted(false);
        jButton_Guide.setContentAreaFilled(false);
        jButton_Guide.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Guide.setFocusPainted(false);
        jButton_Guide.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jButton_Guide.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton_Guide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_GuideMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_GuideMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton_GuideMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButton_GuideMouseReleased(evt);
            }
        });
        jButton_Guide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuideActionPerformed(evt);
            }
        });
        jPanel_IDE_frame.add(jButton_Guide);
        jButton_Guide.setBounds(1770, 1340, 280, 64);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_IDE_frame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_IDE_frame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ExitActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton_ExitActionPerformed

    private void jButton_InfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_InfoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_InfoActionPerformed

    private void jButton_UndoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_UndoMouseEntered
        // TODO add your handling code here:
        Font originalFont = jButton_Undo.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>(originalFont.getAttributes());
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        jButton_Undo.setFont(originalFont.deriveFont(attributes));

        jButton_Undo.setForeground(new Color(75,55,164));
        ImageIcon hoverIcon = new ImageIcon("src/Finals/Images/REDOn.png");
        jButton_Undo.setRolloverIcon(hoverIcon);
    }//GEN-LAST:event_jButton_UndoMouseEntered
    
    private void jButton_UndoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_UndoMouseExited
        // TODO add your handling code here:
        jButton_Undo.setFont((defaultButtonFont));
        jButton_Undo.setForeground(new Color(75,55,136));
    }//GEN-LAST:event_jButton_UndoMouseExited

    private void jButton_RedoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_RedoMouseEntered
        // TODO add your handling code here:
        Font originalFont = jButton_Redo.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>(originalFont.getAttributes());
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        jButton_Redo.setFont(originalFont.deriveFont(attributes));

        jButton_Redo.setForeground(new Color(75,55,164));
        ImageIcon hoverIcon = new ImageIcon("src/Finals/Images/UNDOn.png");
        jButton_Redo.setRolloverIcon(hoverIcon);
    }//GEN-LAST:event_jButton_RedoMouseEntered

    private void jButton_RedoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_RedoMouseExited
        // TODO add your handling code here:
        jButton_Redo.setFont((defaultButtonFont));
        jButton_Redo.setForeground(new Color(75,55,136));
        
    }//GEN-LAST:event_jButton_RedoMouseExited

    private void jButton_RunMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_RunMouseEntered
        // TODO add your handling code here:
        Font originalFont = jButton_Run.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>(originalFont.getAttributes());
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        jButton_Run.setFont(originalFont.deriveFont(attributes));

        jButton_Run.setForeground(new Color(75,55,134));
    }//GEN-LAST:event_jButton_RunMouseEntered

    private void jButton_RunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_RunMouseClicked
        // TODO add your handling code here:
//        jButton_Run.setFont((defaultButtonFont));
//        if (isRunning) {
//            ImageIcon readyIcon = new ImageIcon("src/Finals/Images/ON.png"); 
//            jButton_Run.setRolloverIcon(readyIcon);
//            jButton_Run.setIcon(readyIcon); 
//            jButton_Run.setText(" RUN");
//  
//            // Update the state variable
//            isRunning = false;
//        
//        } 
//        else {
//            ImageIcon runningIcon = new ImageIcon("src/Finals/Images/OFF.png"); // The 'OFF' state icon
//            jButton_Run.setRolloverIcon(runningIcon);
//            jButton_Run.setIcon(runningIcon);
//            jButton_Run.setText(" RUNNING");
//
//            // Update the state variable
//            isRunning = true;
//        }
//
//        jButton_Run.repaint();
    }//GEN-LAST:event_jButton_RunMouseClicked

    private void jButton_RunMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_RunMouseReleased
        // TODO add your handling code here:
        ImageIcon readyIcon = new ImageIcon("src/Finals/Images/ON.png");
        jButton_Run.setRolloverIcon(readyIcon);
        jButton_Run.setIcon(readyIcon);
        jButton_Run.setText(" RUN");
    }//GEN-LAST:event_jButton_RunMouseReleased

    private void jButton_RunMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_RunMousePressed
        // TODO add your handling code here:
        ImageIcon runningIcon = new ImageIcon("src/Finals/Images/OFF_C.png");
        jButton_Run.setRolloverIcon(runningIcon);
        jButton_Run.setIcon(runningIcon);
        jButton_Run.setText(" RUN");
    }//GEN-LAST:event_jButton_RunMousePressed

    private void jButton_RunMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_RunMouseExited
        // TODO add your handling code here:
        jButton_Run.setFont((defaultButtonFont));
        jButton_Run.setForeground(new Color(75,55,136));
    }//GEN-LAST:event_jButton_RunMouseExited

    private void jButton_RunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RunActionPerformed
        // TODO add your handling code here:
        try {
            String code = jTextArea_CodingArea.getText();

            Lexer lexer = new Lexer();
            List<Token> tokens = lexer.tokenize(code);

            Parser parser = new Parser();
            ProgramNode program = parser.parse(tokens);

            // Always create & store interpreter
            savedInterpreter = new Interpreter();
            savedProgram = program;

            String output = savedInterpreter.run(program);
            jTextArea_OutputArea.setText(output);
            
            jTextArea_TerminalArea.setText("You Spoke in Kritter!");
            jLabel_Screen.setIcon(new ImageIcon("src/Finals/Images/KUROMI.gif"));

        } catch (Exception e) {
            jTextArea_OutputArea.setText("Error:\n" + e.getMessage());
            jTextArea_TerminalArea.setText("I can't understand");
            jLabel_Screen.setIcon(new ImageIcon("src/Finals/Images/BIBBLE.gif"));
        }
    }//GEN-LAST:event_jButton_RunActionPerformed

    private void jButton_UndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_UndoActionPerformed
        // TODO add your handling code here:
        if (undoManager.canUndo()) {
            undoManager.undo();
        }   
    }//GEN-LAST:event_jButton_UndoActionPerformed

    private void jButton_RedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RedoActionPerformed
        // TODO add your handling code here:
        if (undoManager.canRedo()) {
            undoManager.redo();
        }
    }//GEN-LAST:event_jButton_RedoActionPerformed

    private void jButton_EraseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EraseActionPerformed
        // TODO add your handling code here:
        jTextArea_CodingArea.setText("");

        // Clear the output area
        jTextArea_OutputArea.setText("");

        // Kung gusto ko gid Clear LMAO
        //jTextArea_TerminalArea.setText("");

        // Confirmation message (kung nag gana)
        jTextArea_TerminalArea.setText("Workspace cleared.");
    }//GEN-LAST:event_jButton_EraseActionPerformed
    
    Interpreter savedInterpreter = null;
    ProgramNode savedProgram = null;
    private void jButton_Submit_InputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Submit_InputActionPerformed
        // TODO add your handling code here:
        if (savedInterpreter == null) return;

        String text = jTextArea_Input.getText().trim();
        jTextArea_Input.setText("");

        savedInterpreter.provideInput(text);

        String output = savedInterpreter.run(savedProgram);
        jTextArea_OutputArea.setText(output);
    }//GEN-LAST:event_jButton_Submit_InputActionPerformed

    private void jTextArea_InputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea_InputKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            evt.consume(); // Prevents a newline from being added
            jButton_Submit_Input.doClick();
        }
    }//GEN-LAST:event_jTextArea_InputKeyPressed

    private void jButton_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SaveActionPerformed
        // TODO add your handling code here:
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(System.getProperty("user.home"), "Downloads"));
            chooser.setDialogTitle("Save Critter Code");

            int result = chooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                java.io.FileWriter writer = new java.io.FileWriter(file);
                writer.write(jTextArea_CodingArea.getText());
                writer.close();

                jTextArea_OutputArea.setText("Saved to: " + file.getAbsolutePath());
            }
       } catch (Exception e) {
            jTextArea_OutputArea.setText("Error saving file: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton_SaveActionPerformed

    private void jButton_OpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OpenActionPerformed
        // TODO add your handling code here:
        try {
            // Customize UI
            UIManager.put("FileChooser.background", new java.awt.Color(238, 225, 255));
            UIManager.put("FileChooser.foreground", new java.awt.Color(73, 55, 110));
            UIManager.put("FileChooser.font", new java.awt.Font("Arial", java.awt.Font.PLAIN, 42));
            UIManager.put("FileChooser.listViewBackground", new java.awt.Color(238, 225, 255));
            UIManager.put("FileChooser.listViewForeground", new java.awt.Color(73, 55, 110));

            // Create chooser
            JFileChooser chooser = new JFileChooser();
            chooser.setPreferredSize(new java.awt.Dimension(800, 800));
            chooser.setCurrentDirectory(new File(System.getProperty("user.home"), "Downloads"));
            chooser.setDialogTitle("Open Critter Code File");
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files", "txt"));

            int result = chooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();

                java.util.Scanner scanner = new java.util.Scanner(file);
                StringBuilder content = new StringBuilder();
                while (scanner.hasNextLine()) {
                    content.append(scanner.nextLine()).append("\n");
                }
                scanner.close();

                jTextArea_CodingArea.setText(content.toString());
                jTextArea_OutputArea.setText("Opened: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            jTextArea_OutputArea.setText("Error opening file: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton_OpenActionPerformed

    private void jButton_GuideMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_GuideMouseEntered
        // TODO add your handling code here:
        ImageIcon GlowIcon = new ImageIcon("src/Finals/Images/BULB_lit.png");
        jButton_Guide.setRolloverIcon(GlowIcon);
        jButton_Guide.setIcon(GlowIcon);
        jButton_Guide.setText(" GUIDE");
    }//GEN-LAST:event_jButton_GuideMouseEntered

    private void jButton_GuideMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_GuideMouseExited
        // TODO add your handling code here:
        // TODO add your handling code here:
        ImageIcon OffLightIcon = new ImageIcon("src/Finals/Images/BULB.png");
        jButton_Guide.setRolloverIcon(OffLightIcon);
        jButton_Guide.setIcon(OffLightIcon);
        jButton_Guide.setText("");
    }//GEN-LAST:event_jButton_GuideMouseExited

    private void jButton_GuideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuideActionPerformed
        // TODO add your handling code here:
        GuideFrame guide = new GuideFrame();
        guide.setVisible(true);
    }//GEN-LAST:event_jButton_GuideActionPerformed

    private void jButton_GuideMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_GuideMousePressed
        // TODO add your handling code here:
        ImageIcon GlowIcon = new ImageIcon("src/Finals/Images/BULB_lit.png");
        jButton_Guide.setRolloverIcon(GlowIcon);
        jButton_Guide.setIcon(GlowIcon);
        jButton_Guide.setText(" GUIDE");
    }//GEN-LAST:event_jButton_GuideMousePressed

    private void jButton_GuideMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_GuideMouseReleased
        // TODO add your handling code here:
        ImageIcon OffLightIcon = new ImageIcon("src/Finals/Images/BULB.png");
        jButton_Guide.setRolloverIcon(OffLightIcon);
        jButton_Guide.setIcon(OffLightIcon);
        jButton_Guide.setText("");
    }//GEN-LAST:event_jButton_GuideMouseReleased

    private void jButton_SaveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_SaveMouseEntered
        // TODO add your handling code here:
        Font originalFont = jButton_Save.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>(originalFont.getAttributes());
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        jButton_Save.setFont(originalFont.deriveFont(attributes));

        jButton_Save.setForeground(new Color(75,55,134));
    }//GEN-LAST:event_jButton_SaveMouseEntered

    private void jButton_SaveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_SaveMouseExited
        // TODO add your handling code here:
        jButton_Save.setFont((defaultButtonFont));
        jButton_Save.setForeground(new Color(75,55,136));
    }//GEN-LAST:event_jButton_SaveMouseExited

    private void jButton_SaveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_SaveMousePressed
        // TODO add your handling code here:
        ImageIcon SavedIcon = new ImageIcon("src/Finals/Images/SAVEp.png");
        jButton_Save.setRolloverIcon(SavedIcon);
        jButton_Save.setIcon(SavedIcon);
    }//GEN-LAST:event_jButton_SaveMousePressed

    private void jButton_OpenMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_OpenMousePressed
        // TODO add your handling code here:
        ImageIcon OpenIcon = new ImageIcon("src/Finals/Images/NEW_FILEc.png");
        jButton_Open.setRolloverIcon(OpenIcon);
        jButton_Open.setIcon(OpenIcon);
    }//GEN-LAST:event_jButton_OpenMousePressed

    private void jButton_OpenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_OpenMouseExited
        // TODO add your handling code here:
        jButton_Open.setFont((defaultButtonFont));
        jButton_Open.setForeground(new Color(75,55,136));
    }//GEN-LAST:event_jButton_OpenMouseExited

    private void jButton_EraseMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_EraseMousePressed
        // TODO add your handling code here:
        ImageIcon EraseIcon = new ImageIcon("src/Finals/Images/ERASE_C.png");
        jButton_Erase.setRolloverIcon(EraseIcon);
        jButton_Erase.setIcon(EraseIcon);
    }//GEN-LAST:event_jButton_EraseMousePressed

    private void jButton_OpenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_OpenMouseEntered
        // TODO add your handling code here:
        Font originalFont = jButton_Save.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>(originalFont.getAttributes());
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        jButton_Open.setFont(originalFont.deriveFont(attributes));

        jButton_Open.setForeground(new Color(75,55,134));
    }//GEN-LAST:event_jButton_OpenMouseEntered

    private void jButton_EraseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_EraseMouseEntered
        // TODO add your handling code here:
        Font originalFont = jButton_Erase.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>(originalFont.getAttributes());
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        jButton_Erase.setFont(originalFont.deriveFont(attributes));

        jButton_Erase.setForeground(new Color(75,55,134));
    }//GEN-LAST:event_jButton_EraseMouseEntered

    private void jPanel_LowerNavBarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_LowerNavBarMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel_LowerNavBarMouseExited

    private void jButton_EraseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_EraseMouseExited
        // TODO add your handling code here:
        jButton_Erase.setFont((defaultButtonFont));
        jButton_Erase.setForeground(new Color(75,55,136));
    }//GEN-LAST:event_jButton_EraseMouseExited

    private void jButton_SaveMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_SaveMouseReleased
        // TODO add your handling code here:
        ImageIcon readyIcon = new ImageIcon("src/Finals/Images/Interface-Essential-Floppy-Disk--Streamline-Pixel.png");
        jButton_Save.setRolloverIcon(readyIcon);
        jButton_Save.setIcon(readyIcon);
        jButton_Save.setText(" SAVE");
    }//GEN-LAST:event_jButton_SaveMouseReleased

    private void jButton_OpenMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_OpenMouseReleased
        // TODO add your handling code here:
        ImageIcon OpenIcon = new ImageIcon("src/Finals/Images/NEW_FILE.png");
        jButton_Open.setRolloverIcon(OpenIcon);
        jButton_Open.setIcon(OpenIcon);
        
    }//GEN-LAST:event_jButton_OpenMouseReleased

    private void jButton_EraseMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_EraseMouseReleased
        // TODO add your handling code here:
        ImageIcon EraseIcon = new ImageIcon("src/Finals/Images/ERASE.png");
        jButton_Erase.setRolloverIcon(EraseIcon);
        jButton_Erase.setIcon(EraseIcon);
    }//GEN-LAST:event_jButton_EraseMouseReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CritterCode_IDE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CritterCode_IDE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CritterCode_IDE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CritterCode_IDE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CritterCode_IDE().setVisible(true);
            }
        });
    }

    
    public static String lastInputValue = "";


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Erase;
    private javax.swing.JButton jButton_Exit;
    private javax.swing.JButton jButton_Guide;
    private javax.swing.JButton jButton_Info;
    private javax.swing.JButton jButton_Open;
    private javax.swing.JButton jButton_Redo;
    private javax.swing.JButton jButton_Run;
    private javax.swing.JButton jButton_Save;
    private javax.swing.JButton jButton_Submit_Input;
    private javax.swing.JButton jButton_Undo;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabel_Egg;
    private javax.swing.JLabel jLabel_Input;
    private javax.swing.JLabel jLabel_Output;
    private javax.swing.JLabel jLabel_Screen;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel_IDE_frame;
    private javax.swing.JPanel jPanel_IDE_tab;
    private javax.swing.JPanel jPanel_LowerNavBar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea_CodingArea;
    private javax.swing.JTextArea jTextArea_Input;
    private javax.swing.JTextArea jTextArea_OutputArea;
    private javax.swing.JTextArea jTextArea_TerminalArea;
    // End of variables declaration//GEN-END:variables
}