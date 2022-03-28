package de.shiirroo.islands.event.menu.menus.items;

import de.shiirroo.islands.event.menu.Menu;

import java.util.ArrayList;
import java.util.List;

public class ItemMenu {

    public List<Menu> menus = new ArrayList<>();

    public ItemMenu(Menu menu){
        this.menus.add(menu);
    }

    public void addMenu(Menu menu){
      this.menus.add(menu);

    }

    public List<Menu> getMenus() {
        return menus;
    }

    public Boolean hasMenus(){
        return this.menus.size() > 2;
    }

    public Menu getLastMenu(){
        return this.menus.get(menus.size() - 1);
    }

    public Menu getPopLastMenu(){
        Menu m = this.menus.get(menus.size() - 1);
        this.menus.remove(menus.size() - 1);
        return m;
    }



}
