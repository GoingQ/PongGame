import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Rect {
    public double x, y, w, h;
    public Color color;


    public Rect(double x, double y, double w, double h, Color color) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fill(new Rectangle2D.Double(x, y, w, h));
    }

}
