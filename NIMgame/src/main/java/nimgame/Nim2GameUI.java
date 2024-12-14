/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nimgame;
 import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Nim2GameUI extends JFrame implements ActionListener {
    private List<Integer> piles;
    private int currentPlayer;
    private int winner;
    int depth ,flag;
    JPanel pilesPanel = new JPanel(new FlowLayout());

     JPanel panel1 = new JPanel(new FlowLayout());
        JPanel panel3 = new JPanel();

    private JButton[] pileButtons;
    private JButton aiMoveButton,playAgain,changeSticksINpiles,home;
    private SticksPanel sticksPanel;
    public Nim2GameUI(int depth,int sticks) {
        // Set up the frame
        setTitle("Nim Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground( new Color(1, 95, 48));
        this.depth=depth;
        // Initialize game variables
        piles = Nim2Methods.divideSticks(sticks); 
         Random random = new Random();
        currentPlayer = random.nextInt(2) + 1;;

        
        winner = 0;

        // Create buttons for each pile
        pileButtons = new JButton[30];
        pilesPanel.setBackground( new Color(1, 95, 48));

        for (int i = 0; i < piles.size(); i++) {
            pileButtons[i] = new JButton("Pile " + (i + 1) + ": " + piles.get(i));
            pileButtons[i].addActionListener(this);
           pileButtons[i].setBackground( new Color(236,236,154));

            pilesPanel.add(pileButtons[i]);
        }
        add(pilesPanel, BorderLayout.NORTH);

        // Create a button for the AI move
        aiMoveButton = new JButton("AI Move");
        aiMoveButton.setBackground( new Color(236,236,154));
        aiMoveButton.addActionListener(this);

        playAgain = new JButton("Play Again");
        playAgain.setBackground( new Color(236,236,154));
        playAgain.addActionListener(this);
        
        changeSticksINpiles = new JButton("change sticks in pile");
        changeSticksINpiles.setBackground( new Color(236,236,154));
        changeSticksINpiles.addActionListener(this);
        
        home = new JButton("home");
        home.setBackground( new Color(236,236,154));
        home.addActionListener(this);
        
        panel1.add(aiMoveButton);
        panel1.add(playAgain);
        panel1.add(changeSticksINpiles);
        panel1.add(home);

        panel1.setBackground( new Color(1, 95, 48));
        add(panel1, BorderLayout.SOUTH);
        // Create a label for displaying the current player
        JLabel playerLabel = new JLabel("Current Player: " + currentPlayer);
        add(playerLabel, BorderLayout.CENTER);

        // Create a panel to display the sticks
        sticksPanel = new SticksPanel(piles);
        add(sticksPanel, BorderLayout.CENTER);
     updateUI();
        // Set up the frame size
        setSize(400, 500);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
      if(currentPlayer==1){
       JOptionPane.showMessageDialog(null, "The Player "+currentPlayer+" start play");
        setTitle("Nim Game - Current Player: " + currentPlayer);

        }
        else{
          JOptionPane.showMessageDialog(null,"The AI start play");
          setTitle("Nim Game - Current Player: AI ");

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == aiMoveButton) {
            handleAIMove();
        }
         else if(e.getSource() == playAgain){
             flag=1;
             setVisible(false);
          new num_difficulty(2).setVisible(true);

        }
         else if(e.getSource() == home){
                flag=1;
             setVisible(false);
          new NIM().setVisible(true);
         }
         else if(e.getSource()==changeSticksINpiles){
           List<Integer> piles2=new ArrayList();

       int index = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of piles you want" ));
       int sum=0;
             for(int i=0;i<index;i++ ){
       int sticks = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of stiks  you want in pile" + (i+1)));
       piles2.add(i, sticks);
             
             }
             piles=piles2;
         }
        else {
            for (int i = 0; i < pileButtons.length; i++) {
                if (e.getSource() == pileButtons[i]) {
                    handleMove(i);
                    break;
                }
            }
        }
        updateUI();
    }

    private void handleMove(int pileIndex) {
        int countersToRemove = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of counters to remove:"));
while(!Nim2Methods.isValidMove(piles, pileIndex, countersToRemove)){
       countersToRemove = Integer.parseInt(JOptionPane.showInputDialog("Not valid try again"));
}
 piles.set(pileIndex, piles.get(pileIndex) - countersToRemove);
       

        currentPlayer = 3 - currentPlayer; // Switch players
    }

    private void handleAIMove() {
              if(depth==2){
                  int[] move = Nim2Methods.aiMoveEasy(piles);
                int pileIndex = move[0];
                int countersToRemove = move[1];
                piles.set(pileIndex, piles.get(pileIndex) - countersToRemove);
                pileIndex=pileIndex+1;

                JOptionPane.showMessageDialog(null,"The AI remove "+countersToRemove+" From pile "+pileIndex);

                }
                else if(depth==4){
                    int[] move = Nim2Methods.aiMoveMedium(piles);
                int pileIndex = move[0];
                int countersToRemove = move[1];
                piles.set(pileIndex, piles.get(pileIndex) - countersToRemove);
                pileIndex=pileIndex+1;

                JOptionPane.showMessageDialog(null,"The AI remove "+countersToRemove+" From pile "+pileIndex);

                }
              else if(depth==8){
                int[] move = Nim2Methods.aiMoveHard(piles, Integer.MIN_VALUE, Integer.MAX_VALUE);
                int pileIndex = move[0];
                int countersToRemove = move[1];
                piles.set(pileIndex, piles.get(pileIndex) - countersToRemove);
                pileIndex=pileIndex+1;
               JOptionPane.showMessageDialog(null,"The AI remove "+countersToRemove+" From pile "+ pileIndex);

            } 
        
        currentPlayer = 3 - currentPlayer; // Switch players
    }

    private void updateUI() {
        
        
        pilesPanel.removeAll();
        getContentPane().remove(pilesPanel);
               
        
                revalidate();
                repaint();
        // Update the buttons' text

        for (int i = 0; i < piles.size(); i++) {
            pileButtons[i] = new JButton("Pile " + (i + 1) + ": " + piles.get(i));
            pileButtons[i].addActionListener(this);
            pileButtons[i].setBackground( new Color(236,236,154));

            pilesPanel.add(pileButtons[i]);      
        }
        add(pilesPanel, BorderLayout.NORTH);
       
            revalidate();
            repaint();
        
        
      
 

        // Update the sticks panel
        sticksPanel.updatePiles(piles);
        
         if(currentPlayer==1){
         aiMoveButton.setEnabled(false);
                     for (int i = 0; i < piles.size(); i++) {
                         if(piles.get(i)==0 ){
                             pileButtons[i].setEnabled(false);
                         }
                         else{
                     pileButtons[i].setEnabled(true);
                             }
                     }
          // Display the current player
        setTitle("Nim Game - Current Player: " + currentPlayer);
         }
         else{
                      aiMoveButton.setEnabled(true);
                     for (int i = 0; i < piles.size(); i++) {
                             pileButtons[i].setEnabled(false);                             
         }
         setTitle("Nim Game - Current Player: AI " );

         }
       
        // Check for game over
        if (Nim2Methods.isGameOver(piles)) {
        winner=currentPlayer;
            endGame();
        }
    }

    private void endGame() {
        // Display the winner
        if(flag!=1){
        if(winner==2){
        JOptionPane.showMessageDialog(this, "AI "  + " wins!");
        }
        else{
        JOptionPane.showMessageDialog(this, "player " + currentPlayer + " wins!");

        }
        }
      
    }

    public static void main(String[] args) {
    }

}

class SticksPanel extends JPanel {
    private List<Integer> piles;

    public SticksPanel(List<Integer> piles) {
        this.piles = piles;
    }

    public void updatePiles(List<Integer> piles) {
        this.piles = piles;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
          Graphics2D g2d = (Graphics2D) g;

         setBackground( new Color(1, 95, 48));
   this.setSize(500,400);
        int x = 10;
        int y = 40;

        for (int i = 0; i < piles.size(); i++) {
            int sticksCount = piles.get(i);

            for (int j = 0; j < sticksCount; j++) {
                // Draw a small red circle above each line
                g2d.setColor(Color.RED);
                g2d.fillOval(x, y - 2, 10, 10);

                g2d.setColor(new Color(236, 236, 154));
                g2d.setStroke(new BasicStroke(8));
                g2d.drawLine(x + 5, y + 10, x + 5, y + 50);
                x += 40; // Move to the next position
            }
            
        
            
            // Move to the next line
            x = 10;
            y += 70;
        }


    }
}
