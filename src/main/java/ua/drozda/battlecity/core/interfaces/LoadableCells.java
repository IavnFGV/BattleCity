package ua.drozda.battlecity.core.interfaces;


import ua.drozda.battlecity.core.TileUnit;

/**
 * Created by GFH on 17.05.2015.
 */
public interface LoadableCells {
    public Boolean addCell(Integer x, Integer y, TileUnit.TileType tileType);
}
