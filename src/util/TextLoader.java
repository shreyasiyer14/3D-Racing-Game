/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Ferrari car description
 * Created on: 8/11/2016
 */
package util;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author EOF-1
 */
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
