/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fruit;

    public class Sigmoid implements ActivationFunction {

    @Override
    public double apply(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    @Override
    public double derivative(double x) {
        double sigmoidValue = apply(x);
        return sigmoidValue * (1 - sigmoidValue);
    }
}