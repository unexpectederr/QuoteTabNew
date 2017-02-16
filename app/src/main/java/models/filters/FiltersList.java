package models.filters;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Spaja on 16-Feb-17.
 */

public class FiltersList {

    private Map<String, String> filtersList = new LinkedHashMap<>();


    public FiltersList() {
        filtersList.put("Sepia", "jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation");
        filtersList.put("Toon", "jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation");
        filtersList.put("Kuwahara", "jp.wasabeef.glide.transformations.gpu.KuwaharaFilterTransformation");
        filtersList.put("Grayscale", "jp.wasabeef.glide.transformations.GrayscaleTransformation");
        filtersList.put("Invert", "jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation");
    }

    public Map<String, String> getFiltersList() {
        return filtersList;
    }
}
