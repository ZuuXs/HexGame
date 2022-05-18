package graphic;
 
import java.awt.Graphics;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
 
public class BackgroundPanel extends JPanel {
 
    private static final long serialVersionUID = 1L;
    private ImageIcon background;
    private int width,height;
    public BackgroundPanel(String fileName,int width,int height) {
        super();
        this.setLayout(new GridBagLayout());
        this.background = new ImageIcon(fileName);
        this.width=width;
        this.height=height;
    }
 
    public void setBackground(ImageIcon background) {
        this.background = background;
    }
 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getImage(),0,0, width, height, this);
    }

}