
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

public class SimpleCarSimulator implements CarSimulator {
    double maxHeight = 1000.0;
    double r = 5.0;
    double d = 4.0;
    double S = 30.0;
    double L = 40.0;
    double x;
    double y;
    double theta;
    ArrayList<Rectangle2D.Double> obstacles;
    double t;
    double distMoved;
    double phi;
    boolean isAccelModel;
    double v;
    boolean isUnicycle = true;

    public SimpleCarSimulator(boolean var1, boolean var2) {
        this.isAccelModel = var1;
        this.isUnicycle = var2;
    }

    public void init(double var1, double var3, double var5, ArrayList<Rectangle2D.Double> var7) {
        this.x = var1;
        this.y = var3;
        this.theta = var5;
        this.obstacles = var7;
        this.t = 0.0;
        this.distMoved = 0.0;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getTheta() {
        return this.theta;
    }

    public double getV() {
        return this.v;
    }

    public void draw(Graphics2D var1, Dimension var2) {
        int var3 = (int)this.x;
        int var4 = (int)this.y;
        AffineTransform var5 = AffineTransform.getRotateInstance(-this.theta, (double)var3, (double)(var2.height - var4));
        var1.transform(var5);
        if (this.isUnicycle) {
            var1.setColor(Color.cyan);
            var1.fillOval(var3 - 15, var2.height - var4 - 8, 30, 16);
            var1.setColor(Color.black);
            var1.drawLine(var3, var2.height - var4, var3 + 15, var2.height - var4);
        } else {
            int var6 = (int)(this.x - this.r);
            int var7 = (int)(this.y + this.S / 2.0);
            int var8 = (int)(this.x - this.r);
            int var9 = (int)(this.y + this.S / 2.0 - this.d);
            int var11 = (int)(this.y - this.S / 2.0 + 2.0 * this.d);
            var1.setColor(Color.gray);
            var1.fillRect(var6, var2.height - var7, (int)this.L, (int)this.S);
            var1.setColor(Color.black);
            var1.fillRect(var8, var2.height - var9, (int)(2.0 * this.r), (int)this.d);
            var1.fillRect(var8, var2.height - var11, (int)(2.0 * this.r), (int)this.d);
            int var12 = (int)(this.x + this.L - 3.0 * this.r);
            int var13 = (int)(this.y + this.S / 2.0 - this.d);
            AffineTransform var14 = (AffineTransform)var5.clone();
            AffineTransform var15 = AffineTransform.getRotateInstance(-this.phi, (double)var12, (double)(var2.height - var13));
            var14.concatenate(var15);
            var1.transform(var14);
            var1.fillRect(var12, var2.height - var13, (int)(2.0 * this.r), (int)this.d);
            int var17 = (int)(this.y - this.S / 2.0 + 2.0 * this.d);
            var14 = (AffineTransform)var5.clone();
            AffineTransform var18 = AffineTransform.getRotateInstance(-this.phi, (double)var12, (double)(var2.height - var17));
            var14.concatenate(var18);
            var1.transform(var14);
            var1.fillRect(var12, var2.height - var17, (int)(2.0 * this.r), (int)this.d);
        }
    }

    public void nextStep(double var1, double var3, double var5) {
        if (this.isAccelModel) {
            this.v += var5 * var1;
        } else {
            this.v = var1;
        }

        double var7 = var5 * this.v * Math.cos(this.theta);
        this.x += var7;
        double var9 = var5 * this.v * Math.sin(this.theta);
        this.y += var9;
        this.phi = 0.15707963267948966 * var3;
        double var11 = var5 * this.phi;
        if (!this.isUnicycle) {
            var11 = var5 * this.v * Math.sin(this.phi);
        }

        this.theta += var11;
        this.theta = this.angleFix(this.theta);
        this.t += var5;
        this.distMoved += Math.sqrt(var7 * var7 + var9 * var9);
    }

    double angleFix(double var1) {
        if (var1 < 0.0) {
            while(var1 < 0.0) {
                var1 += 6.283185307179586;
            }
        } else if (var1 > 6.283185307179586) {
            while(var1 > 6.283185307179586) {
                var1 -= 6.283185307179586;
            }
        }

        return var1;
    }

    public double getDistanceMoved() {
        return this.distMoved;
    }

    public double getTime() {
        return this.t;
    }

    public boolean hitObstacle() {
        if (this.obstacles != null && this.obstacles.size() != 0) {
            int var1;
            int var2;
            if (this.isUnicycle) {
                var1 = (int)this.x - 15;
                var2 = (int)this.y + 8;
                Rectangle2D.Double[] var8 = new Rectangle2D.Double[]{new Rectangle2D.Double((double)var1, this.maxHeight - (double)var2, 30.0, 16.0)};
                return this.checkRects(var8);
            } else {
                var1 = (int)(this.x + this.r - this.L);
                var2 = (int)(this.y + this.S / 2.0);
                int var3 = (int)(this.x - this.r);
                int var4 = (int)(this.y + this.S / 2.0 + this.d);
                int var6 = (int)(this.y - this.S / 2.0);
                Rectangle2D.Double[] var7 = new Rectangle2D.Double[]{new Rectangle2D.Double((double)var1, this.maxHeight - (double)var2, this.L, this.S), new Rectangle2D.Double((double)var3, this.maxHeight - (double)var4, 2.0 * this.r, this.d), new Rectangle2D.Double((double)var3, this.maxHeight - (double)var6, 2.0 * this.r, this.d)};
                return this.checkRects(var7);
            }
        } else {
            return false;
        }
    }

    boolean checkRects(Rectangle2D.Double[] var1) {
        AffineTransform var2 = AffineTransform.getRotateInstance(-this.theta, this.x, this.maxHeight - this.y);

        for(int var3 = 0; var3 < var1.length; ++var3) {
            Shape var4 = var2.createTransformedShape(var1[var3]);
            Iterator var5 = this.obstacles.iterator();

            while(var5.hasNext()) {
                Rectangle2D.Double var6 = (Rectangle2D.Double)var5.next();
                Rectangle2D.Double var7 = new Rectangle2D.Double(var6.x, this.maxHeight - var6.y, var6.width, var6.height);
                if (var4.intersects(var7)) {
                    return true;
                }
            }
        }

        return false;
    }
}
