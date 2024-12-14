/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nimgame;

import java.util.List;
import java.util.Random;


public class Nim1Methods {
    
        
    public static boolean isGameOver(List<Integer> heaps) {
        for (int i = 0; i < heaps.size(); i++) {
            int matches = heaps.get(i);
            if (matches >= 3 ) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidMove(List<Integer> heaps, int heapIndex, int matchesToRemove) {
        if (heapIndex < 0 || heapIndex >= heaps.size() || matchesToRemove <= 0) {
            return false;
        }

        int matchesInHeap = heaps.get(heapIndex);
        if (matchesInHeap < matchesToRemove || matchesInHeap - matchesToRemove == matchesToRemove || matchesToRemove==matchesInHeap) {
            return false;
        }

        return true;
    }

    // Remove matches from a heap
    public static void removeMatches(List<Integer> heaps, int heapIndex, int matchesToRemove) {
        int newMatches = heaps.get(heapIndex) - matchesToRemove;
        heaps.add(matchesToRemove);
        heaps.set(heapIndex, newMatches);
    }

    public static int[] aiMoveHard(List<Integer> heaps, int alpha, int beta) {
        int[] bestMove = new int[2];
        int maxEval = Integer.MIN_VALUE;

        for (int i = 0; i < heaps.size(); i++) {
            int matchesInHeap = heaps.get(i);

            for (int matchesToRemove = 1; matchesToRemove <= matchesInHeap-1; matchesToRemove++) {
                if (isValidMove(heaps, i, matchesToRemove)) {
                    removeMatches(heaps, i, matchesToRemove);
                    int eval = alphaBeta(heaps, alpha, beta,false);
                    addMatches(heaps, i, matchesToRemove);

                    if (eval > maxEval) {
                        maxEval = eval;
                        bestMove[0] = i;
                        bestMove[1] = matchesToRemove;
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

    public static int alphaBeta(List<Integer> heaps, int alpha, int beta,  boolean isMaximizing) {
        if (  isGameOver(heaps)) {
            return evaluateState(heaps,isMaximizing);
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < heaps.size(); i++) {
                int matchesInHeap = heaps.get(i);

                for (int matchesToRemove = 1; matchesToRemove <= matchesInHeap-1; matchesToRemove++) {
                    if (isValidMove(heaps, i, matchesToRemove)) {
                        removeMatches(heaps, i, matchesToRemove);
                        int eval = alphaBeta(heaps, alpha, beta , false);
                        addMatches(heaps, i, matchesToRemove);
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
            for (int i = 0; i < heaps.size(); i++) {
                int matchesInHeap = heaps.get(i);

                for (int matchesToRemove = 1; matchesToRemove <= matchesInHeap-1; matchesToRemove++) {
                    if (isValidMove(heaps, i, matchesToRemove)) {
                        removeMatches(heaps, i, matchesToRemove);
                        int eval = alphaBeta(heaps, alpha, beta , true);
                        addMatches(heaps, i, matchesToRemove);
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
           return -1;
       } else { 
           return 1;
        }
        
 
      }
       
        
       
   

    // Add matches back to a heap (undo move)
    public static void addMatches(List<Integer> heaps, int heapIndex, int matchesToAdd) {
        int newMatches = heaps.get(heapIndex) + matchesToAdd;
        heaps.set(heapIndex, newMatches);
       heaps.remove(heaps.size() - 1);
    }
     public static int[] aiMoveEasy(List<Integer> heaps) {
        int heapIndex;
        int matchesToRemove;
        Random random = new Random(); 
        do {
            heapIndex = random.nextInt(heaps.size());
            matchesToRemove = random.nextInt(heaps.get(heapIndex));
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
        if(heaps.get(i)==5){
            sticks=heaps.get(i);
            index2=i;
        }
    }
if(sticks==5){
     Random random = new Random(); 
   int matchesToRemove= random.nextInt(5);
   while (!isValidMove(heaps, heapIndex, matchesToRemove)){
          matchesToRemove= random.nextInt(5);

       }
       return new int[]{index2, matchesToRemove};
}

else{  
    if(maxMatches>5){
            Random random = new Random(); 
        int maxToRemove=maxMatches-5;
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

