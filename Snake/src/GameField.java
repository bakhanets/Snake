import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE=320;/*Поля 320x320*/
    private final int DOT_SIZE=16;///Размер одной ячейки 16пикселей
    private final int ALL_DOTS=400;///Всео может быть яблок и количество полей
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[]x=new int[ALL_DOTS];
    private int[]y=new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left=false;/*Направление движения*/
    private boolean right=true;
    private boolean down=false;
    private boolean up =false;
    private boolean inGame=true;


    public GameField()
    {
    setBackground(Color.black);
    loadImages();
    initGame();
    addKeyListener(new FieldKeyListener());
    setFocusable(true);

    }

    public void initGame()
    {
       dots=3;
       for (int i=0;i<dots;i++)
       {
            x[i]=48-i*DOT_SIZE;
            y[i]=48;
       }
       timer=new Timer(250,this);
       timer.start();
       createApple();
    }

    public void createApple()
    {
        appleX=new Random().nextInt(19)*DOT_SIZE;
        appleY=new Random().nextInt(19)*DOT_SIZE;
    }



    public void loadImages()
    {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame)
        {
            g.drawImage(apple,appleX,appleY,this);
            for (int i = 0; i <dots ; i++) {
                g.drawImage(dot,x[i],y[i],this);
            }
        }
        else
            {
                String str="Game Over";
                g.setColor(Color.WHITE);
                g.drawString(str,125,SIZE/2);
                JButton button = new JButton("Test button");
            }
    }

    public void move()
    {
        for (int i=dots;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if (left)
        {
            x[0]-=DOT_SIZE;
        }
        if (right)
        {
            x[0]+=DOT_SIZE;
        }
        if (up)
        {
            y[0]-=DOT_SIZE;
        }
        if (down)
        {
            y[0]+=DOT_SIZE;
        }
    }

    public void checkApple()
    {
        if (x[0]==appleX && y[0]==appleY)
        {
            dots++;
            createApple();
        }
    }

    private void checkCollisions()
    {
        for (int i = dots; i >0 ; i--)
        {
            if(i>4  && x[0]==x[i] && y[0]==y[i])
            {
            inGame=false;
            }
        }
        if (x[0]>SIZE){inGame=false;}
        if (x[0]<0){inGame=false;}
        if (y[0]>SIZE){inGame=false;}
        if (y[0]<0){inGame=false;}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame)
        {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }
    class FieldKeyListener extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key= e.getKeyCode();
            if (key==KeyEvent.VK_LEFT&&!right)
            {
                left=true;
                down=false;
                up=false;
            }
            if (key==KeyEvent.VK_RIGHT&&!left)
            {
                right=true;
                down=false;
                up=false;
            }
            if (key==KeyEvent.VK_UP&&!down)
            {
                left=false;
                right=false;
                up=true;
            }
            if (key==KeyEvent.VK_DOWN&&!up)
            {
                left=false;
                right=false;
                down=true;
            }
        }
    }



}
