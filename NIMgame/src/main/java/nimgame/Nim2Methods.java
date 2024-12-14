/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nimgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Nim2Methods {
    
 public static boolean isGameOver(List<Integer> piles) {
        for (int pile : piles) {
            if (pile > 0) {
                return false;
            }
        }
        return true;
    }

   

    public static int[] aiMoveHard(List<Integer> piles, int alpha, int beta) {
        
        int[] bestMove = new int[2];
        int maxEval = Integer.MIN_VALUE;

       for (int i = 0; i < piles.size(); i++) {
            for (int j = 1; j <= piles.get(i); j++) {
                if (isValidMove(piles, i, j)) {
                    List<Integer> newPiles = new ArrayList<>(piles);
                       newPiles.set(i, piles.get(i) - j);
                    int eval = alphaBeta(newPiles, alpha, beta,false);

                    if (eval > maxEval) {
                        maxEval = eval;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }

                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }

        return bestMove; 
        
        
    
    }

    public static int alphaBeta(List<Integer> piles, int alpha, int beta,boolean isMaximizing) {
         if ( isGameOver(piles)) {
            return evaluateState(piles,isMaximizing);
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
           for (int i = 0; i < piles.size(); i++) {
            for (int j = 1; j <= piles.get(i); j++) {
                    if (isValidMove(piles, i, j)) {
                        List<Integer> newPiles = new ArrayList<>(piles);
                       newPiles.set(i, piles.get(i) - j);
                        int eval = alphaBeta(newPiles, alpha, beta , false);
                        maxEval = Math.max(maxEval, eval);
                        alpha = Math.max(alpha, eval);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
             for (int i = 0; i < piles.size(); i++) {
            for (int j = 1; j <= piles.get(i); j++) {
                    if (isValidMove(piles, i, j)) {
                         List<Integer> newPiles = new ArrayList<>(piles);
                       newPiles.set(i, piles.get(i) - j);
                        int eval = alphaBeta(newPiles, alpha, beta , true);
                        minEval = Math.min(minEval, eval);
                        beta = Math.min(beta, eval);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return minEval;
        }
    }

   
    public static int evaluateState(List<Integer> heaps, boolean ismax) {
        if (ismax) { 
           return +1;
       } else { 
           return -1;
       }
      }
    public static boolean isValidMove(List<Integer> heaps, int heapIndex, int matchesToRemove) {
        if (heapIndex < 0 || heapIndex >= heaps.size() || matchesToRemove <= 0|| matchesToRemove>heaps.get(heapIndex)) {
            return false;
        }
        else{
        return true;
    }
    }
   

    public static List<Integer> divideSticks(int numberOfSticks) {
        List<Integer> result = new ArrayList<>();

        // Rule 1: Index 0 can only fit 1
        result.add(1);
        numberOfSticks--;
        // Check if numberOfSticks is zero
        if (numberOfSticks == 0) {
            return result;
        }

        // Rule 2: Index 1 can only fit 3 or less
        result.add(Math.min(3, numberOfSticks));
        numberOfSticks -= result.get(1);
        // Check if numberOfSticks is zero
        if (numberOfSticks == 0) {
            return result;
        }
        // Rule 3: Index 2 can only fit 5 or less
        result.add(Math.min(5, numberOfSticks));
        numberOfSticks -= result.get(2);
        // Check if numberOfSticks is zero
        if (numberOfSticks == 0) {
            return result;
        }

        // Rule 4: Index 3 can only fit 7 or less
        result.add(Math.min(7, numberOfSticks));
        numberOfSticks -= result.get(3);
        // Check if numberOfSticks is zero
        if (numberOfSticks == 0) {
            return result;
        }
        // Rule 5: Index 4 can only fit 9 or less
        result.add(Math.min(9, numberOfSticks));
        numberOfSticks -= result.get(4);

            return result;
        
    }
    
     public static int[] aiMoveEasy(List<Integer> heaps) {
        int heapIndex;
        int matchesToRemove;
        Random random = new Random(); 
        do {
            heapIndex = random.nextInt(heaps.size());
            matchesToRemove = random.nextInt(heaps.get(heapIndex)+1);
        } while (!isValidMove(heaps, heapIndex, matchesToRemove));

        return new int[]{heapIndex, matchesToRemove};
    }
     
     
     public static int[] aiMoveMedium(List<Integer> heaps) {
 
 int maxMatches = -1;
    int heapIndex = -1;
    int sticks=0;
    int index2=-1;
    // Find the heap with the max matches
    for (int i = 0; i < heaps.size(); i++) {
        if (heaps.get(i) > maxMatches) {
            maxMatches = heaps.get(i);
            heapIndex = i;
        }
        if(heaps.get(i)==2){
            sticks=heaps.get(i);
            index2=i;
        }
    }
if(sticks==2){
     Random random = new Random(); 
   int matchesToRemove= random.nextInt(3);
   while (!isValidMove(heaps, heapIndex, matchesToRemove)){
          matchesToRemove= random.nextInt(3);

       }
       return new int[]{index2, matchesToRemove};


}

else{  
    if(maxMatches>2){
            Random random = new Random(); 
        int maxToRemove=maxMatches-2;
     int matchesToRemove= random.nextInt(maxToRemove+1);
     while (!isValidMove(heaps, heapIndex, matchesToRemove)){
      matchesToRemove= random.nextInt(maxToRemove+1);
     }
       return new int[]{heapIndex, matchesToRemove};    
    }
    else{
    int matchesToRemove=maxMatches/2 ;
       while (!isValidMove(heaps, heapIndex, matchesToRemove)){
                matchesToRemove=matchesToRemove+1 ;
       }
       return new int[]{heapIndex, matchesToRemove};

}
     }
}
    }

