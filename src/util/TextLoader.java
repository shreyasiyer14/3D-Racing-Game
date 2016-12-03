/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Loading text files as assets.
 * Created on: 8/11/2016
 */
package util;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 */
//This is a class suplementing the AssetLoader class, which is used to load a text file as an asset in the engine.
public class TextLoader implements AssetLoader {
    @Override
    public Object load(AssetInfo assetInfo) throws IOException {
        Scanner scan = new Scanner(assetInfo.openStream());
        StringBuilder sb = new StringBuilder();

        try {
            while (scan.hasNextLine()) {
                sb.append(scan.nextLine()).append(" ");
            }
        } finally {
            scan.close();
        }
        return sb.toString();
    }
}
