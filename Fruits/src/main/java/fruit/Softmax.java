/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fruit;

public class Softmax implements ActivationFunction {

    @Override
    public double apply(double x) {
        double expX = Math.exp(x);
        return expX / (expX + 1);
    }

    @Override
    public double derivative(double x) {
        return apply(x) * (1 - apply(x));
    }
}