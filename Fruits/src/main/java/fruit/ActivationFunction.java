/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fruit;

public interface ActivationFunction {
    double apply(double x);
    double derivative(double x);
}