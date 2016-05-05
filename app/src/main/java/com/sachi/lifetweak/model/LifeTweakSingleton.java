package com.sachi.lifetweak.model;

import java.util.ArrayList;

/**
 * Created by jombay on 28/3/16.
 */
public class LifeTweakSingleton {

    private static LifeTweakSingleton instance;


    private ArrayList<LifeTweak> lifetweaks;

    public static LifeTweakSingleton getInstance() {
        if (instance == null) {
            instance = new LifeTweakSingleton();
        }
        return instance;
    }

    public ArrayList<LifeTweak> getLifeTweakArrayList() {
        return lifetweaks;
    }

    private LifeTweakSingleton() {
        lifetweaks = new ArrayList<>();
        lifetweaks.add(new LifeTweak(10, "http://i.kinja-img.com/gawker-media/image/upload/s--Y4dtVukZ--/1880kfyt9vya8jpg.jpg", "Keep bananas fresher by wrapping the stems in plastic wrap,Stop your bananas from browning too quickly with this genius trick. ", 0));
        lifetweaks.add(new LifeTweak(9, "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQO5sUixK2LUS8wl3URBIE7LwebIRZeSOH4yZ5O9fEfhTnx1LUE_w", "Put a dab of toothpaste on the frame where you need the nails to be. Press against the wall to leave marks (which can later be wiped) as guides for hammering in. ", 0));
        lifetweaks.add(new LifeTweak(8, "http://happiness-wave.com/wp-content/uploads/2015/11/phone-life-hack-water.jpg", "Water damaged phone? \n Put in a ziploc bag with rice for 24 hours. It will absorb the moisture and work again!", 0));
        lifetweaks.add(new LifeTweak(7, "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcR1rYfONAZ6bZiLgqqalajHTM_rQBSO-0Mjz2kVVlllltxsFsIIog", "Place a paper clip on the end of a roll of tape. This will save you a ton of time trying to find where your tape cuts off.", 0));
        lifetweaks.add(new LifeTweak(6, "http://i.imgur.com/dSVvfIH.jpg", "Always give your kids a choice that makes them think they are in control. For instance when you want them to put their shoes on, you can say: Do you want to put your Iron man or your bunny shoes on?", 0));
        lifetweaks.add(new LifeTweak(5, "http://i.imgur.com/4egMAMM.jpg", "Fuel gauge dashboards in many cars show an arrow indicating whether the fuel door is on the left or right side of the vehicle.", 0));

        lifetweaks.add(new LifeTweak(4, "http://i.imgur.com/3BjCiTel.jpg", "A strong sense of humor is generally associated with intelligence and honesty. This is why most women are attracted to men who possess a strong sense of humor. ", 0));
        lifetweaks.add(new LifeTweak(3, "http://i.imgur.com/R9ZKHDol.jpg", "Strawberries and cashews are the only fruits that have their seeds on the outside unlike all other fruits which have their seeds inside. ", 0));
        lifetweaks.add(new LifeTweak(2, "http://i.imgur.com/BibbYdcl.jpg", "Nomophobia is the fear of being without your cellphone or losing your signal.  ", 0));
        lifetweaks.add(new LifeTweak(1, "http://i.imgur.com/BbgTAE7l.jpg", "Human brain can live for 4 to 6 minutes without oxygen, and then it begins to die. No oxygen for 5 to 10 minutes will result in permanent brain damage. ", 0));
        lifetweaks.add(new LifeTweak(0, "http://i.imgur.com/x5C7TXSl.jpg", "Indian housewives hold 11% of the world's gold. That is more than the reserves of USA, IMF, Switzerland and Germany put together. ", 0));


    }
}
