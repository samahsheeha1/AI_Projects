/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fruit;

public class Tanh implements ActivationFunction {

    @Override
    public double apply(double x) {
        return (Math.exp(x) - Math.exp(-x)) / (Math.exp(x) + Math.exp(-x));
    }

    @Override
    public double derivative(double x) {
        return 1.0 - (apply(x) * apply(x));
    }
}
