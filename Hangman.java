//hangman game
import java.util.*;
import java.util.concurrent.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.Timer;

class TimedInput extends Thread
{
    String inp = "";

    public void run()
    {
        Scanner sc = new Scanner(System.in);
        this.inp = sc.nextLine();
    }
}

class Hangman
{
    String[] word_list;
    String word;
    int lives, currMode, timeLim, time;
    boolean blitz;
    JFrame frame;
    JPanel cont;
    JLabel titleLabel, Doomed, guessedChars, InpTake, wordLabel, countDownLabel;
    JButton giveUp;
    MyKeyListener kls;
    ActionListener countdown;
    Timer timer;

    class MyKeyListener implements KeyListener
    {
        String guessLine = "<html><pre>Your Guesses: ";
        char guessed[] = new char[26];
        int current = 0;

        @Override
        public void keyTyped(KeyEvent e)
        {

        }

        @Override
        public void keyPressed(KeyEvent e)
        {

        }

        @Override
        public void keyReleased(KeyEvent e)
        {
            char inp = Character.toUpperCase(e.getKeyChar());
            boolean guessedAlready = false;
            String wrd = "";
            int right = 0;

            for(int i = 0; i < this.guessed.length; i++)
            {
                if(inp == this.guessed[i])
                {
                    guessedAlready = true;
                    break;
                }
            }

            if(guessedAlready)
            {
                Hangman.this.InpTake.setText("Letter has been already been guessed.");
            }
            else if(!Character.isAlphabetic(inp))
            {
                Hangman.this.InpTake.setText("Please Press an Alphabet Key");
            }
            else
            {
                if(Hangman.this.blitz)
                {
                    Hangman.this.timer.stop();
                    Hangman.this.time = Hangman.this.timeLim;
                    Hangman.this.countDownLabel.setText("Countdown: " + Hangman.this.timeLim);
                    Hangman.this.timer.start();
                }

                if(current == 11)
                {
                    this.guessLine += inp + "<br/>";
                }
                else
                {
                    this.guessLine += inp + "  ";
                }

                if(current == 12)
                {
                    Hangman.this.guessedChars.setBounds(25, 478, 750, 60);
                    Hangman.this.InpTake.setBounds(25, 543, 750, 50);
                }

                Hangman.this.guessedChars.setText(guessLine + "</pre></html>");

                this.guessed[this.current] = inp;
                this.current++;

                boolean guessedRight = false;
                for(int i = 0; i < Hangman.this.word.length(); i++)
                {
                    for(int j = 0; j < this.guessed.length; j++)
                    {
                        if(Hangman.this.word.charAt(i) == this.guessed[j])
                        {
                            guessedRight = true;
                            wrd += this.guessed[j] + " ";
                            right++;
                        }
                    }
                    if(!guessedRight)
                    {
                        wrd += "_ ";
                    }

                    guessedRight = false;
                }

                Hangman.this.wordLabel.setText(wrd);

                if(right == Hangman.this.word.length())
                {
                    Hangman.this.InpTake.setText("Congratulations! You have guessed the word!");
                    Hangman.this.gameOver();
                }
                else if(Hangman.this.word.indexOf(inp) == -1)
                {
                    Hangman.this.InpTake.setText("Wrong Guess!");
                    Hangman.this.lives--;
                    Hangman.this.Doomed.setText(Hangman.this.DrawMan(Hangman.this.lives));

                    if(Hangman.this.lives == 0)
                    {
                        Hangman.this.gameOver();
                    }
                }
                else
                {
                    Hangman.this.InpTake.setText("Correct Guess!");
                }
            }
        }
    }

    String DrawMan(int lives)
    {
        switch(lives)
        {
            case 11:
                return("<html><pre><br/>"+
                                   "<br/>"+
                                   "<br/>"+
                                   "<br/>"+
                                   "<br/>"+
                                   "<br/>"+
                                   "=========<br/></pre></html>");
            case 10:
                return("<html><pre><br/>"+
                                   "<br/>"+
                                   "<br/>"+
                                   "<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "=========<br/></pre></html>");
            case 9:
                return("<html><pre><br/>"+
                                   "<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "=========<br/></pre></html>");
            case 8:
                return("<html><pre>      +<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "=========<br/></pre></html>");
            case 7:
                return("<html><pre>  +---+<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "=========<br/></pre></html>");
            case 6:
                return("<html><pre>  +---+<br/>"+
                                   "  |   |<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "=========<br/></pre></html>");
            case 5:
                return("<html><pre>  +---+<br/>"+
                                   "  |   |<br/>"+
                                   "  O   |<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "=========<br/></pre></html>");
            case 4:
                return("<html><pre>  +---+<br/>"+
                                   "  |   |<br/>"+
                                   "  O   |<br/>"+
                                   "  |   |<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "=========<br/></pre></html>");
            case 3:
                return("<html><pre>  +---+<br/>"+
                                   "  |   |<br/>"+
                                   "  O   |<br/>"+
                                   " /|   |<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "=========<br/></pre></html>");
            case 2:
                return("<html><pre>  +---+<br/>"+
                                   "  |   |<br/>"+
                                   "  O   |<br/>"+
                                   " /|\\  |<br/>"+
                                   "      |<br/>"+
                                   "      |<br/>"+
                                   "=========<br/></pre></html>");
            case 1:
                return("<html><pre>  +---+<br/>"+
                                   "  |   |<br/>"+
                                   "  O   |<br/>"+
                                   " /|\\  |<br/>"+
                                   " /    |<br/>"+
                                   "      |<br/>"+
                                   "=========<br/></pre></html>");
            case 0:
                return("<html><pre>  +---+<br/>"+
                                   "  |   |<br/>"+
                                   "  O   |<br/>"+
                                   " /|\\  |<br/>"+
                                   " / \\  |<br/>"+
                                   "      |<br/>"+
                                   "=========<br/></pre></html>");
        }
        return "String messed up, my bad";
    }

    void game()
    {

        this.lives = this.currMode;
        this.word = this.word_list[(int)(Math.random() * 58109)];
        String Dashed = "";

        this.cont.removeAll();
        this.cont.setBackground(Color.decode("#EEA47F"));

        this.cont.add(this.titleLabel);

        this.Doomed = new JLabel(this.DrawMan(this.lives), SwingConstants.CENTER);
        this.Doomed.setBounds(0, 175, 750, 200);
        this.Doomed.setBackground(Color.decode("#FFAD4A"));
        this.Doomed.setForeground(Color.decode("#C5299B"));
        this.Doomed.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        this.Doomed.setOpaque(true);
        this.cont.add(this.Doomed);

        for(int i = 0; i < this.word.length(); i++)
        {
            Dashed += "_ ";
        }

        this.wordLabel = new JLabel(Dashed, SwingConstants.CENTER);
        this.wordLabel.setBounds(0, 355, 750, 100);
        this.wordLabel.setBackground(Color.decode("#FFAD4A"));
        this.wordLabel.setForeground(Color.decode("#C5299B"));
        this.wordLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        this.wordLabel.setOpaque(true);
        this.cont.add(this.wordLabel);

        this.guessedChars = new JLabel("<html><pre>Your Guesses: </pre></html>");
        this.guessedChars.setBounds(25, 465, 750, 60);
        this.guessedChars.setForeground(Color.decode("#00539C"));
        this.guessedChars.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 23));
        this.cont.add(this.guessedChars);

        this.InpTake = new JLabel("Press Your Guess on the Keyboard");
        this.InpTake.setBounds(25, 530, 750, 50);
        this.InpTake.setForeground(Color.decode("#00539C"));
        this.InpTake.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 23));
        this.cont.add(this.InpTake);

        this.countDownLabel = new JLabel("Countdown: " + this.timeLim);
        this.countDownLabel.setBounds(25, 590, 750, 50);
        this.countDownLabel.setForeground(Color.decode("#00539C"));
        this.countDownLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 23));

        this.giveUp = new JButton("Give Up");
        this.giveUp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.giveUp.setBounds(10, 670, 150, 50);
        this.giveUp.setBackground(Color.decode("#00539C"));
        this.giveUp.setForeground(Color.decode("#EEA47F"));
        this.giveUp.setBorderPainted(false);
        this.giveUp.setOpaque(true);
        this.giveUp.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        this.cont.add(this.giveUp);

        if(this.blitz)
        {
            this.cont.add(this.countDownLabel);
            this.time = this.timeLim;
            this.countdown = new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    Hangman.this.time--;
                    Hangman.this.countDownLabel.setText("Countdown: " + Hangman.this.time);

                    if(Hangman.this.time == 0)
                    {
                        Hangman.this.InpTake.setText("You ran out of time!");
                        Hangman.this.countDownLabel.setText("Countdown: " + Hangman.this.timeLim);
                        Hangman.this.time = Hangman.this.timeLim;
                        Hangman.this.lives--;
                        Hangman.this.Doomed.setText(Hangman.this.DrawMan(Hangman.this.lives));

                        if(Hangman.this.lives == 0)
                        {
                            Hangman.this.gameOver();
                        }
                    }
                }
            };

            this.timer = new Timer(1000, this.countdown);
            this.timer.start();
        }

        this.giveUp.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Hangman.this.lives = 0;
            Hangman.this.Doomed.setText(Hangman.this.DrawMan(Hangman.this.lives));
            Hangman.this.gameOver();
         }
        });

        this.frame.toFront();
        this.frame.requestFocus();

        this.kls = new MyKeyListener();
        this.frame.addKeyListener(this.kls);

        this.frame.revalidate();
        this.frame.repaint();

    }

    void gameOver()
    {
        String completeWrd = "";

        this.cont.remove(this.giveUp);
        this.cont.remove(this.countDownLabel);
        this.frame.removeKeyListener(this.kls);

        if(this.blitz)
        {
            this.timer.stop();
        }

        if(this.lives == 0)
        {
            for(int i = 0; i < this.word.length(); i++)
            {
                completeWrd += this.word.charAt(i) + " ";
            }
            this.wordLabel.setText(completeWrd);
            this.InpTake.setText("Game Over! You Have Lost.");
        }
        else
        {
            this.InpTake.setText("Game Over! You Won.");
        }

        JButton playAgain = new JButton("Play Again");
        playAgain.setCursor(new Cursor(Cursor.HAND_CURSOR));
        playAgain.setBounds(60, 620, 300, 75);
        playAgain.setBackground(Color.decode("#00539C"));
        playAgain.setForeground(Color.decode("#EEA47F"));
        playAgain.setBorderPainted(false);
        playAgain.setOpaque(true);
        playAgain.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        this.cont.add(playAgain);

        JButton retMainMenu = new JButton("Return to Main Menu");
        retMainMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        retMainMenu.setBounds(385, 620, 300, 75);
        retMainMenu.setBackground(Color.decode("#00539C"));
        retMainMenu.setForeground(Color.decode("#EEA47F"));
        retMainMenu.setBorderPainted(false);
        retMainMenu.setOpaque(true);
        retMainMenu.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        this.cont.add(retMainMenu);

        playAgain.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Hangman.this.game();
         }
        });

        retMainMenu.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Hangman.this.init();
         }
        });

        this.frame.revalidate();
        this.frame.repaint();
    }

    void init()
    {
        this.cont.removeAll();
        this.frame.removeKeyListener(this.kls);

        this.cont.setBackground(Color.decode("#EEA47F"));
        this.cont.setLayout(null);

        int inp = 0;
        this.blitz = false;

        this.cont.add(this.titleLabel);

        JLabel WelcomeTitle = new JLabel("CHOOSE A MODE", SwingConstants.CENTER);
        WelcomeTitle.setBounds(0, 175, 750, 100);
        WelcomeTitle.setBackground(Color.decode("#FFAD4A"));
        WelcomeTitle.setForeground(Color.decode("#C5299B"));
        WelcomeTitle.setBorder(new EmptyBorder(0,10,0,0));
        WelcomeTitle.setFont(new Font(Font.MONOSPACED, Font.BOLD, 55));
        WelcomeTitle.setOpaque(true);
        this.cont.add(WelcomeTitle);

        JButton NormalBtn = new JButton("Normal Game");
        NormalBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        NormalBtn.setBounds(25, 305, 250, 75);
        NormalBtn.setBackground(Color.decode("#00539C"));
        NormalBtn.setForeground(Color.decode("#EEA47F"));
        NormalBtn.setBorderPainted(false);
        NormalBtn.setOpaque(true);
        NormalBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 23));
        this.cont.add(NormalBtn);

        JButton blitzBtn = new JButton("Blitz Game");
        blitzBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        blitzBtn.setBounds(25, 405, 250, 75);
        blitzBtn.setBackground(Color.decode("#00539C"));
        blitzBtn.setForeground(Color.decode("#EEA47F"));
        blitzBtn.setBorderPainted(false);
        blitzBtn.setOpaque(true);
        blitzBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 23));
        this.cont.add(blitzBtn);

        JButton backBtn =  new JButton("Go Back");
        backBtn.setBounds(10, 670, 150, 50);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setBackground(Color.decode("#00539C"));
        backBtn.setForeground(Color.decode("#EEA47F"));
        backBtn.setBorderPainted(false);
        backBtn.setOpaque(true);
        backBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        JButton EasyMode =  new JButton("Easy Mode (11 lives)");
        EasyMode.setCursor(new Cursor(Cursor.HAND_CURSOR));
        EasyMode.setBounds(25, 305, 300, 75);
        EasyMode.setBackground(Color.decode("#00539C"));
        EasyMode.setForeground(Color.decode("#EEA47F"));
        EasyMode.setBorderPainted(false);
        EasyMode.setOpaque(true);
        EasyMode.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        JButton NormalMode =  new JButton("Normal Mode (6 lives)");
        NormalMode.setCursor(new Cursor(Cursor.HAND_CURSOR));
        NormalMode.setBounds(25, 405, 300, 75);
        NormalMode.setBackground(Color.decode("#00539C"));
        NormalMode.setForeground(Color.decode("#EEA47F"));
        NormalMode.setBorderPainted(false);
        NormalMode.setOpaque(true);
        NormalMode.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        JButton HardMode =  new JButton("Hard Mode (5 lives)");
        HardMode.setCursor(new Cursor(Cursor.HAND_CURSOR));
        HardMode.setBounds(25, 505, 300, 75);
        HardMode.setBackground(Color.decode("#00539C"));
        HardMode.setForeground(Color.decode("#EEA47F"));
        HardMode.setBorderPainted(false);
        HardMode.setOpaque(true);
        HardMode.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        JButton EasyTime =  new JButton("Easy Mode (15 seconds)");
        EasyTime.setCursor(new Cursor(Cursor.HAND_CURSOR));
        EasyTime.setBounds(25, 305, 325, 75);
        EasyTime.setBackground(Color.decode("#00539C"));
        EasyTime.setForeground(Color.decode("#EEA47F"));
        EasyTime.setBorderPainted(false);
        EasyTime.setOpaque(true);
        EasyTime.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        JButton NormalTime =  new JButton("Normal Mode (10 seconds)");
        NormalTime.setCursor(new Cursor(Cursor.HAND_CURSOR));
        NormalTime.setBounds(25, 405, 325, 75);
        NormalTime.setBackground(Color.decode("#00539C"));
        NormalTime.setForeground(Color.decode("#EEA47F"));
        NormalTime.setBorderPainted(false);
        NormalTime.setOpaque(true);
        NormalTime.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        JButton HardTime =  new JButton("Hard Mode (5 seconds)");
        HardTime.setCursor(new Cursor(Cursor.HAND_CURSOR));
        HardTime.setBounds(25, 505, 325, 75);
        HardTime.setBackground(Color.decode("#00539C"));
        HardTime.setForeground(Color.decode("#EEA47F"));
        HardTime.setBorderPainted(false);
        HardTime.setOpaque(true);
        HardTime.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        this.frame.revalidate();
        this.frame.repaint();
        this.frame.setVisible(true);

        NormalBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            WelcomeTitle.setText("CHOOSE DIFFICULTY");
            Hangman.this.cont.remove(NormalBtn);
            Hangman.this.cont.remove(blitzBtn);
            Hangman.this.cont.add(EasyMode);
            Hangman.this.cont.add(NormalMode);
            Hangman.this.cont.add(HardMode);
            Hangman.this.cont.add(backBtn);
            Hangman.this.frame.revalidate();
            Hangman.this.frame.repaint();
         }
        });

        backBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
             WelcomeTitle.setText("CHOOSE A MODE");
             Hangman.this.cont.add(NormalBtn);
             Hangman.this.cont.add(blitzBtn);
             Hangman.this.cont.remove(EasyMode);
             Hangman.this.cont.remove(NormalMode);
             Hangman.this.cont.remove(HardMode);
             Hangman.this.cont.remove(backBtn);
             Hangman.this.cont.remove(EasyTime);
             Hangman.this.cont.remove(NormalTime);
             Hangman.this.cont.remove(HardTime);
             Hangman.this.frame.revalidate();
             Hangman.this.frame.repaint();
             Hangman.this.blitz = false;
         }
        });

        blitzBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            WelcomeTitle.setText("CHOOSE DIFFICULTY");
            Hangman.this.cont.remove(NormalBtn);
            Hangman.this.cont.remove(blitzBtn);
            Hangman.this.cont.add(EasyMode);
            Hangman.this.cont.add(NormalMode);
            Hangman.this.cont.add(HardMode);
            Hangman.this.cont.add(backBtn);
            Hangman.this.frame.revalidate();
            Hangman.this.frame.repaint();
            Hangman.this.blitz = true;
         }
        });

        EasyMode.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Hangman.this.currMode = 11;
            if(!Hangman.this.blitz)
            {
                Hangman.this.game();
            }
            else
            {
                WelcomeTitle.setText("CHOOSE TIME LIMIT");
                Hangman.this.cont.remove(EasyMode);
                Hangman.this.cont.remove(NormalMode);
                Hangman.this.cont.remove(HardMode);
                Hangman.this.cont.add(EasyTime);
                Hangman.this.cont.add(NormalTime);
                Hangman.this.cont.add(HardTime);
                Hangman.this.frame.revalidate();
                Hangman.this.frame.repaint();
            }
         }
        });

        NormalMode.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Hangman.this.currMode = 7;
            if(!Hangman.this.blitz)
            {
                Hangman.this.game();
            }
            else
            {
                WelcomeTitle.setText("CHOOSE TIME LIMIT");
                Hangman.this.cont.remove(EasyMode);
                Hangman.this.cont.remove(NormalMode);
                Hangman.this.cont.remove(HardMode);
                Hangman.this.cont.add(EasyTime);
                Hangman.this.cont.add(NormalTime);
                Hangman.this.cont.add(HardTime);
                Hangman.this.frame.revalidate();
                Hangman.this.frame.repaint();
            }
         }
        });

        HardMode.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Hangman.this.currMode = 6;
            if(!Hangman.this.blitz)
            {
                Hangman.this.game();
            }
            else
            {
                WelcomeTitle.setText("CHOOSE TIME LIMIT");
                Hangman.this.cont.remove(EasyMode);
                Hangman.this.cont.remove(NormalMode);
                Hangman.this.cont.remove(HardMode);
                Hangman.this.cont.add(EasyTime);
                Hangman.this.cont.add(NormalTime);
                Hangman.this.cont.add(HardTime);
                Hangman.this.frame.revalidate();
                Hangman.this.frame.repaint();
            }
         }
        });

        EasyTime.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Hangman.this.timeLim = 15;
            Hangman.this.game();
         }
        });

        NormalTime.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Hangman.this.timeLim = 10;
            Hangman.this.game();
         }
        });

        HardTime.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Hangman.this.timeLim = 5;
            Hangman.this.game();
         }
        });
    }

    Hangman()
    {
        int longest = 0;
        this.frame = new JFrame("Hangman");
        this.frame.setSize(750, 750);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.titleLabel = new JLabel("<html><pre>dP<br/>"+
        "88<br/>"+
        "88d888b. .d8888b. 88d888b. .d8888b. 88d8b.d8b. .d8888b. 88d888b.<br/>"+
        "88'  `88 88'  `88 88'  `88 88'  `88 88'`88'`88 88'  `88 88'  `88<br/>"+
        "88    88 88.  .88 88    88 88.  .88 88  88  88 88.  .88 88    88<br/>"+
        "dP    dP `88888P8 dP    dP `8888P88 dP  dP  dP `88888P8 dP    dP<br/>"+
        "                                .88<br/>"+
        "                             d8888P</pre></html>", SwingConstants.CENTER);

        this.titleLabel.setBounds(0, 0, 750, 175);
        this.titleLabel.setForeground(Color.decode("#8a2be2"));
        this.titleLabel.setBackground(Color.decode("#FC776A"));
        this.titleLabel.setOpaque(true);
        this.titleLabel.setFont(new Font(Font.SERIF, Font.BOLD, 14));

        this.cont = new JPanel();
        this.frame.add(this.cont);
        try
        {
            String inputLine = "";

            URL url = new URL("http://www.mieliestronk.com/corncob_caps.txt");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");


            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String csv = "";

            int i = 0;
            System.out.println("Loading...\n[____________________] 0%");

            while((inputLine = in.readLine()) != null)
            {
                if(inputLine.length() > longest)
                {
                    longest = inputLine.length();
                }
                
                if(i%(58108*5/100) == 0)
                {
                    System.out.print("\fLoading...\n");
                    System.out.print("[");

                    int percentage = i/(58108*5/100);

                    for(int j = 0; j < percentage; j++)
                    {
                        System.out.print("███");
                    }

                    for(int j = 0; j < 20 - percentage; j++)
                    {
                        System.out.print("___");
                    }
                    System.out.println("] " + (i/(58108*5/100) * 5) + "%");
                }
                csv += inputLine + ",";
                i++;
            }

            this.word_list = csv.split(",");
        }
        catch (MalformedURLException e)
        {
            System.out.println("Internet is not connected");
            System.exit(1);
        }
        catch (IOException e)
        {
            System.out.println("Internet is not connected");
            System.exit(1);
        }

        this.lives = 6;
        this.currMode = 6;
        this.blitz = false;

        System.out.println(longest);

        this.init();
    }



    public static void main(String[] args)
    {
        Hangman obj = new Hangman();
    }


}
