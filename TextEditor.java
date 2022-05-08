import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;
import java.awt.Image;
import java.util.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.undo.*;
import javax.swing.event.*;
import java.awt.Font;
import javax.swing.filechooser.*;
import javax.swing.JTextField;
import javax.swing.undo.UndoManager;


public class TextEditor extends JFrame implements ActionListener{

  ImageIcon image;
 JTextArea textArea;
 JScrollPane scrollPane;
 JLabel fontLabel;
 JSpinner fontSizeSpinner;
 JButton fontColorButton;
  JButton Replace;
  JPanel pan;
 JComboBox fontBox;
 JMenuBar menuBar;
 JMenu fileMenu;
 JMenu editmenu;
 JMenu alignMenu;
 JMenu findMenu;
 JMenuItem openItem;
 JMenuItem saveItem;
 JMenuItem exitItem;
 JMenuItem printItem;
 JMenuItem newItem;
 JMenuItem cutitem;
 JMenuItem pasteitem;
 JMenuItem copyitem;
 JMenuItem bolditem;
 JMenuItem italicitem;
 JMenuItem left;
 JMenuItem right;
 JMenuItem findnrep;
 JMenuItem undoitem;
 JMenuItem redoitem;
 UndoManager un;
 JFileChooser fileChooser;
    
    // setup icons - File Menu
    private final ImageIcon newIcon = new ImageIcon("icons/new.png");
    private final ImageIcon openIcon = new ImageIcon("icons/open.png");
    private final ImageIcon printIcon = new ImageIcon("icons/print.png");
    private final ImageIcon saveIcon = new ImageIcon("icons/save.png");
    private final ImageIcon closeIcon = new ImageIcon("icons/close.png");

    // setup icons - Edit Menu
    private final ImageIcon cutIcon = new ImageIcon("icons/cut.png");
    private final ImageIcon copyIcon = new ImageIcon("icons/copy.png");
    private final ImageIcon pasteIcon = new ImageIcon("icons/paste.png");
    private final ImageIcon boldIcon = new ImageIcon("icons/bold.png");
    private final ImageIcon italicIcon = new ImageIcon("icons/italic.png");
     private final ImageIcon undoIcon = new ImageIcon("icons/undo.png");
      private final ImageIcon redoIcon = new ImageIcon("icons/redo.png");

    // setup icons - Search Menu
    private final ImageIcon searchIcon = new ImageIcon("icons/search.png");
    // color icons
    private final ImageIcon clrIcon = new ImageIcon("icons/color.png");
    // align icons
    private final ImageIcon LIcon = new ImageIcon("icons/l.png");
    private final ImageIcon RIcon = new ImageIcon("icons/r.png");

 TextEditor(){
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.setTitle("Text Editor");
  this.setSize(500, 500);
  this.setLayout(new FlowLayout());
  this.setLocationRelativeTo(null);

   fileChooser=new JFileChooser(".");
  un=new UndoManager();
  
  textArea = new JTextArea();
  textArea.setLineWrap(true);
  textArea.setWrapStyleWord(true);
  textArea.setFont(new Font("Arial",Font.PLAIN,20));

  
  scrollPane = new JScrollPane(textArea);
  scrollPane.setPreferredSize(new Dimension(450,450));
  scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

  
  fontLabel = new JLabel("Font: ");
  
  fontSizeSpinner = new JSpinner();
  fontSizeSpinner.setPreferredSize(new Dimension(50,35));
  fontSizeSpinner.setValue(20);
  fontSizeSpinner.addChangeListener(new ChangeListener() {

   @Override
   public void stateChanged(ChangeEvent e) {
    
    textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int) fontSizeSpinner.getValue())); 
   }
   
  });

    
  fontColorButton = new JButton("Color", clrIcon);
  fontColorButton.addActionListener(this);
  
  String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
  
  fontBox = new JComboBox(fonts);
   fontBox.setPreferredSize(new Dimension(150,35));
  fontBox.addActionListener(this);
  fontBox.setSelectedItem("Arial");
  
  //****************************************************************//

   
   
  // ------ menubar ------
  
   menuBar = new JMenuBar();
   
   fileMenu = new JMenu("File");
   openItem = new JMenuItem("Open", openIcon);
   saveItem = new JMenuItem("Save", saveIcon);
   printItem = new JMenuItem("Print", printIcon);
   newItem = new JMenuItem("New", newIcon);
   exitItem = new JMenuItem("Exit", closeIcon);


   
   openItem.addActionListener(this);
   saveItem.addActionListener(this);
   printItem.addActionListener(this);
   newItem.addActionListener(this);
   exitItem.addActionListener(this);
   
   fileMenu.add(openItem);
   fileMenu.add(saveItem);
   fileMenu.add(printItem);
   fileMenu.add(newItem);
   fileMenu.add(exitItem);
   menuBar.add(fileMenu);
   
// Create amenu for menu
   editmenu = new JMenu("Edit");
   // Create menu items
   cutitem = new JMenuItem("cut", cutIcon);
   copyitem = new JMenuItem("copy", copyIcon);
   pasteitem = new JMenuItem("paste", pasteIcon);
   bolditem = new JMenuItem("bold", boldIcon);
   italicitem = new JMenuItem("italics", italicIcon);
   undoitem= new JMenuItem("undo", undoIcon);
   redoitem= new JMenuItem("redo", redoIcon);

   // Add action listener
   cutitem.addActionListener(this);
   copyitem.addActionListener(this);
   pasteitem.addActionListener(this);
   bolditem.addActionListener(this);
   italicitem.addActionListener(this);
   undoitem.addActionListener(this);
   undoitem.setActionCommand("Undo");
   undoitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
   redoitem.addActionListener(this);
   redoitem.setActionCommand("Redo");
   redoitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,java.awt.Event.CTRL_MASK | java.awt.Event.SHIFT_MASK));

  
   
   editmenu.add(cutitem);
   editmenu.add(copyitem);
   editmenu.add(pasteitem);
   editmenu.add(bolditem);
   editmenu.add(italicitem);
   editmenu.add(undoitem);
   editmenu.add(redoitem);
   menuBar.add(editmenu);
   textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
       public void undoableEditHappened(UndoableEditEvent e) {
           un.addEdit(e.getEdit());
       }
   });
   
        
   
 // ----- /alignment ------
   alignMenu = new JMenu("Align");
   left = new JMenuItem("Left", LIcon);
   right = new JMenuItem("Right", RIcon);
   

   left.addActionListener(this);
   right.addActionListener(this);

   alignMenu.add(left);
   alignMenu.add(right);
   menuBar.add(alignMenu);

   findMenu = new JMenu("Search");
   findnrep = new JMenuItem("Find and Replace", searchIcon);
   
   findnrep.addActionListener(this);
   findnrep.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
   
   findMenu.add(findnrep);
   menuBar.add(findMenu);
   
  // ------ /menubar ------
   
  this.setJMenuBar(menuBar);
  this.add(fontLabel);
  this.add(fontSizeSpinner);
  this.add(fontColorButton);
  this.add(fontBox);
  this.add(scrollPane);
  this.setVisible(true);
 }
 
 @Override
 public void actionPerformed(ActionEvent e) {
   String command=e.getActionCommand();
	 if(e.getSource()==cutitem) {
		 textArea.cut();
	 }
	 if(e.getSource()==copyitem) {
		 textArea.copy();
	 }
	 if(e.getSource()==pasteitem) {
		 textArea.paste();
	 }
	 if(e.getSource()==bolditem) {
		  Font f = new Font("Serif", Font.BOLD, (int) fontSizeSpinner.getValue());
		  textArea.setFont(f);
	 }
	 if(e.getSource()==italicitem) {
		 Font f = new Font("Serif", Font.ITALIC, (int) fontSizeSpinner.getValue());
		  textArea.setFont(f);
   }
    if(command == "Undo") {
         try {
            un.undo();
         } 
         catch(Exception ex) {
             JOptionPane.showMessageDialog(this, "Field is Empty");
         }
     }
	 if(command == "Redo") {
         try {
             un.redo();
         } 
         catch(Exception ex) {
             JOptionPane.showMessageDialog(this, "Field is Empty");
         }
     }


   
   if(e.getSource()==right) {
     textArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
   }

      if(e.getSource()==left) {
     textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
   }
	 
  if(e.getSource()==fontColorButton) {
   JColorChooser colorChooser = new JColorChooser();
   
   Color color = colorChooser.showDialog(null, "Choose a color", Color.black);
   
   textArea.setForeground(color);
  }
  
  if(e.getSource()==fontBox) {
   textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
  }
   
  if (e.getSource() == findnrep) {
            new Find(textArea);
        }
   
  if(e.getSource()==openItem) {
   JFileChooser fileChooser = new JFileChooser();
   fileChooser.setCurrentDirectory(new File("."));
   FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
   fileChooser.setFileFilter(filter);
   
   int response = fileChooser.showOpenDialog(null);
   
   if(response == JFileChooser.APPROVE_OPTION) {
    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
    Scanner fileIn = null;
    
    try {
     fileIn = new Scanner(file);
     if(file.isFile()) {
      while(fileIn.hasNextLine()) {
       String line = fileIn.nextLine()+"\n";
       textArea.append(line);
      }
     }
    } catch (FileNotFoundException e1) {
     // TODO Auto-generated catch block
     e1.printStackTrace();
    }
    finally {
     fileIn.close();
    }
   }
  }
  if(e.getSource()==saveItem) {
   JFileChooser fileChooser = new JFileChooser();
   fileChooser.setCurrentDirectory(new File("."));
   
   int response = fileChooser.showSaveDialog(null);
   
   if(response == JFileChooser.APPROVE_OPTION) {
    File file;
    PrintWriter fileOut = null;
    
    file = new File(fileChooser.getSelectedFile().getAbsolutePath());
    try {
     fileOut = new PrintWriter(file);
     fileOut.println(textArea.getText());
    } 
    catch (FileNotFoundException e1) {
     // TODO Auto-generated catch block
     e1.printStackTrace();
    }
    finally {
     fileOut.close();
    }   
   }
  }
  if(e.getSource()==newItem) {
	  textArea.setText("");
	  }
  if(e.getSource()==exitItem) {
   System.exit(0);
  }
  if(e.getSource()==printItem) {
	  try {
		textArea.print();
	} catch (PrinterException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}
 	   
	    
 }
}

