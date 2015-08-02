import javax.swing.JFileChooser;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class UI_Frame extends JFrame implements ActionListener
{
    String log ="Console :";
    UI_Canvas canvas;
    JTextArea textarea;
    JPanel pane;
    JPanel panearea;
    JMenuBar menu;
    JScrollPane scrollPane;
    JMenu fichier;
    JMenuItem open;
    ArrayList<Droite> droites = new ArrayList<Droite>();
	public UI_Frame()
	{

        this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setTitle("ADS4 - Projet");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane = new JPanel(new BorderLayout());
        panearea = new JPanel();
        panearea.setLayout(new BoxLayout(panearea, BoxLayout.PAGE_AXIS));
        canvas = new UI_Canvas();
        textarea = new JTextArea(10,10);
        menu = new JMenuBar();
        this.setBackground(Color.white);
        fichier = new JMenu("Fichier");
        open = new JMenuItem("Ouvrir");
        open.addActionListener(this);
        fichier.add(open);
        menu.add(fichier);
        textarea.setLineWrap (true);
        textarea.setWrapStyleWord(false);
        textarea.setEditable(false);
        scrollPane = new JScrollPane( textarea );
        panearea.add(scrollPane);
        pane.add(canvas,BorderLayout.CENTER);
        pane.add(panearea,BorderLayout.PAGE_END);
        pane.setBackground(Color.WHITE);
        
        this.add(pane);
        this.setJMenuBar(menu);
        //this.add(textarea);
        this.setVisible(true);
	}
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        if(source == open)
        {
            JFileChooser dialogue = new JFileChooser(new File("."));
            File fichier;
            dialogue.showOpenDialog(null);
            fichier = dialogue.getSelectedFile();
	    if(fichier != null)
	    {
            this.canvas.clean();

            try {
                Main.executer(fichier);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
	    }
	    else
		{
			System.out.println("Aucun fichier sélectionné");
		}
        }
    }
    public void ajouterDroite(int l)
    {
        this.canvas.ajouterDroite(l);
        this.repaint();
    }
    public void log(String s)
    {
        this.log = this.log+"\n"+s;
        this.textarea.setText(this.log);
    }
}
