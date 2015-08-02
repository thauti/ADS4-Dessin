import java.awt.*;

public class Droite {
    private int x, y;
    private int x1,y1;
    private int angle, longueur;
    private Color c;
    private int size;
    public Droite(int x, int y, int angle, int longueur, Color c, int size)
    {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.longueur = longueur;
        this.size=size;
        int tmp = angle % 360;
        this.x1= this.x;
        this.y1=this.y;
        if(tmp == 0)
            this.x1 = this.x + longueur;
        if(tmp == 180 || tmp == -180)
            this.x1 = this.x - longueur;
        if(tmp == 90 || tmp == -270)
            this.y1 = this.y - longueur;
        if(tmp == -90 || tmp == 270)
            this.y1 = this.y+longueur;
        this.c = c;
        if(this.x1 < 0 || y1 < 0)
        {
            Main.fen.log("[Erreur] Le dessin sort de la zone");
        }
        Main.fen.canvas.curr_x = x1;
        Main.fen.canvas.curr_y = y1;
    }
    public void dessiner(Graphics2D g, int Y)
    {
        g.setColor(c);
        g.setStroke(new BasicStroke(this.size));
        if(y == 0)
            System.out.println("Taille = " +size);
        g.drawLine(x, (Y-y)-1, x1, (Y-y1)-1);
    }
}
