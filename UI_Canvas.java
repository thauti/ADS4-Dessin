import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UI_Canvas extends JPanel
{
    int width;
    int height;
    static int curr_x = 0;
    static int curr_y = 0;
    int angle_cours = 0;
    boolean dessin = false;
    static int size = 1;
    private Color color= Color.BLACK;
    ArrayList<Droite> droites = new ArrayList<Droite>();
    public UI_Canvas()
    {
        super();
        this.width = getWidth();
        this.height = getHeight();
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.WHITE);
        this.setVisible(true);

    }
    public void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for (Droite a: droites)
        {
            a.dessiner(g, this.getHeight());
        }
    }
    public void ajouterDroite(int l)
    {
        if(dessin == true) {
            Droite a = new Droite(curr_x,curr_y, angle_cours, l, this.color, size);
            droites.add(a);
        }else
        {
            int x1=curr_x;
            int y1=curr_y;
            int tmp = angle_cours % 360;
            if(tmp == 0)
                x1 = curr_x + l;
            if(tmp == 180 || tmp == -180)
                x1 = curr_x - l;
            if(tmp == 90 || tmp == -270)
                y1 = curr_y - l;
            if(tmp == -90 || tmp == 270)
                y1 = curr_y+l;
            if(x1 < 0 || y1 < 0)
            {
                Main.fen.log("[Erreur] Le dessin sort de la zone");
            }
            curr_x = x1;
            curr_y = y1;
        }
    }
    public void addangle(int x)
    {
        this.angle_cours += x;
    }
    public void activerDessin()
    {
        this.dessin = true;
    }
    public void desactiverDessin()
    {
        this.dessin = false;
    }
    public void setcolor(String c)
    {
        if(c.equals("black"))
            this.color = Color.BLACK;
        if(c.equals("red"))
            this.color = Color.RED;
        if(c.equals("green"))
            this.color = Color.GREEN;
        if(c.equals("yellow"))
            this.color = Color.YELLOW;
	 if(c.equals("blue"))
            this.color = Color.BLUE;
    }
    public void setDrawSize(int s)
    {
        this.size = s;
    }
    public void clean()
	{
	this.width = getWidth();
    this.height = getHeight();
	this.droites.clear();
	this.color = Color.BLACK;
	angle_cours = 0;
    dessin = false;
	curr_x = 0;
	curr_y = 0;
    size = 1;
	}
}
