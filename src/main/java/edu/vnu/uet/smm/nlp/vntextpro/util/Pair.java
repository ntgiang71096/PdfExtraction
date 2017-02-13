/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vnu.uet.smm.nlp.vntextpro.util;

/**
 *
 * @author hieupx
 */
public class Pair<F, S> {
    public F first;
    public S second;
    
    public Pair(F f, S s) {
        this.first = f;
        this.second = s;
    }
}
