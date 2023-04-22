/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.workshop.tests;

import com.esprit.workshop.services.ServiceCours;
import com.esprit.workshop.utils.MyConnexion;

public class MainClass {
    
    public static void main(String[] args) {
        MyConnexion cn1 = MyConnexion.getInstance();
        MyConnexion cn2 = MyConnexion.getInstance();
        MyConnexion cn3 = MyConnexion.getInstance();
        MyConnexion cn4 = MyConnexion.getInstance();
        MyConnexion cn5 = MyConnexion.getInstance();
        
        System.out.println(cn1.hashCode());
        System.out.println(cn2.hashCode());
        System.out.println(cn3.hashCode());
        System.out.println(cn4.hashCode());
        System.out.println(cn5.hashCode());
        
        ServiceCours sp = new ServiceCours();
        /*
        Cours c = new Cours(20, 1 ,24,1, "league course", "league course description", "league vid","league img", "debutant");

        try {
            sp.ajouterCours(c);

            System.out.println(sp.afficherCours());

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
*/

    }
    
}
