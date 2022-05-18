package graphic;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
 

public class Menu implements ActionListener{
    JFrame frame;
    JSlider slider;
    JOptionPane optionPane;
    JButton soloPlayerButton,multiPlayerButton,tournamentButton,sizeButton;
    int size=5;
    
    public void start(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int) screenSize.getHeight();
    
        BackgroundPanel bg= new BackgroundPanel("data/HexWallpaper.jpg",width,height);
    
        frame=new JFrame("Hex Game");//creating instance of JFrame  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width ,height);//Size init 
    
        soloPlayerButton= myButton("SOLO PLAY",width-50,height+100,400,75);
        multiPlayerButton= myButton("MULTIPLAYER",width-50,height+275,400,75);
        tournamentButton= myButton("TOURNAMENT",width-50,height+450,400,75);
        sizeButton= myButton("SIZE",width-50,height+625,400,75);
        
        soloPlayerButton.addActionListener(this);


        optionPane = new JOptionPane();
        slider = getSlider(optionPane);
    
        frame.add(soloPlayerButton);//adding button in JFrame  
        frame.add(multiPlayerButton);
        frame.add(tournamentButton);
        frame.add(sizeButton);
        frame.add(bg);

        frame.setVisible(true);//making the frame visible
    }
    

    public static JButton myButton(String name,int x,int y,int width,int height){
        JButton button=new JButton(name);
        button.setBounds(((x/2)-(width/2)),((y/2)-(height/2)),width, height);
        button.setForeground(Color.CYAN);
        button.setBackground(Color.GRAY);
        return button;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == soloPlayerButton)
        {
            JOptionPane.showMessageDialog(null, "I am Button 1");
            System.out.println("zzzzzzzz");
            GameInterface soloPlayerGame=new GameInterface(size,"solo");
        }
        else if(e.getSource() == multiPlayerButton)
        {
            GameInterface multiPlayerGame=new GameInterface(size,"multiplayer");
        }
        else if(e.getSource() == tournamentButton)
        {
            GameInterface tournamentGame=new GameInterface(size,"tournament");
        }
        else if(e.getSource() == sizeButton)
        {
            optionPane.setMessage(new Object[] { "Select a value: ", slider });
            optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
            optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
            JDialog dialog = optionPane.createDialog(frame, "My Slider");
            dialog.setVisible(true);
            System.out.println("Input: " + optionPane.getInputValue());
        }  
    }

    static JSlider getSlider(final JOptionPane optionPane) {
        JSlider slider = new JSlider(5,20);
        slider.setMajorTickSpacing(10);
        slider.setMajorTickSpacing(1);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        ChangeListener changeListener = new ChangeListener() {
          public void stateChanged(ChangeEvent changeEvent) {
            JSlider theSlider = (JSlider) changeEvent.getSource();
            if (!theSlider.getValueIsAdjusting()) {
              optionPane.setInputValue((int)(theSlider.getValue()));
            }
          }
        };
        slider.addChangeListener(changeListener);
        return slider;
    }
}
