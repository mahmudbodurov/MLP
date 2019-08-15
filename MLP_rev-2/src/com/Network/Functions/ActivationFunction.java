/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Network.Functions;

/**
 *
 * @author Mahmud
 */
public interface ActivationFunction {

    double evulate(double value);

    public double evaluteDerivate(double value);
}

