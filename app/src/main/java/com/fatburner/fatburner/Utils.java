package com.fatburner.fatburner;

import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.fatburner.fatburner.GlobalVariables.PRODUCTS_COUNT;
import static com.fatburner.fatburner.GlobalVariables.PRODUCTS_PAGES_COUNT;
import static com.fatburner.fatburner.GlobalVariables.PRODUCTS;

/**
 * Created by sergeyteperchuk on 6/20/17.
 */

public class Utils {


    public static void applyShemaProductsList(String Shema){
        List<String> lettersArray = new ArrayList<String>();
        Map<String,Integer> productsCount = new ArrayMap<>();
        for(int i = 0; i < Shema.length(); i++){
            if(Character.isLetter(Shema.charAt(i)))
            {
                String l = "" + Shema.charAt(i);
                if(!l.equals("+"))
                {

                    int index = 0 + Character.getNumericValue(Shema.charAt(i-1));
                    lettersArray.add(l);
                    productsCount.put(l,index);
                }
            }
        }
        PRODUCTS_PAGES_COUNT = lettersArray.size();
        String products[] =  new String[lettersArray.size()];;
        products = lettersArray.toArray(products);
        PRODUCTS = products;
        PRODUCTS_COUNT = productsCount;
    }


    //Пшеничная каша      - 60гр  - 81Ккал

    public static ArrayList<ArrayList<String>> normalizeProductsList(String[] array)
    {
        ArrayList<String> productNames = new ArrayList<>();
        ArrayList<String> productWeights = new ArrayList<>();
        ArrayList<String> productCaloricitys = new ArrayList<>();

        ArrayList<ArrayList<String>> parsedproductsList = new ArrayList<ArrayList<String>>();

        for (int i=0; i< array.length; i++) {
            String arrayItem = array[i];

            arrayItem = arrayItem.trim();
            String productName = arrayItem.substring(0, arrayItem.indexOf("-"));
            arrayItem = arrayItem.replace(productName + "-", "");
            String productWeight = arrayItem.substring(0, arrayItem.indexOf("-"));
            arrayItem = arrayItem.replace(productName + "-", "");
            String productCaloricity = arrayItem.substring(0, arrayItem.indexOf("-"));

            productNames.add(productName);
            productWeights.add(productWeight);
            productCaloricitys.add(productCaloricity);
        }

        parsedproductsList.add(productNames);
        parsedproductsList.add(productWeights);
        parsedproductsList.add(productCaloricitys);

        return parsedproductsList;
    }

    public static String[] normalizeProductsList(Map<Integer, List<String>> listOfProducts){

        ArrayList<List<String>> list = new ArrayList<List<String>>();
        List<String> normalizedList = new ArrayList<String>();
        List<String> l = new ArrayList<String>();

        for(int i = 0; i< listOfProducts.size(); i++){
            list.add(listOfProducts.get(i));
        }

        for (int i = 0; i < list.size(); i++){
            l = list.get(i);

            for (String item : l) {
                if(!item.equals(" ")){
                    normalizedList.add(item);
                }
            }
        }

       return  normalizedList.toArray(new String[0]);

    }
}
