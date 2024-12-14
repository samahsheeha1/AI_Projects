/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fruit;
public class ReLU implements ActivationFunction {

    @Override
    public double apply(double x) {
        return Math.max(0, x);
    }

    @Override
    public double derivative(double x) {
        return x > 0 ? 1 : 0;
    }
}