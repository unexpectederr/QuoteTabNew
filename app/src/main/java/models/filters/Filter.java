package models.filters;

/**
 * Created by Spaja on 14-Feb-17.
 */

public class Filter {

    private String filterName;
    private String filterClass;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getFilterClass() {
        return filterClass;
    }

    public void setFilterClass(String filterClass) {
        this.filterClass = filterClass;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String effectName) {
        this.filterName = effectName;
    }

}
